/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsflogin.com;

import com.jsflogin.ConnectionInfo;
import com.jsflogin.JsfLogin;
import com.jsflogin.NameWithID;
import com.jsflogin.userForRest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.QueryParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
/**
 * REST Web Service
 *
 * @author peddy
 */
@Path("service")
public class ServiceResource {

    @Context
    private UriInfo context;
    String ConnectionPath ;// "jdbc:mysql://localhost:3306/demodb";// 
    String ConnectionUser ;
    String ConnectionPW ;
    String javaSQLDriverPath ;
    
    /**
     * Creates a new instance of ServiceResource
     */
    Logger log;
    public ServiceResource() {
         log= LogManager.getLogger(JsfLogin.class.getName());
        ConnectionInfo tempConn = new ConnectionInfo();
        ConnectionPath = tempConn.ConnectionPath;
        ConnectionUser = tempConn.ConnectionUser;
        ConnectionPW = tempConn.ConnectionPW;
        javaSQLDriverPath = tempConn.javaSQLDriverPath;
    }

    /**
     * Retrieves representation of an instance of jsflogin.com.ServiceResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    @Path("requestGet")
    public JSONObject getHtml(@QueryParam("userName") String userName,@QueryParam("searchFor") String searchString,@QueryParam("getAllusers") String getAllusers,@QueryParam("requestType") String type) {
       JSONObject obj = new JSONObject();
       if(searchString != null){
           if(testValidInput(userName)){
               
                obj = searchForPeople(searchString);
           }else{
               obj.put(("success"), 0);
               obj.put(("message"), "invalid input");
           }
       }
       else if(userName != null ){
           if(testValidInput(userName)){
               
                obj = addPeopleWithUserName(userName);
           }else{
               obj.put(("success"), 0);
               obj.put(("message"), "invalid input");
               
           }
          
            return obj; 
       }else if(getAllusers != null ){
           if(testValidInput(getAllusers)){
               obj.put(("success"), 1);
                obj = getAllNames();
           }else{
               obj.put(("success"), 0);
           }
          
            return obj; 
       }
 
	
        
        //TODO return proper representation object
        String returnThis ="";
        
        
        return obj;
        
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
    public JSONObject searchForPeople(String searchName){
        JSONObject o1 = new JSONObject();
        ArrayList<NameWithID> tempIDList = new JSONArray();
        try{
            Class.forName(javaSQLDriverPath);
            Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);
            Statement st=conn.createStatement();
           
            PreparedStatement inserter = (PreparedStatement) conn.prepareStatement("Select Name from Person WHERE Name=%?%");
            inserter.setString(1, searchName);
           
            ResultSet rs=inserter.executeQuery();
            while(rs.next()){
                o1.put("name", rs.getString("Name"));
            
            }
        
            rs.close();
            st.close();
            conn.close();
                o1.put("success",1);
            }
            catch(Exception e){
            log.info(e.getMessage());
              o1.put("success",0);

            }
        return o1;
    }
    public JSONObject addPeopleWithUserName(String userName){
        JSONObject returnUsers = new JSONObject();
        
        ArrayList<NameWithID> users = searchForNames(userName);
        ArrayList<userForRest> myPeople = new ArrayList<userForRest>();
        for(int i = 0; i < users.size();i++){
            userForRest r1 = new userForRest();
            r1.myID = users.get(i).myID;
            r1.myName = users.get(i).myName;
            r1.relationsWithUsers = myRelation(r1.myID);
            r1.Interests = getListOfHasInterests(r1.myID);
            myPeople.add(r1);
            
            
        }
        if(myPeople.size() == 0){
            returnUsers.put(("success"), 0);
            returnUsers.put(("message"), "did not find user by name");
            return returnUsers;
        }
        else{
             returnUsers.put(("success"), 1);
        }
        JSONArray myFinalList = new JSONArray();
        for(int i = 0 ; i < myPeople.size();i++){
            JSONObject oneUser = new JSONObject();
           
            JSONArray a1 = new JSONArray();
            for(int k = 0; k < myPeople.get(i).Interests.size();k++){
                JSONObject oneInerest = new JSONObject();
                oneInerest.put("interestName",myPeople.get(i).Interests.get(k));
                a1.add(oneInerest);
            }
            JSONArray a2 = new JSONArray();
            for(int k = 0; k < myPeople.get(i).relationsWithUsers.size();k++){
                JSONObject oneName = new JSONObject();
                oneName.put("name",myPeople.get(i).relationsWithUsers.get(k).myName);
              
                oneName.put("relation",myPeople.get(i).relationsWithUsers.get(k).Relation);
                a2.add(oneName);
            }
             oneUser.put("name", myPeople.get(i).myName);
            oneUser.put("interests",a1);
            oneUser.put("friends and relations", a2);
            myFinalList.add(oneUser);
             
        }
        
        returnUsers.put("response", myFinalList);
        return returnUsers;
    }
    
    public ArrayList<NameWithID> myRelation(int myIDINPUT){
        ArrayList<NameWithID> friendList = new ArrayList<NameWithID>();
        try{
            Class.forName(javaSQLDriverPath);
            Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);
            Statement st=conn.createStatement();
            PreparedStatement inserter = (PreparedStatement) conn.prepareStatement("Select * from Relation Where PersonID1=?");
            inserter.setInt(1, myIDINPUT);
         


            ResultSet rs=inserter.executeQuery();
            
            while(rs.next()){
                int FriendsIDInt = rs.getInt("PersonID2");
                String friendsRelation = rs.getString("Relation");
                 NameWithID tempFriend = new NameWithID();
                 tempFriend.setMyID(FriendsIDInt);
                 tempFriend.Relation = friendsRelation;
                 friendList.add(tempFriend);           
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
        return friendList;
    
        
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
    
    public ArrayList<NameWithID> searchForNames(String searchedFor){
       
        ArrayList<NameWithID> tempIDList = new JSONArray();
        try{
            Class.forName(javaSQLDriverPath);
            Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);
            Statement st=conn.createStatement();
           
            PreparedStatement inserter = (PreparedStatement) conn.prepareStatement("Select PersonID from Person WHERE Name=?");
            inserter.setString(1, searchedFor);
           
            ResultSet rs=inserter.executeQuery();
            while(rs.next()){
                NameWithID tempResult = new NameWithID();
                 tempResult.myID = rs.getInt("PersonID");
                 tempResult.myName = searchedFor;
                 tempIDList.add(tempResult);
           
            
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
     public JSONObject getAllNames(){
        JSONObject o1 = new JSONObject();
        JSONArray tempNameList = new JSONArray ();
        try{
            Class.forName(javaSQLDriverPath);
            Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);
            Statement st=conn.createStatement();
           
            PreparedStatement inserter = (PreparedStatement) conn.prepareStatement("Select Name from Person");
            
           
            ResultSet rs=inserter.executeQuery();
            while(rs.next()){
                JSONObject oTemp = new JSONObject();
                oTemp.put("name",rs.getString("Name"));
                tempNameList.add(oTemp);
                
            
            }
        
            rs.close();
            st.close();
            conn.close();
                o1.put("success", 1);
            }
            catch(Exception e){
                o1.put("success", 0);
            log.info(e.getMessage());

            }
        o1.put("users", tempNameList);
        return o1;
        
    }
    /**
     * PUT method for updating or creating an instance of ServiceResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("text/html")
    public void putHtml(String content) {
    }
    
    @POST
    @Consumes("application/x-www-form-urlencoded")
    //@Consumes("text/html")
    @Produces("text/plain")
    public String postHandler(String content) {
        return content + "in post handler";
    }
    
    public String makeList(){
        ArrayList<NameWithID> ourUsers = getListOfNames();
        String returnThis ="";
        for(int i = 0; i < ourUsers.size();i++){
            returnThis += ourUsers.get(i).getMyName()+getStringOfHasInterests(ourUsers.get(i).getMyID());
            returnThis +="\n";
        }
        return returnThis;
    }
    public String getStringOfHasInterests(int thisID){
        String returnThis = "";
        try{
            Class.forName(javaSQLDriverPath);
            Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);
            Statement st=conn.createStatement();
            PreparedStatement inserter = (PreparedStatement) conn.prepareStatement("Select * from HasInterest Where PersonID=?");
            inserter.setInt(1, thisID);
            ResultSet rs=inserter.executeQuery();
            
            while(rs.next()){
                returnThis +="\t"+rs.getString("InterestName");         
            }
           
            rs.close();
            st.close();
            conn.close();
            }
            catch(Exception e){
           

            }
        return returnThis;
    }
    
    
    public ArrayList<NameWithID> getListOfNames(){
       ArrayList<NameWithID> ourUsers = new ArrayList<NameWithID>(); 
         
         
        try{
            Class.forName(javaSQLDriverPath);
            Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);
            Statement st=conn.createStatement();
            PreparedStatement inserter = (PreparedStatement) conn.prepareStatement("Select * from Person ");
          
            ResultSet rs=inserter.executeQuery();
            
            while(rs.next()){
                NameWithID newGuy = new NameWithID();
                newGuy.setMyName(rs.getString("Name"));
                newGuy.setMyID(rs.getInt("PersonID"));
                ourUsers.add(newGuy);    
                
                
            }
           
            rs.close();
            st.close();
            conn.close();
            
            }
            catch(Exception e){
            

            }
        return ourUsers;
    }
    public ArrayList<String> getListOfHasInterests(int searchingID){
        ArrayList<String> s1 = new ArrayList<String>();
        try{
            Class.forName(javaSQLDriverPath);
            Connection conn=(Connection)DriverManager.getConnection(ConnectionPath,ConnectionUser,ConnectionPW);
            Statement st=conn.createStatement();
            PreparedStatement inserter = (PreparedStatement) conn.prepareStatement("Select * from HasInterest Where PersonID=?");
            inserter.setInt(1, searchingID);
            ResultSet rs=inserter.executeQuery();
            while(rs.next()){
                s1.add(rs.getString("InterestName"));
              
            }
           
            rs.close();
            st.close();
            conn.close();
            }
            catch(Exception e){
            log.info(e.getMessage());

            }
        return s1;
         
       // return friendRequestIDS;
    } 
    
}
