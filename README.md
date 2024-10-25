<div align="center">

# WJH Project
**`This repository only has the back end logic`**

<img src="https://github.com/nguyenthanhhy0108/Web-Personal-Project-BE/blob/main/github-image/wjh-logo.png?raw=true" alt="Project Logo" width="300"/>


</div>

A car garage website where customers can deposit vehicles, buy parts, estimate car value, and access crash detection.

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

This below guide will help you set up the project on your local machine for development and testing purposes.

### Prerequisites

This repository relies on Java 17. Ensure you have the following installed:
- [Java](https://www.java.com/en/) (we use `java 21.0.2 2024-01-16 LTS`)
- [Docker](https://www.docker.com/) (for containerization)
- [Git](https://git-scm.com/) (for version control)
- [MySQL](https://www.mysql.com/) (for database, **for `Window`: MySQL Workbench CE is important**)
- [MongoDB](https://www.mongodb.com/) (for database, we recommend you install `MongoDBCompass` and `MongoShell`)

## Architecture

<img src="https://github.com/nguyenthanhhy0108/Web-Personal-Project-BE/blob/main/github-image/CLIENT.png?raw=true" alt="Project Logo" width="300"/>


## Installation

### Clone repository
Clone the repository and navigate into the project folder.

```
git clone https://github.com/nguyenthanhhy0108/Web-Personal-Project-BE.git
cd Web-Personal-Project-BE
```

### Pull docker images

Pull and start necessary docker containers through:

Before run this command, ensure that these ports are free:
**`2181, 9092, 29092, 8085, 8086, 3303, 8180, 6606`**

```
docker-compose up -d
```

**`After this step, make sure your MongoDB, MySQL and these pulled containers active.`**

### Build maven project

At `root`, run this command to build project in Maven:
```
mvn clean install
```

## Usage

At `root`, run this command to run this project:

Grant permission:
```
chmod +x run.sh
```
Run:
```
./run.sh
```

## API

We provide Postman JSON as well as OpenAPI for developers:

### Postman JSON

[Here](https://github.com/nguyenthanhhy0108/Web-Personal-Project-BE/blob/main/postman.json)

You can import this json for creating a new collection in Postman application.

### Open API YML

[Here](https://github.com/nguyenthanhhy0108/Web-Personal-Project-BE/blob/main/openAPI.yml)

You can use [Swagger Editor](https://editor.swagger.io/) for visualizing this yml file.

## Application code

[Here](https://github.com/nguyenthanhhy0108/Web-Personal-Project-BE/blob/main/code.txt)

We also provide a specific file for Front End developer, who can call these API easily.



