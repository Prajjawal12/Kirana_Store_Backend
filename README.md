# Kirana Store Backend

The Kirana Store Register backend facilitates the management of transaction registers for small businesses. It allows tracking of credit and debit transactions across multiple currencies with financial reports, user authentication, API rate limiting, and caching features.



 

## Tech Stack
- **Java**: Enables strong type safety and clean code.
- **Spring Boot**: Simplifies API development with built-in features for RESTful services and dependency management.
- **MongoDB**: Provides a flexible schema to store diverse transaction data without constraints.
- **JUnit**: Supports unit testing to ensure code reliability.
- **Bucket4j**: Implements rate limiting to prevent API abuse.

#### Functional Requirements
- **Transaction API**: Recorded transactions in multiple currencies using an external currency conversion API.
- **Reporting API**: Generated weekly, monthly, and yearly financial reports.
- **User Authentication and Auhtorization**: Secure access with distinct user roles (read-write, read-only).
- **API Rate Limiting**: Implemented rate limiting to control request rates using Bucket4j.
- **Caching**: Cached responses from the currency conversion API to reduce hitting api limit.

### Coding Guidelines Followed
- **Framework**: Used Java, Spring Boot, and MongoDB for development.
- **Code Quality**: Followed industry standards, SOLID principles, and eliminated dead code.
- **Maintainability**: Focused on readability, descriptive naming, and logging.
- **Testing**: Included unit tests for better code reliability.
- **Documentation**: Ensured comprehensive API documentation using javadoc and code comments.

## Documentation and Postman Collection

- **Postman Collection**: You can find the Postman collection for testing the APIs [here](./postman_collection.json).
- **API Documentation**: Detailed documentation explaining the working of the APIs is available [here](./Assignment_Documentation.docx).
- **Javadoc Documentation**: Extensive code documentation using Javadoc is available at [target/classes/site/apidocs](./target/site/apidocs).



# Kirana Store Backend Setup

Follow the steps below to set up and run the Kirana Store Backend locally.

## Prerequisites

Make sure you have the following installed on your system:
- **Java 17** (or higher)
- **Maven 3.6+**
- **MongoDB** (Ensure MongoDB is up and running)
- **Git**

## Clone the Repository

```bash
git clone https://github.com/Prajjawal12/Kirana_Store_Backend.git
cd Kirana_Store_Backend
```
## Build The Project
```
mvn clean install

```
## Run the Application

```
mvn spring-boot:run

 ```
The application should now be running on http://localhost:8080


## Environment Configuration
Make sure you configure MongoDB connection details in application.properties.

```
spring.data.mongodb.uri=mongodb://localhost:27017/kirana_store_db
```

## Testing The Endpoints
### Authentication API
### POST `/api/auth/register`
To register using a role
``` 

curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
        "username": "exampleuser",
        "password": "testpassword",
        "role": "ROLE_READ_WRITE"
      }'

```

### POST `/api/auth/login`
To log in and receive a JWT token:

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
        "username": "exampleuser",
        "password": "testpassword"
      }'
```

### Response will contain a JWT token

```
{
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJleGFtcGxldXNlciIsImlhdCI6MTcyNzE4MzE2NiwiZXhwIjoxNzI3MjY5NTY2LCJhdXRob3JpdGllcyI6IlJPTEVfUkVBRF9XUklURSJ9.3MRJ38W_tc2N1GpN6bGwnB1BQivhGCJ9btCfQtXutcguUDr1bcUOoQNP6ua5o3OMcFJVnCVMmYBxnkxYQB78YQ"
}

```
### Transaction API

#### POST `/api/transactions`
To record a financial transaction (credit or debit),only available for those with "ROLE_READ_WRITE" authorization,for "ROLE_READ" only are not allowed to post transactions.:

```bash
curl -X POST http://localhost:8080/api/transactions \
  -H "Authorization: Bearer <JWT>" \
  -H "Content-Type: application/json" \
  -d '{
        "amount": 500,
        "originalCurrency": "USD",
        "targetCurrency": "INR",
        "transactionType": "debit"
      }'
```
## Weekly Report API

### GET `/api/reports/weekly`
To fetch the weekly report:

```bash
curl -X GET "http://localhost:8080/api/reports/weekly?currency=USD" \
  -H "Authorization: Bearer <JWT>"
```
## Monthly Report API

### GET `/api/reports/monthly`
To fetch the monthly report:

```bash
curl -X GET "http://localhost:8080/api/reports/monthly?currency=USD" \
  -H "Authorization: Bearer <JWT>"
```

## Yearly Report API

### GET `/api/reports/yearly`
To fetch the monthly report:

```bash
curl -X GET "http://localhost:8080/api/reports/yearly?currency=USD" \
  -H "Authorization: Bearer <JWT>"
```
## User Registration API

### POST `/api/auth/register`
To register a new user:

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
        "username": "exampleuser",
        "password": "testpassword",
        "role": "ROLE_READ_WRITE"
      }'
```






## Contact Information

If you have any questions, suggestions, or issues, feel free to reach out:

- **Name**: Prajjawal Deep Yadav
- **Email**: [deepyadavprajjawal@gmail.com](mailto:deepyadavprajjawal@gmail.com)
- **GitHub**: [Prajjawal12](https://github.com/Prajjawal12)
- **LinkedIn**: [Prajjawal Deep Yadav](https://www.linkedin.com/in/prajjawal-deep-yadav/)






