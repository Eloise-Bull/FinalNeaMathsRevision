
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConnectTheDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
/**
 *
 * @author elois
 */
public class ConnectTheDatabase {
    public static void main (String[] args) {
        
                
    }
    ///////////////////////////////////////////////////////////////////////////
    // creates the connection
    public static Connection TheConnectionToDatabase(){
        //4-ebull@10.211.78.2
        //4-ebull@10.211.78.2
        //3306
        //bh-computing
        String url = "jdbc:mysql://185.156.138.148:3306/4-ebull";
        String user = "4-ebull";
        String password = "Aware-Simply7-Known";
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("conection good");
            return connection;
        } catch (Exception e){
            // stack trace shows errors 
            e.printStackTrace();
            System.out.println("not successful");
            return null;
        }
    }
}
