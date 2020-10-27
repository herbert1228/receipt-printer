This application is implemented with Java (Spring boot), MongoDB, ReactJs and Nginx.
You may use docker-compose to start the containers.

<pre>
Prerequisite: Docker installed
</pre>

<ins>Steps to use the app:</ins>

1. Run <code>docker-compose -f docker-compose.yaml up --build</code> on project base directory, it will
    - run the client (ReactJS), server (Spring), MongoDB, nginx on seperated containers
    - run MongoDB on port 27017 that stores the configuration settings for the server
    - init MongoDB with ./mongo/init-db.d/seed.js if the db has not been initiallized before
    - use nginx to reverse proxy the client port from 3000 to 80
    - run spring on port 8080 as a restful api server

2. Navigate to localhost:80 *(or simply localhost)* on your browser

3. The client has Use Case 1 inputted by default. You may enter name, price, qty and click add button to add an item, or click 'X' to remove one. You may also change the location at the top *(Please note that all fields are case sensitive)*

4. Click 'Generate Receipt' to view the receipt for the shopping cart

5. (Optional) You may run the test cases with maven. The classes for test cases are
    - ./server/src/test/java/com/pinnacle/receipt/ReceiptApplicationTests.java (SpringBootTest)
    - ./server/src/test/java/com/pinnacle/receipt/WebMockTests.java (Mockito)
