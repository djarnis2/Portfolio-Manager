# Portfolio Manager

Portfolio Manager is a Spring Boot application for managing investment portfolios, transactions, and calculating gains/losses based on updated stock prices.
It is made for Danish and American stocks, with offline lists available for both markets.

A default user is created for testing:
```text
Username: user
Password: ourpassword
```

## Requirements

- Docker Desktop
- Marketstack API key


## Clone repo
```
git clone git@github.com:djarnis2/Portfolio-Manager.git
```

## Environment variables

Create a `.env` file in the project root with the following variables:

```env
STOCK_API_TOKEN=your_marketstack_api_key
STOCK_API_BASE_URL=https://api.marketstack.com/v2
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=your_mysql_password
MYSQL_ROOT_PASSWORD=your_mysql_password
```

## Building the application

From the project root, run:
```shell
docker compose up --build
```

## On following launches

From the project root, run:
```shell
docker compose up
```
## The application will run on 
```
http://localhost:8080
```

## Stop the application
````shell
docker compose down
````

##  Delete database
````shell
docker compose down -v
````