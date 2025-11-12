package com.casa_training.casa_task_day_one.usecase;
import com.casa_training.casa_task_day_one.domain.Role;
import com.casa_training.casa_task_day_one.domain.User;
import com.casa_training.casa_task_day_one.helper.JwtHelper;
import com.casa_training.casa_task_day_one.presentation.rest.dto.req.CreateUserReqDto;
import com.casa_training.casa_task_day_one.presentation.rest.dto.req.LoginRequest;
import com.casa_training.casa_task_day_one.presentation.rest.dto.res.CreateUserResDto;
import com.casa_training.casa_task_day_one.presentation.rest.dto.res.GetUserResponse;
import com.casa_training.casa_task_day_one.presentation.rest.dto.res.LoginResponse;
import com.casa_training.casa_task_day_one.presentation.rest.exception.CustomException;
import com.casa_training.casa_task_day_one.repository.pgsql.RoleRepository;
import com.casa_training.casa_task_day_one.repository.pgsql.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    public CreateUserResDto register (CreateUserReqDto request) {
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
        return new CreateUserResDto(
                userDb.getUserId().toString(), userDb.getName(), userDb.getEmail()
        );
    }

    public GetUserResponse getUser(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.BAD_REQUEST.value()));
        List<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());
        return new GetUserResponse(
            user.getName(), user.getEmail(), user.getUserId().toString(), roles
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
