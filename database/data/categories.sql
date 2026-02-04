-- Categorias principais para canais peruanos
INSERT INTO categories (name, description, icon, color, order_index) VALUES
('Nacional', 'Canais nacionais do Peru', '🇵🇪', '#1976d2', 1),
('Actualidad', 'Notícias e atualidades', '📰', '#dc004e', 2),
('Deportes', 'Esportes e futebol', '⚽', '#2e7d32', 3),
('Entretenimiento', 'Entretenimento e variedades', '🎬', '#ed6c02', 4),
('Infantil', 'Conteúdo infantil', '🧸', '#9c27b0', 5),
('Regional', 'Canais regionais', '🗺️', '#009688', 6),
('Música', 'Canais de música', '🎵', '#d32f2f', 7),
('Educación', 'Educação e cultura', '📚', '#7b1fa2', 8);

-- Se já existirem, evitar duplicatas
ON DUPLICATE KEY UPDATE 
    description = VALUES(description),
    icon = VALUES(icon),
    color = VALUES(color),
    order_index = VALUES(order_index);