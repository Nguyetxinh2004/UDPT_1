CREATE TABLE IF NOT EXISTS payments (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        user_id BIGINT NOT NULL,
                                        order_id BIGINT NOT NULL,
                                        total_amount INT NOT NULL,
                                        payment_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
    );
