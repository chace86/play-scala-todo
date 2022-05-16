# Play Scala Todo

## Overview

Example Scala Play "todo list" application. Postgres will be used as the database.

## Action Items

### Application

- Controller
- Model
- Repository
- Auth
- Testing
  - Unit tests
  - Integration tests
  - e2e tests
- How to deal with blocking IO?
- AWS SDK - Use [AWScala](https://github.com/seratch/AWScala)?

### Database

- Use H2 initially
- Slick
  - Represent table
  - Configuration
- Postgres
- Flyway Migrations
- Disable Play Evolutions (if required)

### Development

- ScalaStyle
- ScalaCheck
- Scala Steward - update dependencies
- Add GitHub tags for build coverage, pass/fail, etc.

### Deployment

- Dockerfile
- docker-compose
- CF: AWS Elastic Beanstalk?