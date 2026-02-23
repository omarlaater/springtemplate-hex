# PR Acceptance Checklist

## Architecture and code
- [ ] Ports/adapters layering respected.
- [ ] DTOs at external boundaries; no entity leakage.
- [ ] Transaction boundary in service/application layer.
- [ ] Repository port + JPA adapter implementation.
- [ ] Optimistic locking enabled on mutable entities.

## Database and migrations
- [ ] Liquibase changeSets added and included in master changelog.
- [ ] Existing applied changeSets untouched.
- [ ] `ddl-auto=validate` in prod profile.
- [ ] Migration behavior validated in integration tests.

## Quality gates
- [ ] Maven Enforcer passes.
- [ ] Checkstyle passes.
- [ ] SpotBugs passes.
- [ ] JaCoCo report generated.
- [ ] Sonar Quality Gate passes.

## Tests
- [ ] Unit tests updated.
- [ ] Controller slice tests updated.
- [ ] Integration tests (Testcontainers + Liquibase) updated.
- [ ] CI stage separation preserved.

## Observability and security
- [ ] JSON logs and correlation ID preserved.
- [ ] Actuator endpoints secured.
- [ ] No secrets in code or manifests.
- [ ] Conjur/External Secrets references valid.

## Deployment/GitOps
- [ ] Docker image tagged immutably.
- [ ] K8s manifests valid (probes/resources/HPA/PDB).
- [ ] GitOps promotion policy followed.
