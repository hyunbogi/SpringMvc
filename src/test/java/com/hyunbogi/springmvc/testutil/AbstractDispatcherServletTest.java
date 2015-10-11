package com.hyunbogi.springmvc.testutil;

import org.junit.After;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AbstractDispatcherServletTest implements AfterRunService {
    protected MockHttpServletRequest request;
    protected MockHttpServletResponse response;
    protected MockServletConfig config = new MockServletConfig("spring");
    protected MockHttpSession session;

    private ConfigurableDispatcherServlet dispatcherServlet;
    private Class<?>[] classes;
    private String[] locations;
    private String servletPath;

    @After
    public void closeServletContext() {
        if (dispatcherServlet != null) {
            ((ConfigurableApplicationContext) dispatcherServlet.getWebApplicationContext()).close();
        }
    }

    public AbstractDispatcherServletTest setLocation(String... locations) {
        this.locations = locations;
        return this;
    }

    public AbstractDispatcherServletTest setClasses(Class<?>... classes) {
        this.classes = classes;
        return this;
    }

    public AbstractDispatcherServletTest setServletPath(String servletPath) {
        if (request == null) {
            this.servletPath = servletPath;
        } else {
            request.setServletPath(servletPath);
        }

        return this;
    }

    public AbstractDispatcherServletTest initRequest(String requestUri, String method) {
        request = new MockHttpServletRequest(method, requestUri);
        response = new MockHttpServletResponse();
        if (servletPath != null) {
            setServletPath(servletPath);
        }

        return this;
    }

    public AbstractDispatcherServletTest initRequest(String requestUri, RequestMethod method) {
        initRequest(requestUri, method.toString());
        return this;
    }

    public AbstractDispatcherServletTest initRequest(String requestUri) {
        initRequest(requestUri, RequestMethod.GET);
        return this;
    }

    public AbstractDispatcherServletTest addParameter(String name, String value) {
        if (request == null) {
            throw new IllegalStateException("Request is not initialized");
        }
        request.addParameter(name, value);

        return this;
    }

    public AbstractDispatcherServletTest buildDispatcherServlet() throws ServletException {
        if (classes == null && locations == null) {
            throw new IllegalStateException("Must set class or location");
        }

        dispatcherServlet = new ConfigurableDispatcherServlet();
        dispatcherServlet.setClasses(classes);
        dispatcherServlet.setLocations(locations);
        dispatcherServlet.init(config);

        return this;
    }

    public AfterRunService runService() throws ServletException, IOException {
        if (dispatcherServlet == null) {
            buildDispatcherServlet();
        }
        if (request == null) {
            throw new IllegalStateException("Request is not initialized");
        }

        dispatcherServlet.service(request, response);

        return this;
    }

    public AfterRunService runService(String requestUri) throws ServletException, IOException {
        initRequest(requestUri);
        runService();

        return this;
    }

    @Override
    public String getContentAsString() throws UnsupportedEncodingException {
        return response.getContentAsString();
    }

    @Override
    public WebApplicationContext getContext() {
        if (dispatcherServlet == null) {
            throw new IllegalStateException("DispatcherServlet is not initialized");
        }

        return dispatcherServlet.getWebApplicationContext();
    }

    @Override
    public <T> T getBean(Class<T> beanType) {
        if (dispatcherServlet == null) {
            throw new IllegalStateException("DispatcherServlet is not initialized");
        }

        return getContext().getBean(beanType);
    }

    @Override
    public ModelAndView getModelAndView() {
        return dispatcherServlet.getModelAndView();
    }

    @Override
    public AfterRunService assertViewName(String viewName) {
        assertThat(getModelAndView().getViewName(), is(viewName));
        return this;
    }

    @Override
    public AfterRunService assertModel(String name, Object value) {
        assertThat(getModelAndView().getModel().get(name), is(value));
        return this;
    }
}
