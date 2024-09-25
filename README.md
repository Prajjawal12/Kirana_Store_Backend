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
- **Caching**: Cached responses from the currency conversion API to avoid hitting external api limit.

### Coding Guidelines Followed
- **Framework**: Used Java, Spring Boot, and MongoDB for development.
- **Code Quality**: Followed industry standards, SOLID principles, and eliminated dead code.
- **Maintainability**: Focused on readability, descriptive naming, and logging.
- **Testing**: Included unit tests for better code reliability.
- **Documentation**: Ensured comprehensive API documentation using javadoc and code comments.

## Documentation and Postman Collection

- **Postman Collection**: You can find the Postman collection for testing the APIs [here](./postman_collection.json).
- **API Documentation**: Detailed documentation explaining the working of the APIs is available [here](./Kirana_Store_Backend.docx).
- **Javadoc Documentation**: Extensive code documentation using Javadoc is available at [target/classes/site/apidocs](./target/classes/site/apidocs).



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

- To test the APIs, refer to the following resources:

- **Postman Collection**: Test the APIs using the Postman collection available [here](./postman_collection.json).
- **API Documentation**: Detailed API documentation is provided in [Kirana_Store_Backend.docx](./Kirana_Store_Backend.docx).
- **Javadoc Documentation**: Extensive code documentation is available at [target/classes/site/apidocs](./target/classes/site/apidocs).








