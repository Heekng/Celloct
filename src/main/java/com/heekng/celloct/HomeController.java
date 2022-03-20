package com.heekng.celloct;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String home(@RequestParam("name") String name, Model model) {
        if (name != null) {
            model.addAttribute("name", name);
        }

        return "home";
    }
}
