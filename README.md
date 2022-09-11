# Play Scala Todo

## Overview

Example Scala Play "todo list" application. Postgres will be used as the database.

## Action Items

### Application

- [x] Controller
- [x] Model
- [x] Repository
- [ ] Auth
    - JWT, Session/Cookie, etc.?
- [x] Testing
    - Unit tests
    - Integration/e2e tests
- [ ] Integration vs unit test sbt tasks
- [ ] How to deal with blocking IO?
- [ ] AWS SDK? - Use [AWScala](https://github.com/seratch/AWScala)?
- [ ] Swagger/OpenAPI
- [ ] Pagination API example?

### Database

- [x] Use H2 initially
- [x] Slick
    - Represent table
    - Configuration
- [ ] Postgres
- [x] Flyway Migrations
- Slick - FK constraint with todo lists
- Slick - Add optional column

### Development

- [x] Scalafmt
- [ ] Scala Steward - update dependencies
- [ ] Add GitHub CI/CD, tags, etc.

### Deployment

- [ ] Dockerfile
- [ ] docker-compose
- [ ] CF: AWS Elastic Beanstalk?

## Helpful Links

- [Play Swagger](https://github.com/iheartradio/play-swagger)