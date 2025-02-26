# :iphone: APPbility

### Intercambia, aprende, crece.

Actualmente, se ha normalizado en la sociedad el tener que pagar por un bien y servicio con el fin de obtenerlo, lo que también se aplica en cierta manera, al sector personal y la mejora de habilidades o talentos.

Muchas personas enfrentan desafíos al acceder a servicios o aptitudes específicas debido a limitaciones financieras o a la falta de conexiones adecuadas, lo que puede llegar a generar una desmotivación o insatisfacción en ellas y afectar de manera perjudicial el desarrollo de su día a día, todo ello corroborado por parte de fuentes profesionales como _"Startups Españolas"_.

Un sistema de intercambio de habilidades que elimine barreras financieras abordaría este problema al proporcionar un espacio donde las personas pueden ofrecer sus propias habilidades a cambio de los servicios que necesitan, por lo que APPbiliy es la solución a este problema.

**APPbility es una plataforma de intercambio de talentos entre personas, en la que los usuarios van a poder visualizar los perfiles de otros, además de disponer de otras funciones como poder agregarlos a una lista de favoritos o poder realizar intercambios (_"Matching"_) entre ellos.**

## :wrench: Tecnologías Utilizadas

El proyecto está implementado utilizando las siguientes tecnologías:
- **Spring Boot 3** para la construcción de la API REST.
- **Spring Data JPA** y **PostgreSQL** para la persistencia de datos.
- **OpenAPI**, **Swagger** y **Springdoc** para la documentación automática de la API.
- **Postman** para pruebas de la API.

## :clipboard: Características

**La API REST permite realizar diferentes operaciones relacionadas con los intercambios de talento entre usuarios, todo con el objetivo de fomentar la enseñanza de persona a persona y mejorar la adquisición de habilidades.**

La documentación generada automáticamente con Swagger facilita la interacción con la API.

El sistema está diseñado para facilitar la coordinación entre los 2 tipos de usuario:
- **Usuarios**: tras registrase pueden asignarse tags descriptivos y agregar todo tipo de información a su perfil, tales como el dominio de idiomas u otros conocimientos de interés. Además, tienen la opción de poder crear diversos talentos (de los que debe estar capacitado a instruir), caracterizados por un título, una descripción y mínimo una foto que pruebe la profesionalidad del usuario respecto a dicha destreza.

  El usuario también va a poder buscar una habilidad que desee aprender navegando y vislumbrando los perfiles de otras personas (y sus talentos en concretos) y ejercer una propuesta de intercambio de destrezas con la otra persona, que en caso de ser aceptada, se organizará mediante un horario de sesiones. Aparte, los usuarios tienen la oportunidad de valorar a otros usuarios y marcarlos como favoritos.

- **Administradores**: pueden modificar todos aquellos aspectos de la aplicación que no pueden ser editados por un usuario de ninguna manera, tales como los tags. Además, llevan un registro de la mayoría de datos de la plataforma y pueden penalizar a un usuario por conductas inadecuadas o comportamientos inapropiados.

## :warning: Instalación

Para ejecutar el proyecto, es necesario ingresar a él e iniciar el programa con la siguiente opción:
```
spring-boot:run
```

## :pencil2: Proyecto elaborado por...

**Lucas Falla Urtiaga**, alumno de 2º de Desarrollo de Aplicaciones Multiplataforma (DAM) de la promoción 2024 - 2025 del centro Salesianos San Pedro, Sevilla, España.

¡Esperamos que esta aplicación marque un antes y un después en la metodología de enseñanza por talentos!
