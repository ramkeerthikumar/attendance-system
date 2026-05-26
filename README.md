# Attendance & Overtime Management System (Spring Boot)

## 📌 Project Overview
This is a Spring Boot backend system that manages worker attendance and automatically calculates overtime based on working hours.

---

## ⚙️ Features
- Worker Clock-In / Clock-Out
- Automatic total hours calculation
- Overtime calculation (> 8 hours/day)
- Overtime rate rules (1.5x for first 2 hours, 2x after)
- Attendance validation (no duplicate active sessions)
- Database persistence using MySQL
- REST APIs for integration

---

## 🧱 Tech Stack
- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- Maven

---

## 🗄️ Database Setup

## 🗄️ Database Setup

Create database:

```sql
CREATE DATABASE attendance_db;
