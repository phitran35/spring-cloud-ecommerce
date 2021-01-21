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
8. Hibernate Level 2 caching with EhCache

##Project architecture
### High-level design
![High Level Design](files/icommerce-highelevel-design.png)
- **Product Service**: manages our products with CRUD operations. This service also provides the ability to allow user could filter, sort and search for products based on dynamic criteria.
- **Audit Service**: records all customers activities (filtering, sorting, viewing product detail).
- **Cart Service**: manages customers shopping carts with CRUD operations.
- **Order Service**: manages customer orders with CRUD operations.
- **Config server**: a module that uses Spring Cloud Config Server for running configuration server in the native mode. The configuration files are placed on the classpath.
- **discovery-service** - an embedded discovery server.
- **Authentication Service**: authenticates customers, integrates with 3rd party identity platform like Facebook, Google...
- **API Gateway**: Route requests to multiple services using a single endpoint. This service allows us to expose multiple services on a single endpoint and route to the appropriate service based on the request.
- **Tracing server**: using Spring Cloud Sleuth and Zipkin in order to gather timing data for every request propagated between independent services
- **Admin server**: monitoring Microservices With Spring Boot Admin
### Detail design
![High Level Design](files/icommerce-detail-level.png)

#### Product Service
The Product service stores information about all of our product. The storage requirements for the Product are:
- Long-term storage.
- Read-heavy (it's common for ecommerce application because the traffic from users to view, search, sort product are always much higher than the traffic from administrators to update product's information).
- Need complex joins 

A relational database: Postgresql is selected. For the simplicity of the assignment, we simplify the data schema like this:
![Product Schema](files/product-service-diagram.png)

- To support customer filter, sort and search for products based on dynamic criterias, we have 2 options: *Spring Specification* and *QueryDSL*. Here we go with *QueryDSL* because it simplify the implementation.
- To keep track all customer activity, we need to record all customer request parameters when client send GET request to our endpoint to view product detail or to filtering/sorting products. We use *Spring AOP* and define the PointCut to tell Spring which part of the code should be monitored, we also define Advice method to tell Spring how to record these parameters.
- To make sure failure to store customer activity is completely transparent to customer and should have no impact to the activity itself, we use *Spring Async* to run our AOP Advice in a separate thread.
- We use *Spring Cloud Stream* to send all customer activity data from Product Service to a message broker (to simplify the setup, here we use CloudAMQP - a cloud RabbitMQ service). In our case, Product Service acts as a message *Source*, and Audit Service acts as a message *Sink*. We don't want data will not be lost if Audit Service was temporary down, so we config queue as durable queue for guaranteed message delivery.

#### Audit Service
- Audit Service acts as a message Sink, it consumes and process message (message is customer activity in our case). And store to MongoDB.

#### Shopping Cart Service
- A simple CRUD service with *spring-boot-starter-data-redis* and backed by Redis.

#### Order Service
- A simple CRUD Service with *spring-boot-starter-data-rest* and backed by MongoDB. We use @RepositoryRestResource to expose resources without implementing controller/service.

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