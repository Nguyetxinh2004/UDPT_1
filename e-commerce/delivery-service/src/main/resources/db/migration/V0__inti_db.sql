
-- Bảng đơn hàng
CREATE TABLE orders
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id     BIGINT      NOT NULL,
    status      VARCHAR(100) NOT NULL DEFAULT 'PENDING', -- PENDING, SHIPPED, DELIVERED, CANCELED
    total_price INT
);

-- Bảng chi tiết đơn hàng
CREATE TABLE order_items
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id    BIGINT      NOT NULL,
    food_id     VARCHAR(100) NOT NULL,
    quantity    INT         NOT NULL,
    unit_price  INT,
    total_price INT,

    CONSTRAINT fk_order_items_order FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE
);

-- Bảng giao hàng
CREATE TABLE deliveries
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id         BIGINT       NOT NULL,
    delivery_address VARCHAR(255) NOT NULL,
    delivery_time    DATETIME,
    shipper_name     VARCHAR(100),
    shipper_phone    VARCHAR(20),

    CONSTRAINT fk_delivery_order FOREIGN KEY (order_id) REFERENCES orders (id)
);
