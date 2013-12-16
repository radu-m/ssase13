package com.jsflogin;



import static com.jsflogin.JsfLogin.loggedInID;
import static com.jsflogin.Register.log;
import com.jsflogin.URLConnectionReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;
import java.util.HashMap; 
import com.jsflogin.NameWithID;
import com.jsflogin.stringWithPointer;
import java.util.Map;
import java.util.regex.Pattern;
import javax.faces.bean.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


import javax.net.ssl.HttpsURLConnection;
import java.net.*;
import java.io.*;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author peddy
 */
@ManagedBean(name="PartnersData")
@SessionScoped
public  class PartnersData {
    
    String users;
    URLConnectionReader u1 = new URLConnectionReader();
    
    String myPath = "http://192.237.204.46/ssase13/webresources/service/requestGet?getAllusers";
    public PartnersData(){
    try{
        sendPost();
    } 
    catch(Exception E){
        
    }
    
        log.info("in partners data: ");
       log.info(users);
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }
 
    public void testConnect() throws Exception{
        log.info("we acttually get in here");
       System.out.println("yes this happens");
        String  myPathForRest ="http://192.237.204.46/ssase13/webresources/service/requestGet?getAllusers";

        URL oracle = new URL(myPathForRest);
        
        URLConnection yc = oracle.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                                    yc.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null){ 
           // System.out.println(inputLine);
            users = inputLine;
        }
        in.close();
    }
     
    public void sendPost() throws Exception{
        URL obj = new URL(myPath);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("content","test");
        con.setDoOutput(true);
        
        BufferedReader in = new BufferedReader(new InputStreamReader(
                                    con.getInputStream()));
        
        String inputLine;
        while ((inputLine = in.readLine()) != null){ 
           // System.out.println(inputLine);
            users = inputLine;
        }
        in.close();
    }
 
}

