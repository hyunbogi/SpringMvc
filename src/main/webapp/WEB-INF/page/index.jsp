<%@ page import="org.springframework.context.ConfigurableApplicationContext" %>
<%@ page import="org.springframework.web.servlet.FrameworkServlet" %>
<%@ page import="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping" %>
<%@ page import="java.util.Map" %>
<%@ page import="org.springframework.web.servlet.mvc.method.RequestMappingInfo" %>
<%@ page import="org.springframework.web.method.HandlerMethod" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<%
    ConfigurableApplicationContext context = (ConfigurableApplicationContext)
            request.getSession()
                    .getServletContext()
                    .getAttribute(FrameworkServlet.SERVLET_CONTEXT_PREFIX + "spring");
    RequestMappingHandlerMapping rmhm = context.getBean(RequestMappingHandlerMapping.class);

    for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : rmhm.getHandlerMethods().entrySet()) {
        out.println(entry.getKey() + " => " + entry.getValue() + "<br>");
    }
%>
</body>
</html>
