ALTER TABLE games
    ADD COLUMN price numeric(10,2) NOT NULL DEFAULT 0;

CREATE TABLE user_orders(
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    status VARCHAR(20) NOT NULL ,
    created_at TIMESTAMP NOT NULL,
    total_amount numeric(10,2) NOT NULL
);

CREATE INDEX idx_user_orders_user_id ON user_orders(user_id);

CREATE TABLE cart(
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id)
);

CREATE TABLE order_item(
    id SERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL REFERENCES user_orders(id),
    game_id BIGINT NOT NULL REFERENCES games(id),
    price numeric(10,2) NOT NULL,
    quantity INTEGER NOT NULL DEFAULT 1
);

ALTER TABLE order_item ADD CONSTRAINT uq_order_item UNIQUE(order_id, game_id);

CREATE TABLE payments(
    id SERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL REFERENCES user_orders(id),
    amount numeric(10,2) NOT NULL,
    payment_method VARCHAR(20) NOT NULL,
    transaction_id VARCHAR(100),
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- Индекс для ускорения поиска платежей по заказу
CREATE INDEX idx_payments_order_id ON payments(order_id);

-- Индекс для быстрого поиска по transaction_id (часто уникален)
CREATE UNIQUE INDEX idx_payments_transaction_id ON payments(transaction_id);

CREATE TABLE cart_item(
    id SERIAL PRIMARY KEY,
    game_id BIGINT NOT NULL REFERENCES games(id),
    cart_id BIGINT NOT NULL REFERENCES cart(id)
);

ALTER TABLE cart_item ADD CONSTRAINT uq_cart_item UNIQUE(cart_id, game_id);
