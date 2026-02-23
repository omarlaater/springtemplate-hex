# Authentication Guidance

## Current starter behavior
- API endpoints are open by default for local/dev simplicity.
- Actuator sensitive endpoints are secured with HTTP Basic.

## Production recommendation
- Protect business APIs with OAuth2 Resource Server + JWT.
- Validate issuer, audience, and required scopes.
- Use gateway-level auth enforcement where applicable.

## Migration path
1. Add `spring-boot-starter-oauth2-resource-server`.
2. Configure JWT issuer and audience.
3. Apply method/route authorization rules.
4. Update integration and smoke tests for auth flows.
