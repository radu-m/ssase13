<%-- 
    Document   : Login
    Created on : Nov 8, 2013, 10:41:32 AM
    Author     : Drakthal
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
      <% HttpSession  s1 = pageContext.getSession();%>
    <f:view>
       <h:form id="loginform">
            <h:panelGrid id="lpg" columns="2">
 
                <h:outputText value="User ID"/>
                <h:inputText id="loginid" value="#{JsfLogin.loginid}"/>
                
                <h:outputText value="Password"/>
                <h:inputSecret id="password" value="#{JsfLogin.password}"/>
                <h:outputText value=""/>
                <h:commandButton value="Login" action="#{JsfLogin.CheckValidUser}"/>
                <h:outputText value=""/>
                <h:commandButton value="Register" action="#{JsfLogin.register}"/><p>
                
                    <h:outputText value=""/>
               
                
            </h:panelGrid>
           
           <p>
           <p><p>
               
           </p>
           <c:out value=" The site is protected against the following security threats:"></c:out><p>
           <c:out value="-SQL Injectio"></c:out><p>
           <c:out value="- Command Injection"></c:out><p>
           <c:out value="- Man in the Middle Attacks"></c:out><p>
           <c:out value="- XSS"></c:out><p>
           <c:out value="- Magic URLs and Forged Cookies"></c:out><p>
           <c:out value="- Information Leakage"></c:out><p>
           <c:out value="- Weak Passwords"></c:out><p>
          
        </h:form>
        <h:form  >
             <h:commandButton value="navigate to partners data" action="#{JsfLogin.navigatePartners}"/>
        </h:form>
        
    </f:view>
</body>
</html>
