-- WARNING!!!
-- DO NOT USE THESE CREDENTIALS IN PRODUCTION

-- Run the following commands with root/admin credentials

-- Create dev database
CREATE DATABASE smellylist_dev;

-- Create dev user.
-- Database is smellylist_dev
-- Username is smellylist_dev_user
-- Password is smellylist_dev_pwd
CREATE USER 'smellylist_dev_user'@'localhost' IDENTIFIED BY 'smellylist_dev_pwd';

-- Grant privileges
GRANT ALL PRIVILEGES ON smellylist_dev.* TO 'smellylist_dev_user'@'localhost';

FLUSH PRIVILEGES;
