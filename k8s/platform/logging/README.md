# Logging Collector Manifests

This folder contains platform-level logging pipeline examples.

- `fluent-bit-daemonset.yaml`: node-level log collection and forwarding.
- `otel-collector-config.yaml`: OTLP receiver and Splunk HEC exporter example.

In production, store and manage these manifests in the dedicated GitOps platform repository.

Secrets:
- `SPLUNK_HEC_TOKEN` must come from Conjur or External Secrets.
- Do not commit tokens or plaintext credentials.
