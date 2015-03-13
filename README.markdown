# Basic Database Access with Java (JDBC)

Learning what a wizard does before actually using the wizard to perform work for you is the best approach.
Java has a ton of different tools/wizards out there that simplify your life by hiding database access from you.

This Maven project just uses the basics to establish a database connection and then access the database in a simple, yet clean way.

The start of this example is the _TestSampleDAO_ test case. It instantiates the database connection and then the data access
object that interacts with the connection.

It uses:

* [HyperSQL](http://hsqldb.org/), for the in-memory database we are connecting to
* [SLF4J](http://www.slf4j.org/), for logging purposes


