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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;
/**
 *
 * @author peddy
 */
@ManagedBean(name="NonFriendProfile")
@RequestScoped
public class NonFriendProfile {
 
    String ConnectionPath ;// "jdbc:mysql://localhost:3306/demodb";// 
    String ConnectionUser ;
    String ConnectionPW ;
    String javaSQLDriverPath ;
    int friendID;
    int myOwnID;
    String myName;
    ArrayList<NameWithID> interestList;
    String FriendRequestStatus ="  yolo";
    String newFriendRequestStatus = "yes sir";
    int interestPointer = 0;

    public int getInterestPointer() {
        return interestPointer;
    }

    public void setInterestPointer(int interestPointer) {
        this.interestPointer = interestPointer;
    }
    
   public String goToInterestProfile(){
        
        return navigateToInterestProfile();
    }
   
    public String navigateToInterestProfile(){
      
        String profileName = interestList.get(interestPointer).myName;
         HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);        
       session.setAttribute("myInterestProfileName", profileName);        
        return "success";    
    }
    public ArrayList<NameWithID> getInterestList() {
        return interestList;
    }

    public void setInterestList(ArrayList<NameWithID> interestList) {
        this.interestList = interestList;
    }

    public String getNewFriendRequestStatus() {
        return newFriendRequestStatus;
    }

    public void setNewFriendRequestStatus(String newFriendRequestStatus) {
        this.newFriendRequestStatus = newFriendRequestStatus;
    }
    
    public int getFriendID() {
        return friendID;
    }

    public void setFriendID(int friendID) {
        this.friendID = friendID;
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    

    public int getMyOwnID() {
        return myOwnID;
    }

    public void setMyOwnID(int myOwnID) {
        this.myOwnID = myOwnID;
    }

    public String getFriendRequestStatus() {
        return FriendRequestStatus;
    }

    public void setFriendRequestStatus(String FriendRequestStatus) {
        this.FriendRequestStatus = FriendRequestStatus;
    }

   
    public void sendFriendRequest(){
        try{
       //   Class.forName(javaSQLDriverPath);
          Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);  
       
            
            PreparedStatement inserter = (PreparedStatement) conn.prepareStatement("INSERT INTO Relation(PersonID1,PersonID2,Relation) VALUES(?,?,?)");
            inserter.setInt(1,myOwnID);
            inserter.setInt(2,friendID);
            inserter.setString(3,"friendRequest");
            inserter.executeUpdate();;
            inserter.setInt(1,friendID);
            inserter.setInt(2,myOwnID);
            inserter.setString(3,"pending");
            inserter.executeUpdate();;
             conn.close();
            inserter.close();
            }
        
            catch(Exception e){
                
            log.info(e.getMessage());

        }
        
    }
    
    public NonFriendProfile(){
     
        interestList = new ArrayList<NameWithID>();
         
        ConnectionInfo tempConn = new ConnectionInfo();
        
        ConnectionPath = tempConn.ConnectionPath;
        ConnectionUser = tempConn.ConnectionUser;
        ConnectionPW = tempConn.ConnectionPW;
        javaSQLDriverPath = tempConn.javaSQLDriverPath;
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);  
        
       
       
       friendID= Integer.parseInt(session.getAttribute("nonFriendIDSession").toString());  
       myOwnID= Integer.parseInt(session.getAttribute("myLoggedInID").toString());
       myName = getNameByID(friendID);
       getListOfHasInterests(friendID);
    //   FriendRequestStatus = checkFriendRequestStatus();
       newFriendRequestStatus = checkFriendRequestStatus();
       
       
    }
    public String getNameByID(int thisID){
        String myName = "";
        
        try{
            Class.forName(javaSQLDriverPath);
            Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);
            Statement st=conn.createStatement();
           
            PreparedStatement inserter = (PreparedStatement) conn.prepareStatement("Select Name from Person WHERE PersonID=?");
            inserter.setInt(1, thisID);
           
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
    public String checkFriendRequestStatus(){
        String requestStatus = "";
        String foundRelation ="";
        try{
            Class.forName(javaSQLDriverPath);
            Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);
            Statement st=conn.createStatement();
            PreparedStatement inserter = (PreparedStatement) conn.prepareStatement("Select * from Relation Where PersonID1=? AND PersonID2=?");
            inserter.setInt(1, friendID);
            inserter.setInt(2,myOwnID);
            

            ResultSet rs=inserter.executeQuery();
            
            while(rs.next()){
                
                foundRelation = rs.getString("Relation");
                if(foundRelation.equals("pending")){
                    requestStatus = "waiting for friend request response";
                }
            }
            
            rs.close();
            st.close();
            conn.close();
            }
            catch(Exception e){
                log.info(e.getMessage());
            }
        return requestStatus;
    }
    public void getListOfHasInterests(int thisID){
        interestList = new ArrayList<NameWithID>();
        try{
            Class.forName(javaSQLDriverPath);
            Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);
            Statement st=conn.createStatement();
            PreparedStatement inserter = (PreparedStatement) conn.prepareStatement("Select * from HasInterest Where PersonID=?");
            inserter.setInt(1, thisID);
            ResultSet rs=inserter.executeQuery();
            int thisInterestPointer = 0;
            while(rs.next()){
                NameWithID tempInterest = new NameWithID();
                tempInterest.myName = rs.getString("InterestName");
                tempInterest.pointer = thisInterestPointer;
                
                interestList.add(tempInterest);         
                thisInterestPointer ++;
            }
           
            rs.close();
            st.close();
            conn.close();
            }
            catch(Exception e){
            log.info(e.getMessage());

            }
       // return friendRequestIDS;
    } 
   
}
