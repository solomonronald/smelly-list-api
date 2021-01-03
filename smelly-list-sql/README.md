# Smelly List DB

Database setup and queries for Smelly List API service.

## User and Database Setup

Log in to your mysql server instance with admin/root credentials and run:

1. **Delete Existing Setup** ([1-delete-initial-setup.sql](./dev/1-delete-initial-setup.sql))
    
    Recommended if you want to clear any existing smelly list database.

2. **Initialize Smelly List DB** ([2-initial-admin-setup.sql](./dev/2-initial-admin-setup.sql))
    
    To create a new user and database for smelly list api service.

## Test newly created user and database

Verify/Test your newly created sql credentials mentioned in [2-initial-admin-setup](./dev/2-initial-admin-setup.sql) by logging into your mysql server instance with them.

Use the same credentials in your [application.yml](./../smelly-list-api/src/main/resources/application.yml)
