<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%-- 
    Document   : Success
    Created on : Nov 8, 2013, 10:42:33 AM
    Author     : Drakthal
--%>

<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
 
<html>
  <f:view>
      
   

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSF Successfull login</title>
    </head>
    <body>
        
 
        <h:form id="LoginForm">
            
            <h1>interest <h:outputText value="#{interestPage.interestName}"/>!!</h1>
           <%-- <h2>interest<h:outputText value="#{MyProfile.myName}"/></h2>
            <h3>Address: <h:outputText  value="#{MyProfile.myAdress}"/></h3>--%>
            <h:dataTable id="thisPeopleList" value="#{interestPage.peopleList}" var = "orly">
                <h:column>
                    <h:outputText value="#{orly.pointer}"/>
                    <h:outputText value=": "/>
                    <h:outputText value="#{orly.myName}"/>
                    <h:commandButton value="go to profile" action="#{interestPage.goToProfile}">
                        <f:setPropertyActionListener target ="#{interestPage.personPointer}" value = "#{orly.pointer}"/>
                    </h:commandButton><p>
                    
                </h:column>
            </h:dataTable>
           
                
            
            </h:form>
            
                
        
    </body>
   </f:view> 
</html>