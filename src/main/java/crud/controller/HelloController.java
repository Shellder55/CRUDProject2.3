package crud.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HelloController {

    @GetMapping(value = "/home")
    public String printWelcome(ModelMap modelMap){
        List<String> messages = new ArrayList<>();
        messages.add("Hello Evgeniy");
        modelMap.addAttribute("messages", messages);
        return "index";
    }
}
