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


-- NIVELES.
INSERT INTO nivel (id, nombre, color, orden) VALUES (1, 'Inicial', '#b7b7b7', '1');
INSERT INTO nivel (id, nombre, color, orden) VALUES (2, 'Básico', '#009deb', '2');
INSERT INTO nivel (id, nombre, color, orden) VALUES (3, 'Intermedio', '#6dd702', '3');
INSERT INTO nivel (id, nombre, color, orden) VALUES (4, 'Avanzado', '#ff9500', '4');
INSERT INTO nivel (id, nombre, color, orden) VALUES (5, 'Experto', '#e80044', '5');

ALTER SEQUENCE nivel_seq RESTART WITH 55;


-- USUARIOS.
INSERT INTO user_entity (id, username, password, email, nombre, apellidos, fecha_nacimiento, sexo, modalidad_preferida, num_telefono, mostrar_num_telefono, imagen_perfil, idioma_nativo, descripcion_profesional, presentacion_personal, enabled, activation_token, created_at, pais_nativo_id, pais_residencia_id) VALUES ('551e8400-e22b-41d4-a716-446655440010', 'admin', '{noop}admin', 'admin@gmail.com', 'Duke', 'Java', '1985-05-23', 'HOMBRE', 'VIRTUAL', '+34111222333', true, 'https://upload.wikimedia.org/wikipedia/en/thumb/d/d2/Duke_%28Java_mascot%29_waving.svg/1200px-Duke_%28Java_mascot%29_waving.svg.png', 'en', 'Desarrollador senior en Java. Más de 15 años de experiencia en aplicaciones empresariales, microservicios y sistemas distribuidos. Mentor y predicador de buenas prácticas.', 'Apasionado por el café, el código limpio y los patrones de diseño.', true, NULL, CURRENT_TIMESTAMP, 2, 3);
    INSERT INTO user_lista_otros_idiomas (user_id, lista_otros_idiomas) VALUES ('551e8400-e22b-41d4-a716-446655440010', 'es');
    INSERT INTO user_lista_enlaces_externos (user_id, lista_enlaces_externos) VALUES ('551e8400-e22b-41d4-a716-446655440010', 'https://x.com/');
    INSERT INTO user_lista_enlaces_externos (user_id, lista_enlaces_externos) VALUES ('551e8400-e22b-41d4-a716-446655440010', 'https://www.linkedin.com/');
    INSERT INTO user_lista_enlaces_externos (user_id, lista_enlaces_externos) VALUES ('551e8400-e22b-41d4-a716-446655440010', 'https://github.com/');
    INSERT INTO user_roles (user_id, roles) VALUES ('551e8400-e22b-41d4-a716-446655440010', 'ADMIN');

INSERT INTO user_entity (id, username, password, email, nombre, apellidos, fecha_nacimiento, sexo, modalidad_preferida, num_telefono, mostrar_num_telefono, imagen_perfil, idioma_nativo, descripcion_profesional, presentacion_personal, enabled, activation_token, created_at, pais_nativo_id, pais_residencia_id) VALUES ('123e4567-e89b-12d3-a456-426614174000', 'Khin90', '{noop}khin', 'khindasvinto@gmail.com', 'Khindasvinto', 'Batbayar Gaanbatar', '1990-01-01', 'HOMBRE', 'VIRTUAL', '+34123456789', false, 'https://i.pinimg.com/474x/87/39/e4/8739e4274f7fcb13c440dc51030f216b.jpg', 'es', 'Médico licenciado: Experiencia laboral Médica adjunta del Servicio de Obstetricia y Ginecología, mayo 2020- noviembre 2021.', 'Boticario a tiempo completo y karateka de nacimiento.', true, NULL, CURRENT_TIMESTAMP, 1, 1);
    INSERT INTO user_lista_otros_idiomas (user_id, lista_otros_idiomas) VALUES ('123e4567-e89b-12d3-a456-426614174000', 'en');
    INSERT INTO user_lista_otros_idiomas (user_id, lista_otros_idiomas) VALUES ('123e4567-e89b-12d3-a456-426614174000', 'fr');
    INSERT INTO user_lista_enlaces_externos (user_id, lista_enlaces_externos) VALUES ('123e4567-e89b-12d3-a456-426614174000', 'https://x.com/');
    INSERT INTO user_lista_enlaces_externos (user_id, lista_enlaces_externos) VALUES ('123e4567-e89b-12d3-a456-426614174000', 'https://www.linkedin.com/');
    INSERT INTO user_lista_enlaces_externos (user_id, lista_enlaces_externos) VALUES ('123e4567-e89b-12d3-a456-426614174000', 'https://github.com/');
    INSERT INTO user_roles (user_id, roles) VALUES ('123e4567-e89b-12d3-a456-426614174000', 'USER');
    -- TALENTOS.
    INSERT INTO talento (id, titulo, descripcion, imagen, nivel_id, user_id) VALUES (1, 'Karate', 'Tal y cómo se muestra en mi perfil, soy karateka profesional desde hace años y dispongo de cinturón negro, además también enseño trucos de defensa personal contra armas de fuego y llaves de otras artes marciales.', 'https://m.media-amazon.com/images/I/61WraYc6DoL._AC_UF1000,1000_QL80_.jpg', 5, '123e4567-e89b-12d3-a456-426614174000');
    INSERT INTO talento (id, titulo, descripcion, imagen, nivel_id, user_id) VALUES (2, 'Pintura al óleo', 'Enseño técnicas clásicas y mezcla de colores.', null, 2, '123e4567-e89b-12d3-a456-426614174000');
    INSERT INTO talento (id, titulo, descripcion, imagen, nivel_id, user_id) VALUES (3, 'Inglés para conversar', 'He vivido 3 años en Reino Unido y puedo ayudar a mejorar la fluidez, pronunciación y vocabulario en inglés.', null, 3, '123e4567-e89b-12d3-a456-426614174000');

INSERT INTO user_entity (id, username, password, email, nombre, apellidos, fecha_nacimiento, sexo, modalidad_preferida, num_telefono, mostrar_num_telefono, imagen_perfil, idioma_nativo, descripcion_profesional, presentacion_personal, enabled, activation_token, created_at, pais_nativo_id, pais_residencia_id) VALUES ('f13a2e98-70f5-4d61-93ab-349be7022025', 'Elirart', '{noop}elira123', 'elira.qose@example.com', 'Elira', 'Qose', '1995-08-17', 'MUJER', 'VIRTUAL', '+355681234567', true, 'https://medias.artmajeur.com/mini/17840476_1.jpg', 'sq', 'Profesora de arte con enfoque en pintura abstracta. Más de 7 años de experiencia en talleres comunitarios y proyectos escolares.', 'Me encanta ayudar a otros a descubrir su lado artístico y expresarse a través de los colores.', true, NULL, CURRENT_TIMESTAMP, 5, 4);
    INSERT INTO user_lista_otros_idiomas (user_id, lista_otros_idiomas) VALUES ('f13a2e98-70f5-4d61-93ab-349be7022025', 'en');
    INSERT INTO user_lista_otros_idiomas (user_id, lista_otros_idiomas) VALUES ('f13a2e98-70f5-4d61-93ab-349be7022025', 'it');
    INSERT INTO user_lista_enlaces_externos (user_id, lista_enlaces_externos) VALUES ('f13a2e98-70f5-4d61-93ab-349be7022025', 'https://www.instagram.com/eliraart/');
    INSERT INTO user_lista_enlaces_externos (user_id, lista_enlaces_externos) VALUES ('f13a2e98-70f5-4d61-93ab-349be7022025', 'https://www.behance.net/eliraqose');
    INSERT INTO user_roles (user_id, roles) VALUES ('f13a2e98-70f5-4d61-93ab-349be7022025', 'USER');
    -- TALENTOS.
    INSERT INTO talento (id, titulo, descripcion, imagen, nivel_id, user_id) VALUES (4, 'Pintura abstracta', 'Exploro formas, colores y emociones. Enseño técnicas modernas y expresión libre.', 'https://i.pinimg.com/736x/61/98/79/619879c242922d0f7f317d63095d1920.jpg', 4, 'f13a2e98-70f5-4d61-93ab-349be7022025');
    INSERT INTO talento (id, titulo, descripcion, imagen, nivel_id, user_id) VALUES (5, 'Introducción al bordado', 'Curso básico de técnicas de bordado tradicional, ideal para principiantes que quieren aprender a crear sus primeros diseños a mano.', null, 2, 'f13a2e98-70f5-4d61-93ab-349be7022025');


-- TALENTOS.
--INSERT INTO talento (id, titulo, descripcion, imagen, nivel_id, user_id) VALUES (1, 'Karate', 'Tal y cómo se muestra en mi perfil, soy karateka profesional desde hace años y dispongo de cinturón negro, además también enseño trucos de defensa personal contra armas de fuego y llaves de otras artes marciales.', 'https://m.media-amazon.com/images/I/61WraYc6DoL._AC_UF1000,1000_QL80_.jpg', 5, '123e4567-e89b-12d3-a456-426614174000');
--INSERT INTO talento (id, titulo, descripcion, imagen, nivel_id, user_id) VALUES (2, 'Pintura al óleo', 'Enseño técnicas clásicas y mezcla de colores.', null, 2, '123e4567-e89b-12d3-a456-426614174000');
--INSERT INTO talento (id, titulo, descripcion, imagen, nivel_id, user_id) VALUES (3, 'Inglés para conversar', 'He vivido 3 años en Reino Unido y puedo ayudar a mejorar la fluidez, pronunciación y vocabulario en inglés.', null, 3, '123e4567-e89b-12d3-a456-426614174000');

--INSERT INTO talento (id, titulo, descripcion, imagen, nivel_id, user_id) VALUES (4, 'Pintura abstracta', 'Exploro formas, colores y emociones. Enseño técnicas modernas y expresión libre.', 'https://i.pinimg.com/736x/61/98/79/619879c242922d0f7f317d63095d1920.jpg', 4, 'f13a2e98-70f5-4d61-93ab-349be7022025');
--INSERT INTO talento (id, titulo, descripcion, imagen, nivel_id, user_id) VALUES (5, 'Introducción al bordado', 'Curso básico de técnicas de bordado tradicional, ideal para principiantes que quieren aprender a crear sus primeros diseños a mano.', null, 2, 'f13a2e98-70f5-4d61-93ab-349be7022025');

ALTER SEQUENCE talento_seq RESTART WITH 55;






