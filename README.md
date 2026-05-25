# Portfolio Manager

Portfolio Manager is a Spring Boot application for managing investment portfolios, transactions, and calculating gains/losses based on updated stock prices.
It is made for Danish and American stocks, with offline lists available for both markets.

A default user is created for testing:
```text
Username: user
Password: ourpassword
```

## Requirements

- Java 17 or newer
- Maven
- MySQL
- Marketstack API key

## Database

Make sure MySQL is installed and running on the default port `3306`.

Create a database named:

```sql
CREATE DATABASE `portfolio-manager`;
```

## Environment variables

Create a `.env` file in the project root with the following variables:

```env
STOCK_API_TOKEN=YOUR_MARKETSTACK_API_KEY
STOCK_API_BASE_URL=https://api.marketstack.com/v2
SPRING_DATASOURCE_USERNAME=YOUR_MYSQL_USERNAME
SPRING_DATASOURCE_PASSWORD=YOUR_MYSQL_PASSWORD
```

## Running the application

From the project root, run:
```
./mvnw spring-boot:run
```

On Windows:
```
mvnw spring-boot:run
```