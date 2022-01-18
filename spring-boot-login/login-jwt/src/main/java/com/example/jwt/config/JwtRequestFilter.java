package com.example.jwt.config;


import com.alibaba.fastjson.JSON;
import com.example.jwt.constant.RestConstants;
import com.example.jwt.constant.RestResp;
import com.example.jwt.service.JwtUserDetailsService;
import io.vavr.control.Try;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

  @Autowired
  private JwtUserDetailsService jwtUserDetailsService;


  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain)
      throws ServletException, IOException {
    final String requestTokenHeader = request.getHeader(RestConstants.AUTH_HEADER);

    String username = null;
    String jwtToken = null;
    // JWT Token is in the form "Bearer token". Remove Bearer word and get
    // only the Token
    if (requestTokenHeader != null && requestTokenHeader.startsWith(RestConstants.TOKEN_PREFIX)) {
      jwtToken = requestTokenHeader.substring(10);
      String finalJwtToken = jwtToken;
      username = Try.of(() -> jwtTokenUtil.getUsernameFromToken(finalJwtToken)).getOrNull();
      if (ObjectUtils.isEmpty(username)) {  // Token不可解码
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter()
            .write(JSON.toJSONString(RestResp.error(HttpServletResponse.SC_FORBIDDEN, "token已过期")));
        return;
      }
    }
//    else if ((!request.getRequestURI()
//        .endsWith(RestConstants.WEB_PREFIX + RestConstants.USER + RestConstants.LOGIN))||!request.getRequestURI().endsWith("doc.html")) {
//      response.setContentType("application/json;charset=UTF-8");
//      response.getWriter()
//          .write(JSON.toJSONString(RestResp.error(HttpServletResponse.SC_FORBIDDEN, "不是正确的token")));
//      return;
//    }

    // Once we get the token validate it.
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

      UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

      // if token is valid configure Spring Security to manually set
      // authentication
      if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken
            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // After setting the Authentication in the context, we specify
        // that the current user is authenticated. So it passes the
        // Spring Security Configurations successfully.
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      }
    }
    chain.doFilter(request, response);


  }

}