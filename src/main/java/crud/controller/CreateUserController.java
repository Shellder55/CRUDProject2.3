//package crud.controller;
//
//import crud.dao.UserRepository;
//import crud.model.User;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//public class CreateUserController {
//    private UserRepository userRepository;
//
//    @GetMapping(value = "/create")
//    public String addUser(Model model) {
//        return "create";
//    }
//
//    @PostMapping
//    public String addUserPost(@RequestParam("name") String name,
//                              @RequestParam("surname") String surname,
//                              @RequestParam("gender") String gender,
//                              @RequestParam("age") int age, Model model) {
//        User user = new User(name, surname, gender, age);
//        user.setName(name);
//        user.setSurname(surname);
//        user.setGender(gender);
//        user.setAge(age);
//
//        model.addAttribute("user", user);
//        return "redirect:/home";
//    }
//}