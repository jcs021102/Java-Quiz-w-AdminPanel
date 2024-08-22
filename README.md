# Java Assignment READ ME

## Assignment Structure

In this folder there are 3 main files,

1. A database.SQL with all the scripts needed to get the assignment setup
2. A folder called javaAssignment, populated with the test scripts and the console based assignment.
3. A folder called javaAssignmentGUI, populated with an updated version of the code, with proper comments, SQL integration and the highscore.csv

## Setting up the project

1. To setup the project, first add the project files to eclipse.
2. [Note in case of difficulties use java21 as the JRE]
3. For testing, use JUnit4
4. For SQL use a JDBC driver for MySQL, or any other sql based database (not the sql file might need some adjustments to the queries, should you proceed with this)
5. [Note: Add any of the above to the buildpath where needed]
6. In the sqlFunctions class, your databaseURL, username and password should be added to connect to the database.
7. Run As, and the application should run accordingly

## Comments on design choices, with Grading in Mind

1. The console project is purely there for grading Task 1.
2. Tests have ommited from any [getters / setters] or any methods who's sole purpose is printing
3. Tests have also been limited, as the project was created mutable, allowing for any number of catagories to be created dynamically based on the number of files in the specified quiz_data folder.
4. Getters and Setters as well as any simple methods with clear names, have also been ommitted from any desriptive commenting to avoid redundancy.
5. The GUI project, should be seen as the main project, as code has been encapsulated and commented correctly. Testing from Task 1 is still relevant at this point, as no methods have seen any method signiture or function change.
6. This is with the exception of csvPath in quizCatagory, which changes from a path to a String, between the versions.
7. In the quiz class, the dataFunctions variable's datatype should be set to sqlFunctions or csvFunctions, depending on if an SQL database or the csv are to be used.
8. The highscore is saved in a CSV file in the GUI project.
