package com.krakenflex.test.service;

import com.krakenflex.test.exceptions.KrakenFetchException;
import com.krakenflex.test.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Handles KrakenFlex test service REST API.
 */
@Service
public class KrakenFlexTestService {

    private static final String BASE_URL = "https://api.krakenflex.systems/interview-tests-mock-api/v1/";
    private static final CloseableHttpClient httpClient = HttpClients.createDefault();
    public static final int REQUEST_TIMEOUT_SECS = 30;
    public static final int TOO_MANY_REQUESTS_SLEEP_TIME_MILLI_SECS = 1000;
    private final Logger log = LoggerFactory.getLogger(KrakenFlexTestService.class);

    @Autowired
    public KrakenFlexTestService() {}

    public <T> T fetchEndpoint(String endPoint, TypeReference<T> tr) {
        try {
            RequestBuilder request = RequestBuilder.get();
            Optional<String> response = callEndpointWithErrorHandling(request, endPoint);

            if (response.isEmpty()) {
                throw new KrakenFetchException(endPoint);
            }
            return JsonUtil.MAPPER.readValue(response.get(), tr);
        } catch (JsonProcessingException e) {
            throw new KrakenFetchException(endPoint, e);
        }
    }

    public void postEndpoint(String endpoint, String body) {
        try {
            StringEntity requestEntity = new StringEntity(body, ContentType.APPLICATION_JSON);

            RequestBuilder request = RequestBuilder.post()
                    .setEntity(requestEntity);

            callEndpointWithErrorHandling(request, endpoint);
        } catch (UnsupportedCharsetException e) {
            throw new KrakenFetchException(endpoint, e);
        }
    }

    private Optional<String> callEndpointWithErrorHandling(RequestBuilder requestBuilder, String endpoint) {
        String apiKey = getApiKey();
        HttpUriRequest request = requestBuilder.setHeader("x-api-key", apiKey).setUri(BASE_URL + endpoint).build();
        long start = System.nanoTime();
        int sleepTimeMilliSecs = 10;

        while (TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - start) <= REQUEST_TIMEOUT_SECS) {
            log.info("About to call endpoint: {}", endpoint);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                log.info("Endpoint {} returned status code {}", endpoint, statusCode);
                switch (statusCode) {
                    case HttpStatus.SC_OK -> {
                        HttpEntity entity = response.getEntity();
                        if (entity == null) {
                            return Optional.empty();
                        }
                        return Optional.ofNullable(new BufferedReader(new InputStreamReader(entity.getContent())).readLine());
                    }
                    case HttpStatus.SC_INTERNAL_SERVER_ERROR |
                            HttpStatus.SC_REQUEST_TIMEOUT -> {
                        Thread.sleep(sleepTimeMilliSecs);
                        sleepTimeMilliSecs *= 2;
                    }
                    case HttpStatus.SC_TOO_MANY_REQUESTS -> {
                        Thread.sleep(TOO_MANY_REQUESTS_SLEEP_TIME_MILLI_SECS + sleepTimeMilliSecs);
                        sleepTimeMilliSecs *= 2;
                    }
                    default -> throw new KrakenFetchException(endpoint);
                }
            } catch (InterruptedException | IOException e) {
                throw new KrakenFetchException(endpoint, e);
            }
        }
        throw new KrakenFetchException(endpoint);
    }

    private String getApiKey() {
        try {
            return new BufferedReader(new InputStreamReader(Objects.requireNonNull(KrakenFlexTestService.class.getClassLoader().getResourceAsStream("api.key")))).readLine().trim();
        } catch (Throwable t) {
            log.error("You must create an src/main/resources/api.key file, containing valid api key");
            System.exit(-1);
        }
        return null;
    }
}
