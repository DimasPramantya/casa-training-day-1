package com.casa_training.casa_task_day_one.usecase;
import com.casa_training.casa_task_day_one.domain.Role;
import com.casa_training.casa_task_day_one.domain.User;
import com.casa_training.casa_task_day_one.helper.JwtHelper;
import com.casa_training.casa_task_day_one.presentation.rest.dto.*;
import com.casa_training.casa_task_day_one.presentation.rest.exception.CustomException;
import com.casa_training.casa_task_day_one.repository.pgsql.RoleRepository;
import com.casa_training.casa_task_day_one.repository.pgsql.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private RoleRepository roleRepository;

    public CreateUserResponse createUser(CreateUserRequest request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if(existingUser.isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        Role defaultRole = roleRepository.findByName("user")
                .orElseThrow(() -> new CustomException(
                    "Error: Default 'user' role not found.",
                    HttpStatus.BAD_REQUEST.value()
                ));
        Set<Role> roles = new HashSet<>();
        roles.add(defaultRole);
        User user = User.builder()
                .userId(UUID.randomUUID())
                .email(request.getEmail())
                .name(request.getName())
                .roles(roles)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        User userDb = userRepository.save(user);
        return new CreateUserResponse(
            userDb.getName(), userDb.getEmail(), userDb.getUserId().toString()
        );
    }

    public GetUserResponse getUser(UUID id){
        User user = userRepository.findByUserId(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new GetUserResponse(
            user.getName(), user.getEmail(), user.getUserId().toString()
        );
    }

    public LoginResponse login(LoginRequest request){
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if(user.isEmpty()) {
            throw new CustomException(
                "Wrong email or password",
                HttpStatus.BAD_REQUEST.value()
            );
        }
        if(!passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
            throw new CustomException(
                "Wrong email or password",
                HttpStatus.BAD_REQUEST.value()
            );
        }
        return new LoginResponse(
                jwtHelper.generateToken(user.get())
        );
    }
}
