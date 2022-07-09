package com.tinie.Services.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tinie.Services.exceptions.UnauthorisedException;
import com.tinie.Services.repositories.LoginEntryRepository;
import com.tinie.Services.util.EnvConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JWTProcessor {

    @Autowired
    EnvConstants envConstants;
    @Autowired
    LoginEntryRepository loginEntryRepository;

    public DecodedJWT verifyAndDecodeToken(String token){
        var decodedJwt =  JWT.require(Algorithm.HMAC512(envConstants.getTokenSecret()))
                .build()
                .verify(token);

        var loginEntryOptional = loginEntryRepository
                .findByPhoneNumber(Long.parseLong(decodedJwt.getSubject()));
        if (loginEntryOptional.isEmpty() || decodedJwt.getIssuedAt().getTime() < loginEntryOptional.get().getLastLogin()) {
            log.error("LoginEntry empty: " + loginEntryOptional.isEmpty());
            log.error("JWT issued at: " + decodedJwt.getIssuedAt());
            throw new UnauthorisedException("Invalid Token", Long.parseLong(decodedJwt.getSubject()));
        }

        return decodedJwt;
    }
}
