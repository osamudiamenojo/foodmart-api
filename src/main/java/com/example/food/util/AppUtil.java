package com.example.food.util;

import lombok.Data;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Service
public class AppUtil {

    public boolean validImage(String fileName)
    {
        String regex = "(.*/)*.+\\.(png|jpg|gif|bmp|jpeg|PNG|JPG|GIF|BMP|JPEG)$";
        Pattern p = Pattern.compile(regex);
        if (fileName == null) {
            return false;
        }
        Matcher m = p.matcher(fileName);
        return m.matches();
    }

    //Email validation
    public boolean validEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public String getFormattedNumber(String number){
        number=number.trim();
        if(number.startsWith("0"))
            number="+234"+number.substring(1);
        else if(number.startsWith("234"))
            number="+"+number;
        else {
            if (!number.startsWith("+")) {
                if (Integer.parseInt(String.valueOf(number.charAt(0))) > 0) {
                    number = "+234" + number;
                }
            }
        }
        return  number;
    }


}
