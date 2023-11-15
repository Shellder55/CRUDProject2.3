package crud.controller;

import crud.model.Role;
import crud.model.User;
import crud.service.UserService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final static Logger logger = Logger.getLogger(AdminController.class.getName());

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String allUsers(Model model, Principal principal) {
        try {
            model.addAttribute("users", userService.getUsers());
            model.addAttribute("principalName", principal.getName());
            logger.info("Выведены все пользователи на странице по url '/admin'");
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.error("По каким то причинам не были выведены все пользователи");
        }
        return "index_admin";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable(value = "id") Long id, Model model, Principal principal) {
        try {
            model.addAttribute("getUser", userService.getProfileUser(id));
            model.addAttribute("principalName", principal.getName());
            logger.info("'" + principal.getName() + "' перешел на страницу пользователя. ID пользователя: " + id);
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.error("'" + principal.getName() + "' не смог перейти на страницу пользователя. ID пользователя: " + id);
        }
        return "profile_user";
    }

    @GetMapping("/create")
    public String newUser(@ModelAttribute("user") User user, Model model, Principal principal) {
        try {
            model.addAttribute("user", new User());
            model.addAttribute("roles", Role.values());
            logger.info("'" + principal.getName() + "' перешел на страницу создания пользователей");
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.error("'" + principal.getName() + "' не смог перейти на страницу создания пользователей");
        }
        return "create";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("user") User user,
                          @RequestParam("rolesChecked") String[] rolesStrArray,
                          Principal principal) {
        try {
            userService.saveUpdateUser(user, rolesStrArray, principal);
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.info("'" + principal.getName() + "' не смог добавить пользователя");
        }
        return "redirect:/admin";
    }

    @PutMapping("/{id}/edit")
    public String editUsers(@PathVariable(value = "id") Long id, Model model, Principal principal) {
        try {
            model.addAttribute("user", userService.findUserById(id));
            model.addAttribute("roles", Role.values());
            logger.info("'" + principal.getName() + "' перешел на страницу редактирования данных у пользователя. ID: " + id);
        } catch (Exception exception){
            exception.printStackTrace();
            logger.error("'" + principal.getName() + " не смог перейти на страницу для редактирования пользователей");
        }
        return "create";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteUsers(@PathVariable(value = "id") Long id, Principal principal) {
        try{
        userService.deleteUser(id);
        logger.info("'" + principal.getName() + "' удалил пользователя. ID: " + id);
        } catch (Exception exception){
            exception.printStackTrace();
            logger.error("'" + principal.getName() + "' не смог удалить пользователя. ID: " + id);
        }
        return "redirect:/admin";
    }
}
