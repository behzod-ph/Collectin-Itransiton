package uz.itransition.collectin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.itransition.collectin.entity.User;
import uz.itransition.collectin.entity.enums.Status;
import uz.itransition.collectin.exception.JwtValidationException;
import uz.itransition.collectin.payload.request.auth.LoginRequest;
import uz.itransition.collectin.payload.request.auth.RegisterRequest;
import uz.itransition.collectin.payload.response.APIResponse;
import uz.itransition.collectin.payload.response.auth.JwtTokenResponse;
import uz.itransition.collectin.repository.UserRepository;
import uz.itransition.collectin.security.jwt.JWTokenProvider;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    String LOGIN_ERROR = "Email or Password is incorrect";
    String REGISTRATION_ERROR = "This email already signed up. Please log in!";

    private final UserRepository userRepository;
    private final JWTokenProvider jwTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public APIResponse login(LoginRequest requestDto) {
        Optional<User> optionalUser = userRepository.findByEmailAndStatus(requestDto.getEmail(), Status.ACTIVE);
        if (optionalUser.isPresent() &&
                passwordEncoder.matches(requestDto.getPassword(), optionalUser.get().getPassword())) {
            return APIResponse.success(sendJwtSuccessResponse(optionalUser.get()));
        }
        throw new JwtValidationException(LOGIN_ERROR);
    }

    public APIResponse register(RegisterRequest requestDto) {
        Optional<User> optionalUser = userRepository.findByEmailAndStatusIsNot(requestDto.getEmail(), Status.DELETED);
        if (optionalUser.isEmpty() || optionalUser.get().getStatus().equals(Status.DELETED)) {
            User user = userRepository.save(
                    new User(
                            requestDto.getName(),
                            requestDto.getEmail(),
                            passwordEncoder.encode(requestDto.getPassword())));
            return APIResponse.success(sendJwtSuccessResponse(user));
        }
        throw new JwtValidationException(REGISTRATION_ERROR);
    }


    private JwtTokenResponse sendJwtSuccessResponse(User user) {
        return new JwtTokenResponse(
                user.getId(),
                user.getRoles(),
                user.getName(),
                user.getLanguage().name(),
                user.getImageUrl(),
                jwTokenProvider.generateAccessToken(user));
    }
}
