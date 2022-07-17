


API key - add file name with api key



There are three endpoints in this file:

1. `GET /outages` which returns all outages in our system
2. `GET /site-info/{siteId}` which returns specific information about a site
3. `POST /site-outages/{siteId}` which expects outages for a specific site to be posted to it

Your task is to write a small program that:

~~1. Retrieves all outages from the `GET /outages` endpoint~~
~~2. Retrieves information from the `GET /site-info/{siteId}` endpoint for the site with the ID `norwich-pear-tree`~~
~~3. Filters out any outages that began before `2022-01-01T00:00:00.000Z` or don't have an ID that is in the list of~~
   ~~devices in the site information~~
~~4. For the remaining outages, it should attach the display name of the device in the site information to each appropriate outage~~
~~5. Sends this list of outages to `POST /site-outages/{siteId}` for the site with the ID `norwich-pear-tree`~~



Improvements:

getOutagesForSite currently returns outages from the future eg:
   {
      "id": "75e96db4-bba2-4035-8f43-df2cbd3da859",
      "begin": "2023-05-11T14:35:15.359Z",
      "end": "2023-12-27T11:19:19.393Z"
   }