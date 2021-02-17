**ADIDAS CODING CHALLENGE MICROSERVICE APPROACH**
---


General Information
=====

- **FULL API DOCUMENTATION IN DOCUMENTATION FOLDER!!!!!!!!!**

- Note that **for this test we are using in memory database so the database will be regenerated on restart**.

- The API is secured with JWT authentication so the first step to be able to consume the API will be obtain the token to be included in our requests.

- **The only exposed API should be the one served by the API Gateway** [http://localhost:8762/api](http://localhost:8762/api), [http://localhost:8762/subscription-microservice/api](http://localhost:8762/subscription-microservice/api) and [http://localhost:8762/email-microservice/api](http://localhost:8762/email-microservice/api)

- The different APIs has his own swagger ([Auth API swagger](http://localhost:8762/swagger-ui.html), [Subscription Service API swagger](http://localhost:8762/subscription-microservice/swagger-ui.html) and [Email Service swagger](http://localhost:8762/email-microservice/swagger-ui.html)) for test and documentation purposes, the swagger can be deactivated for production if needed with the application property `app.api.swagger.enable`.

- The Application users are stored in user database table (see api-gateway module `org.julio.gregorio.adidas.apigateway.service.model.User` for more information) and **for this demo example the unique existing user has the username "user" and password value "password"**.

- The API has validations so if you are trying to do something incorrect you will receive information to help you to do it in the proper way.

- **Eureka server is configured with security** as well to  control de service registration.


Technical Information
=====

This application is implemented with **Spring Boot framework**.

Main features:

* **Eureka server** to manage Service Discovery.
* **Netflix Zuul** for Intelligent Routing to create the gateway for the microservices. This **gateway is secured with JWT Token security**.
* **Rest API**.
* **JPA**.
* In memory **H2 database**.
* Separated beans for model and data transfer and mappers generated with **mapstruct**.
* Spring boot bean **validation system**.
* **Cucumber JUnits**.
* **Mail** sender system (only pending smtp configuration) this system can be replaced by a dummy thanks to **@ConditionalOnProperty component load**.
* **CORS** configuration.
* **Swagger**.
* **ErrorExceptionHandler** with personalized Error response.

Dependencies:

```
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
	<version>2.1.3.RELEASE</version>
</dependency>
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-netflix-zuul</artifactId>
	<version>2.1.3.RELEASE</version>
</dependency>
```
For Service Discovery and Intelligent Routing.

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```
For easy access to the database and to use the provided layer on the top of JPA to use JPA features (entities, associations, querys...).
			
```
<dependency>
	<groupId>com.h2database</groupId>
	<artifactId>h2</artifactId>
	<scope>runtime</scope>
</dependency>
```
H2 Database support, perfect for JUnit and demo purpose... 	
		
```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
This dependency transitively pulls in all dependencies related to web development including bean validation, databind, webmvc, tomcat starter...
		
```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-security</artifactId>
</dependency>
```
This will include the initial/default security configuration. Thanks to this dependency we can configure our API security with our own configuration component (this component will extends WebSecurityConfigurerAdapter).
	
```
<dependency>
	<groupId>io.jsonwebtoken</groupId>
	<artifactId>jjwt</artifactId>
	<version>${jjwt.version}</version>
</dependency>
```
This dependency in combination with spring-boot-starter-security allow us to configure our JWT token security.
	
```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```
It will pull the JavaMail dependencies
		
```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-devtools</artifactId>
	<scope>runtime</scope>
</dependency>
```
Just for development purpose, Spring Boot DevTools pick up the changes and restart the application, this allow us to save some time while we are developing out application.

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-test</artifactId>
	<scope>test</scope>
</dependency>
```
Contains most of the the elements required for our tests	
		
```
<!-- swagger for api documentation -->
<dependency>
	<groupId>io.springfox</groupId>
	<artifactId>springfox-swagger2</artifactId>
	<version>${swagger.version}</version>
	<exclusions>
		<exclusion>
			<groupId>org.mapstruct</groupId>
			<artifactId>mapstruct</artifactId>
		</exclusion>
	</exclusions>
</dependency>
<!-- swagger-ui for api documentation web ui -->
<dependency>
	<groupId>io.springfox</groupId>
	<artifactId>springfox-swagger-ui</artifactId>
	<version>${swagger.version}</version>
</dependency>
<dependency>
	<groupId>io.springfox</groupId>
	<artifactId>springfox-bean-validators</artifactId>
	<version>${swagger.version}</version>
</dependency>
<dependency>
	<groupId>io.springfox</groupId>
	<artifactId>springfox-data-rest</artifactId>
	<version>${swagger.version}</version>
</dependency>
```
To be able to configure a swagger to provide documentation with the possibility to test the API at the same time. 
		
```
<dependency>
	<groupId>org.mapstruct</groupId>
	<artifactId>mapstruct</artifactId>
	<version>${org.mapstruct.version}</version>
</dependency>
```
Mapstruct is a code generator that simplifies the implementation of mappings between our model and DTOs


Import and Run Project in Eclipse
=====

- Import as maven project.
- Run as maven install (to compile and regenerate mappers).
- Run `org.julio.gregorio.adidas.eurekaserver.EurekaServerApplication.java` as java application.
- Run `org.julio.gregorio.adidas.apigateway.ApiGatewayApplication.java` as java application.
- Run `org.julio.gregorio.adidas.subscription.SubscriptionServiceApplication.java` as java application.
- Run `org.julio.gregorio.adidas.email.EmailServiceApplication.java` as java application.


Build Project
=====

- Execute `mvn clean install` to generate the executable jar files


How To Use
=====

Have a look to ([Auth API swagger](http://localhost:8762/swagger-ui.html), [Subscription Service API swagger](http://localhost:8762/subscription-microservice/swagger-ui.html) and [Email Service swagger](http://localhost:8762/email-microservice/swagger-ui.html)) to know the existing API methods.

First of all you need to make a request to [authenticate service](http://localhost:8762/swagger-ui.html#/jwt-authentication-controller/generateAuthenticationTokenUsingPOST) with the following body `{ "username": "user", "password": "password" }`

The method response contains a JWT token that need to be included on each request as value for a header with name `Authorization`

If your are testing the API with swagger you can easily authenticate your requests by pressing Authorize button. A modal will appear with an input, you can use `Bearer <your_token>` as value and press login.

Now you are authenticated so... enjoy the API... Remember that the subscription is linked with a campaign newsletter so you need to create at less one newsletter to be able to create a subscription. 

**FULL API DOCUMENTATION IN DOCUMENTATION FOLDER!!!!!!!!!**
