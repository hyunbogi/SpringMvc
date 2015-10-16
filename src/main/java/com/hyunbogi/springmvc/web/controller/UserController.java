package com.hyunbogi.springmvc.web.controller;

import com.hyunbogi.springmvc.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addForm(@ModelAttribute @Valid User user,
                          BindingResult bindingResult) {
        return "user/form";
    }
}
