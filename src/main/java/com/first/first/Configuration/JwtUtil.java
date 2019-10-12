package com.first.first.Configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    //密钥
     private static final String SECRET="jaklegjgvaa;56423165eag";
     //过期时间秒
    public static final int EXPIRE=5*60;

    //生成token
    public static String CreateToken(String userId,String userName) throws Exception{
        Calendar now=Calendar.getInstance();
        now.add(Calendar.SECOND,EXPIRE);
        Date expireDate=now.getTime();

        Map<String,Object> map=new HashMap<>();
        map.put("alg","HS256");
        map.put("typ","JWT");
        String token= JWT.create()
                .withHeader(map)
                .withClaim("userId",userId)
                .withClaim("userName",userName)
                .withSubject("test")
                .withIssuedAt(new Date())//签名时间
                 .withExpiresAt(expireDate)//过期时间
                 .sign(Algorithm.HMAC256(SECRET));//签名
        return token;
    }

    //验证token
    public static Map<String , Claim> verifyToken(String token)throws Exception{
        JWTVerifier verifier=JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT jwt=null;
        try {
            jwt=verifier.verify(token);
        }catch (Exception e){
            throw new RuntimeException("凭证过期，请重新登录!");
        }
        return jwt.getClaims();
    }

    //解析token
    public static Map<String ,Claim> parseToken(String token){
        DecodedJWT decodedJWT=JWT.decode(token);
        return decodedJWT.getClaims();
    }
}
