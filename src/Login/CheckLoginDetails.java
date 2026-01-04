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
    // when compare do if username id = password id AS LONG AS id =! -1 
    public int checkUsernameReturnID(String enteredUsername, String user){
        
        if ("Student".equals(user)) {
            try (Connection connection = TheConnectionToDatabase()){
                Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery("SELECT Student_id FROM Student WHERE username = " + "'" +  enteredUsername + "'");
                if (results.next()){
                    int id = results.getInt("Student_id");
                    return id; 
                }
                else {
                    return -1;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                return -1;
            }

        }

        if ("Teacher".equals(user)) {
            try (Connection connection = TheConnectionToDatabase()){
                Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery("SELECT Teacher_id FROM Teacher WHERE username = " + "'" + enteredUsername + "'");
                if (results.next()){
                    int id = results.getInt("Teacher_id");
                    return id; 
                }
                else {
                    return -1;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                return -1;
            }

        }
        else {
            return -1;
        }
            
            
    }
    
    // smae code swap out the username and stuff for password
    // need to encrypt code but thats laters problem.
    public int checkPasswordReturnID(String enteredPassword, String user){
       if ("Student".equals(user)) {
            try (Connection connection = TheConnectionToDatabase()){
                Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery("SELECT Student_id FROM Student WHERE password_hash = " + "'" +  enteredPassword + "'");
                if (results.next()){
                    int id = results.getInt("Student_id");
                    return id; 
                }
                else {
                    return -1;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                return -1;
            }

        }

        if ("Teacher".equals(user)) {
            try (Connection connection = TheConnectionToDatabase()){
                Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery("SELECT Teacher_id FROM Teacher WHERE password_hash = " + "'" + enteredPassword + "'");
                if (results.next()){
                    int id = results.getInt("Teacher_id");
                    return id; 
                }
                else {
                    return -1;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                return -1;
            }

        }
        else {
            return -1;
        }
            
            
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    ///this is to get the class id for either student or teacher to be used in later methods
    ///
    
    public int getClassIdStudent(int StudentID){
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT Class_id FROM Student WHERE Student_id = " + "'" + StudentID + "'");
            if (results.next()){
                int id = results.getInt("Class_id");
                return id; 
            }
            else {
                return -1;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    public int getClassIdTeacher(int TeacherID){
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT Class_id FROM Class WHERE Teacher_id = " + "'" + TeacherID + "'");
            if (results.next()){
                int id = results.getInt("Class_id");
                return id; 
            }
            else {
                return -1;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

}

