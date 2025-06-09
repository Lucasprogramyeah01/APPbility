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

-- Sofia, Martínez López (SophieML - sofiosasosa)
INSERT INTO user_entity (id, username, password, email, nombre, apellidos, fecha_nacimiento, sexo, modalidad_preferida, num_telefono, mostrar_num_telefono, color, imagen_perfil, idioma_nativo, descripcion_profesional, presentacion_personal, enabled, activation_token, created_at, pais_nativo_id, pais_residencia_id) VALUES ('3a5e3f0c-4d99-47d7-b4c5-3c2a6b142b91', 'SophieML', '{noop}sofiosasosa', 'sofia.martinez@gmail.com', 'Sofia', 'Martínez López', '1993-04-12', 'MUJER', 'PRESENCIAL', '+34987654321', true, '#FF0F57', 'https://randomuser.me/api/portraits/women/43.jpg', 'es', 'Ingeniera de software con especialización en inteligencia artificial. 5 años de experiencia en desarrollo de chatbots y modelos predictivos.', 'Amante de la tecnología, el senderismo y la repostería. Siempre aprendiendo algo nuevo.', true, NULL, CURRENT_TIMESTAMP, 1, 1);
    INSERT INTO user_lista_otros_idiomas (user_id, lista_otros_idiomas) VALUES ('3a5e3f0c-4d99-47d7-b4c5-3c2a6b142b91', 'en');
    INSERT INTO user_lista_otros_idiomas (user_id, lista_otros_idiomas) VALUES ('3a5e3f0c-4d99-47d7-b4c5-3c2a6b142b91', 'fr');
    INSERT INTO user_lista_enlaces_externos (user_id, lista_enlaces_externos) VALUES ('3a5e3f0c-4d99-47d7-b4c5-3c2a6b142b91', 'https://github.com/sofiaml');
    INSERT INTO user_roles (user_id, roles) VALUES ('3a5e3f0c-4d99-47d7-b4c5-3c2a6b142b91', 'USER');
        -----
        INSERT INTO talento (id, titulo, descripcion, imagen, nivel_id, user_id) VALUES (11, 'Programación en Python', 'Desde cero hasta funciones avanzadas. Ideal para automatizar tareas o iniciarse en data science.', 'https://upload.wikimedia.org/wikipedia/commons/thumb/c/c3/Python-logo-notext.svg/1200px-Python-logo-notext.svg.png', 3, '3a5e3f0c-4d99-47d7-b4c5-3c2a6b142b91');
        INSERT INTO talento (id, titulo, descripcion, imagen, nivel_id, user_id) VALUES (12, 'Repostería creativa', 'Decoración de cupcakes, fondant y técnicas básicas de pastelería.', 'https://img.freepik.com/fotos-premium/varios-cupcakes-decorados-crema_23-2147741838.jpg', 2, '3a5e3f0c-4d99-47d7-b4c5-3c2a6b142b91');

-- Raj, Patel (RajTech - raj2024)
INSERT INTO user_entity (id, username, password, email, nombre, apellidos, fecha_nacimiento, sexo, modalidad_preferida, num_telefono, mostrar_num_telefono, color, imagen_perfil, idioma_nativo, descripcion_profesional, presentacion_personal, enabled, activation_token, created_at, pais_nativo_id, pais_residencia_id) VALUES ('d29c9b83-eec2-445f-9aa3-b49e2f3f70a7', 'RajTech', '{noop}raj2024', 'raj.patel@outlook.com', 'Raj', 'Patel', '1988-11-25', 'HOMBRE', 'VIRTUAL', '+919876543210', false, '#00E78F', 'https://randomuser.me/api/portraits/men/32.jpg', 'hi', 'Especialista en TI con certificaciones en ciberseguridad. He trabajado en proyectos para bancos y startups.', 'Fanático del cricket y la cocina picante. Enseño con ejemplos prácticos de la vida real.', true, NULL, CURRENT_TIMESTAMP, 3, 3);
    INSERT INTO user_lista_otros_idiomas (user_id, lista_otros_idiomas) VALUES ('d29c9b83-eec2-445f-9aa3-b49e2f3f70a7', 'en');
    INSERT INTO user_lista_enlaces_externos (user_id, lista_enlaces_externos) VALUES ('d29c9b83-eec2-445f-9aa3-b49e2f3f70a7', 'https://www.linkedin.com/in/rajpatel-tech');
    INSERT INTO user_roles (user_id, roles) VALUES ('d29c9b83-eec2-445f-9aa3-b49e2f3f70a7', 'USER');
        -----
        INSERT INTO talento (id, titulo, descripcion, imagen, nivel_id, user_id) VALUES (13, 'Seguridad informática básica', 'Protege tus cuentas y dispositivos: contraseñas seguras, autenticación en dos pasos y detección de phishing.', null, 2, 'd29c9b83-eec2-445f-9aa3-b49e2f3f70a7');
        INSERT INTO talento (id, titulo, descripcion, imagen, nivel_id, user_id) VALUES (14, 'Cocina india vegetariana', 'Recetas auténticas de mi abuela: desde dal tadka hasta masala dosa.', 'https://www.cookwithmanali.com/wp-content/uploads/2020/05/Masala-Dosa.jpg', 4, 'd29c9b83-eec2-445f-9aa3-b49e2f3f70a7');

ALTER SEQUENCE talento_seq RESTART WITH 64;

--INTERCAMBIOS.
-- [UD: Khin90, US: Arman] PROPUESTO
INSERT INTO intercambio (intercambioid, estado, fecha_solicitud, fecha_comienzo, fecha_fin, finalizado_por_demandante, finalizado_por_solicitado, usuario_demandante_id, usuario_solicitado_id, talento_solicitado_id, talento_aceptado_id, talento_sugerido_id) VALUES (1,'PROPUESTO', '2025-04-22T10:45:00', null, null, false, false, '123e4567-e89b-12d3-a456-426614174000', '9f3c5a28-92d4-4e77-b8c0-55a6b7c1ea00', 7, null, 3);

-- [UD: MalenG, US: Arman] ACTIVO
INSERT INTO intercambio (intercambioid, estado, fecha_solicitud, fecha_comienzo, fecha_fin, finalizado_por_demandante, finalizado_por_solicitado, usuario_demandante_id, usuario_solicitado_id, talento_solicitado_id, talento_aceptado_id, talento_sugerido_id) VALUES (2,'ACTIVO', '2025-05-22T17:30:00', '2025-05-24T11:20:00', null, false, false, 'd19b3b6e-8f7a-43f1-a8f4-92e3d5a40007', '9f3c5a28-92d4-4e77-b8c0-55a6b7c1ea00', 6, 9, 9);
    INSERT INTO sesion (id, fecha, intercambio_id_asociado_a_sesion) VALUES (1, '2025-06-07', 2);
        INSERT INTO bloque (id, titulo, descripcion, hora, sesion_id, usuario_creador_id) VALUES (1, 'Cepillado', 'Hoy le enseñaré a cepillar el pelo de un perro regularmente para mantenerlo limpio y prevenir problemas de piel.', '12:30', 1, 'd19b3b6e-8f7a-43f1-a8f4-92e3d5a40007');
        INSERT INTO bloque (id, titulo, descripcion, hora, sesion_id, usuario_creador_id) VALUES (2, 'Estudio de células y tejidos', 'Toca preparar muestras de células humanas, vegetales o animales para observar su estructura y funcionamiento.', '18:00', 1, '9f3c5a28-92d4-4e77-b8c0-55a6b7c1ea00');
    INSERT INTO sesion (id, fecha, intercambio_id_asociado_a_sesion) VALUES (2, '2025-06-25 ', 2);
        INSERT INTO bloque (id, titulo, descripcion, hora, sesion_id, usuario_creador_id) VALUES (3, 'Identificación de microorganismos', 'Utilizaremos los microscopios para identificar bacterias, virus y otros microorganismos en muestras de agua, suelo o aire.', '20:10', 2, '9f3c5a28-92d4-4e77-b8c0-55a6b7c1ea00');
    INSERT INTO sesion (id, fecha, intercambio_id_asociado_a_sesion) VALUES (3, '2025-06-28', 2);
        INSERT INTO bloque (id, titulo, descripcion, hora, sesion_id, usuario_creador_id) VALUES (4, 'Limpieza de oídos', 'Tutorial para limpiar los oídos de un gato regularmente para prevenir infecciones.', '13:30', 3, 'd19b3b6e-8f7a-43f1-a8f4-92e3d5a40007');

-- [UD: Elirart, US: MalenG] FINALIZADO
INSERT INTO intercambio (intercambioid, estado, fecha_solicitud, fecha_comienzo, fecha_fin, finalizado_por_demandante, finalizado_por_solicitado, usuario_demandante_id, usuario_solicitado_id, talento_solicitado_id, talento_aceptado_id, talento_sugerido_id) VALUES (3,'FINALIZADO', '2025-05-28T12:45:00', '2025-05-29T09:10:00', '2025-06-04T19:10:00', true, true, 'f13a2e98-70f5-4d61-93ab-349be7022025', 'd19b3b6e-8f7a-43f1-a8f4-92e3d5a40007', 10, 4, 5);
    INSERT INTO sesion (id, fecha, intercambio_id_asociado_a_sesion) VALUES (4, '2025-06-01', 3);
        INSERT INTO bloque (id, titulo, descripcion, hora, sesion_id, usuario_creador_id) VALUES (5, 'Anatomía de las uñas', 'Comprenderemos la estructura de las uñas y su relación con la piel para un mejor cuidado.', '02:00', 4, 'd19b3b6e-8f7a-43f1-a8f4-92e3d5a40007');
        INSERT INTO bloque (id, titulo, descripcion, hora, sesion_id, usuario_creador_id) VALUES (6, 'Experimentación con formas y colores', 'Realizaremos ejercicios donde los estudiantes manipulen formas básicas como círculos, líneas y cuadrados, utilizando diferentes combinaciones de colores y texturas.', '16:30', 4, 'f13a2e98-70f5-4d61-93ab-349be7022025');
    -----
    INSERT INTO valoracion (id, puntuacion, titulo, resenha, usuario_escritor_id, usuario_valorado_id, intercambio_id_asociado_a_valoracion) VALUES (1, 10, 'La mejor maestra', 'Aprender manicura con ella ha sido una experiencia increíble. Es paciente, detallista y transmite su pasión por el cuidado de las uñas con cada explicación. Gracias a su guía, he ganado confianza y técnica. ¡Una excelente maestra y profesional!', 'f13a2e98-70f5-4d61-93ab-349be7022025', 'd19b3b6e-8f7a-43f1-a8f4-92e3d5a40007', 3);
    INSERT INTO valoracion (id, puntuacion, titulo, resenha, usuario_escritor_id, usuario_valorado_id, intercambio_id_asociado_a_valoracion) VALUES (2, 8, null, 'Su enfoque para enseñar pintura abstracta es muy interesante y me ha ayudado a soltarme creativamente. Aunque a veces me costaba seguir el ritmo, aprendí nuevas formas de expresarme y explorar el color. Una experiencia enriquecedora.', 'd19b3b6e-8f7a-43f1-a8f4-92e3d5a40007', 'f13a2e98-70f5-4d61-93ab-349be7022025', 3);

-- [UD: SofiaML, US: RajTech] ACTIVO
INSERT INTO intercambio (intercambioid, estado, fecha_solicitud, fecha_comienzo, fecha_fin, finalizado_por_demandante, finalizado_por_solicitado, usuario_demandante_id, usuario_solicitado_id, talento_solicitado_id, talento_aceptado_id, talento_sugerido_id) VALUES (4, 'ACTIVO', '2025-05-15T14:20:00', '2025-05-18T10:00:00', null, false, false, '3a5e3f0c-4d99-47d7-b4c5-3c2a6b142b91', 'd29c9b83-eec2-445f-9aa3-b49e2f3f70a7', 14, 11, 12);
    INSERT INTO sesion (id, fecha, intercambio_id_asociado_a_sesion) VALUES (5, '2025-06-10', 4);
        INSERT INTO bloque (id, titulo, descripcion, hora, sesion_id, usuario_creador_id) VALUES (7, 'Funciones en Python', 'Aprenderemos a definir funciones, parámetros y retorno de valores. Ejercicios prácticos incluidos.', '17:00', 5, '3a5e3f0c-4d99-47d7-b4c5-3c2a6b142b91');
        INSERT INTO bloque (id, titulo, descripcion, hora, sesion_id, usuario_creador_id) VALUES (8, 'Preparación de curry de garbanzos', 'Ingredientes, especias y técnicas para un curry perfecto.', '19:30', 5, 'd29c9b83-eec2-445f-9aa3-b49e2f3f70a7');
    INSERT INTO sesion (id, fecha, intercambio_id_asociado_a_sesion) VALUES (6, '2025-06-17', 4);
        INSERT INTO bloque (id, titulo, descripcion, hora, sesion_id, usuario_creador_id) VALUES (9, 'Decoración de cupcakes', 'Uso de manga pastelera y fondant para diseños sencillos.', '16:00', 6, '3a5e3f0c-4d99-47d7-b4c5-3c2a6b142b91');

-- [UD: RajTech, US: Elirart] PROPUESTO (Arte por Seguridad)
INSERT INTO intercambio (intercambioid, estado, fecha_solicitud, fecha_comienzo, fecha_fin, finalizado_por_demandante, finalizado_por_solicitado, usuario_demandante_id, usuario_solicitado_id, talento_solicitado_id, talento_aceptado_id, talento_sugerido_id) VALUES (5, 'PROPUESTO', '2025-06-01T09:15:00', null, null, false, false, 'd29c9b83-eec2-445f-9aa3-b49e2f3f70a7', 'f13a2e98-70f5-4d61-93ab-349be7022025', 4, null, 13);

-- [UD: Khin90, US: SofiaML] FINALIZADO (Karate por Repostería)
INSERT INTO intercambio (intercambioid, estado, fecha_solicitud, fecha_comienzo, fecha_fin, finalizado_por_demandante, finalizado_por_solicitado, usuario_demandante_id, usuario_solicitado_id, talento_solicitado_id, talento_aceptado_id, talento_sugerido_id) VALUES (6, 'FINALIZADO', '2025-04-10T11:30:00', '2025-04-12T16:45:00', '2025-05-20T12:00:00', true, true, '123e4567-e89b-12d3-a456-426614174000', '3a5e3f0c-4d99-47d7-b4c5-3c2a6b142b91', 12, 1, 3);
    INSERT INTO sesion (id, fecha, intercambio_id_asociado_a_sesion) VALUES (7, '2025-04-20', 6);
        INSERT INTO bloque (id, titulo, descripcion, hora, sesion_id, usuario_creador_id) VALUES (10, 'Posturas básicas de karate', 'Kihon: posiciones, bloqueos y golpes fundamentales.', '18:00', 7, '123e4567-e89b-12d3-a456-426614174000');
    -----
    INSERT INTO valoracion (id, puntuacion, titulo, resenha, usuario_escritor_id, usuario_valorado_id, intercambio_id_asociado_a_valoracion) VALUES (3, 9, '¡Increíble maestro!', 'Khin tiene una paciencia infinita y explica cada movimiento con claridad. Ahora me siento más segura al caminar de noche.', '3a5e3f0c-4d99-47d7-b4c5-3c2a6b142b91', '123e4567-e89b-12d3-a456-426614174000', 6);
    INSERT INTO valoracion (id, puntuacion, titulo, resenha, usuario_escritor_id, usuario_valorado_id, intercambio_id_asociado_a_valoracion) VALUES (4, 7, null, 'Sofia es muy creativa, aunque a veces iba demasiado rápido para mi nivel. ¡Mis cupcakes mejoraron mucho!', '123e4567-e89b-12d3-a456-426614174000', '3a5e3f0c-4d99-47d7-b4c5-3c2a6b142b91', 6);


ALTER SEQUENCE intercambio_seq RESTART WITH 56;
ALTER SEQUENCE sesion_seq RESTART WITH 57;
ALTER SEQUENCE bloque_seq RESTART WITH 60;
