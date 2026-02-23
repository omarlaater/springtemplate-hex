# TLS and mTLS Guidance

## TLS
- All external endpoints must be served over TLS.
- Terminate TLS at ingress/gateway with managed certificates.
- Redirect HTTP to HTTPS.

## mTLS (service-to-service)
- Enable mTLS where required by data sensitivity or policy.
- Recommended via service mesh or sidecar-based identity.
- Rotate certificates automatically and monitor expiry.

## Validation
- Include TLS/mTLS checks in pre-production verification.
- Document certificate ownership and rotation runbook.
