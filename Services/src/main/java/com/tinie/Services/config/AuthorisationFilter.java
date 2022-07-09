package com.tinie.Services.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinie.Services.responses.UnauthorisedResponse;
import com.tinie.Services.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class AuthorisationFilter extends BasicAuthenticationFilter {

    private final JWTProcessor tokenProcessor;

    public AuthorisationFilter(AuthenticationManager authenticationManager, JWTProcessor tokenProcessor) {
        super(authenticationManager);
        this.tokenProcessor = tokenProcessor;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(Constants.TOKEN_HEADER_KEY);
        if (token == null){
            chain.doFilter(request, response);
            return;
        }

        var subject = getSubject(token, response);

        if (subject != 0L){
            request.setAttribute(Constants.PHONE_NUMBER_KEY, subject);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    subject, null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

    private Long getSubject(String token, HttpServletResponse response) {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        UnauthorisedResponse unauthorisedResponse = new UnauthorisedResponse();
        unauthorisedResponse.setStatus(HttpStatus.UNAUTHORIZED.toString());
        unauthorisedResponse.setCode(HttpStatus.UNAUTHORIZED.value());
        unauthorisedResponse.setTimestamp(new Date().getTime());

        if (StringUtils.hasText(token)){
            try{
                DecodedJWT decodedJWT = tokenProcessor.verifyAndDecodeToken(token);
                return Long.valueOf(decodedJWT.getSubject());
            } catch(Exception e){
                logger.error(e);
                unauthorisedResponse.setMessage("Invalid Token");
            }
        } else unauthorisedResponse.setMessage("Request must be authenticated");

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            objectMapper.writeValue(response.getWriter(), unauthorisedResponse);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return 0L;
    }
}
