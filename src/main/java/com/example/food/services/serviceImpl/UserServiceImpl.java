package com.example.food.services.serviceImpl;

import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.configurations.security.CustomUserDetailsService;
import com.example.food.configurations.security.JwtUtil;
import com.example.food.dto.EditUserDto;
import com.example.food.model.Users;
import com.example.food.pojos.login.LoginRequestDto;
import com.example.food.repositories.UserRepository;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.UserService;
import com.example.food.util.ResponseCodeUtil;
import com.example.food.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserRepository userRepository;
    private final UserUtil userUtil;
    private final ResponseCodeUtil responseCodeUtil;


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
}
