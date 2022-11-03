<h1>CAR RENTAL</h1>
<h2>Subject area</h2>

A customer selects a car from the list of available cars. Then he books an order by filling out the order form and
setting up the rental period. The administrator sees a list of all created orders, which he can confirm, complete or
cancel (with the cancellation reason mark). The administrator registers the return of the car. Together with the
completion of the order, the administrator creates a return form and fills out the information about the car's damages
and additional bill value (in necessary).

<h2>Implemented project functionality</h2>

<h3>Guest</h3>

* Sign in
* Sign up
* Change the language

<h3>Admin</h3>

* User management
    * View all users
	* Add administrator
    * Block/Unblock users
      <br/>
* Car management
    * View all cars
    * Add a new car
    * Activate/Deactivate cars
      <br/>
* Order management
    * View all orders
    * Approve order
	* Reject order
    * Complete order
      <br/>
* Update profile
	* Change login
	* Change password
	* Change email
      <br/>
* Log out

<h3>Customer</h3>

* Order management
    * Create order
    * Pay order
	* Cancel order
	* View own orders
      <br/>
* Update profile
	* Change login
	* Change password
	* Change email
      <br/>
* Log out

<h2>Database schema</h2>
![image text](https://github.com/pshekoff/JWD-Final_Car-Rental/blob/main/DB_car_rental_model.jpg)

<h2>General requirements for the web-project</h2>

* Create an application using Servlet and JSP technologies.
* The application architecture must follow the Layered architecture and Model-View-Controller patterns.
* The information about the subject area must be stored in the database:
    * the data in the database is stored in Cyrillic, it is recommended to use utf-8 encoding
    * the database access technology - JDBC
    * a connection pool must be implemented to work with the database in the application
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
2. Registration
3. Viewing the existing information (f.e. viewing all users registered in the system)
4. Removing information (f.e. inactivating the user)
5. Adding and modifying the information (f.e. creation of the new car, updating the existing car's info)
