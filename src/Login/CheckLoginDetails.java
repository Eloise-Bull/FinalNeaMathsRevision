/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Login;

import CalculatingStudentStatistics.StudentStatistics;
import static ConnectTheDatabase.ConnectTheDatabase.TheConnectionToDatabase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author elois
 */
public class CheckLoginDetails {
    
    // return id compare id to password id thats returned. make false = -1 cause not true. 
    // currently just returns if present in table 
    public int checkusernameReturnID(String enteredUsername, String user){
        
            if ("Student".equals(user)) {
                try (Connection connection = TheConnectionToDatabase()){
                    Statement statement = connection.createStatement();
                    ResultSet results = statement.executeQuery("SELECT Student_id FROM Student WHERE Username = " + enteredUsername);
                    if (results.next()){
                        int id = results.getInt("username");
                        return id; 
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    return -1;
                }
            if ("Teacher".equals(user)) {
                try (Connection connection = TheConnectionToDatabase()){
                    Statement statement = connection.createStatement();
                    ResultSet results = statement.executeQuery("SSELECT Teacher_id FROM Teacher WHERE Username = " + enteredUsername);
                    if (results.next()){
                        int id = results.getInt("username");
                        return id; 
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    return -1;
                }
                 
    } 
    public Boolean checkpassword(){
        
    }

}

