package com.example.food.services.serviceImpl;

import com.example.food.configurations.security.CustomUserDetailsService;
import com.example.food.configurations.security.JwtUtil;
import com.example.food.dto.EmailSenderDto;
import com.example.food.model.PasswordResetTokenEntity;
import com.example.food.model.Users;
import com.example.food.pojos.login.LoginRequestDto;
import com.example.food.repositories.PasswordResetTokenRepository;
import com.example.food.repositories.UserRepository;
import com.example.food.service.EmailService;
import com.example.food.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final EmailService emailService;


    @Override
    public ResponseEntity<String> login(LoginRequestDto request) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        UserDetails user = customUserDetailsService.loadUserByUsername(request.getEmail());

        if(user!= null ){
            return ResponseEntity.ok(jwtUtil.generateToken(user));
        }
        return ResponseEntity.status(400).body("Some Error Occurred");
    }

    @Override
    public boolean requestPasswordReset(String email) {
        Users users = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("email null"));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        String token = new JwtUtil().generateToken(userDetails);

        PasswordResetTokenEntity passwordResetTokenEntity = new PasswordResetTokenEntity();
        passwordResetTokenEntity.setToken(token);
        passwordResetTokenEntity.setUsersDetails(users);
        passwordResetTokenRepository.save(passwordResetTokenEntity);

        EmailSenderDto emailSenderDto = new EmailSenderDto();
        emailSenderDto.setTo(email);
        emailSenderDto.setSubject("Password reset link");
        emailSenderDto.setContent("http://localhost:8080/users/reset-password/" + token);

        emailService.sendMail(emailSenderDto);

        return true;
    }

    @Override
    public boolean resetPassword(String token, String password) {
        JwtUtil jwtUtil = new JwtUtil();
        String email = jwtUtil.extractUsername(token);

        userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("email null"));


        PasswordResetTokenEntity passwordResetTokenEntity = passwordResetTokenRepository.findByToken(token);

        if (passwordResetTokenEntity == null){
            return false;
        }

        String encodePassword = passwordEncoder.encode(password);

        Users users = passwordResetTokenEntity.getUsersDetails();
        users.setPassword(encodePassword);
        userRepository.save(users);

        passwordResetTokenRepository.delete(passwordResetTokenEntity);

        return true;
    }
}
