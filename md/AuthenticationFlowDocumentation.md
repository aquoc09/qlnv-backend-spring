# Authentication Flow

## 1) Overview
The authentication system is token-based and uses JWT-style access tokens.
It supports these operations:

- login
- token introspection
- logout
- refresh token
- get current authenticated user

## 2) Main Authentication Endpoints

### Login
Purpose:
- verify user credentials
- generate a signed token
- persist token data
- return authentication result

Flow:
1. Client sends username and password.
2. Service loads the user by username.
3. Password is checked against the stored encoded password.
4. If valid, a token is generated.
5. Token information is saved in storage.
6. Client receives the token.

### Introspect
Purpose:
- check whether a token is valid
- verify signature and expiration
- return token status

Flow:
1. Client sends a token.
2. Service checks whether the token is blank.
3. Token header and signature are verified.
4. Expiration is checked.
5. Service returns whether the token is valid.

### Logout
Purpose:
- invalidate the current token

Flow:
1. Client sends a token to logout.
2. Token is parsed and verified.
3. The token record is found in storage.
4. The token record is deleted.

### Refresh Token
Purpose:
- issue a new token when the old one is still within the refresh window

Flow:
1. Client sends a token to refresh.
2. Service verifies the token and checks refresh eligibility.
3. If valid, a new token is generated.
4. Old token data is replaced.
5. Client receives the new token.

## 3) Token Structure
The token includes important claims such as:
- subject
- token id
- issuer
- issue time
- expiration time
- scope/roles
- refresh token value

## 4) Security Behavior
- Tokens are signed using a server-side secret.
- Token verification checks signature and expiration.
- Protected operations should only run for authenticated requests.
- Current user access relies on the Spring Security context.

## 5) Current User Access
Behavior:
1. Read the authenticated principal from the security context.
2. Extract the username.
3. Load the user from the database.
4. Return the user record.

## 6) Error Handling Rules
Authentication should fail safely when:
- username does not exist
- password is incorrect
- token is blank
- token signature is invalid
- token is expired
- token cannot be parsed
- user referenced by token no longer exists

## 7) Agent Guidance
When working on authentication:
- follow the existing endpoint structure
- preserve the current token lifecycle
- update token persistence and security rules together
- check the impact on refresh, logout, and current-user behavior
- avoid changing security logic without understanding downstream effects
