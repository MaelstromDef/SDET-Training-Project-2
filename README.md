# Warehouse Management Application Testing #
## Overview ##
This project consists of a full front, back, and API testing suite of the Project 1 warehouse management application, focusing on CRUD operations, security, and usability.

## Contents ##
- jenkins: contains the definitions for our CI/CD pipeline.

- JMeter: contains the project files for our JMeter test plan.

- project1Frontend: an intuitive frontend design that allows a user to create an account, login, update their account, and manage their warehouses.

- warehouse-frontend-testing: a full Cucumber/Selenium testing suite for the Project 1 Frontend.

- warehousedemo: a secure and comprehensive backend that provides the API to perform CRUD operations on their account's data, containing its own unit testing suite.

## Jenkins ##
Our testing suite offers a fully integrated CI/CD pipeline that builds, tests, and deploys the full stack application. Within it is:

- Full Cucumber/Selenium headless testing integration.

- SonarQube project analysis.

- Jacoco line coverage.

- TestNG unit testing integration.

- Automated AWS Elastic Beanstalk and Static S3 deployment.

### JMeter ###
The JMeter folder contains a jmx file with a full user auth flow testing plan that will go through every API endpoint available and test their functionality and performance.

### Project 1 Frontend ###
Since this was not implemented during Project 2, please see the [Project_1_README.md](./Project_1_README.md) file.

### Warehouse Frontend Testing ###
Offering a full POM-based Cucumber/Selenium testing suite, this folder offers 117 tests that are run within a single, small Step Definitions file and 5 feature files. It does this by making use of the Facade pattern, using the IPage interface to interact with all of the various pages on the application.

### Warehouse Demo ###
This folder contains the backend implementation for the application, please see the [Project_1_README.md](./Project_1_README.md) file for details on that implementation.

In addition to this backend implementation, the warehouse demo folder also contains a full TestNG testing suite with unit tests for:
- Aspect classes,
- Controller classes,
- Repository classes,
- Service classes,
- Model classes.

In order to isolate classes for unit testing, Mockito was used to simulate real beans.

## Resources ##
There are various reources and notes that detail the process and progress of Project 2. 

Before implementations began, a [Test Plan](./Resources/Test%20Plan.pdf) was created with the supporting [Test Coverage](./Resources/Test%20Coverage.pdf) and [User Stories](./Resources/User%20Stories.pdf) documents. Additionally, a [Trello Board](https://trello.com/invite/b/66bbb979749bec5abef8af6d/ATTIb0f85c5633f92afc34920b2d5d4279c92AC48E2E/inventory-management-testing) was maintained to keep the project organized throughout its lifespan.

After completion of each test suite, a report was made with documentation on what tests were implemented, how they were implemented, and what improvements need to be made. They are as follows:
- [Backend Test Report](./Resources/Back%20End%20Testing%20Report.pdf)
- [Frontend Test Report](./Resources/Back%20End%20Testing%20Report.pdf)

### Deployments ###
- [Warehouse Management Frontend](http://ahuggins-warehousemanager-frontend.s3-website.us-east-2.amazonaws.com/)
- [Warehouse Management API Base Endpoint](http://ahuggins-warehousemanager.us-east-1.elasticbeanstalk.com)
- [Jenkins](http://184.72.206.70:8080/)