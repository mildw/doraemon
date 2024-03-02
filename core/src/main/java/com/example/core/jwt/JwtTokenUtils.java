package com.example.core.jwt;

import io.jsonwebtoken.*;
import lombok.experimental.UtilityClass;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@UtilityClass
public class JwtTokenUtils {

    private final String SECRET_KEY = "secret-key";
    public final String TENANT_KEY_CLAIM_NAME = "ten";

    public static TokenInfo createToken(Long tenantId, String accountId) {
        Date iat = new Date();
        Date exp = getExp(iat);

        Map<String, Object> claims = new HashMap<>();
        claims.put(TENANT_KEY_CLAIM_NAME, tenantId);

        return getTokenInfo(accountId, claims, iat, exp);
    }

    public static Optional<TokenInfo> refreshToken(String token) {
        Claims claims = getClaims(token);

        Date iat = new Date();
        Date exp = claims.getExpiration();

        long minutesRemaining = (exp.getTime() - iat.getTime()) / (60 * 1000);
        if (minutesRemaining > 30) {
            return Optional.empty();
        }

        exp = getExp(iat);

        return Optional.ofNullable(getTokenInfo(claims.getAudience(), claims, iat, exp));
    }

    private static Date getExp(Date iat) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(iat);
        calendar.add(Calendar.HOUR_OF_DAY, 2);
        return calendar.getTime();
    }

    private static TokenInfo getTokenInfo(String audience, Map<String, Object> claims, Date iat, Date exp) {
        String token = Jwts.builder()
                .setAudience(audience)
                .addClaims(claims)
                .setIssuedAt(iat)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        return new TokenInfo(audience, claims, exp, token);
    }

    public static String resolveToken(HttpServletRequest request) throws Exception {
        String authorization = request.getHeader("Authorization");

        if (Objects.isNull(authorization)) {
            authorization = request.getParameter("Authorization");
        }

        if (Objects.isNull(authorization) || !authorization.startsWith("Bearer")) {
            return null;
        }

        return URLDecoder.decode(authorization, StandardCharsets.UTF_8.name()).substring("Bearer".length()).trim();
    }

    public static TokenValidResult validateToken(String token) {
        try {
            Header header = getHeader(token);

            if (((String) header.get("alg")).toLowerCase().equals(SignatureAlgorithm.NONE.name().toLowerCase())) {
                return TokenValidResult.INVALID;
            }
            return TokenValidResult.VALID;

        } catch (ExpiredJwtException e) {
            return TokenValidResult.EXPIRED;
        } catch (SignatureException e) {
            return TokenValidResult.NOT_MATCH_SIGNATURE;
        } catch (Exception e) {
            return TokenValidResult.INVALID;
        }
    }

    public static String getAudience(String token) {
        Claims claims = getClaims(token);
        return claims.getAudience();
    }

    public static Claims getClaims(String token) {

        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private static Header getHeader(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parse(token).getHeader();
    }
}
