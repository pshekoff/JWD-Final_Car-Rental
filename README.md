<h1>WEB PROJECT. CAR RENTAL</h1>

<h2>Subject area</h2>
A customer selects a car from the list of available cars, fill the order form with rental period and personal data.
After paying for the order, the administrator should give the car to the customer and then get it back on the appropriate dates.
If the car has damages, the administrator notes them and issues an invoice to customer.
Also admonistrator may reject the order indicating the reason.


<h2>Project functionality</h2>

<h3>Guest</h3>
* Authorization
* Registration
* Change the language (EN, RU)

<h3>Administrator</h3>
* User management
    * Block a user
	* Unlock a user
	* Add new employee (administrator)
      <br/>
* Car management
	* Add new car
	* Block a car
	* Unlock a var
    * Add a new car
    * Give a car
    * Get back a car
      <br/>
* Order management
    * View all orders
    * Approve the orders
    * Reject the orders
      <br/>
* Update personal info
	* Change password
	* Change username
	* Change email
* Log out

<h3>Customer</h3>

* Update personal info
	* Change password
	* Change username
	* Change email
* Update personal data (passport details)
* Create an order and make payment
* Cancel the own order
* View the own orders
* Log out

<h2>Database schema</h2>

![db_schema](https://user-images.githubusercontent.com/61383438/180621027-b77f8de6-b47f-4e1e-a000-15f2e395b1d1.jpg)

<h2>General requirements for the web-project (according to the EPAM course requirements)</h2>

* Create an application using Servlet and JSP technologies.
* The application architecture must follow the Layered architecture and Model-View-Controller patterns.
* The information about the subject area must be stored in the database:
    * the data in the database is stored in Cyrillic, it is recommended to use utf-8 encoding
    * the database access technology - JDBC
    * a connection pool must be implemented to work with the database in the app
    * it is recommended not to use more than 6-8 tables in the database
    * use the DAO template to access the database

* The application interface must be internationalized: English & Russian.
* The application must handle and log exceptions correctly. Use Log4j as a logger.
* Classes must be well-structured into packages and have a name corresponding to their functionality.
* When implementing the business logic of the application, design patterns should be used if necessary (for example, GoF
  patterns: Factory Method, Command, Builder, Strategy, State, Observer etc). Procedural programming should be avoided.
* Use the session to store user's information between requests.
* Apply filters to process request and response objects (f.e. to set the request/response encoding option).
* JSTL tags must be used when implementing JSP pages, scriptlets are not allowed. A custom tag must be implemented and
  used. Viewing “long lists” should be organized in pagination.
* Documentation for the project must be design according to javadoc.
* Code formatting must correspond to the Java Code Convention.

<i>General requirements for the functionality of the project.</i>

1. Sign in and sign out
2. Sign up
3. Viewing the existing information (f.e. viewing all users registered in the system)
4. Removing information (f.e. inactivating the user)
5. Adding and modifying the information (f.e. creation of the new car, updating the existing car's info)