package com.example.food.restartifacts;

import com.example.food.restartifacts.BaseResponse;

public class PasswordResetResponse extends BaseResponse {

    public static BaseResponse success(int code, String description){

        BaseResponse baseResponse = new BaseResponse();

        baseResponse.assignResponseCodeAndDescription(code, description);
        return baseResponse;
    }

    public static BaseResponse error(int code, String description){

        BaseResponse baseResponse = new BaseResponse();

        baseResponse.assignResponseCodeAndDescription(code, description);
        return baseResponse;
    }

}
