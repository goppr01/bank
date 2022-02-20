# Getting Started

### Overview 
Spring boot project where operations related to bank can be performed. 

Operations include:
- Adding customers to Bank
- Deposit amount to Customer Account
- Withdraw amount from Customer Account
- Retrieving Bank Total Balance.

### Design 
- Each customer will have unique customer id.
- All deposits, withdraw will be done using customer id.
- No persistence is added and the customer details are stored in the memory using List
- Before doing any operation, customers need to be added.
- If the operation fails then the exceptions are raised.
  - NoCustomerException
  - InSufficientBalanceException
  - CustomerAlreadyExists

### Technologies and libraries used
- Spring boot
- Java
- Gradle for build
- Junit for testing


### Start the application
./gradlew bootRun

### Running tests
./gradlew clean test
