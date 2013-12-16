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
import com.jsflogin.stringWithPointer;
import java.util.Map;
import java.util.regex.Pattern;
import javax.faces.bean.*;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author peddy
 */
@ManagedBean(name="MyProfile")
@RequestScoped
public  class MyProfile {
    
    String ConnectionPath ;// "jdbc:mysql://localhost:3306/demodb";// 
    String ConnectionUser ;
    String ConnectionPW ;
    String javaSQLDriverPath ;
    String newInterest;
    String myName;
    String myAdress;
    String myUserName;
    int myID;
    Integer integerMyID;
    int hugPointer = 0;
    String searchForUserByName;
    String testinterestPointer = "failed";
   
    public String getsearchForUserByName() {
        return searchForUserByName;
    }

    public String getTestinterestPointer() {
        return testinterestPointer;
    }

    public void setTestinterestPointer(String testinterestPointer) {
        this.testinterestPointer = testinterestPointer;
    }

    public void setsearchForUserByName(String searchForUserByName) {
        this.searchForUserByName = searchForUserByName;
    }
    public int getFriendRequestPointer() {
        return friendRequestPointer;
    }

    public void setFriendRequestPointer(int friendRequestPointer) {
        this.friendRequestPointer = friendRequestPointer;
    }
    int friendPointer = 0;
    int friendRequestPointer = 0;

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

    public ArrayList<NameWithID> getFriendRequests() {
        return friendRequests;
    }

    public void setFriendRequests(ArrayList<NameWithID> friendRequests) {
        this.friendRequests = friendRequests;
    }
    int interestPointer = 0;
    ArrayList<String> HugNames ;
    ArrayList<Integer> HugIDS;
    int currentIndex;
    ArrayList<NameWithID> huggedBy;
    ArrayList<NameWithID> friendList;
    ArrayList<NameWithID> friendRequests;
    ArrayList<String> interestList;
     ArrayList<stringWithPointer> interestsAndPointers;
    
    public MyProfile(){
        interestsAndPointers = new ArrayList<stringWithPointer>();
        friendList = new ArrayList<NameWithID>();
        friendRequests = new ArrayList<NameWithID>();
        HugIDS = new ArrayList<Integer>();
        HugNames = new ArrayList<String>();
        huggedBy = new ArrayList<NameWithID>();
        interestList = new ArrayList<String>();
        ConnectionInfo tempConn = new ConnectionInfo();
        ConnectionPath = tempConn.ConnectionPath;
        ConnectionUser = tempConn.ConnectionUser;
        ConnectionPW = tempConn.ConnectionPW;
        javaSQLDriverPath = tempConn.javaSQLDriverPath;
       HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);  
        
       
       myUserName = (String) session.getAttribute("myLoggedInUserName");
       myID = getIDByUserName(myUserName);
       String profileIDString = ""+myID;
       session.setAttribute("myLoggedInID",profileIDString );
       myName = getNameByUser(myUserName);
       myAdress = getAdressByUser(myUserName);
       getListOfHasInterests();
       getListOfHugNames();
       getListOfFriends();
       getListOfFriendRequests();
       log.info(myName + "  accessed his own profile");
       
    }

    public ArrayList<stringWithPointer> getInterestsAndPointers() {
        return interestsAndPointers;
    }

    public void setInterestsAndPointers(ArrayList<stringWithPointer> interestsAndPointers) {
        this.interestsAndPointers = interestsAndPointers;
    }
    
    public String getNewInterest() {
        return newInterest;
    }

    public void setNewInterest(String newInterest) {
        this.newInterest = newInterest;
    }
    
    public ArrayList<NameWithID> getFriendList() {
        return friendList;
    }

    public void setFriendList(ArrayList<NameWithID> friendList) {
        this.friendList = friendList;
    }

    public int getHugPointer() {
        return hugPointer;
    }

    public void setHugPointer(int hugPointer) {
        this.hugPointer = hugPointer;
    }
    
    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }
    

    public ArrayList<String> getInterestList() {
        return interestList;
    }

    public void setInterestList(ArrayList<String> interestList) {
        this.interestList = interestList;
    }

    public ArrayList<NameWithID> getHuggedBy() {
        return huggedBy;
    }

    public void setHuggedBy(ArrayList<NameWithID> huggedBy) {
        this.huggedBy = huggedBy;
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

    public String getMyUserName() {
        return myUserName;
    }

    public void setMyUserName(String myUserName) {
        this.myUserName = myUserName;
    }
     public String register(){
         return "register";
     }
    public int getMyID() {
        return myID;
    }

    public void setMyID(int myID) {
        this.myID = myID;
    }
    
    public String getAdressByUser(String myUserName){
        String adress = "";
        
        try{
            Class.forName(javaSQLDriverPath);
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
        String myTempName = "";
        
        try{
            Class.forName(javaSQLDriverPath);
            Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);
            Statement st=conn.createStatement();
           
            PreparedStatement inserter = (PreparedStatement) conn.prepareStatement("Select Name from Person WHERE Username=?");
            inserter.setString(1, myUserName);
           
            ResultSet rs=inserter.executeQuery();
            while(rs.next()){
                 myTempName = rs.getString("Name");
           
            
            }
           
            rs.close();
            st.close();
            conn.close();
            }
            catch(Exception e){


            }
        return myTempName;
        
    }
    public void getListOfHugNames(){
        
         HugIDS = new ArrayList<Integer>();
         HugNames = new ArrayList<String>();
         
        try{
            Class.forName(javaSQLDriverPath);
            Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);
            Statement st=conn.createStatement();
            PreparedStatement inserter = (PreparedStatement) conn.prepareStatement("Select * from Hugs Where PersonID2=?");
            inserter.setInt(1, myID);
            ResultSet rs=inserter.executeQuery();
            
            while(rs.next()){
                //HugIDS.add(rs.getInt("personID1"));
                HugIDS.add(rs.getInt("personID1"));    
                
                
            }
           
            rs.close();
            st.close();
            conn.close();
            
            for(int i = 0; i < HugIDS.size();i++){
                int tempIDInt =(int)HugIDS.get(i);
                String tempNameString = getNameByID(tempIDInt);
                NameWithID tempNameID = new NameWithID();
                tempNameID.setMyName(tempNameString);
                tempNameID.setMyID(tempIDInt);
                tempNameID.pointer = i;
                
                huggedBy.add(tempNameID);
                
                HugNames.add(tempNameString);
                  
            }
            }
            catch(Exception e){
            

            }
       // return friendIDS;
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
    
    public void removeHug(){
        int removingID = huggedBy.get(hugPointer).myID;
        huggedBy.remove(hugPointer);
         try{
       //   Class.forName(javaSQLDriverPath);
          Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);  
       
            
            PreparedStatement inserter = (PreparedStatement) conn.prepareStatement("DELETE FROM Hugs WHERE PersonID1=? AND PersonID2=?");
            inserter.setInt(2,myID);
            inserter.setInt(1,removingID);
            
            inserter.executeUpdate();;
           
             conn.close();
            inserter.close();
            log.info("id: "+myID + " accepted hug from " +removingID);
            }
        
            catch(Exception e){                
                log.info(e.getMessage());
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

    public ArrayList<String> getHugNames() {
        return HugNames;
    }

    public void setHugNames(ArrayList<String> HugNames) {
        this.HugNames = HugNames;
    }

    public ArrayList<Integer> getHugIDS() {
        return HugIDS;
    }

    public void setHugIDS(ArrayList<Integer> HugIDS) {
        this.HugIDS = HugIDS;
    }
    public void getListOfFriends(){
        friendList = new ArrayList<NameWithID>();
        try{
            Class.forName(javaSQLDriverPath);
            Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);
            Statement st=conn.createStatement();
            PreparedStatement inserter = (PreparedStatement) conn.prepareStatement("Select * from Relation Where PersonID1=? AND Relation=?");
            inserter.setInt(1, myID);
            inserter.setString(2, "friends");


            ResultSet rs=inserter.executeQuery();
            int friendPointer = 0;
            while(rs.next()){
                int FriendsIDInt = rs.getInt("PersonID2");
                 NameWithID tempFriend = new NameWithID();
                 tempFriend.setMyID(FriendsIDInt);
                 tempFriend.pointer = friendPointer;
                 friendList.add(tempFriend);       
                 friendPointer++;
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
     public boolean testValidInput(String inputString){
        boolean resultBoolean = false;
        boolean test = Pattern.matches("\\w*",inputString);
        if(test){
            if(inputString.length() < 40){
                if(inputString.length() >1){
                  resultBoolean = true;
                }
            }
        }
        return resultBoolean;
    }
    public void addToHasInterestTable(){
            int newPersonID = myID;
            
            String newInterestName = newInterest;
            boolean test = testValidInput(newInterestName);
             if(!test){
               log.info("user: " +myName+  "added bad input for interest");
             }else{
                try{
                  Class.forName(javaSQLDriverPath);
                  Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);  


                    PreparedStatement inserter = (PreparedStatement) conn.prepareStatement("INSERT INTO HasInterest(PersonID,InterestName) VALUES(?,?)");
                    inserter.setInt(1,newPersonID);
                    inserter.setString(2,newInterestName);

                    inserter.executeUpdate();;
                     conn.close();
                    inserter.close();
                    interestList.add(newInterestName);
                    log.info("user: "+myName + " added interest" + newInterestName);
                    }

                    catch(Exception e){
                    log.info(e.getMessage());

                }
                addToInterestTable(newInterestName);
             }
             refreshPage();
    }
    public void addToInterestTable(String newName){
       
        try{
          Class.forName(javaSQLDriverPath);
          Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);  
       
            
            PreparedStatement inserter = (PreparedStatement) conn.prepareStatement("INSERT INTO Interest(Name) VALUES(?)");
            inserter.setString(1,newName);
           
            inserter.executeUpdate();;
             conn.close();
            inserter.close();
            }
        
            catch(Exception e){
            log.info(e.getMessage());

        }
    }
     public void getListOfHasInterests(){
        interestList = new ArrayList();
        try{
            Class.forName(javaSQLDriverPath);
            Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);
            Statement st=conn.createStatement();
            PreparedStatement inserter = (PreparedStatement) conn.prepareStatement("Select * from HasInterest Where PersonID=?");
            inserter.setInt(1, myID);
            ResultSet rs=inserter.executeQuery();
            int found = 0;
            while(rs.next()){
                interestList.add(rs.getString("InterestName"));
                stringWithPointer p1 = new stringWithPointer();
                p1.stringName = rs.getString("InterestName");
                p1.pointer = found;
                
                interestsAndPointers.add(p1);
                found++;
            }
           
            rs.close();
            st.close();
            conn.close();
            }
            catch(Exception e){
            log.info(e.getMessage());

            }
         setToSession("test");
       // return friendRequestIDS;
    } 
     public void getListOfFriendRequests(){
         friendRequests = new ArrayList<NameWithID>();
        try{
            Class.forName(javaSQLDriverPath);
            Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);
            Statement st=conn.createStatement();
            PreparedStatement inserter = (PreparedStatement) conn.prepareStatement("Select * from Relation Where PersonID2=? AND Relation=?");
            inserter.setInt(1, myID);
            inserter.setString(2, "friendRequest");


            ResultSet rs=inserter.executeQuery();
            int friendReqPointer = 0;
            while(rs.next()){
                int FriendsIDInt = rs.getInt("PersonID1");
                 NameWithID tempFriend = new NameWithID();
                 tempFriend.setMyID(FriendsIDInt);
                 tempFriend.pointer = friendReqPointer;
                 friendRequests.add(tempFriend);  
                 friendReqPointer++;
            }
            
            rs.close();
            st.close();
            conn.close();
            for(int i =0;i < friendRequests.size();i++){
                friendRequests.get(i).setMyName(getNameByID(friendRequests.get(i).myID));
                
              
            }
            }
            catch(Exception e){
                log.info(e.getMessage());
            }
       // return friendRequestIDS;
    }
     
     public void acceptFriendRequest(int personID){
        try{
            Class.forName(javaSQLDriverPath);
            Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);
            PreparedStatement inserter = (PreparedStatement) conn.prepareStatement("Update Relation SET Relation=? WHERE PersonID1=? AND PersonID2=?");
            inserter.setString(1, "friends");
            inserter.setInt(2,myID);
            inserter.setInt(3,personID);
            inserter.executeUpdate();
            inserter.setString(1, "friends");
            inserter.setInt(3,myID);
            inserter.setInt(2,personID);
            inserter.executeUpdate();
            conn.close();
            inserter.close();
            log.info("ID:"+ myID + " accepted friend request from " + personID);
            }
            catch(Exception e){
                    log.info(e.getMessage());

            }
        
    }
    public void removeFriendRequest( int removingID){ // also works for remove friend atm
        try{
          Class.forName(javaSQLDriverPath);
          Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);  
       
            
            PreparedStatement inserter = (PreparedStatement) conn.prepareStatement("DELETE FROM Relation WHERE PersonID1=? AND PersonID2=?");
            inserter.setInt(2,myID);
            inserter.setInt(1,removingID);
            
            inserter.executeUpdate();
           inserter.setInt(1,myID);
            inserter.setInt(2,removingID);
            
            inserter.executeUpdate();
             conn.close();
            inserter.close();
              log.info("ID:"+ myID + " removed friend request from " + removingID);
            }
        
            catch(Exception e){
                
           log.info(e.getMessage());

        }
        
    }
    public void acceptHug(){
        
        removeHug();
        refreshPage();
    }
   
    public String goToFriendProfile(){
        
        return goToProfile();
    }
    
    
    public void setToSession(String myInput){
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);  
       stringWithPointer testId = new stringWithPointer();
       testId.stringName = myInput;
       testId.setPointer(1);
       stringWithPointer testId2 = new stringWithPointer();
       testId2.stringName = myInput+"2";
       testId2.setPointer(2);
       ArrayList<stringWithPointer> myTestList = new ArrayList<stringWithPointer>();
       myTestList.add(testId);
       myTestList.add(testId2);
       holderOfListForStringAndPointer h1 = new holderOfListForStringAndPointer();
       h1.setMyList(myTestList);
       
       ArrayList<Integer> i1 = new ArrayList<Integer>();
       i1.add(1);
       i1.add(2);
       session.setAttribute("myInterestListYay", i1.toArray());
    }
    public String goToProfile(){
        int profilesID = friendList.get(friendPointer).myID;
        String profileIDString = ""+profilesID;
         
         HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);  
       
       session.setAttribute("friendsIDSession", profileIDString);
        return "success";
    
    }
    public void goToRemoveFriend(){  
        
        removeFriendRequest(friendList.get(friendPointer).myID);
        friendList.remove(friendPointer);
        refreshPage();
    }
    
    
    
    public void goToRemoveInterest(){
        
        removeFromHasInterestTable(interestsAndPointers.get(interestPointer).stringName);
          log.info("ID:"+ myID + " removed interest " + interestsAndPointers.get(interestPointer).stringName);
        interestList.remove(interestPointer);
        refreshPage();
        
    }
    
    public void removeFromHasInterestTable(String newInterestName){
       
        try{
       //   Class.forName(javaSQLDriverPath);
          Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);  
       
            
            PreparedStatement inserter = (PreparedStatement) conn.prepareStatement("DELETE FROM HasInterest WHERE PersonID=? AND Interestname=? ");
            inserter.setInt(1,myID);
            inserter.setString(2,newInterestName);
           
            inserter.executeUpdate();;
             conn.close();
            inserter.close();
            }      
            catch(Exception e){
                log.info(e.getMessage());
        }
    }
 
    
    public void goToRemoveFriendRequest(){
        removeFriendRequest(friendRequests.get(friendRequestPointer).myID);
        friendRequests.remove(friendRequestPointer);
        refreshPage();
    }
    public void goToAcceptFriendRequest(){
        acceptFriendRequest(friendRequests.get(friendRequestPointer).myID);
        friendList.add(friendRequests.get(friendRequestPointer));
        friendRequests.remove(friendRequestPointer);
    }
    public String goToInterestProfile(){
        
        return navigateToInterestProfile();
    }
   
    public String navigateToInterestProfile(){
      
        String profileName = interestList.get(interestPointer);
        log.info("id: "+myID + " navigated to "+profileName);
         HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);        
       session.setAttribute("myInterestProfileName", profileName);        
        return "success";    
    }
    public String searchForUserAndNavigate(){
        
        boolean test = Pattern.matches("\\w*",searchForUserByName);
        if(!test){
            return "failed search";
        }
        else{
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);  

            session.setAttribute("searchByName", searchForUserByName);
          
            return "success";
        }
    }
    protected void refreshPage() {
        FacesContext fc = FacesContext.getCurrentInstance();
        String refreshpage = fc.getViewRoot().getViewId();
        ViewHandler ViewH =fc.getApplication().getViewHandler();
        UIViewRoot UIV = ViewH.createView(fc,refreshpage);
        UIV.setViewId(refreshpage);
        fc.setViewRoot(UIV);
    }
     
    
}

