# quarkus quickstart with IRIS

```sh
docker-compose up
```

## quickstart with CRUD on people

POST
```sh
curl --location --request POST 'http://localhost:8080/people/' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name" : "toto"
}'
```
GET
```sh
curl --location --request GET 'http://localhost:8080/people/'
```

## quickstart with ORM

POST
```sh
curl --location --request POST 'http://localhost:8081/fruits/' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name" : "test"
}'
```
GET
```sh
curl --location --request GET 'http://localhost:8081/fruits/'
```