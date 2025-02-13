# Snow Mountain Library Management System

## Overview
Snow Mountain Library is a comprehensive library management system built with Vue3 and Spring Framework. The project features a snow mountain-themed design that creates a serene and focused environment for users.

🌨️ [Live Demo](http://192.168.1.85:8080/show)

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
