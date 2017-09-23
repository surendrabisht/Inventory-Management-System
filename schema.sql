CREATE DATABASE Inventory;


GO


USE Inventory;

CREATE TABLE Admin
( 
			 users varchar(40), password varchar(40)
);

INSERT INTO Admin
VALUES( 'mentor', 'hcl' );

SELECT *
FROM Admin;

CREATE TABLE Records
( 
			 uname varchar(40), issue_Date varchar(40), return_Date varchar(40), srno int, item varchar(40), icode int
);

SELECT *
FROM Records;