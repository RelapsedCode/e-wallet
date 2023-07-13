# Overview:

Rest API application build with Spring Boot for simulating simple payment transactions. DB connection is realized with Hibernate.

# Requirements:

* Instance of MySQL server
* Created empty MySQL database, which name is set in application.properties

# How to use:

The app is consisted of 4 controllers. If you want to use each controller separately you have to follow precisely the flow: create new wallet, create new transaction with the wallet, update the platform funds. To easy this procedure there is a Network endpoint which generates random valid data using the Faker library and calls each of the other endpoints in the correct order.

Collection containing examples for Insomnia/Postman is included (e-wallet-example-transactions-Insomnia_2023-07-13.har).
![alt text](https://github.com/RelapsedCode/e-wallet/blob/main/insomnia-example.PNG?raw=true)<br></br>

# Swagger:

Additionally, you can use the application by visiting the swagger ui: http://localhost:8080/swagger-ui/index.html
![alt text](https://github.com/RelapsedCode/e-wallet/blob/main/swagger-example.PNG?raw=true)
