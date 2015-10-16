<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        .errorMessage {
            border: 2px solid red;
        }
    </style>
    <title></title>
</head>
<body>
<p>
    <form:form modelAttribute="user">
        <form:label path="name" cssErrorClass="errorMessage">Name</form:label> :
        <form:input path="name" size="30"/>
        <form:errors path="name" cssClass="errorMessage"/>
    </form:form>
</p>
</body>
</html>
