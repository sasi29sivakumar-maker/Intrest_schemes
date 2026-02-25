Interest Scheme Management System – Java Servlet & JDBC
Description

InterestSchemeManagement is a Java Servlet–based banking application that manages Fixed Deposit interest schemes. The system allows customers to open fixed deposit accounts with defined business rules and automatically applies monthly interest until account maturity.

The application uses MySQL for persistent storage, follows a layered architecture, and is packaged as a WAR file deployed on Apache Tomcat. It demonstrates real-world Java web development using Servlets, JDBC, authentication, and schedulers without Spring or other frameworks.

Features
User Authentication

User registration

Secure password hashing using BCrypt

Login with JWT token generation

Token-based authentication for protected APIs

Customer Management

Create customer

Fetch all customers

Customer linked to authenticated user

Account Management

Create Fixed Deposit account

Fetch all accounts

Deposit validation based on business rules

Automatic maturity tracking

Interest Management

Monthly interest calculation

Interest applied only to active accounts

Interest stored per account

Fetch interest history by account

Automated Interest Scheduler

Interest calculation runs automatically using Quartz Scheduler

Applies interest monthly for all eligible accounts

Stops interest after maturity date

Business Rules

Minimum deposit amount: ₹1,00,000

Minimum tenure: 3 years

Account type: Fixed Deposit

Interest rate: 7.2% annually

Interest applied monthly

Interest stops after maturity date

Invalid deposits are rejected

Technologies Used

Java (Servlets)

JDBC

MySQL

Apache Tomcat

Maven

JWT Authentication

BCrypt Password Hashing

HikariCP Connection Pooling

Quartz Scheduler

Project Structure

Servlets – Handle HTTP requests and responses

Services – Business logic and validations

DAOs – Database operations using JDBC

Models – Entity classes

Utils – JWT, password hashing, DB connection

Scheduler – Automated interest calculation

API Endpoints
Authentication APIs

POST /register

POST /login

Customer APIs

POST /customer

GET /customer

Account APIs

POST /account

GET /account

Interest APIs

GET /interest?accountId=1

Sample Requests
Register User
{
  "username": "arun",
  "password": "1234"
}
Customer
{
  "userId": 1,
  "customerName": "Arun Kumar",
  "gender": "Male",
  "phoneNo": "9876543210",
  "email": "arun@gmail.com",
  "address": "Chennai",
  "aadharNo": "123456789012"
}
Account
{
  "customerId": 1,
  "accountNumber": "ACC1001",
  "amount": 100000,
  "tenure": 3
}
Interest Calculation Logic

Annual Interest = Principal × 7.2 / 100

Monthly Interest = Annual Interest / 12

Interest is applied only if:

Account is active

Current date is before maturity date

Monthly anniversary matches account creation date

Interest records store:

account_id

interest_amount

applied_date

Scheduler Logic

Quartz Scheduler runs automatically on application startup

Flow:

Fetch all active accounts

Check maturity date

Validate monthly anniversary

Calculate interest

Store interest record

Database Design
Tables

users

customer

account

interest

Relationships

User → Customer → Account → Interest

Security Implementation

Password hashing using BCrypt

JWT token authentication

Secret key signing

Token expiration (30 minutes)

Server-side token validation

Database Connection

Uses HikariCP connection pooling for:

High performance

Connection reuse

Efficient resource management

Deployment

Build WAR using Maven

Deploy WAR in Apache Tomcat webapps folder

Configure MySQL database

Start Tomcat server
