<%-- 
    Document   : Register
    Created on : Nov 8, 2013, 4:13:56 PM
    Author     : Drakthal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>
<html>
    
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSF Login Application in Netbeans</title>
    </head>
    <body>
         
    <f:view>
        <h:form id="RegisterForm">
            <h:panelGrid id="lpg" columns="2">
                
               
                <h:outputText value="Username"/>
                <h:inputText id="username" value="#{Register.loginid}"/>
                <h:outputText value="Password"/>
                <h:inputSecret id="password" value="#{Register.password}"/>
                <h:outputText value="Name"/>
                <h:inputText id="name" value="#{Register.name}"/>
                <h:outputText value="Address"/>
                <h:inputText id="address" value="#{Register.address}"/>
                <h:outputText value=""/>
                <h:commandButton value="Register" action="#{Register.registerUser}"/>
            </h:panelGrid>
        </h:form>
    </f:view>
</body>        
</html>
