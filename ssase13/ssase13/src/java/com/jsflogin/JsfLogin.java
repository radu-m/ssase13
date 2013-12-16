package com.jsflogin;

import static com.jsflogin.Register.log;
import java.sql.*;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.BasicConfigurator;

import javax.servlet.*;
import java.io.*;
import java.util.regex.Pattern;
import javax.servlet.http.*;
import javax.faces.bean.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.faces.context.FacesContext;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;


@ManagedBean

@SessionScoped
public class JsfLogin extends HttpServlet{
  
    String loginid;
    String password;
    String searchName;
    
    String ConnectionPath ;// "jdbc:mysql://localhost:3306/demodb";// 
    String ConnectionUser ;
    String ConnectionPW ;
    String javaSQLDriverPath ;
    
    String loggedInName="FALURE";
    String loggedInUserName;
    String loggedInAdress;
    static int loggedInID;

    public static int getLoggedInID() {
        return loggedInID;
        
    }

    public static void setLoggedInID(int loggedInID) {
        JsfLogin.loggedInID = loggedInID;
    }
     public String register(){
         return "Register";
     }
     public String navigatePartners(){
         return "success";
     }
    static Logger log = LogManager.getLogger(JsfLogin.class.getName());
    Register myReg;
    public JsfLogin(){
         
       // log.info("User has entered the slice");
        ConnectionInfo tempConn = new ConnectionInfo();
        ConnectionPath = tempConn.ConnectionPath;
        ConnectionUser = tempConn.ConnectionUser;
        ConnectionPW = tempConn.ConnectionPW;
        javaSQLDriverPath = tempConn.javaSQLDriverPath;
        myReg = new Register();
    }
    public String getLoggedInName() {
        return loggedInName;
    }

    public void setLoggedInName(String loggedInName) {
        this.loggedInName = loggedInName;
    }

    public String getLoggedInUserName() {
        return loggedInUserName;
    }

    public void setLoggedInUserName(String loggedInUserName) {
        this.loggedInUserName = loggedInUserName;
    }

    public String getLoggedInAdress() {
        return loggedInAdress;
    }

    public void setLoggedInAdress(String loggedInAdress) {
        this.loggedInAdress = loggedInAdress;
    }

    public String getLoginid() {
        return loginid;
    }
 
    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }

    public String CheckValidUser(){
        boolean testBool = testValidInput(loginid);
        String returnVal = "userNotFound";
        if(testBool )
        {
            testBool = testValidInput(password);
            if(testBool){
                returnVal = checkValidLogin(loginid, password);
            }
        }
        return returnVal;
        
    }
     public String checkValidLogin(String myUserName, String myPW){
        
        try{
            Class.forName(javaSQLDriverPath);
            Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);
            Statement st=conn.createStatement();
            String query="Select * from User";
            

            ResultSet rs=st.executeQuery(query);
            while(rs.next()){
               // return rs.getString("Username");
                 if(myUserName.equals(rs.getString("Username"))){
                    if(myPW.equals(rs.getString("Password"))){
                        setUserVariables(myUserName);
                        return "success";     
                    }
                    else{
                         return "wrongPassword";                
                    }     
                 }  
                 
                
            }
            
            rs.close();
            st.close();
            conn.close();
           
            return "userNotFound";
            }
            catch(Exception e){
                  
                return e.getMessage();

            }
        
    }
     
    public boolean testValidInput(String inputString){
        boolean resultBoolean = false;
        boolean test = Pattern.matches("\\w*",inputString);
        if(test){
            if(inputString.length() < 40){
                resultBoolean = true;
            }
        }
        return resultBoolean;
    }
    public void setUserVariables(String currentUser){
        loggedInUserName = currentUser;
        
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);  
        
       session.setAttribute("myLoggedInUserName", loggedInUserName);
        
    } 
    public void searchByName(String searchForThis){
         myReg.findIDbyName(searchForThis);
    }
     @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException 
    {       
     //  CheckValidUser();
       //req.getSession().setAttribute("myID", loggedInID);
       log.info("i got into doPost somehow?");
    }
}