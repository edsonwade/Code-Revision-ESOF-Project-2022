# Code-Revision-ESOF-Project-2022

Code Revision about the ESOF project realized from University Fernando Pessoa in 2019.


### Table of Contents
- [about](#about)
- [Implementations](#implementations)
- [Contributing](#contributing)


## About
Implementing the best practical of Clean Code, Design Pattern ,Solid Principal and Unit Testing .


## Implementations
1. Maven v3.6.2
2. JPA(Java Persistence API),Hibernate
3. Flyway Migration (Mysql v8 and MariaDB)
4. Swagger (OpenAPI), Postman 
5. Docker Composer
6. Integration Testing (JUnit5 and Mockito),Functional Test(Cucumber)
7. Testcontainers(Docker Container)
8. Pipeline CI/CD with Github Actions


# University Appointment Service Specifications

## Overview
A service that enables students to schedule appointments with tutors/teachers (Explainers) based on their availability, course, and language preferences.

## Core Features

### 1. User Management
#### Student Features:
- Register/Login with university credentials
- Create and manage student profile
    - Personal information
    - Degree/Course information
    - Preferred languages
    - Academic history
- View and update profile information

#### Explainer (Tutor/Teacher) Features:
- Register/Login with university credentials
- Create and manage explainer profile
    - Academic qualifications
    - Areas of expertise
    - Languages spoken
    - Course associations
- Set and manage availability schedule

### 2. Appointment Management
#### For Students:
- Search for available explainers by:
    - Course
    - Language preference
    - Time slot
    - College/Department
- Book appointments with available explainers
- View upcoming appointments
- Cancel/Reschedule appointments (with time restrictions)
- Rate and review past appointments
- Receive notifications for:
    - Appointment confirmation
    - Upcoming appointments
    - Changes/Cancellations
    - Reminders

#### For Explainers:
- Set recurring availability patterns
- Block/unblock time slots
- View scheduled appointments
- Accept/Reject appointment requests
- Cancel appointments (with justification)
- View student profiles for scheduled appointments
- Mark appointments as completed

### 3. Course and College Management
- Map courses to colleges/departments
- Associate explainers with specific courses
- Track popular courses/time slots
- Manage course prerequisites

### 4. Availability Management
- Define working hours
- Set recurring availability patterns
- Handle timezone differences
- Block specific dates (holidays, personal time)
- Configure appointment duration options

## Technical Requirements

### API Endpoints to Implement

#### Student APIs:
```java
// Authentication
POST /api/v1/students/register
POST /api/v1/students/login

// Profile Management
GET /api/v1/students/{id}
PUT /api/v1/students/{id}
GET /api/v1/students/{id}/appointments

// Appointment Management
GET /api/v1/appointments/available
POST /api/v1/appointments
PUT /api/v1/appointments/{id}
DELETE /api/v1/appointments/{id}
POST /api/v1/appointments/{id}/review
```

#### Explainer APIs:
```java
// Authentication
POST /api/v1/explainers/register
POST /api/v1/explainers/login

// Profile Management
GET /api/v1/explainers/{id}
PUT /api/v1/explainers/{id}

// Availability Management
POST /api/v1/explainers/{id}/availability
GET /api/v1/explainers/{id}/availability
DELETE /api/v1/explainers/{id}/availability/{id}

// Appointment Management
GET /api/v1/explainers/{id}/appointments
PUT /api/v1/explainers/appointments/{id}/status
```

### Service Layer Methods

```java
// StudentService
public interface StudentService {
    Student registerStudent(StudentDTO studentDTO);
    Student updateProfile(Long id, StudentDTO studentDTO);
    List<Appointment> getStudentAppointments(Long studentId);
    List<Explainer> findAvailableExplainers(SearchCriteria criteria);
}

// ExplainerService
public interface ExplainerService {
    Explainer registerExplainer(ExplainerDTO explainerDTO);
    void setAvailability(Long explainerId, List<AvailabilityDTO> slots);
    List<Appointment> getExplainerAppointments(Long explainerId);
    void updateAppointmentStatus(Long appointmentId, AppointmentStatus status);
}

// AppointmentService
public interface AppointmentService {
    Appointment createAppointment(AppointmentDTO appointmentDTO);
    void cancelAppointment(Long appointmentId);
    void rescheduleAppointment(Long appointmentId, LocalDateTime newTime);
    void addReview(Long appointmentId, ReviewDTO reviewDTO);
}
```

### Data Models Required Updates

```java
// Add necessary fields to existing entities

class Student {
    private String studentId;
    private Degree degree;
    private Set<Language> languages;
    private List<Appointment> appointments;
}

class Explainer {
    private Set<Course> expertise;
    private Set<Language> languages;
    private List<Availability> availableSlots;
    private College college;
}

class Appointment {
    private Student student;
    private Explainer explainer;
    private Course course;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private AppointmentStatus status;
    private Review review;
}

class Availability {
    private Explainer explainer;
    private DayOfWeek day;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isRecurring;
}
```

## Testing Requirements

### Unit Tests
- Service layer methods
- Entity validation
- DTO mapping
- Business logic validation

### Integration Tests
- API endpoints
- Database operations
- Service interactions

### E2E Tests
- Appointment booking flow
- Availability management
-


# Contributing
- [Vanilson Muhongo](https://www.github.com/edsonwade)
