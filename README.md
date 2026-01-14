# 🚀 Fully Completed Microservices Project (Spring Boot + Spring Cloud)

This repository contains a **fully completed Microservices Architecture project** built using **Spring Boot 3.2.5** and **Java 21**.  
It follows modern distributed system patterns using **Spring Cloud 2023.0.1** such as:

✅ Service Discovery  
✅ Centralized Config Management  
✅ API Gateway Routing  
✅ Distributed Tracing  
✅ Circuit Breaker / Resilience  
✅ Kafka Messaging  

---

## 🛠️ Tech Stack

- **Java 21**
- **Spring Boot 3.2.5**
- **Spring Cloud 2023.0.1**
- **Spring Cloud Config Server**
- **Netflix Eureka**
- **Spring Cloud Gateway**
- **Kafka**
- **Zipkin**
- **Circuit Breaker (Spring Cloud Circuit Breaker / Resilience4j)**
- **Maven**

---

## 📌 Microservices Included

### ✅ Config Server
Provides centralized configuration for all microservices. Uses **Spring Cloud Config Server**.

### ✅ Discovery Service (Eureka Server)
Service registry using **Netflix Eureka**. Enables service discovery for other microservices.

### ✅ Gateway Service
API Gateway for routing requests to appropriate microservices. Uses **Spring Cloud Gateway**.  
Includes **distributed tracing** and **circuit breaker** support.

### ✅ Customer Service
Manages customer data and operations. Integrated with **Eureka Discovery**.

### ✅ Product Service
Manages product information. Integrated with **Eureka Discovery**.

### ✅ Order Service
Manages orders and their statuses. Integrated with **Eureka Discovery**.

### ✅ Payment Service
Processes payments. Uses **Eureka Discovery** and **Zipkin** for tracing.

### ✅ Notification Service
Handles notifications and alerts. Uses **Kafka** for messaging.

---

## ⭐ Key Features

### 🔍 Service Discovery
All microservices register with the **Eureka server** for easy discovery.

### ⚙️ Centralized Configuration
Configurations are managed centrally using the **Spring Cloud Config Server**.

### 🌐 API Gateway
**Spring Cloud Gateway** is used for routing and handling cross-cutting concerns like security, monitoring, and resilience.

### 🧵 Distributed Tracing
**Zipkin** is used for tracing requests across microservices.

### 🛡️ Circuit Breaker
Circuit breaking capabilities provided by **Spring Cloud Circuit Breaker**.

### 📩 Messaging
**Kafka** is used for asynchronous communication between microservices.

---

## ✅ Prerequisites

Make sure you have the following installed:

- **Java 21 or later**
- **Maven**
- **Docker (optional, for containerized deployment)**

---

## ▶️ Running the Microservices

### 1. Clone the repository

```bash
git clone https://github.com/PramithaMJ/fully-completed-microservices.git
cd fully-completed-microservices
````

### 2. Start Config Server
```bash
cd config-server
mvn spring-boot:run
```

### 3. Start Discovery Service
```bash
cd discovery
mvn spring-boot:run
```

### 4. Start Other Microservices

Start the remaining microservices in any order. Ensure they are configured to register with the Discovery Service.
```bash
cd <microservice-name>
mvn spring-boot:run
```
