Spring Boot Coordinate Services App.
======= 

## Quick Start

### prerequisites

- Java 8: https://openjdk.java.net/install/
- Maven 3+: https://maven.apache.org/download.cgi
- Docker CE 18+ (with docker-compose): https://www.docker.com/get-started

---

There are two ways to start the application, `local mode` and `prod mode`. For both modes, it will run on port `8888` (`http://localhost:8888`).

### Run with local mode

For local mode, we use docker to create the database:

    docker-compose up
    
Once the docker container is started, you need to run the Flyway database migrations:

    ./dev-migration.sh
Or run MigrationApplication through Intellij.

    
After migration is done, you can start BackendApplication through Intellij. 


### Run with prod mode

For prod mode, all components are running under docker. You can start the app from scratch with:

    ./build-and-run.sh
    
It will remove all containers, rebuild all components and start them from scratch.

##  API

Application supports two endpoints:
- `/distance` GET http request: This endpoint requests two parameters `postCode_1` and `postCode_2` (e.g. `localhost:8888/distance?postCode_1=6651EH&postCode_2=1189WK`). \
It will return a JSON object containing information of those two post codes and the distance between them. Following is an example resposne: 
<pre><code>
{
    "location_1": {
        "id": 1,
        "postCode": "6651EH",
        "latitude": 51.88760463,
        "longitude": 5.597723367
    },
    "location_2": {
        "id": 2,
        "postCode": "1189WK",
        "latitude": 52.25902055,
        "longitude": 4.869899155
    },
    "distance": 64.65333475903798,
    "unit": "km"
}
</code></pre>

- `/update` POST http request: This request body should be a JSON object containing `postCode`, `latitude` and `longitude`. This call will update the coordinates of a specified post code with the value in the request body. Following is an example request body and response:
<pre><code>
Request: 
{
	"postCode": "8321AK",
	"latitude": 51.57768705,
	"longitude":5.198987949
}

Response:
{
    "message": "coordinates are successfully updated",
    "postCode": "8321AK",
    "latitude": 51.57768705,
    "longitude": 5.198987949
}
</code></pre>

### NOTE

- Since docker version 3 doesn't support condition of depends_on, migration and backend will start after mysql container is started, which means it wil not wait for mysql to be ready. That's why when you run prod mode, you will see some Spring boot throws exceptions during startup. But <b>don't worry</b>, docker will restart them until it's OK.
- The flyway migration in this application is to import data to DB, so it doesn't support backup, restore etc. If you encounter some errors during migration. You have to throw the database away and start it from scratch again.
