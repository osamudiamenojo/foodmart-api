package com.example.food.services.serviceImpl;

import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.Enum.Role;
import com.example.food.configurations.security.CustomUserDetailsService;
import com.example.food.configurations.security.JwtUtil;
import com.example.food.dto.EmailSenderDto;
import com.example.food.dto.*;
import com.example.food.model.Users;
import com.example.food.model.Wallet;
import com.example.food.pojos.CreateUserRequest;
import com.example.food.dto.LoginRequestDto;
import com.example.food.dto.EditUserDto;
import com.example.food.dto.PasswordResetDto;
import com.example.food.dto.PasswordResetRequestDto;
import com.example.food.model.PasswordResetTokenEntity;
import com.example.food.repositories.PasswordResetTokenRepository;
import com.example.food.repositories.UserRepository;
import com.example.food.repositories.WalletRepository;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.EmailService;
import com.example.food.services.UserService;
import com.example.food.util.AppUtil;
import com.example.food.util.ResponseCodeUtil;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import com.example.food.util.UserUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

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
    private final UserUtil userUtil;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final ResponseCodeUtil responseCodeUtil = new ResponseCodeUtil();
    private final WalletRepository walletRepository;

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
    public BaseResponse editUserDetails(EditUserDto editUserDto) {
        String email = userUtil.getAuthenticatedUserEmail();

        BaseResponse baseResponse = new BaseResponse();

        Optional<Users> users = userRepository.findByEmail(email);

        if (users.isEmpty()){
            return responseCodeUtil.updateResponseData( baseResponse,
                    ResponseCodeEnum.ERROR, "User with provided email was not found");
        }

        Users users1 = users.get();

        users1.setFirstName(editUserDto.getFirstName());
        users1.setLastName(editUserDto.getLastName());
        users1.setEmail(editUserDto.getEmail());
        users1.setDateOfBirth(editUserDto.getDateOfBirth());

        userRepository.save(users1);

        return responseCodeUtil.updateResponseData(baseResponse,
                ResponseCodeEnum.SUCCESS," User Information Updated successfully");
    }

    @Override
    public BaseResponse requestPassword(PasswordResetRequestDto passwordResetRequest) {

        Optional<Users> users = userRepository.findByEmail(passwordResetRequest.getEmail());

        BaseResponse response = new BaseResponse();

        if(!users.isPresent())
        {
            return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.ERROR, "User with provided Email not found");
        }

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

        return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.SUCCESS, "Password reset successful kindly check your email");
    }

    @Override
    public BaseResponse resetPassword(PasswordResetDto passwordReset) {

        String email = jwtUtil.extractUsername(passwordReset.getToken());

        Optional<Users> users = userRepository.findByEmail(email);

        BaseResponse response = new BaseResponse();

        if(!users.isPresent()) {
            return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.ERROR, "User not found");
        }

        Users user = users.get();
        PasswordResetTokenEntity passwordResetTokenEntity = passwordResetTokenRepository.findByToken(passwordReset.getToken());

        if (passwordResetTokenEntity == null){
            return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.ERROR, "Invalid token");
        }
        String encodePassword = passwordEncoder.encode(passwordReset.getPassword());
        user.setPassword(encodePassword);
        userRepository.save(user);
        passwordResetTokenRepository.delete(passwordResetTokenEntity);

        return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.SUCCESS, "Password Successfuly reset");
    }

    @Override
    public BaseResponse signUp(CreateUserRequest createUserRequest){
        BaseResponse response = new BaseResponse();
        if (createUserRequest.getFirstName().trim().length() == 0)
            return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.ERROR,
                    "First name cannot be empty.");

        if (createUserRequest.getLastName().trim().length() == 0)
            return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.ERROR,
                    "Last name cannot be empty.");

        if (createUserRequest.getPassword().trim().length() == 0)
            return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.ERROR,
                    "Password cannot be empty.");

        if (!appUtil.validEmail(createUserRequest.getEmail()))
            return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.ERROR_EMAIL_INVALID);

        if (!createUserRequest.getPassword().equals(createUserRequest.getConfirmPassword()))
            return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.ERROR_PASSWORD_MISMATCH);

        Boolean isUserExist = userRepository.existsByEmail(createUserRequest.getEmail());

        if (isUserExist)
            return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.ERROR_DUPLICATE_USER);

        Users newUser = new Users();
        newUser.setFirstName(createUserRequest.getFirstName());
        newUser.setLastName(createUserRequest.getLastName());
        newUser.setEmail(createUserRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        String token = jwtUtil.generateSignUpConfirmationToken(createUserRequest.getEmail());
        newUser.setIsActive(false);
        newUser.setConfirmationToken(token);
        userRepository.save(newUser);

        String link = "<h3>Hello "  + createUserRequest.getFirstName()  +
                "<br> Copy the link below to activate your account <br>Token :</h3>" + token;
        String subject = "FMT-Email Verification Code";

        EmailSenderDto emailSenderDto = new EmailSenderDto();
        emailSenderDto.setTo(createUserRequest.getEmail());
        emailSenderDto.setSubject(subject);
        emailSenderDto.setContent(link);
        emailService.sendMail(emailSenderDto);

        return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.SUCCESS,
                "You have successful registered. Check your email for verification link to validate your account");
    }
    @Override
    public BaseResponse confirmRegistration(ConfirmRegistrationRequestDto confirmRegistrationRequestDto) {
        BaseResponse response = new BaseResponse();
        Optional<Users> existingUser = userRepository.findByConfirmationToken(confirmRegistrationRequestDto.getToken());
        if (existingUser.isPresent()) {
            existingUser.get().setRole(Role.ROLE_USER);
            existingUser.get().setConfirmationToken(null);
            existingUser.get().setIsActive(true);
            userRepository.save(existingUser.get());

            Wallet wallet = new Wallet();
            wallet.setWalletBalance(BigDecimal.valueOf(0));
            wallet.setUser(existingUser.get());
            walletRepository.save(wallet);

            return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.SUCCESS,
                    "Account verification successful");
        } else {
            return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.ERROR,
                    "User not found");
        }
    }

    @Override
    public BaseResponse updatePassword (ChangePasswordDto passwordDto) {
        String email = userUtil.getAuthenticatedUserEmail();

        BaseResponse baseResponse = new BaseResponse();

        String oldPassword = passwordDto.getOldPassword();
        String newPassword = passwordDto.getNewPassword();
        String confirmPassword = passwordDto.getConfirmPassword();

        Optional<Users> optionalUsers = userRepository.findByEmail(email);

        if(optionalUsers.isPresent()){
            Users users = optionalUsers.get();
            String encodedPassword = users.getPassword();
            boolean isPasswordAMatch = passwordEncoder.matches(oldPassword, encodedPassword);

            if(!isPasswordAMatch) return responseCodeUtil.updateResponseData(baseResponse, ResponseCodeEnum.ERROR, "Old password does not match");

            boolean isPasswordEquals = newPassword.equals(confirmPassword);

            if(!isPasswordEquals) return responseCodeUtil.updateResponseData(baseResponse, ResponseCodeEnum.ERROR, "New password and confirm password do not match");

            users.setPassword(passwordEncoder.encode(newPassword));

            userRepository.save(users);
        }

        return responseCodeUtil.updateResponseData(baseResponse, ResponseCodeEnum.SUCCESS, "Your password is successfully updated");
    }


}
