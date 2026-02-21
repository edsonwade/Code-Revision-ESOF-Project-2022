You now have access to the backend source code (the full src folder).
STEP 1 — BACKEND ANALYSIS (MANDATORY) and refactoring all services to improve it to grade application saas.

1. Analyze the entire src folder structure.
2. Identify:
    - All modules
    - All services
    - Controllers
    - DTOs
    - Database models / schemas
    - Auth flow
    - Roles & permissions
    - External integrations
    - Environment dependencies
3. Understand:
    - Business logic
    - Data relationships
    - Core features
    - SaaS-specific logic all services
4. Summarize the backend architecture before proceeding.

**Do NOT skip this step.**


- If your jobs it to identify **messy or inefficient logic** during your analysis, and  improve it.
  **Refactor** the logic where necessary to ensure:
    - Performance
    - Clarity
    - Maintainability
    - Scalability
    - Clean code
    - Solid principle
    - bussiness logic
    - Databasese flyway migration, redis cache, transaction
    - Add new funcionality, not just crud
    - Unit testing (services-> mocito ,assertJ, no nested testes) controller (mockmvc)
    - Docker compose