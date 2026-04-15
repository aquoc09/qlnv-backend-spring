# Project Structure Guide

## 1) High-Level Structure
This backend project is organized into clear layers to keep responsibilities separated.

## 2) Main Packages

### configuration
Contains application configuration, security setup, and boot-time initialization.

### controller
Contains API endpoints and request handling.

### dto
Contains request and response models used for API communication.

### entity
Contains persistence models mapped to the database.

### repository
Contains Spring Data JPA repository interfaces.

### service
Contains business logic and application workflows.

### mapper
Contains conversions between entities and DTOs.

### validator
Contains custom validation logic if needed.

### exception
Contains application-specific exceptions and error handling support.

### util
Contains shared helper functions and utilities.

### enums
Contains reusable enumeration types for domain values and status flags.

## 3) Layer Responsibilities

### Controller Layer
- handle HTTP requests
- call service methods
- return API responses

### Service Layer
- apply business rules
- orchestrate persistence and mapping
- manage transactional behavior

### Repository Layer
- access the database
- run queries
- keep persistence logic isolated

### DTO Layer
- define request and response contracts
- avoid exposing internal entities directly

### Entity Layer
- define database structure
- represent persistent domain objects

## 4) Working Rules
When adding new features:
- place code in the correct layer
- keep dependencies flowing inward from controller to service to repository
- avoid mixing API and persistence responsibilities
- update related DTOs, mappers, and tests together

## 5) Agent Goal
The AI agent should preserve the structure and avoid turning the project into a “everything goes everywhere” situation.
