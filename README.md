# Interest Scheme Management System – Java Servlet & JDBC

## Description

InterestSchemeManagement is a Java Servlet–based banking application that manages Fixed Deposit interest schemes and automatically applies monthly interest until account maturity. The system allows customers to open fixed deposit accounts with defined business rules and ensures accurate interest calculation through automated processing.

The application uses MySQL for data storage. It is packaged as a WAR file and deployed on an Apache Tomcat server, demonstrating core Java web development concepts including Servlets, JDBC integration, authentication, and scheduled background processing.

---

## Features

Automatic calculation and application of monthly interest for Fixed Deposit accounts
Customer, Account, and Interest data management using relational database design
Business rule validation for deposit amount, tenure, and account eligibility
Automated interest processing using scheduler
Implemented using plain Java Servlets without any frameworks
Secure authentication using password hashing and token-based validation
Reusable JDBC database connection utility with proper exception handling
Data stored and retrieved using MySQL with optimized SQL queries

---

## Technologies Used

Java (Servlets)
JDBC
MySQL
Apache Tomcat
Maven
JWT Authentication
BCrypt Password Hashing
HikariCP Connection Pooling
Quartz Scheduler

---

## Project Structure

Servlets – Handle HTTP requests and responses
Services – Business logic for interest calculation and validation
DAOs – Database operations using JDBC
Models – Entity classes
Utils – JWT, password hashing, database connection
Scheduler – Automated monthly interest processing
MySQL – Data storage

---

## Functional Modules

### User Authentication

Register user
Login user
Password hashing using BCrypt
JWT token generation and validation

---

### Customer

Create customer
Get all customers
Customer linked to authenticated user

---

### Account

Create Fixed Deposit account
Get all accounts
Deposit validation based on business rules
Automatic maturity tracking

---

### Interest

Monthly interest calculation
Interest applied only to active accounts
Store interest history per account
Fetch interest by account ID

---

### Automated Interest Processing

Automatically checks eligible accounts
Calculates monthly interest
Stores interest records
Stops interest after maturity date

---

## Business Rules

Minimum deposit amount: ₹1,00,000
Minimum tenure: 3 years
Account type: Fixed Deposit
Interest rate: 7.2% annually
Interest applied monthly
Interest stops after maturity date
Invalid deposits are rejected

---

## API Endpoints

### Authentication APIs

POST /register
POST /login

---

### Customer APIs

POST /customer
GET /customer

---

### Account APIs

POST /account
GET /account

---

### Interest APIs

GET /interest?accountId=1

---

## Sample Requests

### Register User

```json
{
  "username": "arun",
  "password": "1234"
}
```

### Customer Request

```json
{
  "userId": 1,
  "customerName": "Arun Kumar",
  "gender": "Male",
  "phoneNo": "9876543210",
  "email": "arun@gmail.com",
  "address": "Chennai",
  "aadharNo": "123456789012"
}
```

### Account Request

```json
{
  "customerId": 1,
  "accountNumber": "ACC1001",
  "amount": 100000,
  "tenure": 3
}
```

---

## Interest Calculation Logic

System calculates interest using principal amount and annual interest rate.

Annual Interest = Principal × 7.2 / 100
Monthly Interest = Annual Interest / 12

Interest is applied only if:

* Account is active
* Current date is before maturity date
* Monthly anniversary matches account creation date

Interest records include:

* account_id
* interest_amount
* applied_date

---

## Scheduler Logic

Interest calculation runs automatically using a scheduler.

Flow:

1. Fetch all active accounts
2. Check maturity date
3. Validate monthly anniversary
4. Calculate interest
5. Store interest record

---

## Database Design

Tables:
users
customer
account
interest

Relationships:
User → Customer → Account → Interest

---

## Security Implementation

Password hashing using BCrypt
JWT token authentication
Secret key signing
Token expiration (30 minutes)
Server-side token validation

---

## Database Connection

Uses connection pooling for:
High performance
Connection reuse
Efficient resource management

---

## Deployment

Build WAR using Maven
Deploy WAR in Apache Tomcat webapps folder
Configure MySQL database
Start Tomcat server

---

## Purpose

This project demonstrates:
Servlet-based web application architecture
JDBC database integration
Secure authentication implementation
Scheduled background processing
Business rule validation
Real-world banking interest calculation

