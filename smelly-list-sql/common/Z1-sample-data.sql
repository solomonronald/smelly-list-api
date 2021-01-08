-- Sample Users
-- The password hash for string "123456" is "$2a$12$q67VvDRE4V2aYbtSRfsVJOs2F3yF//3/ITXrXI/zAPxbpr/tEKBR6" (without quotes)
INSERT INTO `user_credential` (`public_id`, `username`, `email`, `role_mask`, `password_hash`)
VALUES ('abbaabba', 'admin', 'admin@smellylist.com', 7, "$2a$12$q67VvDRE4V2aYbtSRfsVJOs2F3yF//3/ITXrXI/zAPxbpr/tEKBR6");