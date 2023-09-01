package am.itspace.eduquizrest.sequrity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
/**
 * Custom implementation of the AuthenticationEntryPoint interface for JWT authentication.
 * This class is used to handle unauthorized access to protected resources.
 * When a user attempts to access a protected resource without proper authentication,
 * this class is invoked to send an "Unauthorized" error response to the client.
 */
@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        log.warn("Unauthorized access to protected resource. Request: {}", request.getRequestURI());

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}