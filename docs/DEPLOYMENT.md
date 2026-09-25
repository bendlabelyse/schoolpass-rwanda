# SchoolPass deployment plan

## Option A — School-owned local server
A school can run SchoolPass on a dedicated Windows PC/server inside the school network. Staff open the server's LAN address in their browsers. This is appropriate for an internal deployment where the school controls the machine and network.

For a serious deployment use PostgreSQL rather than the H2 development database, configure backups, create real staff accounts, and restrict the server to the school network/firewall.

## Option B — Hosted website
SchoolPass can be deployed to a managed Java host or a school-controlled server, with a domain name and HTTPS. Staff then use a normal web address from authorized devices.

Because SchoolPass processes student personal data and financial/attendance information, the hosting location and any international transfer must be reviewed against Rwanda's data-protection requirements before production. Do not simply upload the database to an unknown public service.

## What the school needs
- Domain (if public website is desired)
- HTTPS certificate
- Java 21 runtime
- PostgreSQL database for production
- Server with restricted access and backups
- Administrator and finance staff accounts
- Student CSV/photo data supplied by the school
- Printed QR student cards
- Laptop/phone camera or USB QR scanners
- Agreed support and backup procedure

## Go-live checklist
- Replace bootstrap passwords
- Create named staff accounts
- Verify school profile
- Import real students and check counts
- Test 3–5 cards
- Test unpaid/partial/paid states
- Test duplicate arrival
- Test lost-card replacement
- Test backup and restore
- Test role restrictions
- Confirm privacy/data-protection responsibilities
- Confirm the official payment workflow
