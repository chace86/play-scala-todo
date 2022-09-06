# Play Scala Todo

## Overview

Example Scala Play "todo list" application. Postgres will be used as the database.

## Action Items

### Application

- [x] Controller
- [x] Model
- [x] Repository
- [ ] Auth
- [x] Testing
    - Unit tests
    - Integration/e2e tests
- [ ] How to deal with blocking IO?
- [ ] AWS SDK - Use [AWScala](https://github.com/seratch/AWScala)?
- [ ] Swagger/OpenAP

### Database

- [x] Use H2 initially
- [x] Slick
    - Represent table
    - Configuration
- [ ] Postgres
- [x] Flyway Migrations

### Development

- [x] Scalafmt
- [ ] Scala Steward - update dependencies
- [ ] Add GitHub tags for build coverage, pass/fail, etc.

### Deployment

- [ ] Dockerfile
- [ ] docker-compose
- [ ] CF: AWS Elastic Beanstalk?