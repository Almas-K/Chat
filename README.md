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

###### ``` CREATE TABLE users
( id int NOT NULL AUTO_INCREMENT,
  name varchar(250) NOT NULL,
  password varchar(250),
  CONSTRAINT users_pk PRIMARY KEY (id)
);
```


