package com.hyunbogi.springmvc.controller;

import com.hyunbogi.springmvc.HelloSpring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class HelloController implements Controller {
    @Autowired
    private HelloSpring helloSpring;

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String name = request.getParameter("name");
        String message = helloSpring.sayHello(name);

        Map<String, Object> model = new HashMap<>();
        model.put("message", message);

        return new ModelAndView("/WEB-INF/page/hello.jsp", model);
    }
}
