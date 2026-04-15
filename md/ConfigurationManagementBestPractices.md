# Configuration Guidelines for This Project

## 1) Overview
The project uses Spring Boot configuration to manage:
- application startup behavior
- security configuration
- JWT authentication support
- environment-driven settings
- bean initialization and wiring

## 2) Main Configuration Areas

### Application Initialization
- Use dedicated configuration classes for startup-related logic.
- Keep initialization logic separate from controllers and services.
- Use this area for seeding data, preparing defaults, or boot-time setup when needed.

### Security Configuration
- Keep authentication and authorization rules in a dedicated security configuration.
- Define public and protected endpoints clearly.
- Preserve the existing token-based security flow.
- Avoid mixing security rules with business logic.

### Authentication Entry Point
- Handle unauthorized access through a dedicated authentication entry point.
- Return consistent responses for unauthenticated requests.
- Do not expose sensitive security internals.

### Environment Configuration
- Store externalized settings in configuration files.
- Prefer environment-driven values for:
  - database connection
  - JWT settings
  - server behavior
  - runtime-specific values
- Avoid hardcoding secrets or deployment-specific values in code.

## 3) Configuration File Rules
- Keep application settings in resource configuration files.
- Group related settings logically.
- Use clear, stable names for configuration keys.
- Prefer one source of truth for each setting.

## 4) Security-Related Configuration Rules
- Keep JWT signing, expiration, and refresh settings configurable.
- Make security decisions in configuration classes, not in controllers.
- Ensure protected routes remain protected after refactors.

## 5) Bean and Dependency Configuration
- Use configuration classes for framework-managed beans.
- Keep bean definitions small and focused.
- Reuse beans where appropriate instead of creating duplicate instances.

## 6) Startup and Initialization Rules
- Initialization logic should be deterministic and safe to run.
- Do not perform destructive startup actions unless explicitly required.
- Keep startup code idempotent where possible.

## 7) Agent Rules for Configuration Changes
When modifying configuration:
- check whether the change affects security, startup, or environment settings
- preserve existing configuration conventions
- avoid spreading config values across unrelated files
- keep changes minimal and intentional
- verify impacts on authentication, database access, and application startup

## 8) Goal
Configuration should remain:
- clear
- centralized
- secure
- environment-friendly
- easy to maintain
