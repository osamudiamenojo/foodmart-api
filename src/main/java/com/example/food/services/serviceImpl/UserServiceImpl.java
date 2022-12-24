package com.example.food.services.serviceImpl;

import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.configurations.security.CustomUserDetailsService;
import com.example.food.configurations.security.JwtUtil;
import com.example.food.dto.EmailSenderDto;
import com.example.food.dto.PasswordResetDto;
import com.example.food.dto.PasswordResetRequestDto;
import com.example.food.model.*;
import com.example.food.pojos.login.LoginRequestDto;
import com.example.food.repositories.PasswordResetTokenRepository;
import com.example.food.repositories.UserRepository;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.restartifacts.PasswordResetResponse;
import com.example.food.services.EmailService;
import com.example.food.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public BaseResponse requestPassword(PasswordResetRequestDto passwordResetRequestModel) {

        Optional<Users> users = userRepository.findByEmail(passwordResetRequestModel.getEmail());

        if(users.isPresent())
        {
            Users users1 = users.get();
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(passwordResetRequestModel.getEmail());
            String token = new JwtUtil().generateToken(userDetails);
            PasswordResetTokenEntity passwordResetTokenEntity = new PasswordResetTokenEntity();
            passwordResetTokenEntity.setToken(token);
            passwordResetTokenEntity.setUsersDetails(users1);


            EmailSenderDto emailSenderDto = new EmailSenderDto();
            emailSenderDto.setTo(passwordResetRequestModel.getEmail());
            emailSenderDto.setSubject("Password reset link");
            emailSenderDto.setContent("http://localhost:8080/users/reset-password/" + token);
            emailService.sendMail(emailSenderDto);

            return PasswordResetResponse.success(200, "Password reset successful kindly check your email");
        }
        return new BaseResponse(ResponseCodeEnum.ERROR_PASSWORD_RESET);
    }

    @Override
    public BaseResponse resetPassword(PasswordResetDto passwordResetModel) {

        JwtUtil jwtUtil = new JwtUtil();
        String email = jwtUtil.extractUsername(passwordResetModel.getToken());

        Optional<Users> users = userRepository.findByEmail(email);

        if(users.isPresent())
        {
            PasswordResetTokenEntity passwordResetTokenEntity = passwordResetTokenRepository.findByToken(passwordResetModel.getToken());

            if (passwordResetTokenEntity == null){
                return PasswordResetResponse.error(-400, "Access Denied");
            }
            String encodePassword = passwordEncoder.encode(passwordResetModel.getPassword());
            Users user = passwordResetTokenEntity.getUsersDetails();
            user.setPassword(encodePassword);
            userRepository.save(user);
            passwordResetTokenRepository.delete(passwordResetTokenEntity);

            return new BaseResponse(ResponseCodeEnum.SUCCESS);
        }

        return new BaseResponse(ResponseCodeEnum.ERROR);
    }
}
