# Quickiee UI Tests (Selenium + TestNG)

## Setup
Set these environment variables before running:
- `UI_BASE_URL` (default: https://quickiee-eta.vercel.app)
- `E2E_EMAIL`
- `E2E_PASSWORD`
- `HEADLESS` (true/false)

## Run locally
```bash
cd ui-tests
mvn test
```

## Notes
- Tests use Chrome via WebDriverManager.
- Login happens through the UI using provided credentials.
