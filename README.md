# Spring Boot Seed Application and Angular 5

Spring Boot Angular 5 Seed Application!


## Prerequisites

1. NodeJS - https://nodejs.org/en/download/current/
2. Run: ```sudo npm install -g npm```
3. Run: ```sudo npm install -g @angular/cli@6.0.0```

## Installing

1. Change directory to ```./client``` and run: ```npm install```


## Running

1. Change directory to ```./client``` and run: ```npm run start:open```

## Deployment

1. Change directory to ```./client``` and run: ```npm run build:prod```
2. Run ```mvn install```


## Backend (Java) Features

- Basic Spring Security
- Basic Rest Controller (i.e /api/v1/app/info)
- Swagger 2 (i.e. /v2/api-docs)
- Custom error pages (i.e. 404, 500, ... etc)
- Secured APIs (i.e. /api/v1/app, /api/v1/users)
- Data models extend Auditable class
- MySql database (utilizing Spring JdbcTemplate)
- Simple "in memory" database


## Frontend (Angular) Features
- Simple Angular 6 CLI application
- Bootstrap, jQuery and Toastr integration
- Basic Favicon set (using https://realfavicongenerator.net/)
- Basic example of Angular binding, routing and services
- Angular "in memory" database
- Loading bar
- Basic CRUD views for reading, creating, updating and deleting a custom object


##### TODO
- Integrate Angular Material Design
- Implement backend audit event system
- Implement search and filtering on MySQL Dao
- Add Java unit tests
- Add Facebook OAuth
- Add File I/O DAO