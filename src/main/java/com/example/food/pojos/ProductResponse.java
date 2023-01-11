package com.example.food.pojos;

import com.example.food.dto.ProductDto;
import com.example.food.restartifacts.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class ProductResponse extends BaseResponse {

        private final boolean status;

        private final String message;

        private final List<ProductDto> products;
}
