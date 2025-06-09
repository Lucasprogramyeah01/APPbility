package com.example.APPbility.user.service;

import com.example.APPbility.error.custom.IllegalMatchException;
import com.example.APPbility.error.entity.PaisNotFoundException;
import com.example.APPbility.model.Pais;
import com.example.APPbility.repository.TagPRUEBARepository;
import com.example.APPbility.repository.TalentoPRUEBARepository;
import com.example.APPbility.repository.ValoracionRepository;
import com.example.APPbility.user.error.ActivationExpiredException;
import com.example.APPbility.user.error.UserNotFoundException;
import com.example.APPbility.user.model.User;
import com.example.APPbility.user.repository.UserRepository;
import com.example.APPbility.util.SendGridMailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SendGridMailSender mailSender;

    private final ValoracionRepository valoracionRepository;

    @Value("${activation.duration}")
    private int activationDuration;

    //MÉTODOS NECESARIOS PARA LA TRANSFORMACIÓN A DTO EN LOS MÉTODOS CONTROLADORES ---------------------------

    public Pais getPaisNativoByUsuarioID(UUID id){
        return userRepository.findPaisNativoByUsuarioID(id);
    }

    public Pais getPaisResidenciaByUsuarioID(UUID id){
        return userRepository.findPaisResidenciaByUsuarioID(id);
    }

    /*public Set<GetTagDTO> getListaTagsByUsuarioID(UUID id){
        return tagPRUEBARepository.findListaTagsByUsuarioID(id);
    }

    public List<GetTalentoDTO> getListaTalentosByUsuarioID(UUID id){
        return talentoPRUEBARepository.findListaTalentosByUsuarioID(id);
    }

    public List<GetValoracionDTO> getListaValoracionesRealizadasByUsuarioID(UUID id){
        return valoracionRepository.findListaValoracionesRealizadasByUsuarioID(id);
    }

    public List<GetValoracionDTO> getListaValoracionesRecibidasByUsuarioID(UUID id){
        return valoracionRepository.findListaValoracionesRecibidasByUsuarioID(id);
    }

    public Set<GetUserDTO> getListaUsuariosFavoritosByUsuarioID(UUID id){
        return userRepository.findListaUsuariosFavoritosByUsuarioID(id);
    }

    public Set<GetUserDTO> getListaUsuariosSeguidoresByUsuarioID(UUID id){
        return userRepository.findListaUsuariosSeguidoresByUsuarioID(id);
    }*/

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

    //Buscar Usuario por ID.
    /*public User findById(UUID id){
        Optional<User> usuarioOptional = userRepository.findById(id);

        if(usuarioOptional.isPresent())
            return usuarioOptional.get();
        throw new UserNotFoundException(id);
    }*/

    //Marcar Usuario como Favorito.
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

    /*public User createUser(CreateUserRequest createUserRequest) {
        User user = User.builder()
                .username(createUserRequest.username())
                .password(passwordEncoder.encode(createUserRequest.password()))
                .email(createUserRequest.email())
                .nombre(createUserRequest.nombre())
                .apellidos(createUserRequest.apellidos())
                .sexo(createUserRequest.sexo())
                .numTelefono(createUserRequest.numTelefono())
                .fechaNacimiento(createUserRequest.fechaNacimiento())
                .lugarNacimiento(createUserRequest.lugarNacimiento())
                .lugarResidencia(createUserRequest.lugarResidencia())
                .idiomaNativo(createUserRequest.idiomaNativo())
                .puntosPopularidad(0L)
                .roles(Set.of(UserRole.USER))
                .activationToken(generateRandomActivationCode())
                .build();

        try {
            mailSender.sendMail(createUserRequest.email(), "Activación de cuenta", user.getActivationToken());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Error al enviar el email de activación");
        }

        return userRepository.save(user);
    }*/

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
