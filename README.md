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
- [x] Enum rendered in Swagger?
- [x] Update to Scala 2.13
- [x] Update sbt version
- [ ] JVM tuning and other java options?

### Database

- [x] Use H2 initially
- [x] Slick
    - Represent table
    - Configuration
- [ ] Postgres
- [x] Flyway Migrations
- [x] Slick - FK constraint with todo lists
- [x] Slick - Add optional column

### Development

- [x] Scalafmt
- [ ] Dependency bot (GitHub or Scala Steward?)
- [ ] Add GitHub CI/CD, tags, etc.

### Deployment

- [x] Dockerize
- [ ] docker-compose
- [ ] CF: AWS Elastic Beanstalk?

## Run Application

### Local

To run locally:

`sbt run`

Use `Enter` or `CTRL + D` to stop application

### Docker

Play comes with [SBT Native Packager](https://sbt-native-packager.readthedocs.io/en/latest/index.html), which can build a Docker image

To build Docker image:

`sbt docker:publishLocal`

To run image locally:

`docker run --rm -p 9000:9000 play-scala-todo`

## Testing

`sbt test`

## Swagger

- SwaggerUI at `http://localhost:9000/docs/swagger-ui/index.html?url=/assets/swagger.json`
- Swagger JSON at `localhost:9000/assets/swagger.json` 

## Resources

- [Play](https://www.playframework.com/documentation/2.9.x/Home)
- [Play Slick](https://github.com/playframework/play-slick) and [Slick 3](https://scala-slick.org/doc/stable/)
- [Flyway Play](https://github.com/flyway/flyway-play)
- [Play Swagger](https://github.com/play-swagger/play-swagger) and [OpenAPI/Swagger Specification](https://swagger.io/specification/)
- [Play Samples](https://github.com/playframework/play-samples) - REST, Slick, etc.
- [SBT Native Packager](https://sbt-native-packager.readthedocs.io/en/latest/index.html)
- [ScalaTest](https://www.scalatest.org/) and [ScalaMock](https://scalamock.org/)
- [Scalafmt](https://scalameta.org/scalafmt/docs/configuration.html)
