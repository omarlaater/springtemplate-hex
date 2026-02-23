# Artifact Promotion Policy

## Goals
- Immutable artifacts
- Auditable environment promotions
- Controlled progression: dev -> staging -> prod

## Rules
- Build once, promote same immutable image digest across environments.
- No mutable tags for deployment decisions.
- Promotion requires:
  - Passing tests and quality gates
  - Security scan thresholds met
  - Approval per environment policy

## Workflow
1. CI builds image and publishes immutable tag.
2. CI updates GitOps repo or opens promotion PR.
3. Argo CD syncs target environment.
4. Post-deploy smoke tests must pass before next promotion.
