package com.cloud.config.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Project: ConfigServer
 * Module Desc:com.juntu.config.controller
 * User: zjprevenge
 * Date: 2016/11/21
 * Time: 14:54
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @RequestMapping("/login")
    public String login(String name, String password) {
        return "";
    }

    @RequestMapping("/register")
    public String register(String name, String password) {
        return null;
    }

    @RequestMapping("/logout")
    public String logout() {
        return null;
    }
}
