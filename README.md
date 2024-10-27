<div align="center">

# WJH Project
**`This repository contains the backend logic only`**

<img src="https://github.com/nguyenthanhhy0108/Web-Personal-Project-BE/blob/main/github-image/wjh-logo.png?raw=true" alt="Project Logo" width="300"/>

</div>

Welcome to the **WJH Project**: a comprehensive car garage platform that allows customers to deposit vehicles, purchase parts, estimate car values, and access crash detection services.

<p></p>

---

## Table of Contents

- [Getting Started](#getting-started)
- [Architecture](#architecture)
- [Installation](#installation)
- [Usage](#usage)
- [API](#api)
- [Application Code](#application-code)
- [Technology](#technology-stack)
- [Functionality](#functionality)
- [Data](#data)
- [Author](#author)

---

## Getting Started

Follow this guide to set up the project on your local machine for development and testing purposes.

### Prerequisites

This repository is built on Java 17. Ensure you have the following installed:

- [Java](https://www.java.com/en/) (we use `Java 21.0.2 2024-01-16 LTS`)
- [Docker](https://www.docker.com/) (for containerization)
- [Git](https://git-scm.com/) (for version control)
- [MySQL](https://www.mysql.com/) (for database; **for Windows users, MySQL Workbench CE is essential**)
- [MongoDB](https://www.mongodb.com/) (for database; we recommend installing `MongoDB Compass` and `MongoDB Shell`)

## Architecture

<img src="https://github.com/nguyenthanhhy0108/Web-Personal-Project-BE/blob/main/github-image/CLIENT.png?raw=true" alt="Architecture" width="1200"/>

## Installation

### Clone the Repository
Start by cloning the repository and navigating to the project folder:

```bash
git clone https://github.com/nguyenthanhhy0108/Web-Personal-Project-BE.git
cd Web-Personal-Project-BE
```

### Pull docker images

Pull and start necessary docker containers:

Before run this command, ensure that following ports are free:
**`2181, 9092, 29092, 8085, 8086, 3303, 8180, 6606`**

```
docker-compose up -d
```

**`After this step, make sure your MongoDB, MySQL and these pulled containers active.`**

### Initialize MySQL database

Open your MySQL command line and run the commands from the file scripts.sql.

### Build maven project

At the `project root`, run the following command to build the project with Maven:
```
mvn clean install
```

## Usage

To run the project, execute the following commands at the `project root`:

Grant permission:
```
chmod +x run.sh
```
Run the application:
```
./run.sh
```

## API

We provide Postman JSON as well as OpenAPI for developers:

### Postman JSON

[Download Postman JSON](https://github.com/nguyenthanhhy0108/Web-Personal-Project-BE/blob/main/postman.json)

You can import this json for creating a new collection in Postman application.

### Open API YML

[Download OpenAPI YML](https://github.com/nguyenthanhhy0108/Web-Personal-Project-BE/blob/main/openAPI.yml)

You can use [Swagger Editor](https://editor.swagger.io/) for visualizing this yml file.

## Application code

[Access Application Code](https://github.com/nguyenthanhhy0108/Web-Personal-Project-BE/blob/main/code.txt)

We also provide a dedicated file for `frontend developers`, allowing them to easily call these APIs.

## Functionality

- All necessary CRUD.
- Implement `OAuth2` and `JWT` for robust **authentication** and **authorization**.
- Manage user accounts while securely storing user information.
- Utilize `MongoDB` to store and retrieve images and a variety of file types.
- Send notifications and communications via `SMTP`.
- Announce company campaigns to targeted users.
- Search for and recommend vehicles tailored to user preferences.
- Facilitate the deposit and storage of PDF contracts.
- Integrate `Paypal`.
- Detect damaged vehicle parts using a specifically trained **`AI model`**.
- Predict vehicle pricing through a dedicated **`AI model`**.

## Technology Stack

- **Java Spring Boot**: Framework for building production-ready applications.
- **Java Spring Test**: Provides support for testing Spring components with unit tests and integration tests.
- **Java Spring Security**: Provides security features for authentication and authorization.
- **Java Spring OAuth2 Resource Server**: Enables OAuth2 authorization for secure API access.
- **Java Spring Data JPA**: Simplifies data access and manipulation with JPA.
- **Java Spring Hibernate**: ORM tool for managing database operations.
- **Java Spring MongoDB**: Integration with MongoDB for document-based data storage.
- **Java Spring H2**: Lightweight in-memory database for development and testing.
- **Java Spring Cloud Gateway MVC**: API gateway for routing and managing microservices.
- **Java Eureka Discovery Server**: Service registry for locating microservices in a distributed system.
- **MapStruct**: Code generator for mapping between Java bean types.
- **Lombok**: Reduces boilerplate code with annotations for getters, setters, and more.
- **Spring Cloud Feign Client**: Declarative HTTP client for seamless API calls between services.
- **Spring Boot Mail**: Simplifies sending emails in Spring applications.
- **Kafka**: Distributed streaming platform for building real-time data pipelines and applications.
- **Keycloak**: Identity and access management solution for secure authentication.
- **Resilience4J Circuit Breaker**: Implements circuit breaker patterns for resilient microservices.

## Data

The vehicle data in this application is sourced from various websites and summarized into two CSV files:

- `/data/translated_data.csv`
- `/data/translated_porsche.csv`

Additionally, we provide a **Jupyter Notebook** for inserting these CSV files:

- `/data/initial_data.ipynb`

This notebook serves as a sample code; feel free to expand upon it and customize it to suit your needs!
## Author

### Recommendation
<i>We advise you to run our project on IntelliJ for a better experience.</i>

### Signature
**`Nguyen Thanh Hy (Won Jeong Hee)`**

