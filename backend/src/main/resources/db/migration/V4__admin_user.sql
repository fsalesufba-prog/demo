-- ============================================
-- V4: Usuário administrador inicial
-- ============================================

-- Inserir usuário admin (senha: Admin123!)
INSERT INTO users (email, password, full_name, phone, is_active, is_admin, created_at) 
VALUES (
    'admin@streamflix.demo', 
    '$2a$10$E6lPjW8/7QZk8z1NqY8qH.QKZJ8Z9vL7mFkY1nL2pOqRwS3tUvXyZ', -- Senha: Admin123!
    'Administrador del Sistema',
    '+51 937 466 317',
    TRUE,
    TRUE,
    NOW()
)
ON DUPLICATE KEY UPDATE 
    is_active = TRUE,
    is_admin = TRUE;

-- Inserir usuário de teste da imagem (senha: Password123!)
INSERT INTO users (email, password, full_name, phone, is_active, is_admin, created_at) 
VALUES (
    'emanuel.801.r@gmail.com', 
    '$2a$10$YH6gFd8rQ3pL9mN2bV4cR.uW7xYzA1B2C3D4E5F6G7H8I9J0K1L2M', -- Senha: Password123!
    'Emanuel Mendoza',
    '+51 963 939 633',
    TRUE,
    FALSE,
    NOW()
)
ON DUPLICATE KEY UPDATE 
    is_active = TRUE,
    is_admin = FALSE;

-- Criar assinatura para o usuário de teste (30 dias)
INSERT INTO subscriptions (user_id, start_date, end_date, status, created_at)
SELECT 
    u.id,
    CURDATE(),
    DATE_ADD(CURDATE(), INTERVAL 30 DAY),
    'ACTIVE',
    NOW()
FROM users u 
WHERE u.email = 'emanuel.801.r@gmail.com'
AND NOT EXISTS (
    SELECT 1 FROM subscriptions s 
    WHERE s.user_id = u.id 
    AND s.status = 'ACTIVE'
    AND s.end_date >= CURDATE()
);