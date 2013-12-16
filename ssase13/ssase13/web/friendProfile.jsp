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
        <title>friends profile</title>
    </head>
    <body>
        
 
        <h:form id="LoginForm">
            
           
            <h2>Name: <h:outputText value="#{friendProfile.myName}"/></h2>
            <h3>Address: <h:outputText  value="#{friendProfile.myAdress}"/></h3>
             
            <c:out value="send hug"/><h:commandButton value ="send hug" action="#{friendProfile.goToSendHug}"/>
            
            <c:out value="friend list"/><p>    
                <h:dataTable id="thisfriendTable" value="#{friendProfile.friendList}" var = "orly">
                <h:column>
                    <h:outputText value="#{orly.pointer}"/>
                    <h:outputText value=": "/>
                    <h:outputText value="#{orly.myName}"/>
                    <h:commandButton value="go to friend profile" action="#{friendProfile.goToProfile}">
                        <f:setPropertyActionListener target ="#{friendProfile.friendPointer}" value = "#{orly.pointer}"/>
                    </h:commandButton><p>
                    
                </h:column>
            </h:dataTable>
                <c:out value="interest list"/><p>
            <h:dataTable id="thisinterest" value="#{friendProfile.interestList}" var = "orly">
                <h:column>
                    <h:outputText value="#{orly.pointer}"/>
                    <h:outputText value=": "/>
                    <h:outputText value="#{orly.myName}"/>
                    <h:commandButton value="go to interest page" action="#{friendProfile.goToInterestProfile}">
                        <f:setPropertyActionListener target ="#{friendProfile.interestPointer}" value = "#{orly.pointer}"/>
                    </h:commandButton><p>
                    
                </h:column>
            </h:dataTable>
       
            </h:form>
            
                
        
    </body>
   </f:view> 
</html>