package com.example.APPbility.user.service;

import com.example.APPbility.error.custom.IllegalMatchException;
import com.example.APPbility.error.entity.PaisNotFoundException;
import com.example.APPbility.files.service.StorageService;
import com.example.APPbility.model.Pais;
import com.example.APPbility.model.Sexo;
import com.example.APPbility.repository.PaisRepository;
import com.example.APPbility.repository.ValoracionRepository;
import com.example.APPbility.user.dto.EditUserCMD;
import com.example.APPbility.user.dto.seguridad.CreateUserRequest;
import com.example.APPbility.user.error.ActivationExpiredException;
import com.example.APPbility.user.error.UserNotFoundException;
import com.example.APPbility.user.model.User;
import com.example.APPbility.user.model.UserRole;
import com.example.APPbility.user.repository.UserRepository;
import com.example.APPbility.util.SendGridMailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SendGridMailSender mailSender;

    private final ValoracionRepository valoracionRepository;
    private final PaisRepository paisRepository;

    private final StorageService storageService;

    @Value("${activation.duration}")
    private int activationDuration;

    //MÉTODOS NECESARIOS PARA LA TRANSFORMACIÓN A DTO EN LOS MÉTODOS CONTROLADORES ---------------------------

    public Pais getPaisNativoByUsuarioID(UUID id){
        return userRepository.findPaisNativoByUsuarioID(id);
    }

    public Pais getPaisResidenciaByUsuarioID(UUID id){
        return userRepository.findPaisResidenciaByUsuarioID(id);
    }

    //MÉTODOS DEL SERVICIO -----------------------------------------------------------------------------------

    //Listar todos los Usuarios.
    public Page<User> findAll(Pageable pageable){
        Page<User> result = userRepository.findAll(pageable);

        if(result.isEmpty())
            throw new UserNotFoundException();
        return result;
    }

    //Buscar Usuario por ID.
    public User findById(UUID id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    //Editar Usuario.
    @Transactional
    public User edit(UUID usuarioID, EditUserCMD editUserCMD, MultipartFile multipartFile) {
        User user = userRepository.findById(usuarioID).orElseThrow(() -> new UserNotFoundException(usuarioID));

        user.setNombre(editUserCMD.nombre().trim());
        user.setApellidos(editUserCMD.apellidos().trim());
        user.setFechaNacimiento(editUserCMD.fechaNacimiento());
        user.setSexo(editUserCMD.sexo());
        user.setModalidadPreferida(editUserCMD.modalidadPreferida());
        user.setNumTelefono(editUserCMD.numTelefono().trim());
        user.setMostrarNumTelefono(editUserCMD.mostrarNumTelefono());
        user.setColor(editUserCMD.color() != null ? editUserCMD.color() : "#FF00CC");
        user.setIdiomaNativo(editUserCMD.idiomaNativo().trim());
        user.setListaOtrosIdiomas(editUserCMD.listaOtrosIdiomas());
        user.setDescripcionProfesional(editUserCMD.descripcionProfesional().trim());
        user.setPresentacionPersonal(editUserCMD.presentacionPersonal().trim());
        user.setListaEnlacesExternos(editUserCMD.listaEnlacesExternos());

        user.setPaisNativo(paisRepository.findById(editUserCMD.paisNativoID())
            .orElseThrow(() -> new PaisNotFoundException(editUserCMD.paisNativoID())));
        user.setPaisResidencia(paisRepository.findById(editUserCMD.paisResidenciaID())
            .orElseThrow(() -> new PaisNotFoundException(editUserCMD.paisResidenciaID())));

        if(multipartFile != null && !multipartFile.isEmpty()) {
            String imagen = "/uploads/" + storageService.storeInFolder(multipartFile, "usuarios").getFilename();

            if(user.getImagenPerfil() != null && user.getImagenPerfil().contains("uploads")) {
                String antiguaImagenPerfil = Paths.get(user.getImagenPerfil()).getFileName().toString();
                storageService.deleteFileInFolder("usuarios", antiguaImagenPerfil);
                user.setImagenPerfil(imagen);
            }else{
                user.setImagenPerfil(imagen);
            }
        }

        return userRepository.save(user);
    }

    //Marcar Usuario como Favorito.
    @Transactional
    public User addFavorito(User usuarioAutenticado, UUID id){
        User usuario = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        usuarioAutenticado.addUsuarioFavorito(usuario);

        return userRepository.save(usuario);
    }

    //Marcar Usuario como Favorito.
    @Transactional
    public void marcarUsuarioComoFavorito(UUID usuarioID, UUID favoritoID) {
        User usuario = userRepository.findById(usuarioID).orElseThrow(() -> new UserNotFoundException(usuarioID));

        User usuarioQueVaASerFavorito =
            userRepository.findById(favoritoID).orElseThrow(() -> new UserNotFoundException(favoritoID));

        //Validación para comprobar que un usuario no pueda marcarse a sí mismo como favorito.
        if (usuario.getId().equals(usuarioQueVaASerFavorito.getId())) {
            throw new IllegalMatchException("Un usuario no puede marcarse a sí mismo como favorito.");
        }

        if (!usuario.getListaUsuariosFavoritos().contains(usuarioQueVaASerFavorito)) {
            usuarioQueVaASerFavorito.getListaUsuariosSeguidores().add(usuario);
            usuario.getListaUsuariosFavoritos().add(usuarioQueVaASerFavorito);
            userRepository.save(usuario);
        }
    }

    //Listar Usuarios Favoritos.
    public Set<User> listarUsuariosFavoritos(UUID usuarioID) {
        User usuario = userRepository.findById(usuarioID).orElseThrow(() -> new UserNotFoundException(usuarioID));

        return usuario.getListaUsuariosFavoritos();
    }

    //Desmarcar Usuario de Favoritos.
    @Transactional
    public void desmarcarUsuarioDeFavoritos(UUID usuarioID, UUID favoritoID) {
        User usuario = userRepository.findById(usuarioID).orElseThrow(() -> new UserNotFoundException(usuarioID));
        User usuarioFavorito = userRepository.findById(favoritoID).orElseThrow(() -> new UserNotFoundException(favoritoID));

        if (usuario.getListaUsuariosFavoritos().contains(usuarioFavorito)) {
            usuarioFavorito.getListaUsuariosSeguidores().remove(usuario);
            usuario.getListaUsuariosFavoritos().remove(usuarioFavorito);
            userRepository.save(usuario);
        }
    }

    //Listar Usuarios Seguidores.
    public Set<User> listarUsuariosSeguidores(UUID usuarioID) {
        User usuario = userRepository.findById(usuarioID).orElseThrow(() -> new UserNotFoundException(usuarioID));

        return usuario.getListaUsuariosSeguidores();
    }

    //Calcular Media de Puntuaciones de Usuario.
    public Double calcularMediaDePuntuacionesDeUsuario(UUID usuarioID) {
        Double media = valoracionRepository.calcularPuntuacionMediaDeUsuario(usuarioID);
        return media != null ? media : 0.0;
    }

    //MÉTODOS RELACIONADOS CON SEGURIDAD ---------------------------------------------------------------------

    //Create User (Registrar Usuario).
    public User createUser(CreateUserRequest createUserRequest, MultipartFile multipartFile) {
        //Validación que comprueba si el username que se desea asignar el usuario ya pertenece a otro.
        if (userRepository.existsByUsername(createUserRequest.username().trim())) {
            throw new IllegalMatchException("Ese nombre de usuario ya se encuentra en uso.");
        }

        //Validación que comprueba si el email que se desea asignar el usuario ya pertenece a otro.
        if (userRepository.existsByEmail(createUserRequest.email().trim())) {
            throw new IllegalMatchException("Ese email ya se encuentra registrado.");
        }

        User.UserBuilder builder = User.builder()
            .username(createUserRequest.username().trim())
            .password(passwordEncoder.encode(createUserRequest.password().trim()))
            .nombre(createUserRequest.nombre().trim())
            .apellidos(createUserRequest.apellidos().trim())
            .email(createUserRequest.email().trim())
            .fechaNacimiento(createUserRequest.fechaNacimiento())
            .sexo(createUserRequest.sexo())
            .modalidadPreferida(createUserRequest.modalidadPreferida())
            .numTelefono(createUserRequest.numTelefono().trim())
            .mostrarNumTelefono(createUserRequest.mostrarNumTelefono())
            .color("#FF00CC")
            .idiomaNativo(createUserRequest.idiomaNativo().trim())
            .listaOtrosIdiomas(createUserRequest.listaOtrosIdiomas())
            .descripcionProfesional(createUserRequest.descripcionProfesional().trim())
            .presentacionPersonal(createUserRequest.presentacionPersonal().trim())
            .listaEnlacesExternos(createUserRequest.listaEnlacesExternos())
            .paisNativo(paisRepository.findById(createUserRequest.paisNativoID())
                .orElseThrow(() -> new PaisNotFoundException(createUserRequest.paisNativoID())))
            .paisResidencia(paisRepository.findById(createUserRequest.paisResidenciaID())
                .orElseThrow(() -> new PaisNotFoundException(createUserRequest.paisResidenciaID())))
            .roles(Set.of(UserRole.USER))
            .activationToken(generateRandomActivationCode());

            if (multipartFile != null && !multipartFile.isEmpty()) {
                String imagen = "/uploads/" + storageService.storeInFolder(multipartFile, "usuarios").getFilename();
                builder.imagenPerfil(imagen);
            }else{
                if(createUserRequest.sexo() == Sexo.HOMBRE){
                    builder.imagenPerfil("https://cdn.vectorstock.com/i/500p/99/13/grey-profile-icon-placeholder-avatar-vector-38519913.jpg");
                }else{
                    builder.imagenPerfil("https://phlebotomycareertraining.com/wp-content/uploads/2023/11/default-avatar-photo-placeholder-icon-grey-vector-38519922-e1699300466746.jpg");
                }
            }

            User user = builder.build();
        try {
            mailSender.sendMail(createUserRequest.email(), "Activación de cuenta", user.getActivationToken());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Error al enviar el email de activación.");
        }
        return userRepository.save(builder.build());
    }

    public String generateRandomActivationCode() {
        return UUID.randomUUID().toString();
    }

    public User activateAccount(String token) {
        return userRepository.findByActivationToken(token)
            .filter(user -> ChronoUnit.MINUTES.between(Instant.now(), user.getCreatedAt()) - activationDuration < 0)
            .map(user -> {
                user.setEnabled(true);
                user.setActivationToken(null);
                return userRepository.save(user);
            })
            .orElseThrow(() -> new ActivationExpiredException("El código de activación no existe o ha caducado"));
    }

}
