package com.hyunbogi.springmvc.controller;

import com.hyunbogi.springmvc.HelloSpring;
import com.hyunbogi.springmvc.testutil.AbstractDispatcherServletTest;
import com.hyunbogi.springmvc.testutil.ConfigurableDispatcherServlet;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HelloControllerTest extends AbstractDispatcherServletTest {
    @Test
    public void servletHelloController() throws ServletException, IOException {
        ConfigurableDispatcherServlet servlet = new ConfigurableDispatcherServlet();
        servlet.setLocations("/test-spring-servlet.xml");
        servlet.setClasses(HelloSpring.class);
        servlet.init(new MockServletConfig());

        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/hello");
        request.addParameter("name", "Spring");
        MockHttpServletResponse response = new MockHttpServletResponse();

        servlet.service(request, response);

        ModelAndView modelAndView = servlet.getModelAndView();
        assertThat(modelAndView.getViewName(), is("/WEB-INF/page/hello.jsp"));
        assertThat(modelAndView.getModel().get("message"), is("Hello Spring"));
    }

    @Test
    public void simpleHelloController() throws Exception {
        ApplicationContext context = new GenericXmlApplicationContext(
                "/test-spring-servlet.xml", "/test-applicationContext.xml"
        );
        HelloController helloController = context.getBean(HelloController.class);

        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/hello");
        request.addParameter("name", "Spring");
        MockHttpServletResponse response = new MockHttpServletResponse();

        ModelAndView modelAndView = helloController.handleRequest(request, response);
        assertThat(modelAndView.getViewName(), is("/WEB-INF/page/hello.jsp"));
        assertThat(modelAndView.getModel().get("message"), is("Hello Spring"));
    }

    @Test
    public void helloController() throws ServletException, IOException {
        ModelAndView modelAndView = setLocation("/test-spring-servlet.xml")
                .setClasses(HelloSpring.class)
                .initRequest("/hello", RequestMethod.GET)
                .addParameter("name", "Spring")
                .runService()
                .getModelAndView();

        assertThat(modelAndView.getViewName(), is("/WEB-INF/page/hello.jsp"));
        assertThat(modelAndView.getModel().get("message"), is("Hello Spring"));
    }
}
