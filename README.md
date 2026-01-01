# Leave Planner – Backend (Spring Boot)

Backend service for the Leave Planner application built using **Spring Boot**, responsible for authentication, leave management, and role-based access control.

---

## 🚀 Features

- JWT-based authentication and authorization
- Role-based access control (Admin / User)
- Leave management (apply, view, approve/reject)
- User management APIs
- Centralized exception handling
- Secure REST endpoints

---

## 🛠️ Tech Stack

- Java 17
- Spring Boot
- Spring Security (JWT)
- JPA / Hibernate
- MySQL
- Maven

---

## 📦 Local Setup

### Prerequisites
- Java 17
- Maven
- MySQL

### Steps

```bash
git clone https://github.com/<your-username>/leave-planner-backend.git
cd leave-planner-backend
mvn clean install
mvn spring-boot:run
```

Backend will run at: `http://localhost:8080`

---
## 🗄️ Database Schema (MySQL)
```
CREATE DATABASE leaveplanner;
USE leaveplanner;
```
### 👤 Users Table
```
CREATE TABLE `users` (
`id` int NOT NULL AUTO_INCREMENT,
`name` varchar(255) DEFAULT NULL,
`empid` varchar(255) DEFAULT NULL,
`role` varchar(255) DEFAULT NULL,
`manager_access` int DEFAULT NULL,
`location` varchar(255) DEFAULT NULL,
`module` varchar(255) DEFAULT NULL,
`email` varchar(255) DEFAULT NULL,
`created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
`password` varchar(255) DEFAULT NULL,
PRIMARY KEY (`id`),
UNIQUE KEY `empid` (`empid`),
UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
```
### 📅 Leaves Table
```
CREATE TABLE `leaves` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `leave_date` date NOT NULL,
  `type` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_user_date` (`user_id`,`leave_date`),
  CONSTRAINT `leaves_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
```

---
## ⚙️ Application Configuration
Update `application.properties`:

```
spring.datasource.url=jdbc:mysql://localhost:3306/leaveplanner
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

server.port=8080
```

---
## 🔐 Security
- Stateless authentication using JWT
- Spring Security filter chain for request validation
- Role-based authorization on REST endpoints

---

## 📡 API Overview

| Method | Endpoint       | Description                  |
| ------ | -------------- | ---------------------------- |
| POST   | /auth/login    | User login                   |
| POST   | /auth/register | Register new user            |
| GET    | /leaves        | Fetch leaves                 |
| POST   | /leaves        | Apply for leave              |
| PUT    | /leaves/{id}   | Approve / Reject leave       |
| GET    | /users         | Fetch all users (Admin only) |

---
## 🚀 Deployment

- Can be deployed on any Java-supported platform

- Environment variables used for production configuration

- No CI/CD configured yet (manual deployment)

---

## 🔮 Future Enhancements

- CI/CD using GitHub Actions

- Dockerization

- API documentation using Swagger/OpenAPI

- Improved logging and monitoring
---
## 📄 License

This project is for learning and demonstration purposes.

