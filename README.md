## Motivation

Multi module project maven with spring boot and react in frontend.  
* app-skeleton-core  
  
Core of the project with JPA, and service to access business data

* app-skeleton-external  
  
Exposes service to simulate another business data in external datasource

* app-skeleton-spring-helpers  
  
Some class for spring boot configuration

* app-skeleton-web  
  
Webservices exposed 

* app-skeleton-fitnesse  
  
Empty for the moment the puropose is to expose batch and point to fitnesse server 

* app-skeleton-batch  
  
 Batch (included multi datasources core and external)


## Installation

you can run the web application on web module it will initialize one H2 database

you can run the batch with running as java job="initializeDataJob", it will print the data from antoher h2 database

you can run app-skeleton-frontend, with npm install and npm start in the app-skeleton-frontend folder

## Packaging

Build and war generation is not fully tested but maven install should generate a war with frontend and webServices in the same war ready to deploy

