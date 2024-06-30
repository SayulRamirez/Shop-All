package com.metaphorce.shop_all.domain;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthResponse (

        @Schema(example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWFuQGV4YW1wbGUuY29tIiwiaWF0IjoxNzE5NzIxOTM4LCJleHAiOjE3MTk3MjMzNzh9.Kw-G2zhQuMuL0K-lLjtBLPDaV2U2_OP5xoF5KpmCujs")
        String token
){
}
