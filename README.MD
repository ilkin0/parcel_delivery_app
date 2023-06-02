# PARCEL DELIVERY APP

This is a simple parcel delivery app.

As a microservices application tech stack is:  Java 17, Spring cloud, Docker, etc.

## TODO

- [x] Project initialziation.
    - [x] Empty microservices project.
    - [x] Github mono-repository.
- [x] Plan simple architecture based on task inputs.
- [x] Implement ms-gateway.
    - [x] Accept all requests.
    - [x] Check if URI is in SECURED or not.
    - [x] If SECURED get JWT token from 'Authorization' header.
    - [x] If Header is missing or not valid return UNAUTHORIZED.
    - [x] If Token is valid communicate with ms-auth to get credentials(userId and role) of user based on JWT token
    - [x] Add 2 new header [**_userId_**, **_role_**] to Request.
    - [x] Dynamic forward muted request(new 2 headers added) to destinations.
- [ ] Implement ms-auth.
    - [x] Implement introspecting API.
    - [x] Get user email from JWT token, communicate with ms-user to get user credentials by userEmail
    - [x] If requested JWT is valid, return requested user data (userId, role)
- [ ] Implement user management.
    - [x] Implement Spring security.
    - [x] Implement User signup.
    - [x] Implement User sign-in.
-[x] Docker all services.

## What Could be done?

Project, unfortunately, is not finished.

1. Order microservice is not implemented 'COURIER' APIs.
2. OpenApi 3 is not implemented.
3. Micrometer(Sleuth in old versions) and OpenZipkin could easily implement, for distributing tracing.
4. Did not implement CustomExceptions and did not handle via ExceptionResolver.
5. Could be added Kubernetes helm charts/deployments for easy AWS ESK deployment.
6. Much more Unit tests, Integ test with Testcontaniers, etc.
7. Rate Limiting / Circuit Brake pattern with Redis in Spring CLoud Gateway
8. and much more...

## Installation

The application is containerized, just having docker is enough.
To start the application following command is required:

```bash 
gradle clean build
docker compose up
``` 

Or in root directory, added script, called run.sh. Running the script is enough to build app with gradle, check docker
images, then docker compose up:
```bash 
cd scrtips
sh run.sh
``` 

Due to missing OpenAPI(Swagger), Postman Collection added to project as well.