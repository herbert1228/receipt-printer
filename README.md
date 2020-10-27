This application is implemented with Java (Spring boot), MongoDB, ReactJs and Nginx.
You may use docker-compose to start the containers.

<pre>
Prerequisite: 
    1. Docker installed
    2. modifly ./mongo/init-db.d/seed.js in order to change the default data in mongo db
</pre>

1. Run <code>docker-compose -f docker-compose.yaml up --build</code> on project base directory, it will
    - run the client (ReactJS), server (Spring), MongoDB, nginx on seperate containers
    - init MongoDB with ./mongo/init-db.d/seed.js if the db has not been initiallized before
    - use nginx to reverse proxy the client port from 3000 to 80
    - run spring on port 8080 as a restful api server

2. Navigate to localhost:80 on your browser

3. The client has use case 1 input by default. You may enter name, price, qty and click add button to add an item, or click 'X' to remove one. You may also change to location at the top (Please note that all fields are case sensitive)

4. Click 'Generate Receipt' to view the receipt for the shopping cart

5. (Optional) You may run the test cases with maven. The classes for test cases are
    - ./server/src/test/java/com/pinnacle/receipt/ReceiptApplicationTests.java (SpringBootTest)
    - ./server/src/test/java/com/pinnacle/receipt/WebMockTests.java (Mockito)