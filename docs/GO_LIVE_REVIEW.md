# Go-live review

This project is a complete development release with the requested core workflows. Before a school uses it with real student data, perform the deployment-specific checks listed in DEPLOYMENT.md.

The developer environment used to produce this package did not have Maven's dependency cache/network available, so the final Windows machine must run `mvn clean test` and `mvn spring-boot:run` as the final environment validation step. Do not represent that local environment-specific verification as having been performed here.
