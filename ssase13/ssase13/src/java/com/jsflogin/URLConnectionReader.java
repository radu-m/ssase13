/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsflogin;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
/**
 *
 * @author peddy
 */
public class URLConnectionReader {
     public static String users;
     public static void main(String[] args) throws Exception {
             /*   
        String  myPathForRest ="http://192.237.204.46/ssase13/webresources/service/requestGet?getAllusers";

        URL oracle = new URL(myPathForRest);
        URLConnection yc = oracle.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                                    yc.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null){ 
            System.out.println(inputLine);
            users = inputLine;
        }
        in.close();*/
         System.out.println("yes this happens");
         String  myPathForRest ="http://localhost:8080/ssase13/webresources/service/";
                 System.out.println("in main ");
        java.net.URL obj = new URL(null,myPathForRest,new sun.net.www.protocol.http.Handler());
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("content","test");
        
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        
        String urlParameters = "";
        wr.writeBytes(urlParameters);
        wr.flush();
	wr.close();
 
		int responseCode = con.getResponseCode();
        
        BufferedReader in = new BufferedReader(new InputStreamReader(
                                    con.getInputStream()));
         
        String inputLine;
        while ((inputLine = in.readLine()) != null){ 
            System.out.println(inputLine);
            
        }
        in.close();
    }
}
