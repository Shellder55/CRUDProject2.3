package crud.controller;

import crud.model.Role;
import crud.model.User;
import crud.service.UserService;
import crud.service.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
@Api
public class AdminController {
    private final UserService userService;
    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class.getName());

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    @ApiOperation("Получение списка всех пользователей")
    public String allUsers(Model model, Principal principal) {
        logger.info("От '{}', получен запрос на загрузку всех пользователей.", principal.getName());
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("principalName", principal.getName());
        return "index_admin";
    }

    @GetMapping("/statistics")
    @ApiOperation("Статистика")
    public String statistic(Model model){
        model.addAttribute("stats", userService.getUserForStatistic());
        return "statistics_for_users";
    }

    @GetMapping("/{id}")
    @ApiOperation("Получение страницы пользователя по ID")
    public String getUser(@PathVariable(value = "id") Long id, Model model, Principal principal) {
        logger.info("От '{}', получен запрос на просмотр пользователя. ID пользователя: {}", principal.getName(), id);
        model.addAttribute("getUser", userService.getProfileUser(id));
        model.addAttribute("principalName", principal.getName());
        return "profile_user";
    }

    @GetMapping("/create")
    @ApiOperation("Получение запроса для добавления пользователя")
    public String newUser(@ModelAttribute("user") User user, Model model, Principal principal) {
        logger.info("От '{}', получен запрос на создание пользователя", principal.getName());
        model.addAttribute("user", new User());
        model.addAttribute("roles", Role.values());
        return "create";
    }

    @PostMapping()
    @ApiOperation("Добавление пользователя")
    public String addUser(@ModelAttribute("user") User user,
                          @RequestParam("rolesChecked") String[] rolesStrArray,
                          Principal principal) throws Exception {
        logger.info("От '{}', получен запрос на пользователя. ID пользователя: {}", principal.getName(), user.getId());
        userService.saveUpdateUser(user, rolesStrArray, principal);
        return "redirect:/admin";
    }

    @PutMapping("/{id}/edit")
    @ApiOperation("Изменение данных пользователя")
    public String editUsers(@PathVariable(value = "id") Long id, Model model, Principal principal) {
        logger.info("От '{}', получен запрос на редактирование пользователя. ID пользователя: {}", principal.getName(), id);
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("roles", Role.values());
        return "create";
    }

    @DeleteMapping("/{id}/delete")
    @ApiOperation("Удаление пользователя")
    public String deleteUsers(@PathVariable(value = "id") Long id, Principal principal) {
        logger.info("От '{}', получен запрос на удаление пользователя. ID пользователя: {}", principal.getName(), id);
        userService.deleteUser(id, principal);
        return "redirect:/admin";
    }
}
