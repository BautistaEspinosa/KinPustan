
CREATE TABLE IF NOT EXISTS usuarios (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  correo VARCHAR(100) NOT NULL UNIQUE,
  contrasenia VARCHAR(255) NOT NULL,
  tipUser VARCHAR(50) NOT NULL
);

INSERT INTO usuarios (nombre, correo, contrasenia, tipUser)
VALUES (
  'Admin',
  'admin@kinpustan.com',
  '{bcrypt}$2a$10$2G8PQ1YpB0fL1P63pCsZ1ObLswNfZ4wUp3ZqP0j6p2MCmDqSptZeO',
  'ADMIN'
);