# Unit Testing Rules for This Project

## 1) Overview
Unit tests should verify isolated business logic with minimal dependencies.

The goal is to confirm that each unit behaves correctly when given valid, invalid, or unexpected input.

## 2) What Counts as a Unit Test
A unit test should focus on one class or one method behavior at a time.

Good candidates:
- service methods
- mapper methods
- utility methods
- validation helper methods
- token-related logic that can be isolated

## 3) Unit Testing Principles
- Test one behavior per test when possible.
- Keep tests fast and independent.
- Mock dependencies outside the unit under test.
- Avoid real database, real HTTP, or real network calls in unit tests.
- Prefer clear arrange-act-assert structure.

## 4) Service Layer Unit Tests
For service tests:
- mock repositories
- mock external services
- verify business rules
- verify exception cases
- verify state changes and returned values

Unit tests should confirm:
- correct lookup behavior
- correct save/update/delete decisions
- correct error handling
- correct response building
- correct interaction with dependencies

## 5) Mapper Unit Tests
For mapper tests:
- verify source fields are converted correctly
- verify target fields are populated as expected
- verify null and edge cases when relevant
- update tests when DTOs or entities change

## 6) Utility and Helper Tests
For utility methods:
- verify deterministic outputs
- test edge cases
- test invalid input
- keep tests small and direct

## 7) Exception Testing
When a unit can fail:
- verify the correct exception is thrown
- verify the exception message or error code when relevant
- do not catch exceptions in the test unless needed for assertion

## 8) Mocking Rules
- Mock only dependencies that are not part of the unit’s own logic.
- Do not over-mock.
- Avoid mocking the class under test.
- Keep mock setup readable and minimal.

## 9) Naming Rules
Use descriptive test names that explain behavior.

Recommended pattern:
- shouldDoSomethingWhenCondition
- shouldThrowExceptionWhenInvalidInput
- shouldReturnExpectedResultWhenValidInput

## 10) Regression Testing
When fixing a bug:
- add a unit test that reproduces the bug
- confirm the fix with the new test
- keep the test focused on the behavior that broke

## 11) Agent Rules for Unit Tests
When writing unit tests, the AI agent should:
- choose the smallest test that proves the behavior
- isolate the class under test
- mock only necessary dependencies
- cover success and failure paths for important logic
- keep tests readable and stable
- avoid fragile tests tied to implementation details

## 12) Unit Testing Goal
Unit tests should make the code safer to change and easier to trust.
