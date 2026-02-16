```mermaid
sequenceDiagram
actor User
participant LoginFilter
participant InterestServlet
participant InterestService
participant InterestDAO
participant DbConnection

User ->> LoginFilter: Request with JWT
LoginFilter ->> Jwt: validateToken()
LoginFilter -->> InterestServlet: allowed

InterestServlet ->> InterestService: insert()/findByAccountId()
InterestService ->> InterestDAO: insert()/findByAccountId()
InterestDAO ->> DbConnection: getConnection()
InterestDAO -->> InterestService: result
InterestService -->> InterestServlet: result
InterestServlet -->> User: Response
```