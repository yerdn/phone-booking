# Phone booking API

It is a REST API implemented as a Spring Boot service.

Open API spec is in `src/main/resources/api-contract.yml`

For messaging, it uses embedded ActiveMQ.
Service has Jms Listener which logs messages received in the queue to the console.

## How to build

```
./mvnnw clean package
```

## How to run

It requires PostreSQL DB and ActiveMQ. Docker compose file is provided to run them in docker:
```
docker-compose up -d
```


It can be run in idea (main class PhoneBookingApiApplication) or as executable jar

```
java -jar target/phone-booking-api-0.0.1-SNAPSHOT.jar
```

Project also has Dockerfile, so you can build docker image and run it
```
docker build -t phone-booking .
```
Run docker image and specify the `network` to the same network as in `docker-compose.yaml` file.

You also need to pass `DB_HOST` and `ACTIVE_MQ_HOST` environment variables 
with the values matching container names from `docker-compose.yaml` file. 
```
docker run -p 8080:8080 --network phone-booking-network -e DB_HOST='postgres' -e ACTIVE_MQ_HOST='activemq' phone-booking
```

It will run Tomcat on http://localhost:8080

## Example requests

### GET all phones

```
GET http://localhost:8080/api/v1/phones
```

### Book phones

```
POST http://localhost:8080/api/v1/phones/book
```

```json
{
  "phones": [
    "e34e5fb6-8150-11ee-b962-0242ac120002",
    "6a858eb8-3031-4c61-8f75-0bed3475258e"
  ],
  "bookedBy": "David Jones"
}
```

### Return phones

```
POST http://localhost:8080/api/v1/phones/return
```

```json
{
  "phones": [
    "e34e5fb6-8150-11ee-b962-0242ac120002"
  ],
  "bookedBy": "David Jones"
}
```

### REFLECTION:
• What aspect of this exercise did you find most interesting?

Most interesting was creating OpenAPI spec and shaping out REST API

• What did you find most cumbersome?

Setting up initial project structure and writing Unit Tests