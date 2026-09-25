# SchoolPass Rwanda — 1.0.0

A school administration web application for student records, arrival-only attendance, student QR credentials, finance status and controlled administration workflows.

## Important
This release contains **no pretend/sample students**. The school enters or imports its own records.

## Technology
- Java 21
- Spring Boot 3.5.16
- Spring Security
- Spring Data JPA / Hibernate
- Thymeleaf + HTML/CSS/JavaScript
- H2 for immediate local development
- PostgreSQL for production
- ZXing for QR generation
- Browser camera QR scanning with html5-qrcode

## Run locally
Requirements: Java 21 and Maven 3.9+.

```bat
cd C:\PROJECTS\SchoolPass-Rwanda
mvn clean test
mvn spring-boot:run
```
Open `http://localhost:8080`.

Default local accounts (CHANGE BEFORE PRODUCTION):
- Administrator: `admin` / `ChangeMe123!`
- Finance: `finance` / `Finance123!`

These are bootstrap credentials only. Override them with environment variables:
`SCHOOLPASS_ADMIN_USERNAME`, `SCHOOLPASS_ADMIN_PASSWORD`, `SCHOOLPASS_FINANCE_USERNAME`, `SCHOOLPASS_FINANCE_PASSWORD`.

## First setup
1. Sign in as administrator.
2. Open Settings and enter the school's name, principal, academic year and contact information.
3. Import the school's real CSV or add records individually.
4. Upload student photos where appropriate.
5. Print student cards.
6. Test a card at the scanner before issuing it.
7. Create/assign real staff credentials before operational use.

## S4–S6 streams
- MCB — Mathematics, Chemistry, Biology
- PCB — Physics, Chemistry, Biology
- MCE — Mathematics, Computer Science, Economics

S1–S3 do not use those streams.

## Scanner options
1. Laptop/phone camera in a browser that grants camera permission.
2. USB QR scanner that types the scanned credential into the code field.
3. Manual credential entry as an operational fallback.

Attendance records arrival only, once per student per day.

## CSV import
See `docs/STUDENT_IMPORT.csv` for the header template. No sample student rows are included.

## PostgreSQL production
Set:
- `SCHOOLPASS_DB_URL=jdbc:postgresql://HOST:5432/schoolpass`
- `SCHOOLPASS_DB_USERNAME=...`
- `SCHOOLPASS_DB_PASSWORD=...`
- `SCHOOLPASS_DB_DRIVER=org.postgresql.Driver`
- `SPRING_JPA_HIBERNATE_DDL_AUTO=update` for the initial deployment, then move to controlled migrations before long-term production.

Use HTTPS, backups, restricted database access and separate credentials in production.

## Payment integration
The current finance module stores school-verified fee/uniform status. It does **not** claim to be a live Umwalimu SACCO API integration. A future official integration should be added behind a provider interface after the school obtains an authorized API specification/credentials.

## Data protection
The system includes role separation, audit logging, secure random QR credentials, restricted finance access, photo validation and a privacy page. This is an engineering foundation, not a legal certification. The school and deployment provider must complete the applicable Rwanda data-protection registration, contractual, retention, security and transfer requirements before production.

## Deployment
See `docs/DEPLOYMENT.md`.
