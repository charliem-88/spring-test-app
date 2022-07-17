
Create a file inn src/main/resources called api.key, containing a valid API key 

To run the project, you can either open the project in an IDE like IntelliJ and hit run, or run 

```
./gradlew run
```

which will install all dependencies (except Java) and run the application.

Java 17 is required and can be installed via https://sdkman.io/.

I've created for endpoints, which can be ran using the curl commands:
   1. `curl localhost:8080/api/outages`  which can be used to return a full list of outages 
   2. `curl localhost:8080/api/site-info/{id}` which will return site information for give ID
   3. `curl localhost:8080/api/site-outages/{id}` returns a list of outages associated with the site, that have a begin date on or after 2022-01-01
   4. `curl localhost:8080/api/upload-outages/{id}` uploads a list of outages for a given site, that have a begin date on or after 2022-01-01

Endpoint #4 should complete the task, however a 400 error is returned from the Kraken endpoint, indicating the payload is invalid.

After considerable de-bugging efforts I haven't been able to ascertain why the 400 is returned. 
The filtering in its current form can be found in SiteOutagesService. 

I tried a few approaches, as I perhaps had misunderstood the challenge. I filtered out all dates prior to the date provided. 
There is an outage for norwich-pear-tree that occurred at the given time, which I tried filtering out:
```
   {
      "id": "111183e7-fb90-436b-9951-63392b36bdd2",
      "name": "Battery 1",
      "begin": "2022-01-01T00:00:00Z",
      "end": "2022-09-15T19:45:10.341Z"
   }
```

I also tried ordering by name and date, and removing outages where the end date had not passed. 

