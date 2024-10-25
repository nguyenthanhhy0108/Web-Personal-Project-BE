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
- [License](#license)

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



