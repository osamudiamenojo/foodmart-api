package com.example.food.pojos;

import com.example.food.restartifacts.BaseResponse;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class WalletResponse extends BaseResponse {
    private Double balance;
}
