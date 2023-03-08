package uz.itransition.collectin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.itransition.collectin.controller.core.AbstractCRUDController;
import uz.itransition.collectin.payload.request.UserStatusRequest;
import uz.itransition.collectin.payload.request.user.UserRegistrationRequest;
import uz.itransition.collectin.payload.request.user.UserRoleUpdateRequest;
import uz.itransition.collectin.payload.request.user.UserUpdateRequest;
import uz.itransition.collectin.payload.response.APIResponse;
import uz.itransition.collectin.service.UserService;

import static uz.itransition.collectin.controller.core.ControllerUtils.USER_URI;

@RestController
@RequestMapping(USER_URI)
public class UserController extends AbstractCRUDController<UserService, Long, UserRegistrationRequest, UserUpdateRequest> {

    protected UserController(UserService service) {
        super(service);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<APIResponse> getUserList() {
        return ResponseEntity.ok(service.getUserList());
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<APIResponse> getCurrentUser(@AuthenticationPrincipal String email) {
        return ResponseEntity.ok(service.getUser(email));
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/status")
    public ResponseEntity<APIResponse> updateUserStatus(
            @AuthenticationPrincipal String email,
            @RequestBody UserStatusRequest request) {
        return ResponseEntity.ok(service.updateUserStatus(request, email));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/role")
    public ResponseEntity<APIResponse> updateUserRole(
            @AuthenticationPrincipal String email,
            @RequestBody UserRoleUpdateRequest request) {
        return ResponseEntity.ok(service.updateUserRole(request, email));
    }

    @GetMapping("/language/{userId}")
    public ResponseEntity<APIResponse> updateLanguage(
            @PathVariable(name = "userId") Long userId,
            @RequestParam(name = "lang", defaultValue = "ENG") String lang
    ){
        return ResponseEntity.ok(service.updateLanguage(userId,lang));
    }


}
