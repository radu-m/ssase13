package com.jsflogin;

import static com.jsflogin.JsfLogin.log;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.BasicConfigurator;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.logging.Level;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;

@SessionScoped
@ManagedBean(name="Register")
public class Register {
    String loginid;
    String password;
    String name;
    String address;
    String loggedInName;
    String loggedInUserName;
    String loggedInAdress;
    
    int loggedInID;
    
    public int getLoggedInID() {
        return loggedInID;
    }

    public void setLoggedInID(int loggedInID) {
        this.loggedInID = loggedInID;
    }
    
    static Logger log = LogManager.getLogger(JsfLogin.class.getName());
    int highestPersonID;
    String ConnectionPath ;// "jdbc:mysql://localhost:3306/demodb";// 
    String ConnectionUser ;
    String ConnectionPW ;
    String javaSQLDriverPath ;
    public Register(){
        ConnectionInfo tempConn = new ConnectionInfo();
        ConnectionPath = tempConn.ConnectionPath;
        ConnectionUser = tempConn.ConnectionUser;
        ConnectionPW = tempConn.ConnectionPW;
        javaSQLDriverPath = tempConn.javaSQLDriverPath;
     
        
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
    public boolean testValidPassword(String inputString){
        boolean resultBoolean = false;
        boolean test = Pattern.matches("\\w*\\d",inputString);
        if(test){
            
            if(inputString.length() < 40){
                if(inputString.length() > 5)
                resultBoolean = true;
            }
        }
        return resultBoolean;
    }
    public String registerUser(){
        String message = "";
        if(loginid != "" && password != "" && name != "" && password != "")
        {
            boolean test = testValidInput(loginid);
            boolean test2 = testValidPassword( password);
            boolean test3 = testValidInput( name);
            boolean test4 = testValidInput( address); //test
            if (test && test2 && test3 && test4){
                //(String newUserName, String newUserPW, String newName, String newAdress)
               
                message = addNewProfile(loginid,password,name,address);
                
                log.info("registered user " + name);
                return message;
            }
            else{
                 log.info("user failed to register");
                return "fail";
                
            }
        }
        return "fail";
        
    }
                        
    public boolean addToPersontable(String newUserName,String newName, String newAdress, int newPersonID){
       
        try{
       //   Class.forName(javaSQLDriverPath);
          Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);  
       
            
            PreparedStatement inserter = (PreparedStatement) conn.prepareStatement("INSERT INTO Person(Username,Name,adress,personID) VALUES(?,?,?,?)");
            inserter.setString(1,newUserName);
            inserter.setString(2,newName);
            inserter.setString(3,newAdress);
            inserter.setInt(4, newPersonID);
            inserter.executeUpdate();;
             conn.close();
            inserter.close();
            return true;
            }
        
            catch(Exception e){
                log.info(e.getMessage() +  "    in addtopersontable");
                return false;

        }
    }
    public boolean addToUserTable(String newUserName,String newPassword, boolean newAdmin){
       
        try{
       //   Class.forName(javaSQLDriverPath);
          Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);  
       
            
            PreparedStatement inserter = (PreparedStatement) conn.prepareStatement("INSERT INTO User(Username,Password,Admin) VALUES(?,?,?)");
            inserter.setString(1,newUserName);
            inserter.setString(2,newPassword);
            inserter.setBoolean(3,newAdmin);
            
            inserter.executeUpdate();;
             conn.close();
            inserter.close();
            return true;
            }
        
            catch(Exception e){
                 log.info(e.getMessage() +  "    in addtousetable");
                return false;
            // do something to catch error

        }
    }
    public void addToRelationTable(int newPersonID1,int newPersonID2, String newRelation){
       
        try{
         // Class.forName(javaSQLDriverPath);
          Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);  
       
            
            PreparedStatement inserter = (PreparedStatement) conn.prepareStatement("INSERT INTO Relation(PersonID1,PersonID2,Relation) VALUES(?,?,?)");
            inserter.setInt(1,newPersonID1);
            inserter.setInt(2,newPersonID2);
            inserter.setString(3,newRelation);
            
            inserter.executeUpdate();;
             conn.close();
            inserter.close();
            }
        
            catch(Exception e){
            // do something to catch error

        }
    }
    public void addToInterestTable(String newName){
       
        try{
        //  Class.forName(javaSQLDriverPath);
          Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);  
       
            
            PreparedStatement inserter = (PreparedStatement) conn.prepareStatement("INSERT INTO Interest(Name) VALUES(?)");
            inserter.setString(1,newName);
           
            inserter.executeUpdate();;
             conn.close();
            inserter.close();
            }
        
            catch(Exception e){
            // do something to catch error

        }
    }
    public String testConnection(){
        try{
        Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);
        
     //  Statement st=conn.createStatement();
         //  String query="SELECT * FROM Person ORDER BY PersonID DESC LIMIT 0, 1";
            


          //  ResultSet rs=st.executeQuery(query);
         conn.close();
        // rs.close();
        return "success";
        }
        catch(Exception e){
        
            return e.getMessage();
        }
    }
    public boolean startUp(){
        highestPersonID = 0;
        try{
            Class.forName(javaSQLDriverPath);
            Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);
            Statement st=conn.createStatement();
            String query="SELECT * FROM Person ORDER BY PersonID DESC LIMIT 0, 1";
            


            ResultSet rs=st.executeQuery(query);
            while(rs.next()){
            highestPersonID=rs.getInt("personID");
           
            
            }
           
            rs.close();
            st.close();
            conn.close();
            return true;
            }
            catch(Exception e){
               
                return false;
            // do something to catch error

            }
    }
    public String addNewProfile(String newUserName, String newUserPW, String newName, String newAdress){
        try{
            
            boolean userNameTaken = checkUserNames(newUserName);
            boolean worked = true;
            
            //addToPersontable("functionUsername","functionName","functionAdress",5);
            if(!userNameTaken){
                testConnection();
                startUp();
                highestPersonID++;
                worked = addToPersontable(newUserName,newName, newAdress,highestPersonID);
            
                //addToUserTable(String newUserName,String newPassword, boolean newAdmin)
                worked = addToUserTable(newUserName,newUserPW, false);
                 if(!worked){
                 return "fail";
                 }
                 else {
                     
                     return "success";
                 }
            // addToHasInterestTable(int newPersonID,String newInterestName)
            }
            else{
                return "user name taken";
            }
            
        
       
        }
            catch(Exception e){
            
                return "fail";

        }
    }
     public void updateUserByUser(String myUserName,String newPassword){
        try{
         //   Class.forName(javaSQLDriverPath);
            Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);
            Statement st=conn.createStatement();
            PreparedStatement inserter = (PreparedStatement) conn.prepareStatement("Update User SET Password=? WHERE Username=?");
            inserter.setString(1, newPassword);
            inserter.setString(2,myUserName);
            
            inserter.executeUpdate();;
            conn.close();
            inserter.close();
            
            }
            catch(Exception e){
            // do something to catch error

            }
        
    }
    public String getAdressByUser(String myUserName){
        String adress = "";
        
        try{
          //  Class.forName(javaSQLDriverPath);
            Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);
            Statement st=conn.createStatement();
           
            PreparedStatement inserter = (PreparedStatement) conn.prepareStatement("Select Adress from Person WHERE Username=?");
            inserter.setString(1, myUserName);
           
            ResultSet rs=inserter.executeQuery();
            while(rs.next()){
                 adress = rs.getString("Adress");
           
            
            }
           
            rs.close();
            st.close();
            conn.close();
            }
            catch(Exception e){
              

            }
        return adress;
        
    }
    public String getNameByUser(String myUserName){
        String myName = "";
        
        try{
          //  Class.forName(javaSQLDriverPath);
            Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);
            Statement st=conn.createStatement();
           
            PreparedStatement inserter = (PreparedStatement) conn.prepareStatement("Select Name from Person WHERE Username=?");
            inserter.setString(1, myUserName);
           
            ResultSet rs=inserter.executeQuery();
            while(rs.next()){
                 myName = rs.getString("Name");
           
            
            }
           
            rs.close();
            st.close();
            conn.close();
            }
            catch(Exception e){
              

            }
        return myName;
        
    }
    public int getIDByUserName(String myUserName){
        int myIDgetIDUserName = 0;
        
        try{
            Class.forName(javaSQLDriverPath);
            Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);
            Statement st=conn.createStatement();
           
            PreparedStatement inserter = (PreparedStatement) conn.prepareStatement("Select Name from Person WHERE Username=?");
            inserter.setString(1, myUserName);
           
            ResultSet rs=inserter.executeQuery();
            while(rs.next()){
                 myIDgetIDUserName = rs.getInt("PersonID");
           
            
            }
           
            rs.close();
            st.close();
            conn.close();
            }
            catch(Exception e){
              log.info(e.getMessage());

            }
        return myIDgetIDUserName;
        
    }
    public void updatePersonByUserName(String myUserName,String newName, String newAdress){
        try{
           // Class.forName(javaSQLDriverPath);
            Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);
            PreparedStatement inserter = (PreparedStatement) conn.prepareStatement("Update Person SET Name=?, adress=? WHERE Username=?");
            inserter.setString(1, newName);
            inserter.setString(2,newAdress);
            inserter.setString(3,myUserName);
            inserter.executeUpdate();
            conn.close();
            inserter.close();
            
            }
            catch(Exception e){
           // do something to catch error
                  
            }
        
    }
    public boolean checkUserNames(String newUserName){
        boolean taken = false;
        try{
            Class.forName(javaSQLDriverPath);
            Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);
            Statement st=conn.createStatement();
            String query="Select * from User";
            


            ResultSet rs=st.executeQuery(query);
            while(rs.next()){
                 if(newUserName.equals(rs.getString("Username"))){
                    System.out.println("taken") ;
                    taken = true;
                    break;
                 }
           
            
            }
           
            rs.close();
            st.close();
            conn.close();
            }
            catch(Exception e){
                  
            // do something to catch error

            }
        return taken;
    }
    public ArrayList findIDbyName(String searchForName){
        // not sure if i should return list?
        int idFound;
        ArrayList foundIDS = new ArrayList();
        
     //   ArrayList<int> foundIDS = new ArrayList<int>();
        try{
            Class.forName(javaSQLDriverPath);
            Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);
            Statement st=conn.createStatement();
            String query="SELECT * FROM Person WHERE Name ="+searchForName;
            


            ResultSet rs=st.executeQuery(query);
            while(rs.next()){
                 foundIDS.add((rs.getInt("PersonID")));
           
            
            }
           
            rs.close();
            st.close();
            conn.close();
            }
            catch(Exception e){
                  
            // do something to catch error

            }
        
        
        return foundIDS;
    }
    
     
    
    public void addToHasInterestTable(int newPersonID,String newInterestName){
       
        try{
       //   Class.forName(javaSQLDriverPath);
          Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);  
       
            
            PreparedStatement inserter = (PreparedStatement) conn.prepareStatement("INSERT INTO HasInterest(PersonID,InterestName) VALUES(?,?)");
            inserter.setInt(1,newPersonID);
            inserter.setString(2,newInterestName);
           
            inserter.executeUpdate();;
             conn.close();
            inserter.close();
            }
        
            catch(Exception e){
                
            // do something to catch error

        }
    }
    public void removeFromHasInterestTable(int newPersonID,String newInterestName){
       
        try{
       //   Class.forName(javaSQLDriverPath);
          Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);  
       
            
            PreparedStatement inserter = (PreparedStatement) conn.prepareStatement("DELETE FROM HasInterest WHERE PersonID=? AND Interestname=? ");
            inserter.setInt(1,newPersonID);
            inserter.setString(2,newInterestName);
           
            inserter.executeUpdate();;
             conn.close();
            inserter.close();
            }
        
            catch(Exception e){
                
            // do something to catch error

        }
    }
    public void connectToDatabase(){
        
        
    }
    public void sendFriendRequest(int myID, int recievingID){
        //done
        
    }
    public void acceptFriendRequest(int acceptedID){
        //done
    }
     public void removeFriendRequest(int myID, int removingID){
         //done
     }
    public void getListOfFriendRequests(int myID){
        // done
    }
    public void getListOfFriends(int personsID){
        //done
    }
    public void sendHug(int myID, int recievingID){
        //done
    }
    public void removeHug(int myID, int recievingID){
        //done
    }
    public void getListOfHugs(int myID){
        //done
    }
    public String CheckValidInput(List<String> Users){
        for(String s : Users)
        {
            if(loginid.equals(s))
            {
                return "fail";
            }
        }
        //
            return "success"; 
    }
 
}