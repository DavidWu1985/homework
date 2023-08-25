package com.wuwei.homework.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JwtUtil {
    //创建默认的秘钥和算法，供无参的构造方法使用
    private static final String defaultbase64EncodedSecretKey = "david";
    private static final SignatureAlgorithm defaultsignatureAlgorithm = SignatureAlgorithm.HS256;
    //TOKEN 有效时间 30天
    private static long ttl = 30*24*60*60*1000L;

    public JwtUtil() {
        this(defaultbase64EncodedSecretKey, defaultsignatureAlgorithm);
    }

    private final String base64EncodedSecretKey;
    private final SignatureAlgorithm signatureAlgorithm;

    public JwtUtil(String secretKey, SignatureAlgorithm signatureAlgorithm) {
        this.base64EncodedSecretKey = Base64.encodeBase64String(secretKey.getBytes());
        this.signatureAlgorithm = signatureAlgorithm;
    }

    public String encode(String iss, Map<String, Object> claims) {
        //iss签发人，ttlMillis生存时间，claims是指还想要在jwt中存储的一些非隐私信息
        if (claims == null) {
            claims = new HashMap<>();
        }
        long nowMillis = System.currentTimeMillis();

        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setId(UUID.randomUUID().toString())//这个是JWT的唯一标识
                .setIssuedAt(new Date(nowMillis))//换算当前系统时间
                .setSubject(iss)//签发人
                .signWith(signatureAlgorithm, base64EncodedSecretKey);
        long expMillis = nowMillis + ttl;
        Date exp = new Date(expMillis);//过期时间
        builder.setExpiration(exp);
        return builder.compact();
    }

    //相当于encode的方向，传入jwtToken生成对应的username和password等字段。Claim就是一个map
    //也就是拿到荷载部分所有的键值对
    public Claims decode(String jwtToken) {

        // 得到 DefaultJwtParser
        return Jwts.parser()
                // 设置签名的秘钥
                .setSigningKey(base64EncodedSecretKey)
                // 设置需要解析的 jwt
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    //判断jwtToken是否合法
    public boolean isVerify(String jwtToken) {
        Algorithm algorithm = null;
        switch (signatureAlgorithm) {
            case HS256:
                algorithm = Algorithm.HMAC256(Base64.decodeBase64(base64EncodedSecretKey));
                break;
            default:
                throw new RuntimeException("不支持该算法");
        }
        JWTVerifier verifier = JWT.require(algorithm).build();
        verifier.verify(jwtToken);  // 校验不通过会抛出异常
        //判断合法的标准：1. 头部和荷载部分没有篡改过。2. 没有过期
        return true;
    }

}