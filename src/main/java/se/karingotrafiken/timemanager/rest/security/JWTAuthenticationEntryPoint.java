package se.karingotrafiken.timemanager.rest.security;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import se.karingotrafiken.timemanager.rest.dto.stored.ErrorMessageDTO;
import se.karingotrafiken.timemanager.rest.utils.GensonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/*
* This class gets called when user tries to access a resource without having provided an access token
* at all
* */
@Component
@ControllerAdvice
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws IOException {
        resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        resp.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String errorMessage;
        if (e.getCause() != null)
            errorMessage = e.getCause().getMessage();
        else
            errorMessage = e.getMessage();

        String body = GensonUtils.dateFormatedGenson().serialize(new ErrorMessageDTO(ErrorMessageDTO.ErrorCode.AUTHENTICATION_FAILURE, errorMessage));
        resp.getOutputStream().write(body.getBytes());
    }
}
