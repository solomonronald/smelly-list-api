# Smelly List API - App

Java - Spring Boot app for Smelly List HTTP API endpoints.


## Installation

#### Setup Database
Follow the steps [here](./../smelly-list-sql/README.md) to setup database & tables required for the app to run.

#### Command Line
Run the maven wrapper with following command in a terminal window:
``` sh
./mvnw spring-boot:run
```

#### IntelliJ IDEA
- Open IntelliJ IDEA, File > Open
- Browse to the [folder](./) where [pom.xml](./pom.xml) is located, and open
- Run the app from [ApiApplication.java](./src/main/java/com/smellylist/api/ApiApplication.java)

Verify if the app is deployed by opening [http://localhost:8080/api/marco](http://localhost:8080/api/marco) in your web browser.
If the service is deployed successfully, you will get the following JSON response:
```json
{"message":"Polo"}
```

## Swagger UI
When in `dev` profile, you can access Swagger UI by opening [http://localhost:8080/api/swagger-ui/index.html](http://localhost:8080/api/swagger-ui/index.html) in your browser.


## Authorization
Apart from public endpoints mentioned in [publicUrls](./src/main/java/com/smellylist/api/security/WebSecurityConfig.java) variable, you'll need an authorization token to access all other API endpoints.

To add an authorization token in your API requests, add `Authorization` header in your API calls with the following string:
```
Bearer <your access token>
```

Some tokens are generated in `dev` profile when you run your app. These tokens can be copied from your **log output**. Three access tokens are generated for all three roles (`Admin`, `Mod` and `User`).

Access token will be available in logs with text `<Role> Access Token:`

Example log:
```log
<TIMESTAMP>  INFO 26921 --- [  restartedMain] c.s.api.misc.ApplicationStartup          : Admin Access Token:
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QWRtaW4xIiwiciI6ImFtdSIsImlzcyI6IlNtZWxseUxpc3QiLCJleHAiOjE2MDk2NzA4NDh9.oSTJbrICl871nmrghuDxZ9s36hAyVOdp1tlfNylOsmQ
```

Copy the access token and use it to make protected api calls using any client like [Postman](https://www.postman.com/), [Insomnia](https://insomnia.rest/), [cURL](https://curl.se/), etc.

Below is a sample curl request to access protected endpoint:
```
curl --request GET \
  --url http://localhost:8080/api/psst \
  --header 'authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QWRtaW4xIiwiciI6ImFtdSIsImlzcyI6IlNtZWxseUxpc3QiLCJleHAiOjE2MDk2NzA4NDh9.oSTJbrICl871nmrghuDxZ9s36hAyVOdp1tlfNylOsmQ'
```


## Notes
If your 8080 port is not available, you can configure your app to use some other port by modifying `server.port` in [application.yml](./src/main/resources/application.yml)
