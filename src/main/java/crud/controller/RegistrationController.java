package crud.controller;

import crud.model.Role;
import crud.model.User;
import crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@Controller
@RequestMapping("/users")
public class RegistrationController {
    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String registration(@ModelAttribute ("userForm") User user ,Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping("/login")
    public String addUser(Set<Role> roles, @ModelAttribute("userForm") @Valid User userForm, @RequestParam(value = "error", required = false) String error, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", error != null);
            return "registration";
        }
        userService.findRoles(roles);
        return "redirect:/users";
    }
}
