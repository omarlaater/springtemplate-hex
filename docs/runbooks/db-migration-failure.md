# Runbook: DB Migration Failure

## Symptoms
- App startup fails during Liquibase.
- Migration Job fails in Kubernetes.
- Health endpoint returns down with DB migration errors.

## Immediate actions
1. Stop rollout/promotion.
2. Capture failed changeSet ID and SQL error.
3. Confirm whether partial schema changes were applied.
4. Restore service by deploying previous stable version if needed.

## Triage checklist
- Did an existing changeSet get modified?
- Is target DB state ahead/behind expected changelog?
- Is data violating new constraints?

## Recovery
- Preferred: apply forward-fix changeSet.
- If required and safe: execute rollback/changeLogSync with DBA approval.
- Re-run migration in staging before prod retry.

## Escalation
- Platform on-call
- DBA
- Service owner
