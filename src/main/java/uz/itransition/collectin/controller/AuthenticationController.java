package uz.itransition.collectin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.itransition.collectin.payload.request.auth.LoginRequest;
import uz.itransition.collectin.payload.request.auth.RegisterRequest;
import uz.itransition.collectin.service.AuthService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/")
public class AuthenticationController {

    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest requestDto) {
        return ResponseEntity.ok(authService.register(requestDto));
    }


//    @PostMapping(value = "access/token-from/refresh",
//            consumes = {MediaType.TEXT_PLAIN_VALUE},
//            produces = {MediaType.APPLICATION_JSON_VALUE}
//    )
//    public ResponseEntity<?> getAccessToken(@RequestBody String refreshToken) {
//        return ResponseEntity.ok(authService.getAccessToken(refreshToken));
//    }


}
