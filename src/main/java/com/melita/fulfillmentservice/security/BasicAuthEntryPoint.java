package com.melita.fulfillmentservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.melita.fulfillmentservice.exception.response.ErrorCode;
import com.melita.fulfillmentservice.exception.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Log4j2
@Component
@RequiredArgsConstructor
public class BasicAuthEntryPoint extends BasicAuthenticationEntryPoint {
    private final ObjectMapper om;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        log.error("Unauthorized user. ErrorCode - {} | Message: {}", ErrorCode.UNAUTHORIZED, e.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("WWW-Authenticate", "Basic realm=" + getRealmName());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        PrintWriter writer = response.getWriter();
        writer.println(om.writeValueAsString(ErrorResponse.builder()
                .withCode(ErrorCode.UNAUTHORIZED)
                .withMessage(e.getMessage())
                .build())
        );
    }

    @Override
    public void afterPropertiesSet() {
        setRealmName("melita");
        super.afterPropertiesSet();
    }

}
