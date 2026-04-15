# CRUD Rules for This Project

## 1) CRUD Understanding
When implementing CRUD features, identify the full flow:
- request DTO
- validation
- controller endpoint
- service logic
- repository access
- entity mapping
- response DTO

Do not implement CRUD in a single layer unless the project already does so.

## 2) Create
- Validate incoming data before saving.
- Check required fields, business constraints, and uniqueness rules before insert.
- Use request DTOs for create operations.
- Map request data to the entity in a dedicated mapper or service layer, depending on the project pattern.
- Return a clean response DTO after creation.

## 3) Read
- Support both single-item read and list read patterns when needed.
- Prefer response DTOs instead of returning entities directly.
- Keep read queries efficient and avoid unnecessary loading of related data.

## 4) Update
- Fetch the existing record first before updating.
- Update only allowed fields.
- Preserve immutable fields such as identifiers and audit-related values.
- Validate update data before applying changes.

## 5) Delete
- Before deleting, check whether the record is allowed to be removed.
- Respect business rules such as dependent records, status restrictions, or soft-delete behavior if already used in the project.
- Use hard delete only if the project already follows that pattern.

## 6) CRUD Safety Rules
- Never skip validation just to make CRUD faster.
- Never expose database entities directly unless the project already relies on that pattern.
- Always update related mapper, DTO, service, and repository code together.
- Review security permissions for every create, update, and delete endpoint.
- Keep error handling consistent with existing exception patterns.

## 7) CRUD Implementation Checklist
Before finalizing any CRUD feature, confirm:
- request DTO exists
- validation is applied
- controller endpoint follows existing route style
- service contains the business logic
- repository supports the required query
- entity mapping is correct
- response DTO is returned
- exceptions and edge cases are handled
- tests are added or updated

## 8) CRUD Goal
The AI agent should implement CRUD features that are clean, consistent, secure, and easy to maintain.
