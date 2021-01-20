#REST Microservices architecture for E-commerce
**Spring-Boot e-commerce app with Microservices architecture using Spring Cloud**

### Requirements
A small start-up wants to build a very simple online shopping
application to sell their products. In order to get to the market quickly, they just want
to build an MVP version... For more details, plz drop a message to me

### Technologies
- Java 8
- Spring Boot 2
- Spring Cloud
- Docker

### Features:
1. User authentication and access control using Spring Security Oauth2 with Json Web Token 
2. Spring actuator to monitor API usage including /health endpoint
3. OpenAPI/Swagger for API documentation
4. Spring Boot based Restful API including Validation, Exception Handling
7. Interacting with RabbitMQ server to publish and subscribe messages for auditing
8. Hibernate Level 2 caching with EhCache - TODO

##Project architecture
### High-level design
![High Level Design](files/icommerce-highelevel-design.png)
### Detail design
![High Level Design](files/icommerce-detail-level.png)

### Spring Cloud components
1. Eureka for Microservice Registration and Discovery and Dynamic scaling
2. Netflix-zuul for API Gateway
3. Feign for declarative REST client for Microservices
4. Client side load balancing using Ribbon
5. Hystrix for fault tolerance
6. Distributed Tracing using Spring Cloud Sleuth and Zipkin

## Build and Deploy
### Running apps independently with maven installed:

## Deploy Using Docker
- Run docker compose from project root
 
  -```$ docker-compose up --build```
- This will deploy whole application with microservices where nginx will serve UI and redirect to backend using proxy and mysql and mongodb containers will be running with persistent volume to store data across container lifecycle.
- Navigate to http://localhost to view running app

## Monitor

## URLs

|     Application       |     URL          |
| ------------- | ------------- |
| iCommerce Api | http://localhost:8060/swagger-ui.html |
| Eureka | http://localhost:8061/|
| Zipkin | http://localhost:9411/zipkin/ |




preference
https://spring.io/projects/spring-cloud