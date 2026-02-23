# Runbook: Collector Outage / HEC Throttling

## Symptoms
- Logs missing in Splunk.
- Collector pods crashloop or backlog grows.
- HEC returns 4xx/5xx or throttling indicators.

## Immediate actions
1. Validate application remains healthy (logging failure should not crash app).
2. Check collector DaemonSet health and node coverage.
3. Inspect HEC token validity and endpoint connectivity.
4. Reduce log volume (temporary level change) if ingestion path is saturated.

## Mitigation
- Restart collector pods if config drift/cert issue is fixed.
- Rotate HEC token from Conjur if compromised/expired.
- Apply sampling filters for high-noise logs.

## Recovery verification
- Confirm new app logs reach Splunk.
- Query by recent `correlationId`.
- Monitor ingestion latency and error rate.

## Escalation
- Observability platform team
- Security (token/certificate incidents)
