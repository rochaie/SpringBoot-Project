package com.first.first;

import com.auth0.jwt.interfaces.Claim;
import com.first.first.Configuration.JwtUtil;
import com.mysql.cj.util.Base64Decoder;
import org.springframework.util.Base64Utils;

import java.text.DecimalFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Map;
import java.util.Random;

public class test {

    public static void main(String[] args) {
        try {
            String token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0IiwidXNlck5hbWUiOiIxMjMiLCJleHAiOjE1Njk0NjkxMzksInVzZXJJZCI6IjEyMyIsImlhdCI6MTU2OTQ2ODgzOX0.AtAgpDjvuIgBhz2PDRngxi3EOyeVLtysxKSVHoXGP_0";
            Map<String,Claim> map=JwtUtil.verifyToken(token);
            System.out.println(map);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
