# Transporter Optimizer

A Spring Boot backend application for optimizing transporter assignments to delivery lanes across India. The system minimizes total cost while ensuring all lanes are covered and transporter usage stays within specified limits.

## 🎯 Objectives

1. **Minimize total cost** of assigning transporters to lanes
2. **Maximize transporter usage** up to a user-specified limit
3. **Ensure every lane is covered** by at least one transporter

## 🛠️ Technology Stack

- **Java 17**
- **Spring Boot 3.2.6**
- **Spring Data JPA**
- **H2 Database** (in-memory)
- **Maven**

## 🚀 Quick Start

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Build and Run

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### H2 Database Console

Access the H2 console at: `http://localhost:8080/h2-console`

- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (leave empty)

## 📡 API Endpoints

### 1. Input Data

**POST** `/api/v1/transporters/input`

Accepts lanes and transporters with their quotes.

**Request Body:**

```json
{
  "lanes": [
    {
      "id": 1,
      "origin": "Mumbai",
      "destination": "Delhi"
    },
    {
      "id": 2,
      "origin": "Chennai",
      "destination": "Kolkata"
    }
  ],
  "transporters": [
    {
      "id": 1,
      "name": "Transporter T1",
      "laneQuotes": [
        {
          "laneId": 1,
          "quote": 5000
        },
        {
          "laneId": 2,
          "quote": 7000
        }
      ]
    },
    {
      "id": 2,
      "name": "Transporter T2",
      "laneQuotes": [
        {
          "laneId": 1,
          "quote": 6000
        },
        {
          "laneId": 2,
          "quote": 6500
        }
      ]
    }
  ]
}
```

### 2. Assignment Optimization

**POST** `/api/v1/transporters/assignment`

Returns optimal transporter assignments.

**Request Body:**

```json
{
  "maxTransporters": 3
}
```

**Response:**

```json
{
  "status": "success",
  "totalCost": 11500,
  "assignments": [
    {
      "laneId": 1,
      "transporterId": 1
    },
    {
      "laneId": 2,
      "transporterId": 2
    }
  ],
  "selectedTransporters": [1, 2]
}
```

## 🧠 Optimization Algorithm

The system uses a **Greedy Algorithm** approach:

1. For each lane, find the transporter with the lowest quote
2. Ensure the number of selected transporters doesn't exceed `maxTransporters`
3. If a transporter is already selected, prefer using it for additional lanes
4. Calculate total cost and return optimal assignments

## 🏗️ Architecture

```
Controller Layer (REST APIs)
    ↓
Service Layer (Business Logic + Optimization)
    ↓
Repository Layer (Data Access)
    ↓
Entity Layer (JPA Models)
```

### Key Components:

- **Controller**: `TransporterAssignmentController` - Handles HTTP requests
- **Service**: `TransporterAssignmentService` - Business logic and optimization
- **Repository**: JPA repositories for data persistence
- **Entity**: `Lane`, `Transporter`, `LaneQuote` - Database models
- **DTO**: Request/Response data transfer objects

## 🧪 Testing

Run the test suite:

```bash
mvn test
```

The test includes a sample scenario with:

- 2 lanes (Mumbai-Delhi, Chennai-Kolkata)
- 2 transporters with different quotes
- Verification of cost optimization and assignment correctness

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/example/transporteroptimizer/
│   │   ├── controller/
│   │   │   └── TransporterAssignmentController.java
│   │   ├── service/
│   │   │   └── TransporterAssignmentService.java
│   │   ├── repository/
│   │   │   ├── LaneRepository.java
│   │   │   ├── TransporterRepository.java
│   │   │   └── LaneQuoteRepository.java
│   │   ├── model/
│   │   │   ├── Lane.java
│   │   │   ├── Transporter.java
│   │   │   └── LaneQuote.java
│   │   ├── dto/
│   │   │   ├── InputRequestDTO.java
│   │   │   ├── AssignmentRequestDTO.java
│   │   │   ├── AssignmentResponseDTO.java
│   │   │   └── ...
│   │   └── TransporterOptimizerApplication.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/example/transporteroptimizer/service/
        └── TransporterAssignmentServiceTest.java
```

## 🔧 Configuration

Key configuration in `application.properties`:

- H2 in-memory database
- JPA auto-ddl enabled
- H2 console enabled for debugging

## 🚀 Future Enhancements

- Implement Hungarian Algorithm for optimal assignment
- Add validation and error handling
- Support for weighted constraints
- Real-time optimization updates
- Integration with external logistics systems
