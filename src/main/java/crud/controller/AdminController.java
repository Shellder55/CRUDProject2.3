package crud.controller;

import crud.model.Role;
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
        if (principal.getName() != null) {
            model.addAttribute("principalName", principal.getName());
        }
        return "index_admin";
    }

    @GetMapping("/create")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", Role.values());
        return "create";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("user") User user, @RequestParam("rolesChecked") String[] rolesStrArray) {
        userService.saveUpdateUser(user, rolesStrArray);
        return "redirect:/admin";
    }

    @PutMapping("/{id}/edit")
    public String editUsers(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("roles", Role.values());
        return "create";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteUsers(@PathVariable(value = "id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
