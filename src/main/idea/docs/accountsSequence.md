```mermaid
sequenceDiagram
actor User
participant LoginFilter
participant AccountServlet
participant AccountService
participant AccountDAO
participant DbConnection

User ->> LoginFilter: Request with JWT
LoginFilter ->> Jwt: validateToken()
LoginFilter -->> AccountServlet: allowed

AccountServlet ->> AccountService: insert()
AccountService ->> AccountDAO: insert()
AccountDAO ->> DbConnection: getConnection()
AccountDAO -->> AccountService: success
AccountService -->> AccountServlet: success
AccountServlet -->> User: Account Created
```