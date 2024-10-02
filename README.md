## Getting started

1. Clone the repository:
   ```
   git clone https://github.com/jnp-2.git

2. Navigate to the project directory:
   ```
   cd jnp-2
   ```
   
3. Start the database:
   ```
    docker compose up -d 
    ```

4. Go to CMD:
    ```
   docker exec -it <container-name> mysql - u root -p
    ```
5. Grant permission to the user:
    ```
   GRANT ALL PRIVILEGES ON jnp.* TO 'dbuser'@'localhost';
   FLUSH PRIVILEGES;
    ```
6. Create table and insert value:
   ```
   USE jnp;
   CREATE TABLE students (id INT AUTO_INCREMENT PRIMARY KEY,studentCode VARCHAR(20) NOT NULL,name VARCHAR(100) NOT NULL,yob INT NOT NULL,address VARCHAR(255),gpa DOUBLE CHECK (gpa >= 0.0 AND gpa <= 4.0);
   INSERT INTO students (studentCode, name, yob, address, gpa) VALUES
    ('S001', 'Nguyen Van A', 2000, '123 Nguyen Trai, Ho Chi Minh', 3.5),
    ('S002', 'Tran Thi B', 2001, '456 Nguyen Trai, Ho Chi Minh', 3.0),
    ('S003', 'Le Van C', 2002, '789 Nguyen Trai, Ho Chi Minh', 2.5);
    ```
7. Run the application
   ```
   ServerRun.java
   ClientRun.java
   ```
