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
    - [ ] If Token is valid communicate with ms-auth to get credentials(userId and role) of user based on JWT token
    - [ ] Add 2 new header [**_userId_**, **_role_**] to Request.
    - [ ] Dynamic forward muted request(new 2 headers added) to destinations.
- [ ] Implement ms-auth.
    - [x] Implement introspecting API.
    - [ ] Get user email from JWT token, communicate with ms-user to get user credentials by userEmail
    - [ ] If requested JWT is valid, return requested user data (userId, role)
- [ ] Implement user management.
    - [x] Implement Spring security.
    - [ ] Implement User signup.
    - [ ] Implement User sign-in.
-[ ] Docker all services.

## Bugs

- [ ] UserRole have to be oneToOne with role_id instead of creating a new Role every time for 'Sign-up user' API.