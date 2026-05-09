# 📚 Library Management System - Spring Boot Backend

A complete Library Management System backend developed using Java Spring Boot, Spring Security, JWT Authentication, MySQL, and REST APIs.

This project supports:

- Admin Management
- Librarian Management
- Member Management
- Book Management
- Borrow & Return System
- JWT Authentication
- Role-Based Authorization

---

# 🚀 Technologies Used

- Java 17
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- REST API
- Lombok
- Swagger OpenAPI

---

# 📂 Project Structure

```bash
src/main/java/com/librarymanagementsystem/library_management
│
├── config
│   ├── CorsConfig.java
│   ├── JwtUtilFilter.java
│   ├── SecurityConfig.java
│   └── SwaggerConfig.java
│
├── controller
│   ├── AuthController.java
│   ├── BookController.java
│   ├── BorrowController.java
│   └── MemberController.java
│
├── dto
│   ├── BookDTO.java
│   ├── BorrowDTO.java
│   ├── LoginDTO.java
│   └── MemberDTO.java
│
├── exception
│   └── ResourceNotFoundException.java
│
├── model
│   ├── Book.java
│   ├── BorrowRecord.java
│   └── Member.java
│
├── repository
│   ├── BookRepository.java
│   ├── BorrowRepository.java
│   └── MemberRepository.java
│
├── service
│   ├── AuthService.java
│   ├── BookService.java
│   ├── BorrowService.java
│   └── MemberService.java
│
└── LibraryManagementApplication.java


| Role      | Access                         |
| --------- | ------------------------------ |
| ADMIN     | Full Access                    |
| LIBRARIAN | Manage Books & Borrow Requests |
| MEMBER    | Browse Books & Borrow History  |



User Login
   ↓
JWT Token Generated
   ↓
Frontend Stores Token
   ↓
Frontend Sends Token in Authorization Header
   ↓
Spring Security Validates Token
   ↓
Access Granted



📖 Features

✅ Authentication
Login API
Register API
JWT Token Authentication
Role-Based Authorization

✅ Book Management
Add Book
Update Book
Delete Book
Get All Books
Search Books
Filter Books by Category

✅ Member Management
Register Member
Update Member
Delete Member
View Members

✅ Borrow Management
Borrow Book Request
Approve Borrow Request
Reject Borrow Request
Return Book
Borrow History
