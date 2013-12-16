/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsflogin;
import java.util.ArrayList;
/**
 *
 * @author peddy
 */
public class userForRest {
  public String myName;
  public int myID;
  public   ArrayList<NameWithID> relationsWithUsers;
  public   ArrayList<String> Interests;

    public ArrayList<NameWithID> getRelationsWithUsers() {
        return relationsWithUsers;
    }

    public void setRelationsWithUsers(ArrayList<NameWithID> relationsWithUsers) {
        this.relationsWithUsers = relationsWithUsers;
    }

    public ArrayList<String> getInterests() {
        return Interests;
    }

    public void setInterests(ArrayList<String> Interests) {
        this.Interests = Interests;
    }
    
    
    
    

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public int getMyID() {
        return myID;
    }

    public void setMyID(int myID) {
        this.myID = myID;
    }
    
    
}
