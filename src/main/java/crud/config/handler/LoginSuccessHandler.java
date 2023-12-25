package crud.config.handler;

import crud.model.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private static final Logger logger = LoggerFactory.getLogger(LoginSuccessHandler.class.getName());
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {

        if (authentication.getAuthorities().contains(Role.ADMIN)) {
            httpServletResponse.sendRedirect("/admin");
            logger.info("Авторизация администратора под именем '{}' прошла успешна!", authentication.getName());
        } else {
            httpServletResponse.sendRedirect("/users");
            logger.info("Авторизация пользователя под именем '{}' прошла успешна!", authentication.getName());
        }

    }
}
