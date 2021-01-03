-- WARNING: Following queries WILL DELETE EXISTING DEV DATABASE

-- Delete dev databse
DELETE DATABASE IF EXISTS smellylist_dev;

-- Delete dev user
DROP USER IF EXISTS 'smellylist_dev_user'@'localhost';

FLUSH PRIVILEGES;
