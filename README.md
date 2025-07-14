# Simple Store Authentication Demo

A demo project showcasing **Spring Security** Basic Authentication using **JSESSIONID** for session management. This is a simple online store where users can register, authenticate, and purchase articles.

---

## Features

- Basic Authentication with Spring Security  
- Password hashing with BCrypt  
- Custom `AuthenticationProvider` using database-backed user tables  
- Three user roles with different privileges:  
  - **USER**: Registered customers  
  - **ADMIN**: Fixed admin account (`admin@prova.it` / `admin`)  
  - **TRACK**: Responsible for order tracking (`track@example.it` / `track`)

---

## User Roles & Accounts

| Role  | Description                         | Email               | Password |
|-------|-----------------------------------|---------------------|----------|
| USER  | Regular users who must register    | -                   | -        |
| ADMIN | Administrator with full privileges | admin@prova.it      | admin    |
| TRACK | Order tracking operator            | track@example.it    | track    |

---

## Overview

This project demonstrates a basic authentication mechanism built with Spring Security, featuring:

- Secure password storage using BCrypt  
- A custom authentication provider with user data stored in database tables  
- Role-based access control using annotations (`@PreAuthorize`)  
- Session management via JSESSIONID  

It serves as a foundational example for building authentication in real-world applications.

---

## Important Notes

**Basic Authentication has inherent security limitations**, such as:

- Credentials are sent with every request (risk of interception if not using HTTPS)  
- Challenges around secure credential storage and transmission between frontend and backend  

This demo should **not be used in production** as-is. For more secure authentication, consider:  

- JWT (JSON Web Tokens)  
- OAuth 2 / OpenID Connect  

---

## Getting Started

### Prerequisites

- Java 11+  
- Maven or Gradle  
- A configured relational database (MySQL, PostgreSQL, etc.)

### Running the Application

1. Clone the repository:  
   ```bash
   git clone https://github.com/yourusername/store-auth-demo.git
