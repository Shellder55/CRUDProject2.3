package crud.controller;

import crud.model.User;
import crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "")
    public String allUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "index";
    }

    @GetMapping(value = "/create")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("user", new User());
        return "create";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("user") User user) {
        userService.saveAndUpdate(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String editUsers(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("user", userService.findUser(id));
        return "create";
    }

    @GetMapping("/delete")
    public String deleteUsers(@RequestParam(value = "id") Long id) {
        User user = userService.deleteUser(id);
        return "redirect:/users";
    }
}
