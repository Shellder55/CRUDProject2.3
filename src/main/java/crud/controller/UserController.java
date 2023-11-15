package crud.controller;

import crud.service.UserService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final static Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String myProfile(Model model, Principal principal) {
        try {
            model.addAttribute("user", userService.findUserByLogin(principal.getName()));
            model.addAttribute("principalName", principal.getName());
            logger.info("Загрузка данных пользователя " + principal.getName() + " прошла успешна");
        } catch (Exception exception){
            logger.error("Не удалось загрузить данные пользователя " + principal.getName());
        }
        return "index_user";
        }
    }
