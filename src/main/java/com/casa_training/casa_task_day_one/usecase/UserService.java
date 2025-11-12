package com.casa_training.casa_task_day_one.usecase;

import com.casa_training.casa_task_day_one.domain.User;
import com.casa_training.casa_task_day_one.presentation.rest.dto.req.CreateUserReqDto;
import com.casa_training.casa_task_day_one.presentation.rest.dto.res.CreateUserResDto;
import com.casa_training.casa_task_day_one.repository.pgsql.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

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


}
