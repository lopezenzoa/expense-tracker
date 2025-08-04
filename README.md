# Expense Tracker API

This repository provides the backend API for the Expense Tracker application, enabling management of expenses, categories, users, authentication, and reporting.

## Getting Started

### Prerequisites

- Node.js (version >= 14)
- npm or yarn
- MongoDB (local or remote)

### Installation

1. **Clone the repository:**
   ```sh
   git clone https://github.com/lopezenzoa/expense-tracker.git
   cd expense-tracker
   ```

2. **Install dependencies:**
   ```sh
   npm install
   # or
   yarn install
   ```

3. **Configure environment variables:**
   - Copy `.env.example` to `.env` and update with your configuration:
     ```
     MONGODB_URI=your_mongodb_connection_string
     JWT_SECRET=your_jwt_secret
     PORT=3000
     ```

4. **Start the server:**
   ```sh
   npm start
   # or
   yarn start
   ```

   The API will be available at `http://localhost:3000`.

## API Usage

### Authentication

Most endpoints require JWT authentication.
- Register: `POST /api/auth/register`
- Login: `POST /api/auth/login`
- Use the token in the `Authorization` header:  
  `Authorization: Bearer <your_token>`

### Main Endpoints

- **Expenses**
  - `GET /api/expenses` — List expenses for the authenticated user
  - `POST /api/expenses` — Create an expense
  - `GET /api/expenses/:id` — Get details for a specific expense
  - `PUT /api/expenses/:id` — Update an expense
  - `DELETE /api/expenses/:id` — Delete an expense

- **Categories**
  - `GET /api/categories` — List categories
  - `POST /api/categories` — Create a category
  - `PUT /api/categories/:id` — Update a category
  - `DELETE /api/categories/:id` — Delete a category

- **Reports**
  - `GET /api/reports/summary` — Summarized expense reports

### Request Format

All requests and responses use JSON.

Example for creating an expense:
```json
POST /api/expenses
{
  "title": "Groceries",
  "amount": 50.25,
  "category": "Food",
  "date": "2025-08-01"
}
```

### Error Handling

Errors are returned with a descriptive message and HTTP status code.

## Contributing

1. Fork the repo.
2. Create a branch: `git checkout -b feature-name`
3. Commit changes.
4. Push to your branch.
5. Open a pull request.

## License

MIT License

---

For more details on specific endpoints, see the [API documentation](docs/API.md) if available or review the source code in the `src/` directory.