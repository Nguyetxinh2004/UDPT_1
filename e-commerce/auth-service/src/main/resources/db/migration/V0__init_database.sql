CREATE TABLE users
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    username      VARCHAR(50)  NOT NULL UNIQUE,
    email         VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    full_name     VARCHAR(100),
    phone_number  VARCHAR(20),
    role          VARCHAR(20) DEFAULT 'USER'
);


insert into users(username, email, password_hash, full_name, phone_number, role) -- string
values('admin', 'admin@gmail.com', '$2a$10$cPfM0vYQ70jK5PFn29D1wusdqtddDDJg7y5AUthBidFj8xwrliDhm', 'admin','+1234567890', 'ADMIN');


