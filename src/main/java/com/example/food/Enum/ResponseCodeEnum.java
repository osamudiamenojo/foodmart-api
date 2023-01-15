package com.example.food.Enum;

import lombok.Getter;

/**
 *
 * @author Odira Eze
 */
@Getter
public enum ResponseCodeEnum {

    SUCCESS(0, "Success"),
    ERROR(-1, "An error occurred. Error message : ${errorMessage}"),
    PRODUCT_NOT_FOUND(2, "No products found : ${errorMessage"),

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
