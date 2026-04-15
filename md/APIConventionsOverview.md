# API Conventions for This Project

## 1) Overview
The API should remain consistent, predictable, and easy to consume by clients.

## 2) Endpoint Rules
- Use clear resource-based endpoint names.
- Keep route naming consistent across controllers.
- Prefer stable endpoint structures over frequent naming changes.
- Group related endpoints logically.

## 3) Request Rules
- Use request DTOs for incoming payloads.
- Validate request bodies before service execution.
- Keep request fields minimal and meaningful.
- Do not expose internal entity fields that clients should not control.

## 4) Response Rules
- Use response DTOs for outgoing data.
- Return a consistent response wrapper when the project already uses one.
- Keep success responses simple and predictable.
- Include enough data for the client without leaking internals.

## 5) Status Code Rules
- Use appropriate HTTP status codes for success and failure.
- Match status codes to business meaning.
- Do not return success codes for failed operations.

## 6) Validation Rules
- Use bean validation for request checking.
- Return clear validation messages.
- Keep validation errors consistent across endpoints.

## 7) Error Response Rules
- Keep error payloads standardized.
- Return safe messages for unexpected failures.
- Return project error codes where applicable.

## 8) Versioning and Compatibility
- Avoid breaking existing clients without explicit need.
- If a change affects a public endpoint contract, update all related consumers and documentation.
- Prefer backward-compatible changes.

## 9) Agent Rules
When creating or changing an API:
- follow existing naming conventions
- preserve request/response patterns already used in the project
- verify validation and error handling
- check auth/security impact
- keep the change small and coherent

## 10) API Goal
The API should feel consistent across the whole project, like one team wrote it on purpose.
