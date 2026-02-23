# Conjur Secrets Lifecycle

## Scope
Defines creation, access, rotation, and revocation for application and CI secrets.

## 1. Creation
1. Create policy in Conjur for service namespace/environment.
2. Define variables:
   - `db/url`, `db/username`, `db/password`
   - `actuator/username`, `actuator/password`
   - `sonar/token` (CI)
   - `splunk/hec/token` (collector)
3. Load policy via CI-managed Conjur admin workflow.

## 2. Access
- Runtime: Kubernetes service account identity mapped to Conjur host identity.
- CI: runner identity or short-lived authn token; no static long-lived token in repo.
- Access is least-privilege and environment-specific.

## 3. Rotation
- Rotation frequency:
  - DB credentials: every 90 days (or per platform policy).
  - HEC/Sonar tokens: every 90 days or on incident.
- Rotate in Conjur first, then rollout app/collector using rolling restart.
- Validate post-rotation with smoke tests and health checks.

## 4. Revocation
- Immediate revocation on incident, offboarding, or leaked credentials.
- Reissue new secret and force redeploy.
- Audit Conjur access logs.

## 5. Controls
- No secrets in Git.
- No plaintext secrets in CI logs.
- Use secret references only in manifests and workflows.
