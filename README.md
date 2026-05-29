# Expense Tracker API

A comprehensive financial management system that allows users to create, read, and delete expenses and incomes while maintaining detailed records and balance tracking.

## Features
- **Expense Management**: Create, read, and delete expense records
- **Income Management**: Track income sources and amounts
- **Category Labels**: Create custom labels to organize and categorize expenses and incomes
- **Financial Balance**: View your wallet balance and financial overview
- **Transaction Tracking**: Keep detailed records of all financial transactions

## Technology Stack
- **Language**: Java
- **Type**: REST API

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 11 or higher
- Maven.

### Installation

1. Clone the repository:
```bash
git clone https://github.com/lopezenzoa/expense-tracker-api.git
cd expense-tracker-api
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The API will be available at http://localhost:8080

## API Endpoints

### Expenses
    GET /api/expenses/me - Retrieve all expenses
    POST /api/expenses/me/add - Create a new expense
    DELETE /api/expenses/{id} - Delete an expense

### Incomes
    GET /api/incomes/me - Retrieve all incomes
    POST /api/incomes/me/add - Create a new income
    DELETE /api/incomes/{id} - Delete an income

### Labels
    GET /api/labels/all - Retrieve all labels
    POST /api/labels/add - Create a new label
    UPDATE /api/labels/update - Update a label

## Project Structure

    expense-tracker-api/
    ├── src/
    │   ├── main/
    │   │   ├── java/
    │   │   └── resources/
    │   └── test/
    ├── pom.xml
    └── README.md

## Contributing
Contributions are welcome! Please feel free to submit a Pull Request.

## License
This project is open source and available under the MIT License.