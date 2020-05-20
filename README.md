# Chat

## DESCRIPTION
This is the simple Java Messanger.





## Requirments: 
1. JDK
2. MySQL server


## DataBase setup. 

Create the DataBase in MySQL. 

###### ``` create database chat; ``` 

Select DB

###### ``` use chat;  ```

Create users table 

 ``` 
 CREATE TABLE users
( id int NOT NULL AUTO_INCREMENT,
  name varchar(250) NOT NULL,
  password varchar(250),
  CONSTRAINT users_pk PRIMARY KEY (id)
);
```
## Running the app

Insert your username and password in Db class (Client module) to connect to MySQL.

Default values: 

url = "jdbc:mysql://localhost:3306/chat?serverTimezone=Asia/Almaty";

user = "root";

sqlPass = "root";

Start the ChatServer(server module) the first, then run UserLogin(client module).
