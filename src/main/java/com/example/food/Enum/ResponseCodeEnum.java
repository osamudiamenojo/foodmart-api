package com.example.food.Enum;

import lombok.Getter;

/**
 *
 * @author Odira Eze
 */
@Getter
public enum ResponseCodeEnum {

    SUCCESS(0, "Success"),
    SUCCESSFUL_REGISTRATION(1,"You have successful registered. Check your email for verification link to validate your account"),
    ERROR(-1, "An error occurred. Error message : ${errorMessage}"),
    ERROR_EMAIL_INVALID(-2, "Invalid email address."),
    ERROR_PASSWORD_MISMATCH(-3,"Password does not match"),
    ERROR_DUPLICATE_USER(-4,"User already exist.")

    ERROR_PASSWORD_RESET(-2, "An error occurred. Error message : ${errorMessage}")
//    you can add your custom error codes as shown below just ensure that error codes have a minus sign
//    ERROR_SETTING_THRESHOLD(-2, "An error occurred"),
    ;

    ResponseCodeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    private final int code;
    private final String description;

}
