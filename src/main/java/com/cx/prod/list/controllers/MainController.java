package com.cx.prod.list.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController implements ErrorController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String react() {
        return "index";
    }

    @RequestMapping(value = "/login/auth", method = RequestMethod.GET)
    public String auth() {
        return "index";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String index() {
        return "redirect:/admin/login";
    }

    @RequestMapping(value = "/admin/login")
    public String adminLogin() {
        return "/admin/login";
    }

    @RequestMapping(value = "error")
    public String error() {
        return "error/error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
