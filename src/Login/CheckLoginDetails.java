/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Login;

import static ConnectTheDatabase.ConnectTheDatabase.TheConnectionToDatabase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import org.mindrot.jbcrypt.BCrypt;
/**
 *
 * @author elois
 */
public class CheckLoginDetails {
    
    // gets username id for either teacher or student as i will need it for later
    public int ReturnUsernameID(String enteredUsername, String user){
        
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
    
    // basically gets the stored hash from where it = the entered username 
    public boolean checkPassword(String EnteredUsername, String user, String EnteredPassword){
        if ("Student".equals(user)) {
            try (Connection connection = TheConnectionToDatabase()){
                Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery("SELECT password_hash FROM Student WHERE username = '" +  EnteredUsername + "'");
                if (results.next()){
                    String PasswordHash = results.getString("password_hash");
                    boolean correctPassword = BCrypt.checkpw(EnteredPassword,PasswordHash);
                    return correctPassword;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        }

        if ("Teacher".equals(user)) {
            try (Connection connection = TheConnectionToDatabase()){
                Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery("SELECT password_hash FROM Teacher WHERE username = '" +  EnteredUsername + "'");
                if (results.next()){
                    String PasswordHash = results.getString("password_hash");
                    boolean correctPassword = BCrypt.checkpw(EnteredPassword,PasswordHash);
                    return correctPassword;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        }
        return false;
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

