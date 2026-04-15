# Validation Rules for This Project

## 1) Overview
Validation should protect the application from invalid input before business logic runs.

The goal is to keep request handling safe, consistent, and predictable.

## 2) Validation Principles
- Validate at the boundary of the application.
- Use request DTOs as the main validation layer.
- Keep validation close to the field or rule it protects.
- Do not let invalid data reach the service layer when avoidable.

## 3) Request DTO Validation
- Use standard bean validation annotations where possible.
- Mark required fields clearly.
- Validate format, range, size, and structure when relevant.
- Keep request models expressive and minimal.

Examples of common checks:
- required value
- minimum or maximum length
- numeric range
- non-blank text
- valid email or format
- permitted enum value

## 4) Custom Validation Rules
Use custom validators only when:
- built-in annotations are not enough
- the rule depends on multiple fields
- the rule requires domain-specific logic

Custom validation should:
- be reusable
- be easy to understand
- return clear messages
- integrate with the project’s error handling

## 5) Validation Error Handling
When validation fails:
- return a consistent API error response
- include a meaningful message
- map validation failures to the project’s error code system when applicable
- avoid exposing internal implementation details

## 6) Validation and CRUD
For create and update operations:
- validate required fields before persistence
- validate business constraints before saving
- validate update payloads carefully
- do not allow unsafe or incomplete data to pass through

## 7) Validation and Authentication
For authentication-related requests:
- validate username and password fields
- validate token input before token parsing
- reject blank or malformed token values early
- keep security-related validation strict and clear

## 8) Validation and Exception Flow
Validation errors should be handled centrally and consistently.
Do not duplicate validation handling inside controllers or services unless the case is exceptional.

## 9) Validation Rule Maintenance
When models change:
- update validation annotations
- update custom validator logic
- update error handling if new validation cases appear
- update tests for both valid and invalid input

## 10) Agent Rules for Validation
When the AI agent adds or changes validation:
- validate at the DTO boundary first
- use the simplest rule that solves the problem
- keep validation messages clear and consistent
- ensure validation aligns with business rules
- update tests for invalid input scenarios

## 11) Validation Goal
Validation should keep the application clean, safe, and resistant to bad input.
