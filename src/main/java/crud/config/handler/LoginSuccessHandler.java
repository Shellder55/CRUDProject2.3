package crud.config.handler;

import crud.model.Role;
import org.jboss.logging.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private static final Logger logger = Logger.getLogger(LoginSuccessHandler.class.getName());
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {

        if (authentication.getAuthorities().contains(Role.ADMIN)) {
            httpServletResponse.sendRedirect("/admin");
            logger.info("Авторизация администратора под именем '" + authentication.getName() + "' прошла успешна!");
        } else {
            httpServletResponse.sendRedirect("/users");
            logger.info("Авторизация пользователя под именем '" + authentication.getName() + "' прошла успешна!");
        }

    }
}
