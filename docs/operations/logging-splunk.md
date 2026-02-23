# Logging and Splunk Operations

## Pipeline
`app -> stdout JSON -> collector DaemonSet -> Splunk HEC`

## Log level policy
- `INFO` in production.
- `DEBUG` only for targeted troubleshooting windows.
- Dynamic level adjustment via `/actuator/loggers` (secured endpoint).

## High-volume handling
- Apply sampling for noisy logs at collector layer.
- Avoid request/response payload logging by default.
- Use retention policies aligned with compliance requirements.

## Sample queries
```spl
index=app_logs service=spring-template-starter correlationId="<id>"
```
```spl
index=app_logs service=spring-template-starter level=ERROR | stats count by logger, message
```
```spl
index=app_logs service=spring-template-starter | timechart count by level span=1m
```
