# Argo CD GitOps Workflow

## Repositories
- App repo: source code + CI
- GitOps app repo: deployment overlays
- GitOps platform repo: shared platform components (collectors, ingress, etc.)

## Flow
1. CI builds immutable app image.
2. CI updates GitOps app repo and opens promotion PR.
3. Argo CD syncs merged manifest changes.
4. Post-sync smoke tests validate health and basic CRUD.

## Separation of concerns
- App manifests live under `k8s/app`.
- Platform manifests (logging collector, shared infra) live under platform GitOps repo.
