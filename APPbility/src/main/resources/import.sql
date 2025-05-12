-- CONTINENTES.
INSERT INTO continente (id, nombre) VALUES (1, 'América del Norte');
INSERT INTO continente (id, nombre) VALUES (2, 'América del Sur');
INSERT INTO continente (id, nombre) VALUES (3, 'Europa');
INSERT INTO continente (id, nombre) VALUES (4, 'África');
INSERT INTO continente (id, nombre) VALUES (5, 'Asia');
INSERT INTO continente (id, nombre) VALUES (6, 'Oceanía');
INSERT INTO continente (id, nombre) VALUES (7, 'Antártida');

ALTER SEQUENCE continente_seq RESTART WITH 57;


-- PAISES.
INSERT INTO pais (id, nombre, codigoISO, bandera, continente_id) VALUES (1, 'Andorra', 'AD', 'https://flagpedia.net/data/flags/emoji/twitter/256x256/ad.png', 3);
INSERT INTO pais (id, nombre, codigoISO, bandera, continente_id) VALUES (2, 'Emiratos Árabes Unidos', 'AE', 'https://flagpedia.net/data/flags/emoji/twitter/256x256/ae.png', 5);
INSERT INTO pais (id, nombre, codigoISO, bandera, continente_id) VALUES (3, 'Afghanistan', 'AF', 'https://flagpedia.net/data/flags/emoji/twitter/256x256/af.png', 5);
INSERT INTO pais (id, nombre, codigoISO, bandera, continente_id) VALUES (4, 'Antigua y Barbuda', 'AG', 'https://flagpedia.net/data/flags/emoji/twitter/256x256/ag.png', 1);
INSERT INTO pais (id, nombre, codigoISO, bandera, continente_id) VALUES (5, 'Albania', 'AL', 'https://flagpedia.net/data/flags/emoji/twitter/256x256/al.png', 3);

ALTER SEQUENCE pais_seq RESTART WITH 55;


-- USUARIOS.
INSERT INTO user_entity (id, username, password, email, nombre, apellidos, fecha_nacimiento, sexo, modalidad_preferida, num_telefono, mostrar_num_telefono, imagen_perfil, idioma_nativo, descripcion_profesional, presentacion_personal, enabled, activation_token, created_at, pais_nativo_id, pais_residencia_id) VALUES ('123e4567-e89b-12d3-a456-426614174000', 'Khin90', 'hash', 'khindasvinto@gmail.com', 'Khindasvinto', 'Batbayar Gaanbatar', '1990-01-01', 'HOMBRE', 'VIRTUAL', '+34123456789', false, 'https://i.pinimg.com/474x/87/39/e4/8739e4274f7fcb13c440dc51030f216b.jpg', 'es', 'Desarrollador Java', 'Programador a tiempo completo y karateka de nacimiento.', true, NULL, CURRENT_TIMESTAMP, 1, 1);

INSERT INTO user_lista_otros_idiomas (user_id, lista_otros_idiomas) VALUES ('123e4567-e89b-12d3-a456-426614174000', 'en');
INSERT INTO user_lista_otros_idiomas (user_id, lista_otros_idiomas) VALUES ('123e4567-e89b-12d3-a456-426614174000', 'fr');

INSERT INTO user_lista_enlaces_externos (user_id, lista_enlaces_externos) VALUES ('123e4567-e89b-12d3-a456-426614174000', 'https://x.com/');
INSERT INTO user_lista_enlaces_externos (user_id, lista_enlaces_externos) VALUES ('123e4567-e89b-12d3-a456-426614174000', 'https://www.linkedin.com/');
INSERT INTO user_lista_enlaces_externos (user_id, lista_enlaces_externos) VALUES ('123e4567-e89b-12d3-a456-426614174000', 'https://github.com/');

INSERT INTO user_roles (user_id, roles) VALUES ('123e4567-e89b-12d3-a456-426614174000', 'USER');

ALTER SEQUENCE pais_seq RESTART WITH 51;


