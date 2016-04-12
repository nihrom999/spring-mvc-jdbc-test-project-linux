# spring-mvc-jdbc-test-project-linux
A simple project for departments and employees management.

To run the project, load it to your local machine.

You must have mysql server on your machine.

To configure database run script "createDB" with parameters:

1) "port"(required)

2) "db name"(required)

3) "mysql username"(required)

4) "mysql userpassword"(required)


This script will create two databases on your mysql server with names <db name> and <db name_test> 
and it will insert some information to test db for junit tests.

db creation of tables you can see in the file "createTables"

===================================================================================

example of using createDB

shell> ./createDB 3306 projectDB root root

===================================================================================
 
To drop databases you can use "dropDB" with parameters

1) "mysql user"(required)

2) "mysql password"(required)

Script will get "db name" from "dbName" file

And will drop "db name" and "db name_test" databases.

===================================================================================

example of using dropDB

shell> ./dropDB root root
