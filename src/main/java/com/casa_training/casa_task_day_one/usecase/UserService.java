package com.casa_training.casa_task_day_one.usecase;

import com.casa_training.casa_task_day_one.domain.User;
import com.casa_training.casa_task_day_one.helper.JwtHelper;
import com.casa_training.casa_task_day_one.presentation.rest.dto.req.CreateUserReqDto;
import com.casa_training.casa_task_day_one.presentation.rest.dto.req.LoginRequest;
import com.casa_training.casa_task_day_one.presentation.rest.dto.res.CreateUserResDto;
import com.casa_training.casa_task_day_one.presentation.rest.dto.res.LoginResponse;
import com.casa_training.casa_task_day_one.repository.pgsql.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.UUID;

public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtHelper jwtHelper;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public CreateUserResDto createUser(CreateUserReqDto request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        UUID userId = UUID.randomUUID();
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = User.builder().userId(userId)
                .name(request.getName())
                .email(request.getEmail())
                .password(encodedPassword).build();

        User savedUser = userRepository.save(user);

        return new CreateUserResDto(
                savedUser.getUserId().toString(),
                savedUser.getName(),
                savedUser.getEmail()
        );
    }

    public LoginResponse login(LoginRequest request){
        // Get email
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isEmpty()){
            throw new IllegalArgumentException("Invalid email or password");
        }

        // Check password
        boolean isMatch = passwordEncoder.matches(request.getPassword(), user.get().getPassword());
        if (!isMatch){
        throw new IllegalArgumentException("Invalid email or password");
        }

        // Generate token
        String token = jwtHelper.generateToken(user.get());

        // Return Token
        return new LoginResponse(token);
    }
}
