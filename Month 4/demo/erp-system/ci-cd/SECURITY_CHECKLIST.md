# CI Security Checklist

- Store registry credentials and cloud credentials in GitHub Secrets
- Run SAST and dependency checks in CI (e.g., OWASP Dependency-Check or Snyk)
- Fail the build on high-severity vulnerabilities
- Sign artifacts and Docker images where possible
- Use minimal base images and non-root user in Dockerfile
- Rotate secrets regularly

