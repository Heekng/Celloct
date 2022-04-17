package com.heekng.celloct.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/store")
public class StoreController {

    @GetMapping("/create")
    public String createStore() {
        log.info("createStore");
        return "/store/createStore";
    }

    @GetMapping("/join")
    public String joinStore() {
        log.info("joinStore");
        return "/store/joinStore";
    }
}
