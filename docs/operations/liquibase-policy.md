# Liquibase Policy

## Rules
- Every schema change must be represented by a new changeSet file.
- Never edit an already applied changeSet.
- Use naming: `YYYYMMDD-NNN-short-description.xml`.
- Include rollback where feasible and safe.
- Destructive changes require explicit review and migration rehearsal.

## Rollback policy
- Prefer forward-fix migrations for production incidents.
- Use rollback blocks for reversible operations.
- For destructive changes (drop/rename), use staged rollout:
  1. Backfill or dual-write.
  2. Deploy read compatibility.
  3. Execute destructive migration only after validation window.

## Validation
- ChangeSets validated in CI through integration tests with Testcontainers.
- Migration rehearsal required in staging before prod rollout.
