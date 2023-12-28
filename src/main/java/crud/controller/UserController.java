package crud.controller;

import crud.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/users")
@Api
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ApiOperation("Страница пользователя")
    public String myProfile(Model model, Principal principal) {
        model.addAttribute("user", userService.findUserByLogin(principal.getName()));
        model.addAttribute("principalName", principal.getName());
        return "index_user";
    }
}
