package com.example.food.services.serviceImpl;

import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.Enum.Role;
import com.example.food.configurations.security.CustomUserDetailsService;
import com.example.food.configurations.security.JwtUtil;
import com.example.food.dto.AdminPasswordResetDto;
import com.example.food.dto.AdminPasswordResetRequestDto;
import com.example.food.dto.EmailSenderDto;
import com.example.food.model.PasswordResetTokenEntity;
import com.example.food.model.Users;
import com.example.food.repositories.AdminPasswordResetTokenRepository;
import com.example.food.repositories.UserRepository;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.AdminService;
import com.example.food.services.EmailService;
import com.example.food.util.ResponseCodeUtil;
import com.example.food.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;



@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final AdminPasswordResetTokenRepository adminPasswordResetTokenRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final UserUtil userUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final EmailService emailService;

    private final ResponseCodeUtil responseCodeUtil;

    private final HttpServletRequest request;


    BaseResponse baseResponse = new BaseResponse();

    public static  Users unwrapUsers(Optional<Users> users) {
        if (users.isPresent())
            return users.get();
        throw new RuntimeException("User not found");
    }
    @Override
    public BaseResponse adminRequestNewPassword(AdminPasswordResetRequestDto adminPasswordResetRequestDto) {
        Optional<Users> users = userRepository.findByEmail(adminPasswordResetRequestDto.getEmail());
        Users adminUser = unwrapUsers(users);

        //Check if adminUser is registered and has admin role, then create token and assign it to adminUser
        if (adminUser.getRole().equals(Role.ROLE_ADMIN)) {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(adminPasswordResetRequestDto.getEmail());
            String token = new JwtUtil().generateToken(userDetails);
            PasswordResetTokenEntity passwordResetTokenEntity = new PasswordResetTokenEntity();
            passwordResetTokenEntity.setToken(token);
            passwordResetTokenEntity.setUsersDetails(adminUser);

            //After creating and assigning token to adminUser, send token via email to adminUser
            EmailSenderDto emailSenderDto = new EmailSenderDto();
            emailSenderDto.setTo(adminPasswordResetRequestDto.getEmail());
            emailSenderDto.setSubject("Admin Password Reset Link");
            emailSenderDto.setContent("http://localhost:8080/admin/reset-password/" + token);
            emailService.sendMail(emailSenderDto);
            baseResponse.setCode(0);
            baseResponse.setDescription("Please check your email for a password reset link.");

            return responseCodeUtil.updateResponseData(baseResponse, ResponseCodeEnum.SUCCESS);
        } else {
            baseResponse.setCode(-6);
        }
        return responseCodeUtil.updateResponseData(baseResponse, ResponseCodeEnum.ERROR_RESETTING_PASSWORD);
    }

    @Override
    public BaseResponse adminResetPassword(AdminPasswordResetDto adminPasswordResetDto) {
        JwtUtil jwtUtil1 = new JwtUtil();
        String email = jwtUtil1.extractUsername(adminPasswordResetDto.getToken());
        Optional<Users> users = userRepository.findByEmail(email);

        if (users.isPresent()) {
            PasswordResetTokenEntity passwordResetTokenEntity = adminPasswordResetTokenRepository.findByToken(adminPasswordResetDto.getToken());
            if (passwordResetTokenEntity != null) {
                String encodePassword = passwordEncoder.encode(adminPasswordResetDto.getPassword());
                Users user = passwordResetTokenEntity.getUsersDetails();
                user.setPassword(encodePassword);
                userRepository.save(user);
                adminPasswordResetTokenRepository.delete(passwordResetTokenEntity);
                baseResponse.setCode(0);
                baseResponse.setDescription("Password reset was successful.");
                return responseCodeUtil.updateResponseData(baseResponse, ResponseCodeEnum.SUCCESS);
            }
            baseResponse.setCode(-6);

        }
        return responseCodeUtil.updateResponseData(baseResponse, ResponseCodeEnum.ERROR_RESETTING_PASSWORD);
    }
}
