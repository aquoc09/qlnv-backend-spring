# Exception Handling in This Project

## 1) Overview
The project uses centralized exception handling to keep error responses consistent across the API.

Main goals:
- return predictable error responses
- avoid repeating try/catch logic in controllers
- map domain and validation errors to proper HTTP responses
- keep error handling aligned with the project’s API response format

## 2) Main Exception Types

### Application exceptions
Use custom application exceptions when:
- a business rule is violated
- a resource is missing
- authentication or authorization rules fail
- the request is syntactically valid but logically invalid

### Generic runtime exceptions
Use a generic runtime fallback when:
- the error is not mapped to a known business case
- the application needs to fail safely without exposing internal details

### Validation exceptions
Use validation exception handling when:
- request data fails bean validation
- request payload fields violate annotations or constraints

## 3) Global Exception Handler Responsibilities
The global exception handler should:
- catch application exceptions
- catch authorization failures
- catch validation failures
- catch unexpected exceptions
- return a consistent response body format
- log errors for debugging and tracing

It should not:
- contain business logic
- perform recovery operations
- expose stack traces or sensitive internal details to clients

## 4) Error Response Style
The API should return a structured response with at least:
- error code
- message
- optional validation details

For application exceptions, the HTTP status should reflect the error type.
For generic exceptions, the response should remain safe and generic.

## 5) Validation Error Flow
When input validation fails:
1. The request is rejected before service execution.
2. The global handler catches the validation exception.
3. The handler reads the field error information.
4. The handler maps the validation key to a project error code.
5. The handler returns a user-friendly validation message.

## 6) Authorization Failure Flow
When access is denied:
1. The security layer raises an authorization-related exception.
2. The global handler maps it to an unauthorized response.
3. The API returns a consistent error payload.

## 7) Logging Rules
- Log exceptions server-side for troubleshooting.
- Keep logs meaningful and concise.
- Include stack traces for unexpected failures.
- Do not return stack traces to the client.

## 8) Agent Rules for Exceptions
When adding or changing exceptions:
- reuse the existing error response structure
- prefer custom application exceptions for business rules
- keep validation errors centralized
- avoid catching exceptions in controllers unless necessary
- update the error code catalog if new business cases are added

## 9) Goal
The exception system should remain clean, centralized, and safe to use.
