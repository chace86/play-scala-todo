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
- [ ] AWS SDK for Java
- [x] Swagger/OpenAPI
- [ ] Pagination API example?
- [ ] Enum rendered in Swagger?
- [ ] Update to Scala 2.13
- [ ] JVM tuning and other java options?

### Database

- [x] Use H2 initially
- [x] Slick
    - Represent table
    - Configuration
- [ ] Postgres
- [x] Flyway Migrations
- [ ] Slick - FK constraint with todo lists
- [ ] Slick - Add optional column

### Development

- [x] Scalafmt
- [ ] Scala Steward - update dependencies
- [ ] Add GitHub CI/CD, tags, etc.

### Deployment

- [x] Dockerize
- [ ] docker-compose
- [ ] CF: AWS Elastic Beanstalk?

## Docker

Play comes with [SBT Native Packager](https://sbt-native-packager.readthedocs.io/en/latest/index.html), which can build a Docker image

To build Docker image:

`sbt docker:publishLocal`

To run image locally:

`docker run --rm -p 9000:9000 play-scala-todo`

## Testing

`sbt test`

## Resources

- [Play Swagger](https://github.com/iheartradio/play-swagger)
- [Play Samples](https://github.com/playframework/play-samples) - REST, Slick, etc.
- [SBT Native Packager](https://sbt-native-packager.readthedocs.io/en/latest/index.html)
