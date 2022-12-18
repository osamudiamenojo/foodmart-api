package com.example.food.services.serviceImpl;

import com.example.food.configurations.security.CustomUserDetailsService;
import com.example.food.configurations.security.JwtUtil;
import com.example.food.dto.EmailSenderDto;
import com.example.food.model.Users;
import com.example.food.pojos.UserRequest;
import com.example.food.pojos.login.LoginRequestDto;
import com.example.food.repositories.UserRepository;
import com.example.food.services.EmailService;
import com.example.food.services.UserService;
import com.example.food.util.ApiResponse;
import com.example.food.util.AppUtil;
import com.example.food.util.ResponseProvider;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
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
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final EmailService emailService;
    private final ResponseProvider responseProvider;
    private final AppUtil appUtil;


    @Override
    public ResponseEntity<ApiResponse<Object>> signUp(UserRequest userRequest) {
        if (!appUtil.validEmail(userRequest.getEmail()))
            throw new com.example.food.exception.ValidationException("Invalid email address");

        if (!userRequest.getPassword().equals(userRequest.getConfirmPassword()))
            throw new com.example.food.exception.ValidationException("Password does not match");

        Boolean isUserExist = userRepository.existsByEmail(userRequest.getEmail());

        if (isUserExist)
            throw new com.example.food.exception.ValidationException("User already exists.");

        Users newUser = new Users();
        newUser.setFirstName(userRequest.getFirstName());
        newUser.setLastName(userRequest.getLastName());
        newUser.setEmail(userRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        String token = RandomString.make(64);
        userRepository.save(newUser);

        String URL = "http://localhost:8080/foodmart/api/v1/user/confirm?token=" + token;
        String link = "<h3>Hello "  + userRequest.getFirstName()  +
                "<br> Click the link below to activate your account <a href=" +
                URL + "><br>Activate</a><h3>";
        String subject = "FMT-Email Verification Code";

        EmailSenderDto emailSenderDto = new EmailSenderDto();
        emailSenderDto.setTo(userRequest.getEmail());
        emailSenderDto.setSubject(subject);
        emailSenderDto.setContent(link);
        emailService.sendMail(emailSenderDto);

        return responseProvider.success(newUser);
    }

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
}
