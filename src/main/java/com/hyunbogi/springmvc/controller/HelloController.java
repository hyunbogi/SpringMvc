package com.hyunbogi.springmvc.controller;

import com.hyunbogi.springmvc.view.HelloPdfView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class HelloController implements Controller {
    @Autowired
    private HelloPdfView helloPdfView;

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String name = request.getParameter("name");

        Map<String, Object> model = new HashMap<>();
        model.put("message", "Hello " + name);

        return new ModelAndView(helloPdfView, model);
    }
}
