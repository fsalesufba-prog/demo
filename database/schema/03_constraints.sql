-- Constraints adicionais
ALTER TABLE categories ADD CONSTRAINT chk_category_name_length CHECK (LENGTH(name) >= 2);
ALTER TABLE channels ADD CONSTRAINT chk_channel_name_length CHECK (LENGTH(name) >= 2);
ALTER TABLE channels ADD CONSTRAINT chk_channel_url CHECK (url LIKE 'http%' OR url LIKE 'rtmp%' OR url LIKE 'rtsp%');
ALTER TABLE users ADD CONSTRAINT chk_username_length CHECK (LENGTH(username) >= 3);
ALTER TABLE users ADD CONSTRAINT chk_email_format CHECK (email LIKE '%@%.%');
ALTER TABLE users ADD CONSTRAINT chk_password_hash CHECK (LENGTH(password_hash) >= 60);