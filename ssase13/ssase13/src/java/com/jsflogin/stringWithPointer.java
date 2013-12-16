/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsflogin;
import static com.jsflogin.Register.log;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author peddy
 */
@SessionScoped
public class stringWithPointer {
    public String stringName;
    public int pointer;

    public String getStringName() {
        return stringName;
    }

    public void setStringName(String stringName) {
        this.stringName = stringName;
    }

    public int getPointer() {
      
        return pointer;
    }

    public void setPointer(int pointer) {
        this.pointer = pointer;
    }
    
    public void setSessionStuff(){
        
    }
    public void setNavigationString(){
        log.info(" i get in here in set navigation");
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);  
       
       session.setAttribute("pointerInterest", pointer);
    }
    
}
