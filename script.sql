CREATE DATABASE IF NOT EXISTS PrasadFashion;

USE PrasadFashion;

CREATE TABLE IF NOT EXISTS Customer
(
    cusID      VARCHAR(6)  NOT NULL,
    cusName    VARCHAR(30) NOT NULL,
    cusNIC     VARCHAR(15),
    cusAddress VARCHAR(30),
    contact    VARCHAR(20),
    CONSTRAINT PRIMARY KEY (cusID)
);

CREATE TABLE IF NOT EXISTS Cashier
(
    cashierID      VARCHAR(6)  NOT NULL,
    cashierName    VARCHAR(30) NOT NULL,
    cashierNIC     VARCHAR(15),
    cashierAddress VARCHAR(30),
    contact        VARCHAR(20),
    password       VARCHAR(30),
    CONSTRAINT PRIMARY KEY (cashierID)
);

CREATE TABLE IF NOT EXISTS Employee
(
    employeeID      VARCHAR(6)  NOT NULL,
    employeeName    VARCHAR(30) NOT NULL,
    employeeNIC     VARCHAR(15),
    employeeAddress VARCHAR(30),
    contact         VARCHAR(20),
    state           VARCHAR(20),
    CONSTRAINT PRIMARY KEY (employeeID)
);


CREATE TABLE IF NOT EXISTS Brand
(
    brandID     VARCHAR(6) NOT NULL,
    brandName   VARCHAR(20),
    description VARCHAR(30),
    CONSTRAINT PRIMARY KEY (brandID)
);

CREATE TABLE IF NOT EXISTS Category
(
    categoryID   VARCHAR(6) NOT NULL,
    categoryName VARCHAR(30),
    CONSTRAINT PRIMARY KEY (categoryID)
);

CREATE TABLE IF NOT EXISTS SubCategory
(
    subCategoryID   VARCHAR(6) NOT NULL,
    subCategoryName VARCHAR(30),
    CONSTRAINT PRIMARY KEY (subCategoryID)
);


CREATE TABLE IF NOT EXISTS Product
(
    productID     VARCHAR(6) NOT NULL,
    name          VARCHAR(50),
    material      VARCHAR(20),
    size          VARCHAR(10),
    colorCode     VARCHAR(10),
    unitPrice     DECIMAL(8, 2),
    qtyOnHand     INT,
    brandID       VARCHAR(6),
    categoryID    VARCHAR(6),
    subCategoryID VARCHAR(6),
    imageSrc      VARCHAR(60),
    CONSTRAINT PRIMARY KEY (productID),
    CONSTRAINT FOREIGN KEY (brandID) REFERENCES Brand (brandID) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FOREIGN KEY (categoryID) REFERENCES Category (categoryID) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FOREIGN KEY (subCategoryID) REFERENCES SubCategory (subCategoryID) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS `Order`
(
    orderID    VARCHAR(6) NOT NULL,
    orderDate  VARCHAR(20),
    orderTime  VARCHAR(20),
    cusID      VARCHAR(6),
    cashierID  VARCHAR(6),
    status     VARCHAR(20),
    totalPrice DECIMAL(10, 2),
    CONSTRAINT PRIMARY KEY (orderID),
    CONSTRAINT FOREIGN KEY (cashierID) REFERENCES Cashier (cashierID) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS OrderDetail
(
    orderID    VARCHAR(6) NOT NULL,
    productID  VARCHAR(6) NOT NULL,
    productQTY INT,
    discount   DECIMAL(8, 2),
    totalPrice DECIMAL(10, 2),
    CONSTRAINT PRIMARY KEY (orderID, productID)
);

CREATE TABLE IF NOT EXISTS Payment
(
    invoiceNo     VARCHAR(6) NOT NULL,
    orderID       VARCHAR(6) NOT NULL,
    paymentMethod VARCHAR(20),
    totalAmount   DECIMAL(10, 2),
    CONSTRAINT PRIMARY KEY (invoiceNo),
    CONSTRAINT FOREIGN KEY (orderID) REFERENCES `Order` (orderID) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS ReturnOrder
(
    returnNo   VARCHAR(6) NOT NULL,
    invoiceNo  VARCHAR(6) NOT NULL,
    returnDate VARCHAR(20),
    totalPrice DECIMAL(10, 2),
    CONSTRAINT PRIMARY KEY (returnNo),
    CONSTRAINT FOREIGN KEY (invoiceNo) REFERENCES Payment (invoiceNo) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS ReturnDetail
(
    returnNo    VARCHAR(6) NOT NULL,
    productID   VARCHAR(6) NOT NULL,
    qty         INT,
    description VARCHAR(20),
    CONSTRAINT PRIMARY KEY (returnNo,productID)
);

CREATE TABLE IF NOT EXISTS Income
(
    recordNo    VARCHAR(6) NOT NULL,
    date        VARCHAR(20),
    invoiceNo   VARCHAR(6),
    salaryID    VARCHAR(6),
    description VARCHAR(30),
    balance     DECIMAL(10, 2),
    CONSTRAINT PRIMARY KEY (recordNo)
);

CREATE TABLE IF NOT EXISTS Salary
(
    salaryID    VARCHAR(6) NOT NULL ,
    salDate VARCHAR(20) NOT NULL,
    salary  DECIMAL(10, 2),

    CONSTRAINT PRIMARY KEY (salaryID)
);

CREATE TABLE IF NOT EXISTS SalaryDetail
(
    salDate       VARCHAR(20) NOT NULL,
    employeeSalID VARCHAR(6)  NOT NULL,
    status        VARCHAR(30),
    workDays      INT,
    epf           DECIMAL(10, 2),
    basicSalary   DECIMAL(10, 2),
    CONSTRAINT PRIMARY KEY (salDate,employeeSalID)
);

CREATE TABLE IF NOT EXISTS Attendance
(
    attID      VARCHAR(6) NOT NULL,
    employeeID VARCHAR(6) NOT NULL,
    inTime     VARCHAR(20),
    outTime    VARCHAR(20),
    date       VARCHAR(20),
    CONSTRAINT PRIMARY KEY (attID)
);

SHOW TABLES;

DESCRIBE Customer;
DESCRIBE Cashier;
DESCRIBE Employee;
DESCRIBE Product;
DESCRIBE `Order`;
DESCRIBE OrderDetail;
DESCRIBE Payment;
DESCRIBE Income;
DESCRIBE Attendance;
DESCRIBE Salary;
DESCRIBE SalaryDetail;


select SUM(balance)
From income;
select date, SUM(balance)
From income
Group By date;
select COUNT(*)
From income
where description = 'salary';
select date, SUM(balance)
From income
where description = 'sale';
select date, SUM(balance)
From income
where description = 'salary';

select productID, discount, SUM(productQty), SUM(totalPrice), orderID
From orderDetail
Group By productID
ORDER By SUM(productQty) DESC;


USE PrasadFashion;

SELECT *
FROM Customer;
SELECT *
FROM Cashier;
SELECT *
FROM Employee;
SELECT *
FROM Product;
SELECT *
FROM `Order`;
SELECT *
FROM OrderDetail;
SELECT *
FROM Payment;
SELECT *
FROM Income;
SELECT *
FROM Attendance;
SELECT *
FROM Salary;
SELECT *
FROM SalaryDetail;



