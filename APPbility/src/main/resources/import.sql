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
INSERT INTO pais (id, nombre, codigoISO, bandera, continente_id) VALUES (7, 'Armenia', 'AM', 'https://flagpedia.net/data/flags/emoji/twitter/256x256/am.png', 3);
INSERT INTO pais (id, nombre, codigoISO, bandera, continente_id) VALUES (8, 'Angola', 'AO', 'https://flagpedia.net/data/flags/emoji/twitter/256x256/ao.png', 4);
INSERT INTO pais (id, nombre, codigoISO, bandera, continente_id) VALUES (9, 'Región Antártica', 'AQ', 'https://flagpedia.net/data/flags/emoji/twitter/256x256/aq.png', 7);
INSERT INTO pais (id, nombre, codigoISO, bandera, continente_id) VALUES (10, 'Argentina', 'AR', 'https://flagpedia.net/data/flags/emoji/twitter/256x256/ar.png', 2);

ALTER SEQUENCE pais_seq RESTART WITH 59;


-- NIVELES.
INSERT INTO nivel (id, nombre, color, orden) VALUES (1, 'Inicial', '#b7b7b7', '1');
INSERT INTO nivel (id, nombre, color, orden) VALUES (2, 'Básico', '#009deb', '2');
INSERT INTO nivel (id, nombre, color, orden) VALUES (3, 'Intermedio', '#6dd702', '3');
INSERT INTO nivel (id, nombre, color, orden) VALUES (4, 'Avanzado', '#ff9500', '4');
INSERT INTO nivel (id, nombre, color, orden) VALUES (5, 'Experto', '#e80044', '5');

ALTER SEQUENCE nivel_seq RESTART WITH 55;


-- USUARIOS.
-- Duke, Java (admin - admin)
INSERT INTO user_entity (id, username, password, email, nombre, apellidos, fecha_nacimiento, sexo, modalidad_preferida, num_telefono, mostrar_num_telefono, color, imagen_perfil, idioma_nativo, descripcion_profesional, presentacion_personal, enabled, activation_token, created_at, pais_nativo_id, pais_residencia_id) VALUES ('551e8400-e22b-41d4-a716-446655440010', 'admin', '{noop}admin', 'admin@gmail.com', 'Duke', 'Java', '1985-05-23', 'HOMBRE', 'VIRTUAL', '+34111222333', true, '#0F7BFF', 'https://upload.wikimedia.org/wikipedia/en/thumb/d/d2/Duke_%28Java_mascot%29_waving.svg/1200px-Duke_%28Java_mascot%29_waving.svg.png', 'en', 'Desarrollador senior en Java. Más de 15 años de experiencia en aplicaciones empresariales, microservicios y sistemas distribuidos. Mentor y predicador de buenas prácticas.', 'Apasionado por el café, el código limpio y los patrones de diseño.', true, NULL, CURRENT_TIMESTAMP, 2, 3);
    INSERT INTO user_lista_otros_idiomas (user_id, lista_otros_idiomas) VALUES ('551e8400-e22b-41d4-a716-446655440010', 'es');
    INSERT INTO user_lista_enlaces_externos (user_id, lista_enlaces_externos) VALUES ('551e8400-e22b-41d4-a716-446655440010', 'https://x.com/');
    INSERT INTO user_lista_enlaces_externos (user_id, lista_enlaces_externos) VALUES ('551e8400-e22b-41d4-a716-446655440010', 'https://www.linkedin.com/');
    INSERT INTO user_lista_enlaces_externos (user_id, lista_enlaces_externos) VALUES ('551e8400-e22b-41d4-a716-446655440010', 'https://github.com/');
    INSERT INTO user_roles (user_id, roles) VALUES ('551e8400-e22b-41d4-a716-446655440010', 'ADMIN');

-- Khindasvinto, Batbayar Gaanbatar (Khin90 - khin)
INSERT INTO user_entity (id, username, password, email, nombre, apellidos, fecha_nacimiento, sexo, modalidad_preferida, num_telefono, mostrar_num_telefono, color, imagen_perfil, idioma_nativo, descripcion_profesional, presentacion_personal, enabled, activation_token, created_at, pais_nativo_id, pais_residencia_id) VALUES ('123e4567-e89b-12d3-a456-426614174000', 'Khin90', '{noop}khin', 'khindasvinto@gmail.com', 'Khindasvinto', 'Batbayar Gaanbatar', '1990-01-01', 'HOMBRE', 'VIRTUAL', '+34123456789', false, '#6A7FDE', 'https://i.pinimg.com/474x/87/39/e4/8739e4274f7fcb13c440dc51030f216b.jpg', 'es', 'Médico licenciado: Experiencia laboral Médica adjunta del Servicio de Obstetricia y Ginecología, mayo 2020- noviembre 2021.', 'Boticario a tiempo completo y karateka de nacimiento.', true, NULL, CURRENT_TIMESTAMP, 1, 1);
    INSERT INTO user_lista_otros_idiomas (user_id, lista_otros_idiomas) VALUES ('123e4567-e89b-12d3-a456-426614174000', 'en');
    INSERT INTO user_lista_otros_idiomas (user_id, lista_otros_idiomas) VALUES ('123e4567-e89b-12d3-a456-426614174000', 'fr');
    INSERT INTO user_lista_enlaces_externos (user_id, lista_enlaces_externos) VALUES ('123e4567-e89b-12d3-a456-426614174000', 'https://x.com/');
    INSERT INTO user_lista_enlaces_externos (user_id, lista_enlaces_externos) VALUES ('123e4567-e89b-12d3-a456-426614174000', 'https://www.linkedin.com/');
    INSERT INTO user_lista_enlaces_externos (user_id, lista_enlaces_externos) VALUES ('123e4567-e89b-12d3-a456-426614174000', 'https://github.com/');
    INSERT INTO user_roles (user_id, roles) VALUES ('123e4567-e89b-12d3-a456-426614174000', 'USER');
        -----
        INSERT INTO talento (id, titulo, descripcion, imagen, nivel_id, user_id) VALUES (1, 'Karate', 'Tal y cómo se muestra en mi perfil, soy karateka profesional desde hace años y dispongo de cinturón negro, además también enseño trucos de defensa personal contra armas de fuego y llaves de otras artes marciales.', 'https://m.media-amazon.com/images/I/61WraYc6DoL._AC_UF1000,1000_QL80_.jpg', 5, '123e4567-e89b-12d3-a456-426614174000');
        INSERT INTO talento (id, titulo, descripcion, imagen, nivel_id, user_id) VALUES (2, 'Pintura al óleo', 'Enseño técnicas clásicas y mezcla de colores.', null, 2, '123e4567-e89b-12d3-a456-426614174000');
        INSERT INTO talento (id, titulo, descripcion, imagen, nivel_id, user_id) VALUES (3, 'Inglés para conversar', 'He vivido 3 años en Reino Unido y puedo ayudar a mejorar la fluidez, pronunciación y vocabulario en inglés.', null, 3, '123e4567-e89b-12d3-a456-426614174000');

-- Elira, Qose (Elirart - elira123)
INSERT INTO user_entity (id, username, password, email, nombre, apellidos, fecha_nacimiento, sexo, modalidad_preferida, num_telefono, mostrar_num_telefono, color, imagen_perfil, idioma_nativo, descripcion_profesional, presentacion_personal, enabled, activation_token, created_at, pais_nativo_id, pais_residencia_id) VALUES ('f13a2e98-70f5-4d61-93ab-349be7022025', 'Elirart', '{noop}elira123', 'elira.qose@gmail.com', 'Elira', 'Qose', '1995-08-17', 'MUJER', 'VIRTUAL', '+355681234567', true, '#FF00CC', 'https://medias.artmajeur.com/mini/17840476_1.jpg', 'sq', 'Profesora de arte con enfoque en pintura abstracta. Más de 7 años de experiencia en talleres comunitarios y proyectos escolares.', 'Me encanta ayudar a otros a descubrir su lado artístico y expresarse a través de los colores.', true, NULL, CURRENT_TIMESTAMP, 5, 4);
    INSERT INTO user_lista_otros_idiomas (user_id, lista_otros_idiomas) VALUES ('f13a2e98-70f5-4d61-93ab-349be7022025', 'en');
    INSERT INTO user_lista_otros_idiomas (user_id, lista_otros_idiomas) VALUES ('f13a2e98-70f5-4d61-93ab-349be7022025', 'it');
    INSERT INTO user_lista_enlaces_externos (user_id, lista_enlaces_externos) VALUES ('f13a2e98-70f5-4d61-93ab-349be7022025', 'https://www.instagram.com/eliraart/');
    INSERT INTO user_lista_enlaces_externos (user_id, lista_enlaces_externos) VALUES ('f13a2e98-70f5-4d61-93ab-349be7022025', 'https://www.behance.net/eliraqose');
    INSERT INTO user_roles (user_id, roles) VALUES ('f13a2e98-70f5-4d61-93ab-349be7022025', 'USER');
        -----
        INSERT INTO talento (id, titulo, descripcion, imagen, nivel_id, user_id) VALUES (4, 'Pintura abstracta', 'Exploro formas, colores y emociones. Enseño técnicas modernas y expresión libre.', 'https://i.pinimg.com/736x/61/98/79/619879c242922d0f7f317d63095d1920.jpg', 4, 'f13a2e98-70f5-4d61-93ab-349be7022025');
        INSERT INTO talento (id, titulo, descripcion, imagen, nivel_id, user_id) VALUES (5, 'Introducción al bordado', 'Curso básico de técnicas de bordado tradicional, ideal para principiantes que quieren aprender a crear sus primeros diseños a mano.', null, 2, 'f13a2e98-70f5-4d61-93ab-349be7022025');

-- Tigran Miqayel, Harutyunyan Kirakosyan (Arman - timihaki)
INSERT INTO user_entity (id, username, password, email, nombre, apellidos, fecha_nacimiento, sexo, modalidad_preferida, num_telefono, mostrar_num_telefono, color, imagen_perfil, idioma_nativo, descripcion_profesional, presentacion_personal, enabled, activation_token, created_at, pais_nativo_id, pais_residencia_id) VALUES ('9f3c5a28-92d4-4e77-b8c0-55a6b7c1ea00', 'Arman', '{noop}timihaki', 'armanharutyunyan@gmail.com', 'Tigran Miqayel', 'Harutyunyan Kirakosyan', '1990-08-14', 'HOMBRE', 'PRESENCIAL', '+37477123456', true, '#CD0070', 'https://img.freepik.com/foto-gratis/retrato-hombre-sonriente-posando-al-aire-libre_23-2148803564.jpg', 'hy', 'Apasionado por la biología, la cultura gastronómica armenia y los métodos científicos tradicionales.', 'Curioso, dedicado… y a veces un poco terco. Pero siempre con ganas de compartir conocimientos.', true, NULL, CURRENT_TIMESTAMP, 7, 8);
    INSERT INTO user_lista_otros_idiomas (user_id, lista_otros_idiomas) VALUES ('9f3c5a28-92d4-4e77-b8c0-55a6b7c1ea00', 'en');
    INSERT INTO user_lista_otros_idiomas (user_id, lista_otros_idiomas) VALUES ('9f3c5a28-92d4-4e77-b8c0-55a6b7c1ea00', 'ru');
    INSERT INTO user_lista_enlaces_externos (user_id, lista_enlaces_externos) VALUES ('9f3c5a28-92d4-4e77-b8c0-55a6b7c1ea00', 'https://www.linkedin.com/in/tigran-harutyunyan');
    INSERT INTO user_roles (user_id, roles) VALUES ('9f3c5a28-92d4-4e77-b8c0-55a6b7c1ea00', 'USER');
        -----
        INSERT INTO talento (id, titulo, descripcion, imagen, nivel_id, user_id) VALUES (6, 'Microscopía para principiantes', 'Descubre el fascinante mundo microscópico: aprende a usar un microscopio óptico, preparar muestras biológicas sencillas y observar células y tejidos con tus propios ojos.', NULL, 1, '9f3c5a28-92d4-4e77-b8c0-55a6b7c1ea00');
        INSERT INTO talento (id, titulo, descripcion, imagen, nivel_id, user_id) VALUES (7, 'Cocina tradicional armenia', 'Aprende a preparar platos clásicos como el khorovats, dolma o harissa. Conoce los secretos de las especias armenias y la historia detrás de cada receta.', 'https://www.seriouseats.com/thmb/B7k0_4SlsrJBmzbQcBp4f6NTCTg=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/20230106-SHISH-KEBAB-ANDREW-JANJIGIAN-25-09d222b8b2764cfdb058e5ac592a99c4.jpg', 2, '9f3c5a28-92d4-4e77-b8c0-55a6b7c1ea00');
        INSERT INTO talento (id, titulo, descripcion, imagen, nivel_id, user_id) VALUES (8, 'Técnicas experimentales en biología molecular', 'Desde la extracción de ADN hasta la electroforesis en gel: aprende paso a paso los fundamentos experimentales que se usan en laboratorios de genética y biotecnología.', NULL, 4, '9f3c5a28-92d4-4e77-b8c0-55a6b7c1ea00');

-- Malena, García Urquiza (MalenG - D3SM3L3N4D4)
INSERT INTO user_entity (id, username, password, email, nombre, apellidos, fecha_nacimiento, sexo, modalidad_preferida, num_telefono, mostrar_num_telefono, color, imagen_perfil, idioma_nativo, descripcion_profesional, presentacion_personal, enabled, activation_token, created_at, pais_nativo_id, pais_residencia_id) VALUES ('d19b3b6e-8f7a-43f1-a8f4-92e3d5a40007', 'MalenG', '{noop}D3SM3L3N4D4', 'malena.garcia@gmail.com', 'Malena', 'García Urquiza', '1992-08-15', 'MUJER', 'AMBAS', '+5491122334455', false, '#FF0F57', 'https://plus.unsplash.com/premium_photo-1661892088256-0a17130b3d0d?fm=jpg&q=60&w=3000&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8cGVycml0b3xlbnwwfHwwfHx8MA%3D%3D', 'es', 'Graduada en Veterinaria, con especializaciones en dermatología animal y comportamiento felino. También cuenta con estudios de estética y cuidado personal.', 'Soy una persona optimista, amante de los animales y la belleza. Disfruto enseñando lo que sé y aprendiendo de los demás.', true, NULL, CURRENT_TIMESTAMP, 10, 4);
    INSERT INTO user_lista_otros_idiomas (user_id, lista_otros_idiomas) VALUES ('d19b3b6e-8f7a-43f1-a8f4-92e3d5a40007', 'en');
    INSERT INTO user_lista_enlaces_externos (user_id, lista_enlaces_externos) VALUES ('d19b3b6e-8f7a-43f1-a8f4-92e3d5a40007', 'https://www.instagram.com/malenanails');
    INSERT INTO user_lista_enlaces_externos (user_id, lista_enlaces_externos) VALUES ('d19b3b6e-8f7a-43f1-a8f4-92e3d5a40007', 'https://www.linkedin.com/in/malenagarcia');
    INSERT INTO user_roles (user_id, roles) VALUES ('d19b3b6e-8f7a-43f1-a8f4-92e3d5a40007', 'USER');
        -----
        INSERT INTO talento (id, titulo, descripcion, imagen, nivel_id, user_id) VALUES (9, 'Cuidados básicos para mascotas', 'Enseño cómo cuidar de perros y gatos: desde la alimentación hasta la higiene y manejo del estrés. Ideal para nuevos dueños.', null, 3, 'd19b3b6e-8f7a-43f1-a8f4-92e3d5a40007');
        INSERT INTO talento (id, titulo, descripcion, imagen, nivel_id, user_id) VALUES (10, 'Manicura y nail art profesional', 'Aprende técnicas de manicura moderna, cuidado de uñas y decoración creativa. Incluye consejos de higiene y estética.', null, 4, 'd19b3b6e-8f7a-43f1-a8f4-92e3d5a40007');

ALTER SEQUENCE talento_seq RESTART WITH 60;

--INTERCAMBIOS.
-- [UD: Khin90, US: Arman] PROPUESTO
INSERT INTO intercambio (intercambioid, estado, fecha_solicitud, fecha_comienzo, fecha_fin, finalizado_por_demandante, finalizado_por_solicitado, usuario_demandante_id, usuario_solicitado_id, talento_solicitado_id, talento_aceptado_id, talento_sugerido_id) VALUES (1,'PROPUESTO', '2025-04-22T10:45:00', null, null, false, false, '123e4567-e89b-12d3-a456-426614174000', '9f3c5a28-92d4-4e77-b8c0-55a6b7c1ea00', 7, null, 3);

-- [UD: MalenG, US: Arman] ACTIVO
INSERT INTO intercambio (intercambioid, estado, fecha_solicitud, fecha_comienzo, fecha_fin, finalizado_por_demandante, finalizado_por_solicitado, usuario_demandante_id, usuario_solicitado_id, talento_solicitado_id, talento_aceptado_id, talento_sugerido_id) VALUES (2,'ACTIVO', '2025-05-22T17:30:00', '2025-05-24T11:20:00', null, false, false, 'd19b3b6e-8f7a-43f1-a8f4-92e3d5a40007', '9f3c5a28-92d4-4e77-b8c0-55a6b7c1ea00', 6, 9, 9);
    INSERT INTO sesion (id, fecha, intercambio_id_asociado_a_sesion) VALUES (1, '2025-06-07', 2);
    INSERT INTO sesion (id, fecha, intercambio_id_asociado_a_sesion) VALUES (2, '2025-06-25 ', 2);
    INSERT INTO sesion (id, fecha, intercambio_id_asociado_a_sesion) VALUES (3, '2025-06-28', 2);

-- [UD: Elirart, US: MalenG] FINALIZADO
INSERT INTO intercambio (intercambioid, estado, fecha_solicitud, fecha_comienzo, fecha_fin, finalizado_por_demandante, finalizado_por_solicitado, usuario_demandante_id, usuario_solicitado_id, talento_solicitado_id, talento_aceptado_id, talento_sugerido_id) VALUES (3,'FINALIZADO', '2025-05-28T12:45:00', '2025-05-29T09:10:00', '2025-06-04T19:10:00', true, true, 'f13a2e98-70f5-4d61-93ab-349be7022025', 'd19b3b6e-8f7a-43f1-a8f4-92e3d5a40007', 10, 4, 5);

ALTER SEQUENCE intercambio_seq RESTART WITH 53;
ALTER SEQUENCE sesion_seq RESTART WITH 53;
