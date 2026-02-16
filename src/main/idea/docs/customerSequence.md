```mermaid
sequenceDiagram
actor User
participant LoginFilter
participant CustomerServlet
participant CustomerService
participant CustomerDAO
participant DbConnection

User ->> LoginFilter: Request with JWT
LoginFilter ->> Jwt: validateToken()
LoginFilter -->> CustomerServlet: allowed

CustomerServlet ->> CustomerService: insert()
CustomerService ->> CustomerDAO: insert()
CustomerDAO ->> DbConnection: getConnection()
CustomerDAO -->> CustomerService: success
CustomerService -->> CustomerServlet: success
CustomerServlet -->> User: Customer Created
```