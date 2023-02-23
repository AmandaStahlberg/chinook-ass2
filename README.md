# SQL Database Creation and Manipulation Assignment
This readme file provides the instructions and requirements for creating and manipulating a SQL database using PostgreSQL in PgAdmin. The assignment is divided into two parts, Appendix A and Appendix B.

## Appendix A: SQL scripts to create database
In this part, we were required to create several SQL scripts that can be run to create a database, set up tables, add relationships, and populate the tables with data. The database theme is superheroes, and it's called `SuperheroesDb`. The scripts are written in PgAdmin through the query tool. You can find the scripts to Appendix A in the SCRIPTFOLDER

### Requirements
* Create a database in PgAdmin called Superheroes
* Create three main tables named Superhero, Assistant, and Power
* Superhero table should have an auto-incremented integer ID, name, alias, and origin
* Assistant table should have an auto-incremented integer ID and name
* Power table should have an auto-incremented integer ID, name, and description
* Add primary keys to all tables
* Set up relationships between tables using foreign keys and constraints
* Create a linking table for the Superhero-Power relationship
* Insert data into the tables
* Update data in the Superhero table
* Delete data in the Assistant table
### Scripts
* `01_tableCreate.sql`: Contains statements to create the three main tables with primary keys
* `02_relationshipSuperheroAssistant.sql`: Contains statements to add foreign keys and set up constraints to configure the relationship between Superhero and Assistant tables
* `03_relationshipSuperheroPower.sql`: Contains statements to create the linking table and set up foreign key constraints between the linking tables and the Superhero and Power tables
* `04_insertSuperheroes.sql`: Inserts three new superheroes into the database
* `05_insertAssistants.sql`: Inserts three assistants and decides which superheroes they can assist
* `06_powers.sql`: Inserts four powers and assigns them to superheroes
* `07_updateSuperhero.sql`: Updates a superhero's name
* `08_deleteAssistant.sql`: Deletes an assistant by name
## Appendix B: Reading data with JDBC
In this part, we were required to create a Spring Boot application and use JDBC with the PostgreSQL driver to interact with the Chinook database. The Chinook database models the iTunes database of customers purchasing songs. You need to create the database in PgAdmin, open a query tool, and run the script to generate all the tables and populate the database.

### Requirements
* Create a Spring Boot application
* Include the correct driver
* Create a repository pattern to interact with the database
* Read all the customers in the database and return their first name, last name, and email
* Read all the customers who live in a specific city and return their first name, last name, and email
* Read all the tracks that belong to a specific album and return their name, composer, and unit price
* Read all the invoice items for a specific invoice and return the track name and unit price

# 
By completing this assignment, we have gained knowledge and skills in creating and manipulating a SQL database using PostgreSQL and interacting with a database using JDBC and the Spring Boot application.

This assignment is created by [Hassan](https://github.com/hussanmk2014) & [Amanda](https://github.com/AmandaStahlberg).
