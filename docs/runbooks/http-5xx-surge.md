# Runbook: HTTP 5xx Surge

## Symptoms
- Error rate alert fired.
- Elevated 5xx in ingress/API dashboard.
- Latency spike and saturation signals.

## Immediate actions
1. Identify impacted endpoints and time window.
2. Filter logs by `correlationId` and endpoint.
3. Check recent deploys/migrations/config changes.
4. Validate dependencies: DB, downstream services, message brokers.

## Mitigation options
- Roll back to previous known-good release.
- Scale app horizontally if saturation-related.
- Temporarily disable high-cost feature paths (feature flags if available).

## Investigation focus
- DB contention / lock conflicts.
- Migration side-effects.
- Downstream timeout/circuit-break behavior.

## Exit criteria
- 5xx rate returns below SLO threshold.
- No ongoing incident alerts.
- Post-incident review with remediation items created.
