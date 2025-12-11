# Business engagements

_The service is a proxy for interacting with bolagsverkets ssbten and ssbtgu API's. It allows passing party id's that are translated to legal id's before interacting with the external API's_

## Getting Started

### Prerequisites

- **Java 25 or higher**
- **Maven**
- **Git**

### Installation

1. **Clone the repository:**

```bash
git clone https://github.com/Sundsvallskommun/api-service-business-engagements.git
cd api-service-business-engagements
```

2. **Configure the application:**

   Before running the application, you need to set up configuration settings.
   See [Configuration](#configuration)

   **Note:** Ensure all required configurations are set; otherwise, the application may fail to start.

3. **Ensure dependent services are running:**

   *Party*

   - Purpose: Used for translating between party id and legal id.
   - Repository: https://github.com/Sundsvallskommun/api-service-party
   - Setup Instructions: See documentation in repository above for installation and configuration steps.

   *Bolagsverket*

   - Setup Instructions: Ensure you have the necessary credentials and configuration for ssbten & ssbtgu.
4. **Build and run the application:**

- Using Maven:

```bash
mvn spring-boot:run
```

- Using Gradle:

```bash
gradle bootRun
```

## API Documentation

Access the API documentation via:

- **Swagger UI:** [http://localhost:8080/api-docs](http://localhost:8080/api-docs)

## Usage

### API Endpoints

See the [API Documentation](#api-documentation) for detailed information on available endpoints.

### Example Request

```bash
curl -X 'GET' 'https://localhost:8080/2281/engagements/f7a1dd2e-d8ea-4803-992a-8f7cf87b2d73?personalName=John%serviceName=Mina%20Sidor

```

## Configuration

Configuration is crucial for the application to run successfully. Ensure all necessary settings are configured in
`application.yml`.

### Key Configuration Parameters

- **Server Port:**

```yaml
server:
  port: 8080
```

- **Integration Settings:**

```yaml
integration:
  party:
    oauth2-token-url: <party-token-uri>
    oauth2-client-id: <party-client-id>
    oauth2-client-secret: <party-client-secret>
    url: <party-url>
  bolagsverket:
    ssbten-api-url: <ssbten-api-url>
    ssbtgu-api-url: <ssbtgu-api-url>
    should-verify-transactionid: <true|false>
    should-use-keystore: <true|false>
    key-store-as-base64: <base64-encoded-keystore>
    keystore-password: <keystore-password>
```

### Additional Notes

- **Application Profiles:**

  Use Spring profiles (`dev`, `prod`, etc.) to manage different configurations for different environments.

- **Logging Configuration:**

  Adjust logging levels if necessary.

## Contributing

Contributions are welcome! Please
see [CONTRIBUTING.md](https://github.com/Sundsvallskommun/.github/blob/main/.github/CONTRIBUTING.md) for guidelines.

## License

This project is licensed under the [MIT License](LICENSE).

## Status

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-business-engagements&metric=alert_status)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-business-engagements)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-business-engagements&metric=reliability_rating)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-business-engagements)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-business-engagements&metric=security_rating)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-business-engagements)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-business-engagements&metric=sqale_rating)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-business-engagements)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-business-engagements&metric=vulnerabilities)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-business-engagements)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-business-engagements&metric=bugs)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-business-engagements)

## 

&copy; 2024 Sundsvalls kommun
