# ms-accounts

### Assumptions
- Relationship between Customer and Account is one-to-many : A customer shall contain multiple accounts, and an account can be held by one customer.
- Relationship between Account and Transaction is one-to-many : An account can have multiple transactions and a transaction corresponds to a single account.
- Account mapped to a Currency and that account will always contain transactions on the account currency.
- This microservice is placed behind an API gateway. All calls to the microservice will pass through the gateway.
- When the call received to the microservices it is an authenticated call that passed the client gateway and correct access tokens with authorizations attached to the header.
- A class has been implemented only for demonstration purposes. (UserService) this mimics the following responsibilities. 
  - Decoding the access token and getting the customer id attached to the user context. 
  - Performs entitlement checks whether the logged in user can perform the action such as reading an account transactions.

In an ideal situation the call to UserService should be a downstream call. In which case it should go through a service gateway where the calling service should have the valid scope approved and header should include the service token that should be verified by the gateway before allowing the call.

### API Specification
[accounts_v1.swagger.yaml](src/main/api/accounts_v1.swagger.yaml)

### APIs
```http
  GET v{version}/accounts
```

| Controller                                             | Description                |
| :--------                                              | :------------------------- |
| [AccountsController](src/main/java/com/anz/accounts/controller/AccountsController.java)  | Lists accounts by customer |

```http
  GET v{version}/accounts/{accountId}/transactions
```

| Controller                                             | Description                |
| :--------                                              | :------------------------- |
| [AccountTransactionsController](src/main/java/com/anz/accounts/controller/AccountTransactionsController.java)  | Lists accounts by customer |

####To run the app locally:
The docker-compose up will start the sidecars (postgres)  
```
cd src\main\docker
docker-compose up
gradle bootRun
```

####To run the tests locally:
```
cd src\main\docker
docker-compose up
gradle clean test
```

####To build locally:
```
cd src\main\docker
docker-compose up
gradle clean build
```

###Testing  - Example API endpoints
```
curl --location 'http://localhost:8080/v1/accounts' \
--header 'X-TRACE-ID: 123' \
--header 'X-ACCESS-TOKEN: 200'
```

```
curl --location 'http://localhost:8080/v1/accounts/100/transactions' \
--header 'X-TRACE-ID: 123' \
--header 'X-ACCESS-TOKEN: 200'
```
