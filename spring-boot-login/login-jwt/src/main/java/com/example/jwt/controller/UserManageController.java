package com.example.jwt.controller;


import static com.example.jwt.constant.RestConstants.AUTH_HEADER;


import com.example.jwt.bean.UserEntity;
import com.example.jwt.bean.dto.UserDTO;
import com.example.jwt.bean.param.UserParam;
import com.example.jwt.bean.rsp.UserRsp;
import com.example.jwt.config.JwtTokenUtil;
import com.example.jwt.constant.RestConstants;
import com.example.jwt.constant.RestResp;
import com.example.jwt.service.JwtUserDetailsService;
import io.swagger.annotations.Api;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户验证")
@RequestMapping(RestConstants.WEB_PREFIX + RestConstants.USER)
@RestController
@CrossOrigin
@Slf4j
public class UserManageController {


  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private JwtTokenUtil jwtTokenUtil;
  @Autowired
  private JwtUserDetailsService jwtUserDetailsService;


  @RequestMapping(value = RestConstants.LOGIN, method = RequestMethod.POST)
  public RestResp<?> login(@RequestBody UserParam userParam) throws Exception {

    authenticate(userParam.getUsername(), userParam.getPassword());
    final UserDetails userDetails = jwtUserDetailsService
        .loadUserByUsername(userParam.getUsername());
    final UserEntity user = jwtUserDetailsService.findByUsername(userDetails.getUsername())

        .orElseThrow(
            () -> new UsernameNotFoundException(String.format("未匹配到该用户: %s"
                , userDetails.getUsername())));

    return RestResp.ok(UserRsp.builder()
        .nickname(user.getNickname())
        .id(user.getId())
        .username(user.getUsername())
        .token(
            RestConstants.TOKEN_PREFIX + jwtTokenUtil.generateToken(userDetails, user.getSeed()))
        .build());
  }

  @GetMapping(RestConstants.REFRESH_TOKEN_URL)
  public RestResp<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
    String authToken = request.getHeader(AUTH_HEADER);
    final String token = authToken.substring(10);
    String username = jwtTokenUtil.getUsernameFromToken(token);
    final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
    if (jwtTokenUtil.canTokenBeRefreshed(token)) {
      final UserEntity userEntity = jwtUserDetailsService.findByUsername(userDetails.getUsername())
          .get();
      String refreshedToken = jwtTokenUtil.refreshToken(token);
      return RestResp.ok(UserRsp.builder()
          .nickname(userEntity.getNickname())
          .id(userEntity.getId())
          .username(userEntity.getUsername())
          .token(
              RestConstants.TOKEN_PREFIX + refreshedToken)
          .build());
    } else {
      return RestResp.error(HttpServletResponse.SC_UNAUTHORIZED, "token解析失败");
    }
  }

  @RequestMapping(value = RestConstants.LOGIN_OUT, method = RequestMethod.GET)
  public RestResp<?> loginOut(@RequestParam("token") String authToken) throws Exception {
    final String token = authToken.substring(10);
    String username = jwtTokenUtil.getUsernameFromToken(token);
    final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
    if (jwtTokenUtil.canTokenBeRefreshed(token)) {

      jwtTokenUtil.refreshToken(token);

    }
    return RestResp.ok("退出登陆成功");
  }

  @RequestMapping(value = RestConstants.REGISTER, method = RequestMethod.POST)
  public RestResp<?> saveUser(@RequestBody UserDTO user) throws Exception {
    return RestResp.ok(jwtUserDetailsService.save(user));
  }


  private void authenticate(String username, String password) throws Exception {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,
          password));
    } catch (DisabledException e) {
      throw new Exception("用户被停用", e);
    } catch (BadCredentialsException e) {
      throw new Exception("无效认证", e);
    }
  }
}
