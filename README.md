<div align="center"> <h1>🛒 E-Commerce Backend API</h1> <p>A scalable and secure E-Commerce backend built using Spring Boot.  
This project provides RESTful APIs for managing users, products, orders, authentication, and more.
</p> </div>

---

## 📌 Overview

This backend system powers an online shopping platform, handling core business logic such as:

- User authentication & authorization (JWT)
- Products management
- Shopping cart & order processing
- Email verification & security features

The API is designed to be consumed by any frontend (React, Angular, Mobile apps).

---

## 🚀 Features

### 🔐 Authentication & Security
- JWT-based authentication
- Role-based authorization (Admin / User)
- Email verification system

### 👤 User Management
- Register & Login
- Profile management

### 📦 Product Management
- Create, update, delete products
- Pagination

### 🛒 Cart & Orders
- Add/remove items from cart
- Checkout system
- Order history

### ⚙️ Admin Features
- Manage users, products, and orders
- Dashboard-ready APIs

### 💬 Reviews & Comments
- Authenticated users can add comments on products
- Users can view all comments for a specific product
- Each comment is linked to a user and product
- Supports validation and moderation-ready structure

---

## 🛠 Tech Stack

### Backend
- Java 17+
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate

### Database
- PostgreSQL

### Tools & Libraries
- JWT (Authentication)
- Lombok
- Maven
- JavaMailSender (Email Verification)

---

## 📂 Project Structure
```bash
src/main/java/com/yourapp
│
├── controller # REST Controllers
├── service # Business Logic
├── repository # Data Access Layer
├── entity # JPA Entities
├── dto # Data Transfer Objects
├── security # JWT & Security Config
└── config # App Configuration
```

---

## 🔗 API Endpoints

### 🔐 Auth
```bash
POST /api/v1/auth/register
POST /api/v1/auth/login
```

### 📦 Products
```bash
GET /api/v1/products
GET /api/v1/products/{productId}
POST /api/v1/products
PUT /api/v1/products/{productId}
DELETE /api/v1/products/{productId}
```

### 🛒 Cart
```bash
POST /api/v1/cart/add
GET /api/v1/cart
DELETE /api/v1/cart
DELETE /api/v1/cart/{productId}
```

### 🛒 Orders
```bash
GET /api/v1/orders
POST /api/v1/orders
GET /api/v1/orders/user
PUT /api/v1/orders/{orderId}/status
```

### 👤 Users
```bash
PATCH /api/v1/users/confirm-email
PATCH /api/v1/users/change-password
```

### 💬 Reviews & Comments
```bash
GET /api/v1/products/{productId}/comments
POST /api/v1/products/{productId}/comments
```

---

## 🧪 API Documentation
Swagger UI available at:
```bash
http://localhost:8080/swagger-ui.html
```

---

## 🔒 Security
- JWT Authentication
- Password encryption using BCrypt
- Role-based access control

---

## ⚙️ Installation & Setup

### 1️⃣ Clone Repository
```bash
git clone https://github.com/Mahmoudarafa0/E_commerce.git
cd E_commerce
```

### 2️⃣ Configure Database
```bash
spring.datasource.url=jdbc:postgresql://localhost:3306/ecommerce_db
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

### 3️⃣ Run the Application
```bash
mvn clean install
mvn spring-boot:run
```
