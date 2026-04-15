# Testing Rules for This Project

## 1) Overview
Testing in this project should verify business behavior, API behavior, security behavior, and data mapping correctness.

The goal is not just to make tests pass, but to make them useful for protecting the codebase from regressions.

## 2) Testing Principles
- Test behavior, not implementation details.
- Keep tests clear, small, and focused.
- Follow the same project architecture in tests as in production code.
- Add tests when behavior changes.
- Update tests when contracts, mappings, or security rules change.

## 3) What to Test

### Controller tests
Use controller tests to verify:
- endpoint behavior
- request validation
- response structure
- HTTP status codes
- authentication and authorization behavior when relevant

### Service tests
Use service tests to verify:
- business rules
- token handling
- validation-dependent logic
- error cases
- repository interactions

### Repository tests
Use repository tests to verify:
- query behavior
- persistence rules
- entity loading and saving
- custom repository methods

### Mapper tests
Use mapper tests to verify:
- entity-to-DTO mapping
- DTO-to-entity mapping
- field consistency after model changes

### Security tests
Use security tests to verify:
- protected endpoints stay protected
- public endpoints remain accessible
- authentication failures are handled correctly
- token-based flows behave as expected

## 4) Test Data Rules
- Keep test data minimal and realistic.
- Use only the fields needed for the scenario.
- Avoid hardcoding sensitive values.
- Prefer reusable test fixtures when they improve readability.

## 5) Assertion Rules
- Assert the outcome that matters to the feature.
- Check status codes, response body, and error messages when relevant.
- Verify both success and failure cases for important flows.
- Do not over-assert internal implementation details.

## 6) Exception and Validation Testing
When testing errors:
- verify the correct exception is thrown or translated
- verify the API response format is consistent
- verify validation errors return the expected message
- verify business rule violations map to the correct project error response

## 7) Authentication Testing
When testing authentication:
- verify login success and failure
- verify token introspection
- verify logout behavior
- verify token refresh behavior
- verify current-user behavior when authenticated and unauthenticated

## 8) CRUD Testing
For CRUD features, verify:
- create returns the expected response
- read returns correct data
- update changes only allowed fields
- delete behaves according to business rules
- invalid input is rejected
- missing records are handled properly

## 9) Test Maintenance Rules
- Keep tests aligned with current code conventions.
- Update tests whenever DTOs, entities, responses, or security rules change.
- Remove obsolete tests that no longer reflect actual behavior.
- If a bug is fixed, add a regression test for it.

## 10) Agent Rules for Testing
When the AI agent writes or updates tests:
- choose the smallest test that proves the behavior
- match the project’s current test style
- include both happy path and failure path where important
- do not create fragile tests tied to internal details
- ensure tests remain understandable for future maintainers

## 11) Testing Goal
The testing suite should help the project stay stable, secure, and maintainable as it grows.
