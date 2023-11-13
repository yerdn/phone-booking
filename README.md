# Phone booking API

It is a REST API implemented as a Spring Boot service.

Open API spec is in `src/main/resources/api-contract.yml`

For messaging, it uses embedded ActiveMQ.
Service has Jms Listener which logs messages received in the queue to the console.

## How to build

```
mvn clean package
```

## How to run

It can be run in idea (main class PhoneBookingApiApplication) or as executable jar

```
java -jar target/phone-booking-api-0.0.1-SNAPSHOT.jar
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