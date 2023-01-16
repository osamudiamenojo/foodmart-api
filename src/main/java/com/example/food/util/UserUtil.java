package com.example.food.util;

import com.example.food.model.Users;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Data
public class UserUtil {

    public String getAuthenticatedUserEmail(){

      UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

      return userDetails.getUsername();
    }

    public Users getCurrentLoginUser(){
        Users user =  new Users();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BeanUtils.copyProperties(userDetails,user);
        return user ;
    }
}
