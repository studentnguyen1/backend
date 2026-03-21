package vn.khanguyen.backend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.khanguyen.backend.domain.User;
import vn.khanguyen.backend.domain.dto.LoginDTO;
import vn.khanguyen.backend.domain.dto.RestLoginDTO;
import vn.khanguyen.backend.service.UserService;
import vn.khanguyen.backend.util.SecurityUtil;
import vn.khanguyen.backend.util.annotation.ApiMessage;

@RestController
public class AuthController {
    private final SecurityUtil securityUtil;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserService userService;

    @Value("${jobhunter.jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenExpiration;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, SecurityUtil securityUtil,
            UserService userService) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil = securityUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    @ApiMessage("Login to system")
    public ResponseEntity<RestLoginDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        // Nạp input gồm username/password vào Security
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(), loginDTO.getPassword());

        // xác thực người dùng => cần viết hàm loadUserByUsername // xac thuc password
        // va truyen username, passw
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // tao token
        String access_token = this.securityUtil.createAccessToken(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // lay thong tin user
        RestLoginDTO res = new RestLoginDTO();
        User currentUser = this.userService.getUserByUsername(loginDTO.getUsername());
        if (currentUser != null) {
            RestLoginDTO.UserLogin userLogin = new RestLoginDTO.UserLogin(currentUser.getId(), currentUser.getEmail(),
                    currentUser.getName());
            res.setUser(userLogin);
            res.setAccessToken(access_token);
        }
        this.userService.updateUserToken(access_token, loginDTO.getUsername());

        String refresh_token = this.securityUtil.createRefreshToken(access_token, res);
        ResponseCookie resCookies = ResponseCookie.from("refresh_token", refresh_token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(refreshTokenExpiration)
                .build();

        return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE, resCookies.toString()).body(res);

    }

}
