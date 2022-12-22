package com.example.food.services.serviceImpl;

import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.configurations.security.CustomUserDetailsService;
import com.example.food.configurations.security.JwtUtil;
import com.example.food.dto.EmailSenderDto;
import com.example.food.model.Users;
import com.example.food.pojos.CreateUserRequest;
import com.example.food.pojos.login.LoginRequestDto;
import com.example.food.repositories.UserRepository;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.EmailService;
import com.example.food.services.UserService;
import com.example.food.util.AppUtil;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final AppUtil appUtil;

    @Override
    public BaseResponse signUp(CreateUserRequest createUserRequest){
        if (!appUtil.validEmail(createUserRequest.getEmail()))
            return new BaseResponse(ResponseCodeEnum.ERROR_EMAIL_INVALID);

        if (!createUserRequest.getPassword().equals(createUserRequest.getConfirmPassword()))
            return new BaseResponse(ResponseCodeEnum.ERROR_PASSWORD_MISMATCH);

        Boolean isUserExist = userRepository.existsByEmail(createUserRequest.getEmail());

        if (isUserExist)
            return new BaseResponse(ResponseCodeEnum.ERROR_DUPLICATE_USER);

        Users newUser = new Users();
        newUser.setFirstName(createUserRequest.getFirstName());
        newUser.setLastName(createUserRequest.getLastName());
        newUser.setEmail(createUserRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        String token = RandomString.make(64);
        userRepository.save(newUser);

        String URL = "http://localhost:8080/foodmart/api/v1/user/confirm?token=" + token;
        String link = "<h3>Hello "  + createUserRequest.getFirstName()  +
                "<br> Click the link below to activate your account <a href=" +
                URL + "><br>Activate</a><h3>";
        String subject = "FMT-Email Verification Code";

        EmailSenderDto emailSenderDto = new EmailSenderDto();
        emailSenderDto.setTo(createUserRequest.getEmail());
        emailSenderDto.setSubject(subject);
        emailSenderDto.setContent(link);
        emailService.sendMail(emailSenderDto);

        return new BaseResponse(ResponseCodeEnum.SUCCESSFUL_REGISTRATION);
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
