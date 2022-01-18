package com.example.jwt.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTokenUtil implements Serializable {

  private static final long serialVersionUID = -2550185165626007488L;

  /**
   *  单位毫秒
   */
  @Value("${jwt.timeout:50}")
  public long JWT_TOKEN_VALIDITY;

  @Value("${jwt.secret}")
  private String secret;

  //retrieve username from jwt token
  public String getUsernameFromToken(String token) {
    final String claimFromToken = getClaimFromToken(token, Claims::getSubject);
    log.info("claimFromToken:{}", claimFromToken);
    final JSONObject jsonObject = JSON.parseObject(claimFromToken);
    return jsonObject.getString("username");
  }

  //retrieve expiration date from jwt token
  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  //for retrieveing any information from token we will need the secret key
  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

  }

  //check if the token has expired
  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  //generate token for user
  public String generateToken(UserDetails userDetails, String seed) {
    Map<String, Object> claims = new HashMap<>();
    final JSONObject jsonObject = new JSONObject();
    jsonObject.put("username", userDetails.getUsername());
    jsonObject.put("password", userDetails.getPassword());
    jsonObject.put("seed", seed);
    String subject = jsonObject.toJSONString();

    return doGenerateToken(claims, subject);
  }

  //while creating the token -
  //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
  //2. Sign the JWT using the HS512 algorithm and secret key.
  //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
  //   compaction of the JWT to a URL-safe string
  private String doGenerateToken(Map<String, Object> claims, String subject) {

    return Jwts.builder().setClaims(claims).setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(calculateExpirationDate(null))
        .signWith(SignatureAlgorithm.HS512, secret).compact();
  }

  //validate token
  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = getUsernameFromToken(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  public Boolean canTokenBeRefreshed(String token) {
    return (!isTokenExpired(token) || ignoreTokenExpiration(token));
  }

  private Boolean ignoreTokenExpiration(String token) {
    // here you specify tokens, for that the expiration is ignored
    return false;
  }

  private Date calculateExpirationDate(Date createdDate) {
    if (Objects.nonNull(createdDate)) {
      new Date(createdDate.getTime() + System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000);
    }
    return new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000);
  }

  public String refreshToken(String token) {
    final Date createdDate = new Date();
    final Date expirationDate = calculateExpirationDate(createdDate);

    final Claims claims = getAllClaimsFromToken(token);
    claims.setIssuedAt(createdDate);
    claims.setExpiration(expirationDate);
    return Jwts.builder().setClaims(claims)
        .signWith(SignatureAlgorithm.HS512, secret).compact();
  }

}