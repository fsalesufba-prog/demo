-- ============================================
-- V2: Seed das categorias padrão
-- ============================================

-- Inserir categorias padrão baseadas nas telas fornecidas
INSERT INTO categories (name, slug, description, sort_order, is_active, created_at) VALUES
('Todos', 'todos', 'Todos os canais disponíveis', 0, TRUE, NOW()),
('Nacional', 'nacional', 'Canais de TV nacionais peruanos', 1, TRUE, NOW()),
('Actualidad', 'actualidad', 'Canais de notícias e atualidades', 2, TRUE, NOW()),
('Infantil', 'infantil', 'Canais infantis e desenhos animados', 3, TRUE, NOW()),
('Regional', 'regional', 'Canais regionais do Peru', 4, TRUE, NOW())
ON DUPLICATE KEY UPDATE 
    name = VALUES(name),
    description = VALUES(description),
    sort_order = VALUES(sort_order),
    is_active = VALUES(is_active);