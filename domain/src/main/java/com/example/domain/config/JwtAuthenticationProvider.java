package com.example.domain.config;

import com.example.domain.domain.user.UserType;
import com.example.domain.domain.user.UserVo;
import com.example.domain.util.Aes256Util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationProvider {

    @Value("${spring.jwt.secret}")
    private String secreteKey;

    private static final long TOKEN_EXPIRATION_TIME = 1000L * 60 * 60 * 24; // 24시간

    // JWT 토큰 생성 메소드
    public String createToken(String userPk, Long id, UserType userType) {
        Claims claims = Jwts.claims().setSubject(Aes256Util.encrypt(userPk))
                .setId(Aes256Util.encrypt(id.toString()));
        claims.put("roles", userType);

        log.info("토큰이 생성되었습니다. -> " + userPk);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, secreteKey)
                .compact();
    }

    // JWT 토큰 유효성 검증 메소드
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secreteKey).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    // JWT 토큰에서 사용자 정보 추출 메소드
    public UserVo getUserVo(String token) {
        Claims claims = Jwts.parser().setSigningKey(secreteKey).parseClaimsJws(token).getBody();
        return new UserVo(
                Long.valueOf(Objects.requireNonNull(Aes256Util.decrypt(claims.getId()))),
                Aes256Util.decrypt(claims.getSubject()),
                null, // JWT에는 패스워드를 포함하지 않기 때문에 null 설정
                UserType.valueOf((String) claims.get("roles"))
        );
    }
}
