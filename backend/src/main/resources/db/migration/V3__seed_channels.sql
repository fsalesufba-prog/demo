-- ============================================
-- V3: Seed dos canais iniciais baseados nas imagens
-- ============================================

-- Inserir canais nacionais (Nacional)
INSERT INTO channels (number, name, slug, description, stream_url, logo_url, category_id, is_hd, is_active, created_at) VALUES
('001', 'Willax HD', 'willax-hd', 'Desde 2015, Willax cambia de programacion y paso a emitir compuestas por programas...', 'https://cdn-tv.tutelehd.com.mx/live/willax_stream.m3u8', '/static/images/logos/willax.png', (SELECT id FROM categories WHERE slug = 'nacional'), TRUE, TRUE, NOW()),
('002', 'Latina HD', 'latina-hd', 'Revisa la programacion y no te pierdas de nuestras noticias de hoy...', 'https://cdn-tv.tutelehd.com.mx/live/latina_stream.m3u8', '/static/images/logos/latina.png', (SELECT id FROM categories WHERE slug = 'nacional'), TRUE, TRUE, NOW()),
('003', 'RPP', 'rpp', 'Canal nacional de noticias', 'https://cdn-tv.tutelehd.com.mx/live/rpp_stream.m3u8', '/static/images/logos/rpp.png', (SELECT id FROM categories WHERE slug = 'nacional'), FALSE, TRUE, NOW()),
('004', 'América HD', 'america-hd', 'Es la cadena de television de mayor audiencia a nivel nacional', 'https://cdn-tv.tutelehd.com.mx/live/america_stream.m3u8', '/static/images/logos/americatv.png', (SELECT id FROM categories WHERE slug = 'nacional'), TRUE, TRUE, NOW()),
('005', 'Panamericana HD', 'panamericana-hd', 'Panamericana TV, tiene 62 anos de experiencia, los mismos que nos respaldan y nos...', 'https://cdn-tv.tutelehd.com.mx/live/panamericana_stream.m3u8', '/static/images/logos/panamericana.png', (SELECT id FROM categories WHERE slug = 'nacional'), TRUE, TRUE, NOW()),
('006', 'Exitosa HD', 'exitosa-hd', 'Canal de television peruano que ofrece informacion actualizado sobre politica mundo', 'https://cdn-tv.tutelehd.com.mx/live/exitosa_stream.m3u8', '/static/images/logos/exitosa.png', (SELECT id FROM categories WHERE slug = 'nacional'), TRUE, TRUE, NOW())
ON DUPLICATE KEY UPDATE 
    description = VALUES(description),
    stream_url = VALUES(stream_url),
    logo_url = VALUES(logo_url),
    category_id = VALUES(category_id),
    is_hd = VALUES(is_hd),
    is_active = VALUES(is_active);

-- Inserir canais de atualidades (Actualidad)
INSERT INTO channels (number, name, slug, description, stream_url, logo_url, category_id, is_hd, is_active, created_at) VALUES
('007', 'TV Perú', 'tv-peru', 'Canal público nacional del Perú', 'https://cdn-tv.tutelehd.com.mx/live/tvperu_stream.m3u8', '/static/images/logos/tvperu.png', (SELECT id FROM categories WHERE slug = 'actualidad'), FALSE, TRUE, NOW()),
('008', 'Canal N', 'canal-n', 'Canal de noticias 24 horas', 'https://cdn-tv.tutelehd.com.mx/live/canaln_stream.m3u8', '/static/images/logos/canaln.png', (SELECT id FROM categories WHERE slug = 'actualidad'), FALSE, TRUE, NOW()),
('009', 'ATV+', 'atv-plus', 'Canal de noticias y entretenimiento', 'https://cdn-tv.tutelehd.com.mx/live/atvplus_stream.m3u8', '/static/images/logos/atvplus.png', (SELECT id FROM categories WHERE slug = 'actualidad'), TRUE, TRUE, NOW()),
('010', 'JUSTICIATIVO', 'justiciativo', 'Poder Judicial del Peruano', 'https://cdn-tv.tutelehd.com.mx/live/justiciativo_stream.m3u8', '/static/images/logos/justiciativo.png', (SELECT id FROM categories WHERE slug = 'actualidad'), FALSE, TRUE, NOW())
ON DUPLICATE KEY UPDATE 
    description = VALUES(description),
    stream_url = VALUES(stream_url),
    logo_url = VALUES(logo_url),
    category_id = VALUES(category_id),
    is_hd = VALUES(is_hd),
    is_active = VALUES(is_active);

-- Inserir canais infantis (Infantil)
INSERT INTO channels (number, name, slug, description, stream_url, logo_url, category_id, is_hd, is_active, created_at) VALUES
('011', 'IPE', 'ipe', 'Canal educativo infantil', 'https://cdn-tv.tutelehd.com.mx/live/ipe_stream.m3u8', '/static/images/logos/ipe.png', (SELECT id FROM categories WHERE slug = 'infantil'), FALSE, TRUE, NOW()),
('012', 'DM KidsTV', 'dm-kidstv', 'Canal infantil con programación educativa', 'https://cdn-tv.tutelehd.com.mx/live/dmkidstv_stream.m3u8', '/static/images/logos/dmkidstv.png', (SELECT id FROM categories WHERE slug = 'infantil'), FALSE, TRUE, NOW()),
('013', 'Ipe Kids', 'ipe-kids', 'Variante infantil del canal IPE', 'https://cdn-tv.tutelehd.com.mx/live/ipekids_stream.m3u8', '/static/images/logos/ipekids.png', (SELECT id FROM categories WHERE slug = 'infantil'), FALSE, TRUE, NOW())
ON DUPLICATE KEY UPDATE 
    description = VALUES(description),
    stream_url = VALUES(stream_url),
    logo_url = VALUES(logo_url),
    category_id = VALUES(category_id),
    is_hd = VALUES(is_hd),
    is_active = VALUES(is_active);

-- Inserir canais regionais (Regional)
INSERT INTO channels (number, name, slug, description, stream_url, logo_url, category_id, is_hd, is_active, created_at) VALUES
('014', 'Inka Vision', 'inka-vision', 'Canal regional de Cusco', 'https://cdn-tv.tutelehd.com.mx/live/inkavision_stream.m3u8', '/static/images/logos/inkavision.png', (SELECT id FROM categories WHERE slug = 'regional'), FALSE, TRUE, NOW()),
('015', 'TV Mundo', 'tv-mundo', 'Canal regional de noticias', 'https://cdn-tv.tutelehd.com.mx/live/tvmundo_stream.m3u8', '/static/images/logos/tvmundo.png', (SELECT id FROM categories WHERE slug = 'regional'), FALSE, TRUE, NOW()),
('016', 'TVM', 'tvm', 'Televisión Municipal', 'https://cdn-tv.tutelehd.com.mx/live/tvm_stream.m3u8', '/static/images/logos/tvm.png', (SELECT id FROM categories WHERE slug = 'regional'), FALSE, TRUE, NOW()),
('017', 'FISHIN', 'fishin', 'Canal regional de pesca y naturaleza', 'https://cdn-tv.tutelehd.com.mx/live/fishin_stream.m3u8', '/static/images/logos/fishin.png', (SELECT id FROM categories WHERE slug = 'regional'), FALSE, TRUE, NOW()),
('018', 'Agro TV', 'agro-tv', 'Canal agrícola y ganadero', 'https://cdn-tv.tutelehd.com.mx/live/agrotv_stream.m3u8', '/static/images/logos/agrotv.png', (SELECT id FROM categories WHERE slug = 'regional'), FALSE, TRUE, NOW())
ON DUPLICATE KEY UPDATE 
    description = VALUES(description),
    stream_url = VALUES(stream_url),
    logo_url = VALUES(logo_url),
    category_id = VALUES(category_id),
    is_hd = VALUES(is_hd),
    is_active = VALUES(is_active);