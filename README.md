# 🗓️ Employee Leave Tracker

A production-ready **Spring Boot** backend system to automate employee leave application, approval, and tracking workflows — with role-based access control and a clean layered architecture.

---

## 🚀 Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 4.x, Spring MVC |
| Security | Spring Security (Role-Based) |
| ORM | Spring Data JPA / Hibernate |
| Database | PostgreSQL |
| Mapping | MapStruct |
| Frontend (optional) | Thymeleaf |
| Build Tool | Maven |
| API Docs | Swagger / OpenAPI |

---

## ✨ Features

- ✅ Employee can apply for leave
- ✅ Manager can approve / reject leave requests
- ✅ Role-based access control (`ROLE_EMPLOYEE`, `ROLE_MANAGER`, `ROLE_ADMIN`)
- ✅ Leave balance tracking per employee
- ✅ Leave history with status (PENDING / APPROVED / REJECTED)
- ✅ Layered architecture: Controller → Service → DAO
- ✅ Global exception handling & input validation
- ✅ Swagger UI for API exploration

---

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/deepak/leavetracker/
│   │   ├── controller/       # REST Controllers
│   │   ├── service/          # Business Logic
│   │   ├── repository/       # DAO / JPA Repositories
│   │   ├── model/            # JPA Entities
│   │   ├── dto/              # Request & Response DTOs (MapStruct)
│   │   ├── security/         # Spring Security Config
│   │   └── exception/        # Global Exception Handler
│   └── resources/
│       └── application.properties
└── test/
```

---

## 📌 API Endpoints

### Auth
| Method | Endpoint | Role | Description |
|---|---|---|---|
| `POST` | `/api/auth/register` | Public | Register new user |
| `POST` | `/api/auth/login` | Public | Login and get session |

### Leave Management
| Method | Endpoint | Role | Description |
|---|---|---|---|
| `POST` | `/api/leaves/apply` | Employee | Apply for leave |
| `GET` | `/api/leaves/my` | Employee | Get my leave history |
| `GET` | `/api/leaves/pending` | Manager | View all pending requests |
| `PUT` | `/api/leaves/{id}/approve` | Manager | Approve a leave |
| `PUT` | `/api/leaves/{id}/reject` | Manager | Reject a leave |
| `GET` | `/api/leaves/all` | Admin | Get all leave records |
| `DELETE` | `/api/leaves/{id}` | Admin | Delete a leave record |

### Employee
| Method | Endpoint | Role | Description |
|---|---|---|---|
| `GET` | `/api/employees` | Admin | Get all employees |
| `GET` | `/api/employees/{id}` | Manager/Admin | Get employee by ID |

---

## ⚙️ Getting Started

### Prerequisites
- Java 21+
- PostgreSQL (running locally)
- Maven 3.8+

### 1. Clone the repository
```bash
git clone https://github.com/rogerkd/employee-leave-tracker.git
cd employee-leave-tracker
```

### 2. Configure the database

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/leave_tracker
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### 3. Run the application
```bash
./mvnw spring-boot:run
```

App will start at: `http://localhost:8080`

### 4. Explore APIs
Swagger UI: `http://localhost:8080/swagger-ui.html`

---

## 🔐 Roles & Access

| Role | Permissions |
|---|---|
| `EMPLOYEE` | Apply leave, view own history |
| `MANAGER` | View & approve/reject team's leaves |
| `ADMIN` | Full access to all records |

---

## 🗃️ Database Schema (Overview)

```
employees       — id, name, email, role, department
leave_requests  — id, employee_id, leave_type, from_date, to_date, status, reason
leave_balance   — id, employee_id, total, used, remaining
```

---

## 👨‍💻 Author

**Deepak Kumar**  
[LinkedIn](https://linkedin.com) • [GitHub](https://github.com/rogerkd) • deepakt90su30@gmail.com

---

## 📄 License

This project is open-source and available under the [MIT License](LICENSE).
