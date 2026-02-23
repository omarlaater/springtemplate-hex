# Dependency and SBOM Policy

## Dependency updates
- Dependabot runs weekly for Maven and GitHub Actions.
- Security patches are prioritized and should be merged within SLA.
- Base image updates reviewed at least monthly.

## SBOM
- CycloneDX SBOM is generated in CI (`makeAggregateBom`).
- SBOM artifact must be retained with build artifacts.

## Vulnerability controls
- Maven dependency scanning in CI (OWASP Dependency-Check or equivalent).
- Container image scanning in CI (Trivy or equivalent).
- Block on CRITICAL/HIGH findings unless documented exception approved.
