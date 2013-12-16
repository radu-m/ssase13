/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsflogin;



import static com.jsflogin.JsfLogin.loggedInID;
import static com.jsflogin.Register.log;
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
import java.util.Map;
import java.util.regex.Pattern;
import javax.faces.bean.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;


/**
 *
 * @author peddy
 */
@ManagedBean(name="SearchByName")
@RequestScoped
public class SearchByName {
    String ConnectionPath ;// "jdbc:mysql://localhost:3306/demodb";// 
    String ConnectionUser ;
    String ConnectionPW ;
    String javaSQLDriverPath ;
    String searchedName;
    int searchedPointer;
    int myOwnID;
    ArrayList<NameWithID> resultList;

    public int getSearchedPointer() {
        return searchedPointer;
    }

    public void setSearchedPointer(int searchedPointer) {
        this.searchedPointer = searchedPointer;
    }

    public String getSearchedName() {
        return searchedName;
    }

    public void setSearchedName(String searchedName) {
        this.searchedName = searchedName;
    }

    public ArrayList<NameWithID> getResultList() {
        return resultList;
    }

    public void setResultList(ArrayList<NameWithID> resultList) {
        this.resultList = resultList;
    }
    public SearchByName(){
        
        ConnectionInfo tempConn = new ConnectionInfo();
        ConnectionPath = tempConn.ConnectionPath;
        ConnectionUser = tempConn.ConnectionUser;
        ConnectionPW = tempConn.ConnectionPW;
        javaSQLDriverPath = tempConn.javaSQLDriverPath;
       HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);  
        
       resultList = new ArrayList<NameWithID>();
       searchedName = (String) session.getAttribute("searchByName");
       resultList = searchForNames(searchedName); 
       String myUserName = (String) session.getAttribute("myLoggedInUserName");
       myOwnID = getIDByUserName(myUserName);
       log.info("searched for users named "+searchedName);
        // session.setAttribute("searchByName", searchForUserByName);
    }
    public ArrayList<NameWithID> searchForNames(String searchedFor){
       
        ArrayList<NameWithID> tempIDList = new ArrayList<NameWithID>();
        try{
            Class.forName(javaSQLDriverPath);
            Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);
            Statement st=conn.createStatement();
           
            PreparedStatement inserter = (PreparedStatement) conn.prepareStatement("Select * from Person WHERE Name LIKE ?");
            inserter.setString(1, "%"+searchedFor+"%");
           
            ResultSet rs=inserter.executeQuery();
            int y = 0;
            while(rs.next()){
                NameWithID tempResult = new NameWithID();
                 tempResult.myID = rs.getInt("PersonID");
                 tempResult.myName = rs.getString("Name");
                 tempResult.pointer = y;
                 tempIDList.add(tempResult);
                 y++;
           
            
            }
        
            rs.close();
            st.close();
            conn.close();
            
            }
            catch(Exception e){
            log.info(e.getMessage());

            }
        return tempIDList;
        
    }
    public boolean checkIfFriend(int questionedID){
        boolean aFriend = false;
        try{
            Class.forName(javaSQLDriverPath);
            Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);
            Statement st=conn.createStatement();
            PreparedStatement inserter = (PreparedStatement) conn.prepareStatement("Select * from Relation Where PersonID1=? AND PersonID2=? AND Relation=?");
            inserter.setInt(1, myOwnID);
            inserter.setInt(2,questionedID);
            inserter.setString(3, "friends");
            

            ResultSet rs=inserter.executeQuery();
            
            while(rs.next()){
                
                aFriend = true;
            }
            
            rs.close();
            st.close();
            conn.close();
            }
            catch(Exception e){
                log.info(e.getMessage());
            }
        return aFriend;
    }
    public String goToProfile(){
        if(myOwnID == resultList.get(searchedPointer).myID){
            return "myProfile";
        }
        else{
            if(checkIfFriend(resultList.get(searchedPointer).myID)){
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);  
                int profilesID = resultList.get(searchedPointer).myID;
                String profileIDString = ""+profilesID;
                session.setAttribute("friendsIDSession",profileIDString );
                return "friendProfile";
            }
            else{
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);  
                int profilesID = resultList.get(searchedPointer).myID;
                String profileIDString = ""+profilesID;
                session.setAttribute("nonFriendIDSession",profileIDString );
                return "nonFriendProfile";
            }
        } 
    }
     public int getIDByUserName(String myUserName){
        int myIDgetIDUserName = 0;
        
        try{
            Class.forName(javaSQLDriverPath);
            Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);
            Statement st=conn.createStatement();
           
            PreparedStatement inserter = (PreparedStatement) conn.prepareStatement("Select * from Person WHERE Username=?");
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
}
