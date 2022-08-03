package com.yanki.redis.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Yunki {
    private String id;
    private Long userId;
    private Long secondUserId;
    private String transaction;
    private String payMode;
    private double amount;
    private double amountBootcoin;
    private String phone;
    private String email;
}