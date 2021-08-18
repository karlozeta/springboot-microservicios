INSERT INTO usuarios(USERNAME, PASSWORD, ENABLED, NOMBRE, APELLIDO, EMAIL) VALUES('carlos', '$2a$10$eMPBL9Db8Nx4Qzwv2blqO.7KgxY8S1g6f/B.LpRWm3AaqNalRSjBa', 1, 'Carlos', 'Zapata', 'carlos@correo.com');
INSERT INTO usuarios(USERNAME, PASSWORD, ENABLED, NOMBRE, APELLIDO, EMAIL) VALUES('admin', '$2a$10$ntIZwI/kguK4phPoC22PHuBqYxR8OG5ChWTnpDf0EEG/E7Q3wH./a', 1, 'Fernando', 'Gomez', 'fernando@correo.com');

INSERT INTO roles(NOMBRE) VALUES('ROLE_USER');
INSERT INTO roles(NOMBRE) VALUES('ROLE_ADMIN');

INSERT INTO usuarios_roles(USUARIO_ID, ROLE_ID) VALUES(1, 1);
INSERT INTO usuarios_roles(USUARIO_ID, ROLE_ID) VALUES(2, 2);
INSERT INTO usuarios_roles(USUARIO_ID, ROLE_ID) VALUES(2, 1);