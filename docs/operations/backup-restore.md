# Backup and Restore Guidance

## Backup Policy
- Preferred: managed database snapshots with point-in-time recovery.
- Alternative (self-managed Postgres): scheduled `pg_dump` + WAL archiving.
- Minimum retention: 30 days for dev/staging, 90 days for production.

## Kubernetes approach
- Use CronJob or managed backup operator.
- Store backups in encrypted object storage.
- Tag backup artifacts with env, cluster, and timestamp.

## Restore Procedure
1. Select target backup by timestamp.
2. Restore into isolated instance/namespace.
3. Run integrity checks and smoke tests.
4. Validate Liquibase changelog state matches expected release.
5. Promote restored DB or reroute traffic per incident command.

## Rehearsal
- Perform restore rehearsal at least monthly in staging.
- Capture restore duration and compare to RTO.
