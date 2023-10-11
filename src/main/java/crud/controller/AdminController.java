package crud.controller;

import crud.model.User;
import crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String allUsers(Model model, Principal principal) {
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("principalName", principal.getName());
        return "index_admin";
    }

    @GetMapping("/create")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("user", new User());
        return "create";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("user") User user) {
        userService.saveUpdateUser(user);
        return "redirect:/admin";
    }

    @PutMapping("/{id}/edit")
    public String editUsers(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "create";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteUsers(@PathVariable(value = "id") Long id) {
        User user = userService.deleteUser(id);
        return "redirect:/admin";
    }
}
