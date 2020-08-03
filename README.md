# Challenge Star Wars 🚀🌎
> This application was developed with API REST architecture in order to manage planet data from the Star Wars movie.

### The api contains the following planet data:

* Name
* Climates
* Terrain
* Movies

### Functionalities

* Add a planet
* List planets
* Find by planet name
* Find by ID
* Delete planet

## Base URL

```sh
http://localhost:8080/starWars/
```

## Run

To run the application with Docker it is necessary to have Docker installed.

```sh
mvn package -DskipTests && docker-compose up
```

## Test

To run the application tests it is necessary to have Maven installed:

```sh
mvn test
```

## Swagger

To check the endpoints, enter the swagger:

```sh
http://localhost:8080/starWars/swagger-ui.html#/
```

![](swagger.png)

## Development setup

The following libraries were used:

-  Java 11; ✔️ 
-  Apache Maven; ✔️ 
-  Spring Boot (2.3.1 Release); ✔️ 
-  Swagger (2.9.2); ✔️ 
-  Hibernate Validator (5.4.2 Final); ✔️ 
-  Spring Boot Starter Data JPA (2.2.7 Release); ✔️ 
-  Spring Boot Starter Data Mongodb (2.2.7 Release); ✔️ 
-  Spring MOC (2.0.0);✔️ 
-  Spring Boot DevTools (2.2.7 Release); ✔️ 
-  Junit; ✔️ 
-  Mock/Mockito; ✔️ 
-  Open Feign (2.2.2 Release); ✔️ 
-  LomBok (1.18.12). ✔️ 

## Developer

<div align="center">
  <h3 align="center">Connect with me<img align="center" src="https://github.com/rajput2107/rajput2107/blob/master/Assets/Handshake.gif" height="33px" /></h3> 
</div>
<p align="center">
 <a href="https://www.linkedin.com/in/michel-eckhardt-026121b1/" target="blank">
  <img align="center" alt="Pramod's LinkedIn" width="30px" src="https://www.vectorlogo.zone/logos/linkedin/linkedin-icon.svg" /> &nbsp; &nbsp;
 </a>
 <a href="https://www.instagram.com/michel_eckhardt/" target="blank">
  <img align="center" alt="Pramod's Instagram" width="30px" src="https://www.vectorlogo.zone/logos/instagram/instagram-icon.svg" /> &nbsp; &nbsp;
 </a>

## Contributing

1. Fork it (<https://github.com/michel-eckhardt/ChallengeStarWars>)
2. Create your feature branch (`git checkout -b feature/update`)
3. Commit your changes (`git commit -am 'Add some update'`)
4. Push to the branch (`git push origin feature/update`)
5. Create a new Pull Request
