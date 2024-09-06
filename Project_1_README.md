# Warehouse Management Application #
## Overview ##
This project consists of a warehouse management application, which allows for the creation of company administrator accounts, the creation of warehouses, and the ability to place items in those warehouses.

## Contents ##
- project1Frontend: an intuitive frontend design that allows a user to create an account, login, update their account, and manage their warehouses.

- warehousedemo: a secure and comprehensive backend that provides the API to perform CRUD operations on their account's data.

## Project 1 Frontend ##
Upon opening the web application, the user is directed to the landing page. While logged out, the user is able to visit the following pages:
- Landing page
- Signup page
- Login page

Once logged in, the user is able to visit the following pages:
- Home page
- Account page
- Warehouses page
- Items page

A navbar can be found at the top of the screen representing these options, with the exception of the Items page which must be navigated to through the Warehouses page.

A more in-depth description of these pages follows.

### Landing page ###
The landing page is the front page of the application, and appears upon entry. It allows navigation to the Signup and Login pages.

### Signup page ###
The signup page allows a user to create an account. It provides fields for the company name and desired password, and requires the user to confirm the password before attempting a signup. 

On a successful signup, the page automatically routes the user to the login page.

On an unsuccessful signup, a message is shown stating what went wrong, and the user is allowed to try again.

### Login page ###
The login page allows a user to log into their account. It provides fields for the company name and associated password.

On a successful login, the user's account is stored internally and the user is redirected to the Home page.

On an unsuccessful login, a message is shown stating what went wrong, and the user is allowed to try again.

### Home page ###
The home page acts is the front page for a company administrator, displaying the company's name and providing options ot navigate to the account page or the warehouses page.

### Account page ###
The account page allows a user to modify their account's information, providing fields for the company name and password. In order to modify the company name, a checkbox must be checked. After submitting the form, a message shows displaying the result of the attempt.

### Warehouses page ###
The warehouses page allows a user to view and manage their company's warehouses. They can add and remove warehouses, or navigate to a warehouse's Item page.

### Items page ###
The items page allows a user to view and modify a warehouse's items. They can add and remove items, or modify the item's quantity.

## Warehouse Demo (Backend) ##
The warehouse demo sub-folder contains the backend code for this project. Implemented using the Spring Framework, it contains:
- A filter layer,
- A controller layer,
- A service layer,
- And a repository layer,

for the purposes of providing a CRUD API with an administrator, warehouse, and item resource.

### Filter layer ###
The filter layer provides security to the API and its data with the assistance of the service layer. Intercepting requests before they reach the controller, it checks the authorization of each controlled request (which does not include signup and login), validating its signature and expiration before passing along the appropriate administrator information.

### Controller layer ###
The controller layer provides the common interface for the API, with HTTP methods that allow GET, PUT, POST, and DELETE methods compliant with CRUD and REST specifications.

It receives all valid requests, interpreting their data and passing it to the service layer before creating and returning a response.

### Service Layer ###
The service layer provides the complex computation capabilities of the API, receiving interpreted data from the controller layer and mapping it to repository requests while verifying the data's values.

Additionally, this layer secures user passwords using a hashing method to lessen the impact of potential data leaks.

### Repository Layer ###
The repository layer directly communicates with the database that contains all of the data being accessed.

## Resources ##
### Data Model ###
![Data Model](./Data%20Model.jpeg)

The data model contains 4 tables:
- administrators: contains entries that model an administrator, with a company name and password. Company names must be unique, and an id is generated for these entries.
- warehouses: contains entries that model a warehouse, with a name, location, size, and administrator id. Names must be unique, and an id is generated for these entries.
- items: contains entries that model an item, simply containing a name. Names must be unique, and an id is generated for these entries.
- item_locations: contains entries that model the many-to-many relationship between items and warehouses, with each entry being a number of some item in a warehouse. Contains a composite primary key of the item id and the warehouse id, and contains a quantity field.

### Notes ###
Deleting administrators that owned warehouses was not functioning at the time of presentations due to a cascade issue, since then a fix has been made.