# Spring Boot Seed Application and Angular 5

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 1.7.3.

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
- Simple Angular 5 CLI application
- Bootstrap, jQuery and Toastr integration
- Basic Favicon set (using https://realfavicongenerator.net/)
- Basic example of Angular binding, routing and services
- Angular "in memory" database
- Loading bar
- Basic CRUD views for reading, creating, updating and deleting a custom object

Note: To disable the Angular "in memory" database, comment out the following lines in app.module.ts:

```
HttpClientInMemoryWebApiModule.forRoot(
  InMemoryDatabase, { delay: 750, apiBase: 'api/v1/', dataEncapsulation: false }
)
```

##### TODO
- Integrate Angular Material Design
- Implement backend audit event system
- Design search object
- Add Java unit tests
- Add Facebook OAuth
- Add Elastic DAO
- Add File I/O DAO