package crud.config.handler;

import crud.model.Role;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @SneakyThrows
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) {
        if (authentication.getAuthorities().contains(Role.ADMIN)) {
            httpServletResponse.sendRedirect("/admin");
            log.info("Авторизация администратора под именем '{}' прошла успешна!", authentication.getName());
        } else {
            httpServletResponse.sendRedirect("/users");
            log.info("Авторизация пользователя под именем '{}' прошла успешна!", authentication.getName());
        }
    }
}
