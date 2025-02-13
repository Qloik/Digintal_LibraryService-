# Snow Mountain Library Management System

## Overview
Snow Mountain Library is a comprehensive library management system built with Vue3 and Spring Framework. The project features a snow mountain-themed design that creates a serene and focused environment for users.

🌨️ [Live Demo](http://192.168.1.85:8080/show)

<img width="1462" alt="image" src="https://github.com/user-attachments/assets/8a9ad521-66ca-41d2-8c78-01936ff544e6" />

<img width="1217" alt="image" src="https://github.com/user-attachments/assets/9f97730a-f4db-43b3-8306-6fcd7a191b6c" />

<img width="1227" alt="image" src="https://github.com/user-attachments/assets/b1c81985-55cc-4d76-b4f7-cf9154698da7" />

<img width="1225" alt="image" src="https://github.com/user-attachments/assets/3aedfe89-3e06-4d1d-86a7-285a1fab361d" />

<img width="1225" alt="image" src="https://github.com/user-attachments/assets/ffc94341-675a-473a-9822-716e47b4ed2c" />

<img width="1219" alt="image" src="https://github.com/user-attachments/assets/041dfbe0-2ccb-41dd-ba43-67d94e0578d0" />

<img width="1227" alt="image" src="https://github.com/user-attachments/assets/3da68270-627c-4569-a3b9-ee00bb581723" />


## Features

### Core Functionalities
- **Book Management**
 - Book searching and browsing
 - Collection distribution visualization
 - Book rating and reviews
 - Book reservation system
 
- **User System**
 - User registration and authentication
 - JWT token-based security
 - Personal information management
 - Reading history tracking

- **Study Room Management**
 - Single/Double/Multi-person room booking
 - Appointment management
 - Real-time availability check

- **Community Features**
 - Discussion forum
 - Post and comment system
 - Automated content moderation
 - User interaction platform

### Administrative Features
- Book inventory management
- Damage compensation handling
- User blacklist management
- Library notice publication
- Book lending/return processing

## Tech Stack

### Frontend
- Vue 3
- TypeScript
- Element Plus UI
- Vuex for state management
- Vue Router
- Axios for API calls

### Backend
- Spring Boot
- MyBatis Plus
- JWT Authentication
- MySQL Database
- RESTful APIs

### Deployment
- Nginx for frontend serving
- Tomcat (embedded in Spring Boot)
- Tencent Cloud ECS


## Development Setup

### Frontend

# Install dependencies
npm install

# Serve with hot reload
npm run dev

# Build for production
npm run build

###  Backend

mvn clean package -DskipTests

# Run the application
java -jar JavaLibrary.jar
