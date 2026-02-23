# Disaster Recovery Summary

## Objectives
- Target RTO: 60 minutes
- Target RPO: 15 minutes

## Scope
Applies to database, application workloads, and log ingestion path.

## Strategy
- Multi-AZ database and cluster setup.
- GitOps-managed manifests for deterministic rebuild.
- Backups and restore rehearsals in staging.

## Trigger events
- Region/cluster outage
- Irrecoverable DB corruption
- Security incident requiring rebuild

## DR Flow
1. Declare incident and freeze deployments.
2. Restore DB to recovery point.
3. Rehydrate workloads from GitOps manifests.
4. Validate smoke tests and critical paths.
5. Resume traffic and monitor error budget burn.
