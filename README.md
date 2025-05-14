
# Reward Points Calculation API

This Spring Boot application calculates reward points earned by customers based on their transaction history. It exposes a RESTful API to return monthly and total reward points for each customer.

## Features

- REST endpoint to retrieve reward points per customer
- Monthly and total reward breakdown
- Customizable reward rules
- Test coverage and clean architecture

User has to pass a dataset of transactions to Spring Boot API endpoint(http://localhost:9091/api/rewards),

API Input :List of tranctions 
[
  {
    "customerId": "c1",
    "amount": 140,
    "transactionDate": "2025-03-01"
  },
  {
    "customerId": "c1",
    "amount": 150,
    "transactionDate": "2025-03-15"
  },
  {
    "customerId": "c2",
    "amount": 200,
    "transactionDate": "2024-04-01"
  },
  {
    "customerId": "c2",
    "amount": 45,
    "transactionDate": "2024-05-01"
  }
]

API Output :

[
    {
        "customerId": "c1",
        "monthlyPoints": {
            "2025-03": 280
        },
        "totalPoints": 280
    },
    {
        "customerId": "c2",
        "monthlyPoints": {
            "2024-04": 250,
            "2024-05": 0
        },
        "totalPoints": 250
    }
]






Directory Structure :
1. Controller 
	RewardController.java
2. Service 
	RewardService.java
		Contains business logic
3. Model 
	*Transaction.java
		Represents a customer's transaction 
	*RewardResponse.java
		Response object for Customer'rewards points
4)Exception layer
       *GlobalExceptionHandler.java
       *InvalidTransactionException.java
5)Test :
         *RewardControllerTest.java :Integration Test
	*RewardServiceTest.java : Unit Test
		


