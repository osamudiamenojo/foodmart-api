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
import com.example.food.services.EmailService;
import com.example.food.services.UserService;
import com.example.food.util.ResponseCodeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private EmailService emailService;

    ResponseCodeUtil responseCodeUtil = new ResponseCodeUtil();



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
    public BaseResponse requestPassword(PasswordResetRequestDto passwordResetRequest) {

        Optional<Users> users = userRepository.findByEmail(passwordResetRequest.getEmail());

        if(users.isPresent())
        {
            Users users1 = users.get();
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(passwordResetRequest.getEmail());
            String token = new JwtUtil().generateToken(userDetails);
            PasswordResetTokenEntity passwordResetTokenEntity = new PasswordResetTokenEntity();
            passwordResetTokenEntity.setToken(token);
            passwordResetTokenEntity.setUsersDetails(users1);
            passwordResetTokenRepository.save(passwordResetTokenEntity);

            EmailSenderDto emailSenderDto = new EmailSenderDto();
            emailSenderDto.setTo(passwordResetRequest.getEmail());
            emailSenderDto.setSubject("Password reset link");
            emailSenderDto.setContent("http://localhost:8080/users/reset-password/" + token);
            emailService.sendMail(emailSenderDto);

            var response = new BaseResponse(ResponseCodeEnum.SUCCESS);
            return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.SUCCESS);
        }

        return responseCodeUtil.updateResponseData(new BaseResponse(), ResponseCodeEnum.ERROR);
    }

    @Override
    public BaseResponse resetPassword(PasswordResetDto passwordReset) {

        String email = jwtUtil.extractUsername(passwordReset.getToken());

        Optional<Users> users = userRepository.findByEmail(email);

        if(users.isPresent()) {
            Users user = users.get();
            PasswordResetTokenEntity passwordResetTokenEntity = passwordResetTokenRepository.findByToken(passwordReset.getToken());

            if (passwordResetTokenEntity == null){
                return new BaseResponse(ResponseCodeEnum.ERROR);
            }
            String encodePassword = passwordEncoder.encode(passwordReset.getPassword());
            user.setPassword(encodePassword);
            userRepository.save(user);
            passwordResetTokenRepository.delete(passwordResetTokenEntity);

            var response = new BaseResponse(ResponseCodeEnum.SUCCESS);
            return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.SUCCESS);
        }

        return responseCodeUtil.updateResponseData(new BaseResponse(), ResponseCodeEnum.ERROR);
    }

}
