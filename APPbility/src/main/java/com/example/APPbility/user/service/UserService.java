package com.example.APPbility.user.service;

import com.example.APPbility.user.dto.GetUserDTO;
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
import org.springframework.web.server.ResponseStatusException;

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

    @Value("${activation.duration}")
    private int activationDuration;

    //MÉTODOS DEL SERVICIO -----------------------------------------------------------------------------------

    //Listar todos los Usuarios.
    public Page<GetUserDTO> findAll(Pageable pageable){
        Page<GetUserDTO> result = userRepository.findAllUserDTO(pageable);

        if(result.isEmpty())
            throw new UserNotFoundException();
        return result;
    }

    //MÉTODOS RELACIONADOS CON SEGURIDAD ---------------------------------------------------------------------

    public User createUser(CreateUserRequest createUserRequest) {
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
