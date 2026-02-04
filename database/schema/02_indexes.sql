-- Índices adicionais para performance
CREATE INDEX idx_channels_name ON channels(name);
CREATE INDEX idx_channels_country ON channels(country);
CREATE INDEX idx_users_full_name ON users(full_name);
CREATE INDEX idx_users_subscription_end ON users(subscription_end);
CREATE INDEX idx_categories_order_index ON categories(order_index);
CREATE INDEX idx_channels_order_index ON channels(order_index);