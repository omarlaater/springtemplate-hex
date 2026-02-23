# Sonar Quality Gate Policy

## Scope
Run Sonar analysis on:
- Every pull request
- `main` branch

## Gate thresholds (new code)
- Coverage: >= 75% (set between 70-80% based on team policy)
- No new blocker issues
- No new critical issues
- Duplicated lines: < 3%
- Maintainability rating: A or B

## Enforcement
- PR pipeline must fail when gate status is not `PASSED`.
- Merge is blocked until remediation.

## Remediation process
1. Open Sonar report from CI.
2. Fix root causes and add/adjust tests.
3. Re-run CI and confirm gate passes.
4. If exceptional waiver is needed, obtain explicit architect + security approval.
