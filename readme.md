# BusinessEngagements

### Running as Spring Boot service

If you want to run the service without using docker, start a wiremock on port 9090
from the path `src/test/resources/wiremock .` Notice the trailing "dot".

`java -jar path-to-wiremock/wiremock.jar --port=9090 .`

### Stubbings with Wiremock

All stubs are taken directly from Bolagsverkets "static" test environment where the responses are
based on which personal number is provided.
For now only two requests are created to work with the service and wiremock<br>

- 198000000001
- 198001011234

The other ones are there in case we need to test further.

## Status

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-business-engagements&metric=alert_status)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-business-engagements)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-business-engagements&metric=reliability_rating)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-business-engagements)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-business-engagements&metric=security_rating)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-business-engagements)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-business-engagements&metric=sqale_rating)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-business-engagements)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-business-engagements&metric=vulnerabilities)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-business-engagements)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-business-engagements&metric=bugs)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-business-engagements)

## 

Copyright (c) 2023 Sundsvalls kommun
