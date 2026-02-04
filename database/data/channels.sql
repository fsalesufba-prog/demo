-- Canais peruanos populares
INSERT INTO channels (name, description, url, logo_url, category_id, is_active, is_premium, order_index) VALUES
-- Nacional (Categoria 1)
('América TV', 'Canal 4 - América Televisión', 'http://example.com/america.m3u8', 'https://logo.pt/america.png', 1, TRUE, FALSE, 1),
('ATV', 'Canal 9 - Andina de Televisión', 'http://example.com/atv.m3u8', 'https://logo.pt/atv.png', 1, TRUE, FALSE, 2),
('Latina', 'Canal 2 - Latina Televisión', 'http://example.com/latina.m3u8', 'https://logo.pt/latina.png', 1, TRUE, FALSE, 3),
('Panamericana', 'Canal 5 - Panamericana Televisión', 'http://example.com/panamericana.m3u8', 'https://logo.pt/panamericana.png', 1, TRUE, FALSE, 4),

-- Actualidad (Categoria 2)
('Willax', 'Willax Televisión - Notícias', 'http://example.com/willax.m3u8', 'https://logo.pt/willax.png', 2, TRUE, FALSE, 1),
('RPP', 'Radio Programas del Perú - Notícias', 'http://example.com/rpp.m3u8', 'https://logo.pt/rpp.png', 2, TRUE, FALSE, 2),
('Exitosa', 'Exitosa TV - Notícias', 'http://example.com/exitosa.m3u8', 'https://logo.pt/exitosa.png', 2, TRUE, FALSE, 3),

-- Deportes (Categoria 3)
('Gol Perú', 'Canal de futebol peruano', 'http://example.com/golperu.m3u8', 'https://logo.pt/golperu.png', 3, TRUE, TRUE, 1),
('Movistar Deportes', 'Deportes en Movistar', 'http://example.com/movistardeportes.m3u8', 'https://logo.pt/movistardeportes.png', 3, TRUE, TRUE, 2),

-- Infantil (Categoria 5)
('Paka Paka', 'Canal infantil argentino', 'http://example.com/pakapaka.m3u8', 'https://logo.pt/pakapaka.png', 5, TRUE, FALSE, 1),

-- Regional (Categoria 6)
('TV Perú Norte', 'TV Perú para el norte', 'http://example.com/tvperunorte.m3u8', 'https://logo.pt/tvperu.png', 6, TRUE, FALSE, 1),
('Arequipa TV', 'Canal regional de Arequipa', 'http://example.com/arequipatv.m3u8', 'https://logo.pt/arequipatv.png', 6, TRUE, FALSE, 2);

-- Atualizar se já existir
ON DUPLICATE KEY UPDATE 
    description = VALUES(description),
    url = VALUES(url),
    logo_url = VALUES(logo_url),
    category_id = VALUES(category_id),
    is_active = VALUES(is_active),
    is_premium = VALUES(is_premium),
    order_index = VALUES(order_index);