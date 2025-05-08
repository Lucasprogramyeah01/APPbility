-- USUARIOS
-- (Para la entrega final, sustituir CURRENT_TIMESTAMP (Compatible con H2) por NOW() (Compatible con SQL).
INSERT INTO user_entity (id, username, password, email, enabled, nombre, apellidos, sexo, num_telefono, imagen_perfil, fecha_nacimiento, lugar_nacimiento, lugar_residencia, puntos_popularidad, idioma_nativo, otros_idiomas, conocimientos, descripcion, activation_token, created_at) VALUES ('551e8400-e22b-41d4-a716-446655440010', 'admin', '{noop}admin', 'admin@gmail.com', true, 'Duke', 'Java', 'HOMBRE', '101010101', NULL, '1995-01-01', 'MADRID', 'SEVILLA', 0, 'Inglés', 'Alemán', 'Programación', 'Mascota de Java.', NULL, CURRENT_TIMESTAMP);
INSERT INTO user_entity (id, username, password, email, enabled, nombre, apellidos, sexo, num_telefono, imagen_perfil, fecha_nacimiento, lugar_nacimiento, lugar_residencia, puntos_popularidad, idioma_nativo, otros_idiomas, conocimientos, descripcion, activation_token, created_at) VALUES ('550e8400-e29b-41d4-a716-446655440000', 'SkyHunter92', '{noop}PassWord123', 'juan.perez@gmail.com', true, 'Juan', 'Pérez', 'HOMBRE', '123456789', NULL, '1990-01-01', 'MADRID', 'BARCELONA', 100, 'Español', 'Inglés', 'Programación', 'Apasionado de la tecnología y los videojuegos.', NULL, CURRENT_TIMESTAMP);
INSERT INTO user_entity (id, username, password, email, enabled, nombre, apellidos, sexo, num_telefono, imagen_perfil, fecha_nacimiento, lugar_nacimiento, lugar_residencia, puntos_popularidad, idioma_nativo, otros_idiomas, conocimientos, descripcion, activation_token, created_at) VALUES ('660e8400-e29b-41d4-a716-446655440001', 'StarGazer88', '{noop}GalaxyWay9', 'maria.lopez@gmail.com', true, 'María', 'López', 'MUJER', '987654321', NULL, '1995-05-15', 'VALENCIA', 'SEVILLA', 50, 'Español', 'Francés', 'Diseño gráfico', 'Creativa y amante del arte digital.', NULL, CURRENT_TIMESTAMP);
INSERT INTO user_entity (id, username, password, email, enabled, nombre, apellidos, sexo, num_telefono, imagen_perfil, fecha_nacimiento, lugar_nacimiento, lugar_residencia, puntos_popularidad, idioma_nativo, otros_idiomas, conocimientos, descripcion, activation_token, created_at) VALUES ('770e8400-e29b-41d4-a716-446655440002', 'CodeMaster77', '{noop}SecureCodeX1', 'CodeMaster77@gmail.com', true, 'Carlos', 'Gómez', 'HOMBRE', '654321987', NULL, '1985-07-23', 'ALICANTE', 'MADRID', 75, 'Español', 'Alemán', 'Música', 'Desarrollador de software con pasión por la inteligencia artificial.', NULL, CURRENT_TIMESTAMP);
INSERT INTO user_entity (id, username, password, email, enabled, nombre, apellidos, sexo, num_telefono, imagen_perfil, fecha_nacimiento, lugar_nacimiento, lugar_residencia, puntos_popularidad, idioma_nativo, otros_idiomas, conocimientos, descripcion, activation_token, created_at) VALUES ('880e8400-e29b-41d4-a716-446655440003', 'LunaDreamer99', '{noop}DreamMoonX9', 'lucia99@gmail.com', true, 'Lucía', 'Martínez', 'MUJER', '111222333', NULL, '1992-04-10', 'SEVILLA', 'VALENCIA', 90, 'Español', 'Italiano', 'Fotografía', 'Fotógrafa freelance con espíritu viajero.', NULL, CURRENT_TIMESTAMP);
INSERT INTO user_entity (id, username, password, email, enabled, nombre, apellidos, sexo, num_telefono, imagen_perfil, fecha_nacimiento, lugar_nacimiento, lugar_residencia, puntos_popularidad, idioma_nativo, otros_idiomas, conocimientos, descripcion, activation_token, created_at) VALUES ('990e8400-e29b-41d4-a716-446655440004', 'FastRunner24', '{noop}SpeedWayX3', 'miguelFastRunner24@gmail.com', true, 'Miguel', 'Fernández', 'HOMBRE', '444555666', NULL, '1988-12-05', 'MURCIA', 'ALBACETE', 60, 'Español', 'Portugués', 'Deportes', 'Atleta profesional y entrenador personal.', NULL, CURRENT_TIMESTAMP);
INSERT INTO user_entity (id, username, password, email, enabled, nombre, apellidos, sexo, num_telefono, imagen_perfil, fecha_nacimiento, lugar_nacimiento, lugar_residencia, puntos_popularidad, idioma_nativo, otros_idiomas, conocimientos, descripcion, activation_token, created_at) VALUES ('111e8400-e29b-41d4-a716-446655440005', 'PaintQueenX', '{noop}ColorsMagic99', 'PaintQueenX@gmail.com', true, 'Ana', 'García', 'MUJER', '555666777', NULL, '1993-03-20', 'BADAJOZ', 'SALAMANCA', 85, 'Español', 'Ruso', 'Pintura', 'Artista autodidacta con exposiciones en galerías.', NULL, CURRENT_TIMESTAMP);
INSERT INTO user_entity (id, username, password, email, enabled, nombre, apellidos, sexo, num_telefono, imagen_perfil, fecha_nacimiento, lugar_nacimiento, lugar_residencia, puntos_popularidad, idioma_nativo, otros_idiomas, conocimientos, descripcion, activation_token, created_at) VALUES ('222e8400-e29b-41d4-a716-446655440006', 'ZenChef77', '{noop}TastyFoodX5', 'pedro.sanchez@gmail.com', true, 'Pedro', 'Sánchez', 'HOMBRE', '999888777', NULL, '1980-09-12', 'ZARAGOZA', 'NAVARRA', 40, 'Español', 'Japonés', 'Cocina', 'Chef innovador en busca de nuevos sabores.', NULL, CURRENT_TIMESTAMP);
INSERT INTO user_entity (id, username, password, email, enabled, nombre, apellidos, sexo, num_telefono, imagen_perfil, fecha_nacimiento, lugar_nacimiento, lugar_residencia, puntos_popularidad, idioma_nativo, otros_idiomas, conocimientos, descripcion, activation_token, created_at) VALUES ('333e8400-e29b-41d4-a716-446655440007', 'DanceStar101', '{noop}GrooveX9', 'anceStar101@gmail.com', true, 'Laura', 'Hernández', 'MUJER', '777666555', NULL, '1998-06-30', 'BURGOS', 'CANTABRIA', 70, 'Español', 'Chino', 'Danza', 'Bailarina y coreógrafa en ascenso.', NULL, CURRENT_TIMESTAMP);
INSERT INTO user_entity (id, username, password, email, enabled, nombre, apellidos, sexo, num_telefono, imagen_perfil, fecha_nacimiento, lugar_nacimiento, lugar_residencia, puntos_popularidad, idioma_nativo, otros_idiomas, conocimientos, descripcion, activation_token, created_at) VALUES ('444e8400-e29b-41d4-a716-446655440008', 'PhotoExplorerX', '{noop}LensMagic88', 'danPhotoExplorerX@gmail.com', true, 'Daniel', 'Ruiz', 'HOMBRE', '222333444', NULL, '1987-11-18', 'ALMERIA', 'GRANADA', 95, 'Español', 'Coreano', 'Fotografía', 'Fotógrafo de naturaleza y aventura.', NULL, CURRENT_TIMESTAMP);
INSERT INTO user_entity (id, username, password, email, enabled, nombre, apellidos, sexo, num_telefono, imagen_perfil, fecha_nacimiento, lugar_nacimiento, lugar_residencia, puntos_popularidad, idioma_nativo, otros_idiomas, conocimientos, descripcion, activation_token, created_at) VALUES ('555e8400-e29b-41d4-a716-446655440009', 'WriterSoul55', '{noop}InkMasterX7', 'elena.diaz@gmail.com', true, 'Elena', 'Díaz', 'MUJER', '666777888', NULL, '1991-02-08', 'LEON', 'VALLADOLID', 55, 'Español', 'Holandés', 'Escritura', 'Escritora y amante de la literatura clásica.', NULL, CURRENT_TIMESTAMP);

-- ROLES
INSERT INTO user_roles (user_id, roles) VALUES ('551e8400-e22b-41d4-a716-446655440010', 'ADMIN');
INSERT INTO user_roles (user_id, roles) VALUES ('550e8400-e29b-41d4-a716-446655440000', 'USER');
INSERT INTO user_roles (user_id, roles) VALUES ('660e8400-e29b-41d4-a716-446655440001', 'USER');
INSERT INTO user_roles (user_id, roles) VALUES ('770e8400-e29b-41d4-a716-446655440002', 'USER');
INSERT INTO user_roles (user_id, roles) VALUES ('880e8400-e29b-41d4-a716-446655440003', 'USER');
INSERT INTO user_roles (user_id, roles) VALUES ('990e8400-e29b-41d4-a716-446655440004', 'USER');
INSERT INTO user_roles (user_id, roles) VALUES ('111e8400-e29b-41d4-a716-446655440005', 'USER');
INSERT INTO user_roles (user_id, roles) VALUES ('222e8400-e29b-41d4-a716-446655440006', 'USER');
INSERT INTO user_roles (user_id, roles) VALUES ('333e8400-e29b-41d4-a716-446655440007', 'USER');
INSERT INTO user_roles (user_id, roles) VALUES ('444e8400-e29b-41d4-a716-446655440008', 'USER');
INSERT INTO user_roles (user_id, roles) VALUES ('555e8400-e29b-41d4-a716-446655440009', 'USER');

-- TAGS
INSERT INTO tag (id, nombre) VALUES (1, 'Empático/a');
INSERT INTO tag (id, nombre) VALUES (2, 'Estudioso/a');
INSERT INTO tag (id, nombre) VALUES (3, 'Musical');
INSERT INTO tag (id, nombre) VALUES (4, 'Sobreviviendo');
INSERT INTO tag (id, nombre) VALUES (5, 'Autodidacta');
INSERT INTO tag (id, nombre) VALUES (6, 'Recto/a');
INSERT INTO tag (id, nombre) VALUES (7, 'Espontáneo/a');
INSERT INTO tag (id, nombre) VALUES (8, 'Divertido/a');
INSERT INTO tag (id, nombre) VALUES (9, 'Fluyendo');
INSERT INTO tag (id, nombre) VALUES (10, 'Aplicado/a');
INSERT INTO tag (id, nombre) VALUES (11, 'Con ganas');
INSERT INTO tag (id, nombre) VALUES (12, 'Con ritmo');
INSERT INTO tag (id, nombre) VALUES (13, 'Entusiasta');
INSERT INTO tag (id, nombre) VALUES (14, 'Dispuesto/a');
INSERT INTO tag (id, nombre) VALUES (15, 'En forma');
INSERT INTO tag (id, nombre) VALUES (16, 'De hecho');
INSERT INTO tag (id, nombre) VALUES (17, 'Tranquilo/a');
INSERT INTO tag (id, nombre) VALUES (18, 'Intercultural');
INSERT INTO tag (id, nombre) VALUES (19, 'Perseverante');
INSERT INTO tag (id, nombre) VALUES (20, 'Perfeccionista');

ALTER SEQUENCE tag_seq RESTART WITH 71;

-- SE ETIQUETA COMO (Relación Usuario - Tag)
INSERT INTO se_etiqueta_con (tag_id, usuario_id) VALUES (1, '550e8400-e29b-41d4-a716-446655440000');
INSERT INTO se_etiqueta_con (tag_id, usuario_id) VALUES (2, '333e8400-e29b-41d4-a716-446655440007');
INSERT INTO se_etiqueta_con (tag_id, usuario_id) VALUES (3, '555e8400-e29b-41d4-a716-446655440009');
INSERT INTO se_etiqueta_con (tag_id, usuario_id) VALUES (8, '990e8400-e29b-41d4-a716-446655440004');
INSERT INTO se_etiqueta_con (tag_id, usuario_id) VALUES (8, '222e8400-e29b-41d4-a716-446655440006');
INSERT INTO se_etiqueta_con (tag_id, usuario_id) VALUES (8, '660e8400-e29b-41d4-a716-446655440001');
INSERT INTO se_etiqueta_con (tag_id, usuario_id) VALUES (12, '333e8400-e29b-41d4-a716-446655440007');
INSERT INTO se_etiqueta_con (tag_id, usuario_id) VALUES (13, '880e8400-e29b-41d4-a716-446655440003');
INSERT INTO se_etiqueta_con (tag_id, usuario_id) VALUES (13, '222e8400-e29b-41d4-a716-446655440006');
INSERT INTO se_etiqueta_con (tag_id, usuario_id) VALUES (15, '550e8400-e29b-41d4-a716-446655440000');
INSERT INTO se_etiqueta_con (tag_id, usuario_id) VALUES (18, '555e8400-e29b-41d4-a716-446655440009');
