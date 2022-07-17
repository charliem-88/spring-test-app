# KrakenFlex Test Application (Charlotte Metcalfe)

## Setup and running
Create a file in src/main/resources called api.key, containing a valid API key. 

To run the project, you can either open the project in an IDE like IntelliJ and hit run, or run:
```
./gradlew run
```

which will install all dependencies (except Java) and run the application.
It runs a Spring web application on port 8080.

Java 17 is required and can be installed via https://sdkman.io/.

## Tests
Execute the tests with:
```
./gradlew test
```

## Endpoints
I've created four endpoints, which can be run using the curl commands:

   1. `curl localhost:8080/api/outages`  which can be used to return a full list of outages. 
   2. `curl localhost:8080/api/site-info/{id}` which will return site information for a given ID.
   3. `curl localhost:8080/api/site-outages/{id}` returns a list of outages associated with the site, that have a begin date on or after 2022-01-01.
   4. `curl localhost:8080/api/upload-outages/{id}` uploads a list of outages for a given site, that have a begin date on or after 2022-01-01.

Endpoint #4 _should_ complete the task, however a 400 error is returned from the Kraken endpoint, indicating the payload is invalid.

## UI
There is an optional web based UI available here:  

https://github.com/charliem-88/react-app

## Discussion
After considerable debugging efforts I haven't been able to ascertain why the 400 is returned. 
The filtering in its current form can be found in `SiteOutagesService`.
Some other approaches tried included:
- Manually calculating the outages and comparing them.
- Filtering out an outage that occurred exactly on the cutoff date.
- Filtering out outages that extended into the future.
- Filtering out outages that spanned the cutoff date.
- Sorting by the device order returned from site-info, with a secondary sort on the outage's start time.
- Sorting by the outage's start time.
