package crud.controller;


import crud.model.User;
import crud.service.UserService;
//import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;


@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/home")
    public String printWelcome(ModelMap modelMap, Model model) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello Evgeniy");
        modelMap.addAttribute("messages", messages);

        List<User> userList = new ArrayList<>();
        userList = userService.getUsers(userList);
        model.addAttribute("userList", userList);
        return "index";
    }

    @GetMapping(value = "/create")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "create";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("user") User user){
        userService.save(user);
        return "redirect:/home";
    }

//    @PostMapping()
//    public String saveUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
//                           RedirectAttributes attributes) {
//        if (bindingResult.hasErrors()) {
//            return "form";
//        }
//
//        userService.save(user);
//        attributes.addFlashAttribute("flashMessage",
//                "User " + user.getName() + " successfully created!");
//        return "redirect:/home";
//    }
}
