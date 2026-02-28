package com.bookstore.controllers;

import com.bookstore.dtos.AuthRecordDto;
import com.bookstore.dtos.AuthRecordResponseDto;
import com.bookstore.exceptions.DataFormatWrongException;
import com.bookstore.models.RoleModel;
import com.bookstore.models.UserModel;
import com.bookstore.services.UserService;
import org.apache.catalina.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookstore/auth")
public class TokenController {

    private final JwtEncoder jwtEncoder;
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public TokenController(JwtEncoder jwtEncoder, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping
    public ResponseEntity<AuthRecordResponseDto> login(@RequestBody AuthRecordDto authRecordDto) {
        UserModel user = userService.userExists(authRecordDto.email());

        if (!user.isLoginCorrect(authRecordDto, bCryptPasswordEncoder)) {
            throw new DataFormatWrongException("User or password is incorrect.");
        }

        var now = Instant.now();
        var expiresIn = 300L;

        var scopes = user.getRoleModels()
                .stream()
                .map(RoleModel::getName)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("bookstore")
                .subject(user.getEmail())
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes)
                .issuedAt(now)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.status(HttpStatus.OK).body(new AuthRecordResponseDto(jwtValue, expiresIn));
    }
}
