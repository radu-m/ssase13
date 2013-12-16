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
import javax.faces.bean.SessionScoped;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
/**
 *
 * @author peddy
 */
@RequestScoped
@ManagedBean(name="friendProfile")
public class friendProfile {
    String myUserName="Jonatan";
    String friendName="testName";
    String friendAddress;
    ArrayList listOfNames; 
    boolean HugSent;
    public String testVAL;
    String ConnectionPath ;// "jdbc:mysql://localhost:3306/demodb";// 
    String ConnectionUser ;
    String ConnectionPW ;
    String javaSQLDriverPath ;
    int friendID;
    int myOwnID;
    String myName,myAdress;
    int interestPointer = 0;
    int friendPointer = 0; 
    ArrayList<NameWithID> friendList;
    ArrayList<NameWithID> interestList;

    public int getFriendPointer() {
        return friendPointer;
    }

    public void setFriendPointer(int friendPointer) {
        this.friendPointer = friendPointer;
    }

    public int getInterestPointer() {
        return interestPointer;
    }

    public void setInterestPointer(int interestPointer) {
        this.interestPointer = interestPointer;
    }

    public int getFriendID() {
        return friendID;
    }

    public void setFriendID(int friendID) {
        this.friendID = friendID;
    }
    public friendProfile(){
         friendList = new ArrayList<NameWithID>();
          interestList = new ArrayList<NameWithID>();
         
        ConnectionInfo tempConn = new ConnectionInfo();
        
        ConnectionPath = tempConn.ConnectionPath;
        ConnectionUser = tempConn.ConnectionUser;
        ConnectionPW = tempConn.ConnectionPW;
        javaSQLDriverPath = tempConn.javaSQLDriverPath;
       HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);  
        
       
       
       myUserName = (String) session.getAttribute("myLoggedInUserName");
       myOwnID = getIDByUserName(myUserName);
       
       friendID= Integer.parseInt(session.getAttribute("friendsIDSession").toString());
       
       myName = getNameByID(friendID);
       myAdress = getAdressByID(friendID);
       getListOfHasInterests(friendID);
       
       getListOfFriends(friendID);
       
    }

    
    public ArrayList<NameWithID> getInterestList() {
        return interestList;
    }

    public void setInterestList(ArrayList<NameWithID> interestList) {
        this.interestList = interestList;
    }

    public String getMyUserName() {
        return myUserName;
    }

    public void setMyUserName(String myUserName) {
        this.myUserName = myUserName;
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public String getMyAdress() {
        return myAdress;
    }

    public void setMyAdress(String myAdress) {
        this.myAdress = myAdress;
    }

    public ArrayList<NameWithID> getFriendList() {
        return friendList;
    }

    public void setFriendList(ArrayList<NameWithID> friendList) {
        this.friendList = friendList;
    }

    
   
    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendAddress() {
        return friendAddress;
    }

    public void setFriendAddress(String friendAddress) {
        this.friendAddress = friendAddress;
    }

    public ArrayList getListOfNames() {
        return listOfNames;
    }

    public void setListOfNames(ArrayList listOfNames) {
        this.listOfNames = listOfNames;
    }

    public boolean isHugSent() {
        return HugSent;
    }

    public void setHugSent(boolean HugSent) {
        this.HugSent = HugSent;
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
    public void getListOfFriends(int thisID){
        friendList = new ArrayList<NameWithID>();
        try{
            Class.forName(javaSQLDriverPath);
            Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);
            Statement st=conn.createStatement();
            PreparedStatement inserter = (PreparedStatement) conn.prepareStatement("Select * from Relation Where PersonID1=? AND Relation=?");
            inserter.setInt(1, thisID);
            inserter.setString(2, "friends");


            ResultSet rs=inserter.executeQuery();
            int y = 0;
            while(rs.next()){
                int FriendsIDInt = rs.getInt("PersonID2");
                 NameWithID tempFriend = new NameWithID();
                 tempFriend.setMyID(FriendsIDInt);
                 tempFriend.pointer = y;
                 friendList.add(tempFriend);
                 y++;
            }
            for(int i =0;i < friendList.size();i++){
                friendList.get(i).setMyName(getNameByID(friendList.get(i).myID));
              
            }
            
            rs.close();
            st.close();
            conn.close();
            }
            catch(Exception e){
                log.info(e.getMessage());
            }
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
            int y = 0;
            while(rs.next()){
                NameWithID tempInterest = new NameWithID();
                tempInterest.myName =rs.getString("InterestName");
                tempInterest.pointer = y;
                interestList.add(tempInterest);
                
                
                y++;
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
    public String goToInterestProfile(){
        
        return navigateToInterestProfile();
    }
   
    public String navigateToInterestProfile(){
      
        String profileName = interestList.get(interestPointer).myName;
         HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);        
       session.setAttribute("myInterestProfileName", profileName);        
        return "success";    
    }
    public String getAdressByID(int thisID){
        String adress = "";
        
        try{
          //  Class.forName(javaSQLDriverPath);
            Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);
            Statement st=conn.createStatement();
           
            PreparedStatement inserter = (PreparedStatement) conn.prepareStatement("Select Adress from Person WHERE PersonID=?");
            inserter.setInt(1, thisID);
           
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
    public String goToProfile(){
        log.info("SHOuld try to navigate to friend "+friendList.get(friendPointer).myID);
        if(myOwnID == friendList.get(friendPointer).myID){
            log.info("myProfile");
            return "myProfile";
        }
        else{
            if(checkIfFriend(friendList.get(friendPointer).myID)){
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);  
                int profilesID = friendList.get(friendPointer).myID;
                String profileIDString = ""+profilesID;
                session.setAttribute("friendsIDSession",profileIDString );
                return "friendProfile";
            }
            else{
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);  
                int profilesID = friendList.get(friendPointer).myID;
                String profileIDString = ""+profilesID;
                session.setAttribute("nonFriendIDSession",profileIDString );
                return "nonFriendProfile";
            }
        } 
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
