# Library Management System - Frontend

A modern React frontend for the Library Management System, built with Vite, Tailwind CSS, and connected to a Spring Boot backend.

## Tech Stack

- **Framework**: React 19 with Vite
- **Styling**: Tailwind CSS
- **HTTP Client**: Axios (Base URL: http://localhost:8080/api)
- **Routing**: React Router DOM v6
- **Notifications**: React-Toastify
- **Icons**: Heroicons

## Features

### 1. Dashboard
- Overview statistics (Books, Students, Categories)
- Recent books listing
- Quick navigation to all modules

### 2. Categories
- View all categories in a table
- Add new categories
- Delete categories
- Used as dropdown in Book forms

### 3. Books Inventory
- List all books with cover images (grid layout)
- Search by title, author, or ISBN
- Add new books with category selection
- View book details with physical copies

### 4. Book Copies (Physical Stock)
- Add physical copies with QR Code and Shelf location
- View all copies for a book with status (AVAILABLE/ISSUED)

### 5. Students
- Register new students with validations
- List all students with status badges
- Search students by ID Card
- Status: ACTIVE (green), BLOCKED (red)

### 6. Circulation Desk
- **Issue Panel**: Issue books by scanning QR and entering Student ID
- **Return Panel**: Return books by scanning QR, displays fine amount
- **Transaction History**: View all transactions with Renew and Pay Fine options

### 7. Admin Users
- Register new Admin/Librarian users

## Project Structure

```
src/
├── components/
│   ├── common/          # Reusable UI components
│   │   ├── Button.jsx
│   │   ├── Card.jsx
│   │   ├── LoadingSpinner.jsx
│   │   ├── Modal.jsx
│   │   ├── PageHeader.jsx
│   │   └── StatusBadge.jsx
│   └── layout/          # Layout components
│       ├── Layout.jsx
│       ├── Navbar.jsx
│       └── Sidebar.jsx
├── config/
│   └── axiosConfig.js   # Axios instance with interceptors
├── constants/
│   └── enums.js         # Enum constants
├── pages/               # Page components
│   ├── AdminUsers.jsx
│   ├── AddBook.jsx
│   ├── BookDetails.jsx
│   ├── BookList.jsx
│   ├── Categories.jsx
│   ├── Circulation.jsx
│   ├── Dashboard.jsx
│   └── Students.jsx
├── services/            # API services
│   ├── bookService.js
│   ├── categoryService.js
│   ├── copyService.js
│   ├── studentService.js
│   ├── transactionService.js
│   └── userService.js
├── App.jsx              # Main app with routes
├── index.css            # Tailwind imports
└── main.jsx             # Entry point
```

## Getting Started

### Prerequisites
- Node.js 18+
- Spring Boot backend running on http://localhost:8080

### Installation

```bash
# Install dependencies
npm install

# Start development server
npm run dev
```

The app will be available at http://localhost:5173

### Build for Production

```bash
npm run build
```

## API Endpoints

The frontend connects to these backend endpoints:

| Module | Endpoints |
|--------|-----------|
| Categories | GET, POST /categories, DELETE /categories/{id} |
| Books | GET, POST /books, GET /books/{id} |
| Copies | POST /copies/add, GET /copies/book/{bookId} |
| Students | GET, POST /students, GET /students/{cardId} |
| Transactions | POST /transactions/issue, return, renew/{id}, pay-fine/{id} |
| Users | POST /users/register |

## Error Handling

All API errors from the backend are automatically caught by Axios interceptors and displayed as toast notifications. The backend returns errors in this format:

```json
{
  "status": 400,
  "message": "Student is BLOCKED",
  "error": "Business Rule Violation"
}
```

