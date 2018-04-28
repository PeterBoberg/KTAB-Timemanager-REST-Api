package se.karingotrafiken.timemanager.rest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import se.karingotrafiken.timemanager.rest.dto.stored.ErrorMessageDTO;
import se.karingotrafiken.timemanager.rest.utils.GensonUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTTokenFilter extends GenericFilterBean {

    @Autowired
    private JWTTokenProvider tokenProvider;

    @Override

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        String token = tokenProvider.resolveToken((HttpServletRequest) req);
        if (token != null) {
            // Token exists, validate and confirm
            if (tokenProvider.isValidateToken(token)) {
                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                String response = GensonUtils.dateFormatedGenson().serialize(new ErrorMessageDTO(ErrorMessageDTO.ErrorCode.INVALID_TOKEN, "Token invalid or expired"));
                HttpServletResponse httpResp = (HttpServletResponse) resp;
                httpResp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                httpResp.setContentType(MediaType.APPLICATION_JSON_VALUE);
                httpResp.getOutputStream().write(response.getBytes());
                return;
            }
        }
        filterChain.doFilter(req, resp);
    }
}
