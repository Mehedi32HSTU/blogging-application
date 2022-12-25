# Spring Boot Blog Application
### Simple blogging platform project developed using:
  1. Java 8
  2. Spring Boot v2.7.7-SNAPSHOT
  3. Spring Web
  4. Spring Data JPA
  5. Spring Security
  6. H2-in-memory-database
  7. Jwt based authorization
  8. Maven


Unregistered/anonymous blog users only create user account which will be pending for admin approval;
Registered and logged in users (Authenticated users) can add new posts, view posts (Ownn and others approved), delete(own) posts (CRUD functionality);
Users can write comments to particular posts (Approved) by own or other users;
Validation for creating new posts, body must not be empty;
Spring Security authentication and authorization rules ensures that users only able to delete their own posts, comments and reactions (LIKE/DISLIKE);

Made an effort to write clean OOP code to best understanding, like separation of concerns and encapsulation of internal workings of the class to hide details from outside while providing a simple interface to work with a class and there should be no to little pain adding new functionality.

How to set up the application
Open terminal and use git clone command to download the remote GitHub repository to your computer:

git clone https://github.com/Mehedi32HSTU/blogging-application.git
It will create a new folder with same name as GitHub repository "blogging-application". All the project files and git data will be cloned into it. After cloning complete change directories into that new folder:

cd blogging-application
How to use
To launch the application run this command (uses maven wrapper):

$ ./mvnw clean spring-boot:run
Or using your installed maven version:

$ mvn clean spring-boot:run
For interacting with application one can use a browser. By default, application uses Tomcat which listening on port: 9091, means you can reach it if run on a local machine by hitting URL http://localhost:9091.

##### Initially an user is created with with ROLE_ADMIN. Credentials given bellow:

    "username": "default_admin",
    "password": "admin_1234"

##### As this is a RESTFull Project, for making it easy to demontrate and explore, all the APIs collection file is attached in the repository. To Explore the project, import the collection into POSTMAN REST Client and use the APIs.
##### All APIs are categorized according to their functionalities.

    baseAPI : http://localhost:9091
#### Database:
    H2-Console: http://localhost:9091/h2-console
    Username of H2-database: h2
    Password: root


