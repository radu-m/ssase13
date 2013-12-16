<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ page import="java.util.List,com.jsflogin.stringWithPointer" %>
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
        
 
        <h:form id="forloop">
            
            <h1>Welcome <h:outputText value="#{MyProfile.myUserName}"/>!!</h1>
            <h2>Name: <h:outputText value="#{MyProfile.myName}"/></h2>
            <h3>Address: <h:outputText  value="#{MyProfile.myAdress}"/></h3>
            <c:out value="hugs recieved"/><p>
                
                <h:dataTable id="thisFriendRequests" value="#{MyProfile.huggedBy}" var = "orly">
                <h:column>
                    <h:outputText value="#{orly.pointer}"/>
                    <h:outputText value=": "/>
                    <h:outputText value="#{orly.myName}"/>
                    <h:commandButton value="accept hug" action="#{MyProfile.acceptHug}">
                        <f:setPropertyActionListener target ="#{MyProfile.hugPointer}" value = "#{orly.pointer}"/>
                    </h:commandButton>
                </h:column>
            </h:dataTable>
                
             <c:out value="friend Requests"/><p> 
             <h:dataTable id="thisFriendRequestsyesyes" value="#{MyProfile.friendRequests}" var = "orly">
                <h:column>
                    <h:outputText value="#{orly.pointer}"/>
                    <h:outputText value=": "/>
                    <h:outputText value="#{orly.myName}"/>
                    <h:commandButton value="accept request" action="#{MyProfile.goToAcceptFriendRequest}">
                        <f:setPropertyActionListener target ="#{MyProfile.friendRequestPointer}" value = "#{orly.pointer}"/>
                    </h:commandButton>
                    <h:commandButton  value="decline friend" action="#{MyProfile.goToRemoveFriendRequest}" >
                        <f:setPropertyActionListener target ="#{MyProfile.friendRequestPointer}" value = "#{orly.pointer}"/>
                        
                    </h:commandButton><p>
                </h:column>
            </h:dataTable>
            
                
            <c:out value="friend list"/><p>   
            <h:dataTable id="thisFriendList" value="#{MyProfile.friendList}" var = "orly">
                <h:column>
                    <h:outputText value="#{orly.pointer}"/>
                    <h:outputText value=": "/>
                    <h:outputText value="#{orly.myName}"/>
                    <h:commandButton value="go to friend profile" action="#{MyProfile.goToFriendProfile}">
                        <f:setPropertyActionListener target ="#{MyProfile.friendPointer}" value = "#{orly.pointer}"/>
                    </h:commandButton>
                    <h:commandButton  value="remove friend" action="#{MyProfile.goToRemoveFriend}" >
                        <f:setPropertyActionListener target ="#{MyProfile.friendPointer}" value = "#{orly.pointer}"/>
                        
                    </h:commandButton><p>
                </h:column>
            </h:dataTable>
               
                <c:out value="interest list"/><p>
               
            <h:dataTable id="thisInterestList" value="#{MyProfile.interestsAndPointers}" var = "orly">
                <h:column>
                    <h:outputText value="#{orly.pointer}"/>
                    <h:outputText value=": "/>
                    <h:outputText value="#{orly.stringName}"/>
                    <h:commandButton value="go to interest page" action="#{MyProfile.goToInterestProfile}">
                        <f:setPropertyActionListener target ="#{MyProfile.interestPointer}" value = "#{orly.pointer}"/>
                    </h:commandButton>
                    <h:commandButton  value="remove interest page" action="#{MyProfile.goToRemoveInterest}" >
                        <f:setPropertyActionListener target ="#{MyProfile.interestPointer}" value = "#{orly.pointer}"/>
                        
                    </h:commandButton><p>
                </h:column>
            </h:dataTable>
             
                          
             
        <h:inputText id="addinterest" value="#{MyProfile.newInterest}"/>
        
        <h:commandButton value="add new interest" action="#{MyProfile.addToHasInterestTable}"/> <p>
           
           <h:inputText id="searchForName" value="#{MyProfile.searchForUserByName}"/>
           <h:commandButton value="search for user" action="#{MyProfile.searchForUserAndNavigate}"/> <p>
             <%--   <h:inputText id="search for user" value="#{Register.name}"/> --%>
            </h:form>
            
                
        
    </body>
   </f:view> 
</html>