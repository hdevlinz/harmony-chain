CREATE
DATABASE IF NOT EXISTS identity_service CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
GRANT ALL PRIVILEGES ON identity_service.* TO
'admin'@'%';

CREATE
DATABASE IF NOT EXISTS inventory_service CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
GRANT ALL PRIVILEGES ON inventory_service.* TO
'admin'@'%';

CREATE
DATABASE IF NOT EXISTS order_serivce CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
GRANT ALL PRIVILEGES ON order_serivce.* TO
'admin'@'%';
