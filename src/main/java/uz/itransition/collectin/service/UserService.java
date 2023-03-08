package uz.itransition.collectin.service;


import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.itransition.collectin.entity.User;
import uz.itransition.collectin.entity.enums.Language;
import uz.itransition.collectin.entity.enums.Role;
import uz.itransition.collectin.entity.enums.Status;
import uz.itransition.collectin.exception.AuthorizationRequiredException;
import uz.itransition.collectin.exception.UserNotFoundException;
import uz.itransition.collectin.payload.request.UserStatusRequest;
import uz.itransition.collectin.payload.request.auth.RegisterRequest;
import uz.itransition.collectin.payload.request.user.UserRoleUpdateRequest;
import uz.itransition.collectin.payload.request.user.UserUpdateRequest;
import uz.itransition.collectin.payload.response.APIResponse;
import uz.itransition.collectin.payload.response.auth.JwtTokenResponse;
import uz.itransition.collectin.payload.response.user.UserResponse;
import uz.itransition.collectin.repository.UserRepository;
import uz.itransition.collectin.service.core.CRUDService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService implements CRUDService<Long, APIResponse, RegisterRequest, UserUpdateRequest> {

    private final UserRepository repository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository repository, ModelMapper modelMapper)
    {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public APIResponse create(RegisterRequest userRegistrationRequest)
    {
        final User user = repository.save(modelMapper.map(userRegistrationRequest, User.class));
        return APIResponse.success(user);
    }

    @Override
    public APIResponse get(Long id)
    {
        final User user = repository.findById(id).orElseThrow(() -> {
            throw UserNotFoundException.of(String.valueOf(id));
        });
        return APIResponse.success(user);
    }

    @Override
    public APIResponse modify(Long id, UserUpdateRequest userUpdateRequest)
    {
        final User user = repository.findById(id).orElseThrow(() -> {
            throw UserNotFoundException.of(String.valueOf(id));
        });
        modelMapper.map(userUpdateRequest, user);
        return APIResponse.success(repository.save(user));
    }

    @Override
    public APIResponse delete(Long id)
    {
        final User user = repository.findById(id).orElseThrow(() -> {
            throw UserNotFoundException.of(String.valueOf(id));
        });
        repository.delete(user);
        return APIResponse.success(HttpStatus.OK.name());
    }

    public APIResponse getUser(String email) {
        return APIResponse.success(
                modelMapper.map(repository
                                .findByEmailAndStatusIsNot(email, Status.DELETED)
                                .orElseThrow(() -> UserNotFoundException.of(email)),
                        JwtTokenResponse.class));
    }

    public APIResponse getUserList() {
        return APIResponse.success(
                repository.findAll().stream().filter(user -> user.getStatus() != Status.DELETED).map(
                        user -> new UserResponse(
                                user.getId(),
                                user.getName(),
                                user.getEmail(),
                                user.getStatus().name(),
                                user.getLanguage().name(),
                                getRoles(user.getRoles()),
                                user.getImageUrl())).collect(Collectors.toList())
                );
    }

    public APIResponse updateUserStatus(UserStatusRequest request, String email) {
        if (repository.existsByEmailAndStatus(email, Status.ACTIVE)) {
            repository.updateUserStatus(Status.valueOf(request.getAction()), request.getContent());
            return APIResponse.success(HttpStatus.OK.value());
        }
        throw new AuthorizationRequiredException();
    }

    public APIResponse updateUserRole(UserRoleUpdateRequest request, String email) {
        if (repository.existsByEmailAndStatus(email, Status.ACTIVE)) {
            repository.updateUserRole(request.isSetAsAdmin()
                    ? (Role.USER.getFlag() + Role.ADMIN.getFlag())
                    : Role.USER.getFlag(), request.getContent());
            return APIResponse.success(HttpStatus.OK.value());
        }
        throw new AuthorizationRequiredException();
    }

    public static List<String> getRoles(int roleValue)
    {
        final List<String> roleList = new ArrayList<>();
        for (Role role : Role.values()) {
            if ((roleValue & role.getFlag()) > 0)
                roleList.add(role.getName());
        }
        return roleList;
    }

    public APIResponse updateLanguage(Long userId, String lang) {
        final User user = repository.findById(userId).orElseThrow(() -> {
            throw UserNotFoundException.of(String.valueOf(userId));
        });
        user.setLanguage(Language.valueOf(lang.toUpperCase()));
        repository.save(user);
        return APIResponse.success(HttpStatus.OK.value());
    }

}
