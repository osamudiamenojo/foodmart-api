package com.example.food.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class ProductSearchDto {
    private String sortDirection;
    private String sortBy;
    private String filter;
    private int pageNumber = 0;
    private int pageSize =  10;
}
