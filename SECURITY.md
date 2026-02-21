You are a senior Spring Security and backend architect.  
Build a **production-grade SaaS backend** in Spring Boot with the following requirements:

### 🛡 AUTHENTICATION & SECURITY
✔ Implement JWT authentication with:
- Access tokens (short expiry)
- Refresh tokens
- Rotation of refresh tokens
- Blacklist or revocation support
  ✔ Register, login, logout, refresh endpoints
  ✔ Optional 2FA (TOTP, e.g., Google Authenticator)
  ✔ Use BCrypt for password hashing
  ✔ Secure endpoints with Spring Security filters
  ✔ Validate tokens (signature, exp, audience)

### 👥 ROLES & PERMISSIONS
✔ Role-based access control:
- Define multiple roles (ADMIN, USER, etc.)
- Custom permissions/scopes mapped to roles
  ✔ Enforce RBAC at method or endpoint level with annotations

### 🗄 DATABASE
✔ PostgreSQL persistence
✔ Flyway migrations — **continue the existing version history** (don’t reset or override existing migrations)  
✔ Define tables for:
- Users
- Roles
- Permissions
- User-Roles
- Role-Permissions
- Refresh tokens (with expiry and rotation tracking)
- Audit logs

### 🔄 AUDIT LOGGING
✔ Log security events into database:
- Logins (success/failure)
- Token refresh
- Role/permission changes
- 2FA events
- Logout/invalidations

✔ Implement email sending support using **Mailgun**:
- Use Mailgun’s **sandbox domain** for development testing
- Add authorized test email addresses (up to 5) in the Mailgun sandbox settings so the backend can send emails to these during development and you can receive 2FA codes in your dev inbox :contentReference[oaicite:0]{index=0}
- Configure Spring Boot to send emails via Mailgun’s API or SMTP
- Ensure email functionality handles success, failure, retries, and logging
- Optionally support email templates for 2FA and notifications

### 🚫 EXCEPTIONS & ERROR HANDLING
✔ Implement custom exception classes (e.g., Unauthorized, Forbidden, TokenExpired)
✔ Use a global exception handler to return structured error responses

### 🧪 TESTING
✔ Unit tests for all services and utilities
✔ Controller tests (MockMvc/WebTestClient) for all endpoints
✔ Test token flows, RBAC permission enforcement, 2FA flows, and error cases

### 📈 SECURITY BEST PRACTICES
✔ Enforce HTTPS only
✔ Validate inputs in controllers (`@Valid`)
✔ Sanitize inputs to prevent injection attacks
✔ Use secure CORS configuration
✔ Structured error messages without leaking internals
✔ Rate-limiting on auth endpoints to mitigate brute force
✔ Store secrets in env vars (no hard-coded secrets)
✔ Use Flyway versioning following existing migration versions (don’t break history)
✔ Audit logs must contain user ID, action, timestamp, and IP/user agent if available

### OUTPUT EXPECTATIONS
1. Full Spring Boot implementation of all requirements above
2. All endpoints functioning with proper security
3. No unimplemented methods
4. Tests that can be run with Maven

**Build this backend exactly as required, following best practices and professional SaaS standards.**