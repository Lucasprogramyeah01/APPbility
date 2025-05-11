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

ALTER SEQUENCE pais_seq RESTART WITH 51;












