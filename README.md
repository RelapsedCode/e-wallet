# Overview:

Rest API application build with Spring Boot for simulating simple payment transactions. DB connection is realized with Hibernate.

# Requirements:

* Instance of MySQL server
* Created empty MySQL database, which name is set in application.properties

# How to use:

The app is consisted of 4 controllers. If you want to use each controller separately you have to follow precisely the flow: create new wallet, create new transaction with the wallet, update the platform funds. To easy this procedure there is a Network endpoint which generates random valid data and calls each of the other endpoints in the correct order.

