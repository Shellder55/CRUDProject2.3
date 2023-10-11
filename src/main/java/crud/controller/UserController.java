package crud.controller;

import crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String myProfile(Model model, Principal principal, Long id){
        model.addAttribute("users", userService.getMyProfile(id));
        model.addAttribute("principalName", principal.getName());
        return "index_user";
    }
}
