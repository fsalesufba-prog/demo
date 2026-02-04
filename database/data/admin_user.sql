-- Usuário administrador padrão
-- Senha: admin123 (deve ser hash em produção)
INSERT INTO users (username, email, password_hash, full_name, is_admin, is_active) VALUES
('admin', 'admin@streamflix.demo', '$2a$10$YourHashedPasswordHere', 'Administrador', TRUE, TRUE)
ON DUPLICATE KEY UPDATE 
    email = VALUES(email),
    full_name = VALUES(full_name),
    is_admin = VALUES(is_admin),
    is_active = VALUES(is_active);