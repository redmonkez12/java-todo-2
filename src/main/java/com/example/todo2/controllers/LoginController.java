package com.example.todo2.controllers;

import com.example.todo2.auth.utils.JwtUtils;
import com.example.todo2.common.ErrorCodes;
import com.example.todo2.entities.UserEntity;
import com.example.todo2.requests.JwtRequest;
import com.example.todo2.responses.ErrorResponse;
import com.example.todo2.responses.JwtResponse;
import com.example.todo2.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path ="api/v1")
public class LoginController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public LoginController(UserService userService,
                           AuthenticationManager authenticationManager,
                           JwtUtils jwtUtils) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }
    @PostMapping("/auth")
    public ResponseEntity<Object> login(@RequestBody @Valid JwtRequest jwtRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.email(),
                            jwtRequest.password()
                    )
            );
        } catch (BadCredentialsException | InternalAuthenticationServiceException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(
                            e.getMessage(),
                            ErrorCodes.BAD_CREDENTIALS,
                            HttpStatus.UNAUTHORIZED.value()
                    ));
        }

        UserEntity userDetails = userService.findUserByEmail(jwtRequest.email());
        String token = jwtUtils.generateToken(userDetails);
        String refreshToken = jwtUtils.generateRefreshToken(userDetails);

        return ResponseEntity.ok().body(new JwtResponse(
                token,
                refreshToken,
                userService.getUserResponse(userDetails)));
    }
}


