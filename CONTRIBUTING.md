# Contributing

## Branch and PR rules
- Create feature branches from `main`: `feature/<ticket>-<short-title>`.
- Keep PRs focused; one concern per PR.
- Fill in `.github/PULL_REQUEST_TEMPLATE/pull_request_template.md`.
- All required CI checks must pass before review.

## Definition of done
- Unit tests for business logic.
- Controller slice tests for API behavior.
- Integration tests for persistence/migrations.
- Liquibase changeSets for all schema changes.
- No secrets committed.
- README/docs updated when behavior or operations change.

## Local commands
- Build + unit/static checks: `mvn clean verify`
- Integration tests: `mvn verify -Pintegration-tests`
- Sonar (same as CI): `mvn verify sonar:sonar -Dsonar.qualitygate.wait=true`

## Commit conventions
- Follow Conventional Commits:
- `feat: ...`
- `fix: ...`
- `refactor: ...`
- `docs: ...`
- `test: ...`

## Security disclosure
- Do not open public issues for vulnerabilities.
- Contact security team via your internal security channel and reference this repo.
