/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Settings;

import static ConnectTheDatabase.ConnectTheDatabase.TheConnectionToDatabase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author 4-EBULL
 */
public class GetOrChangeUserInfo {
    // finish the array
    public String[] GetUserInfo(int ClassID, String User, int Studentid,int TeacherId){
        String[] arrayForDetails = new String[2];
        int id;
        if ("Student".equals(User)){
            id = Studentid;  
        }
        else {
            id = TeacherId;
        }
        
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT username, Email  "
                    + "FROM "+ User +" WHERE " + User+ "_id = " + id);

            while (results.next()){

                String Username = results.getString("username");
                String Email = results.getString("Email");
                
                arrayForDetails[0] = Username;
                arrayForDetails[1] = Email;
            }
            return arrayForDetails;
            
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean ChangeUsername(String User, String Username, int ID){
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet Results = statement.executeQuery("SELECT EXISTS ( "
                    + "SELECT 1 FROM "+User+" WHERE username = '"+ Username+"')");
            if (Results.next()){
                // if =1 then there is already a username like it so return false
                if (Results.getInt(1)==1){
                    return false;
                }
                else{
                    // is unique 
                    statement.execute("UPDATE " + User + " SET username = '" + Username + "' WHERE "+User+"_id = " + ID);
                    return true;
                }
            }
            else{
                return false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean ChangeEmail(String User, String Email, int ID){
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet Results = statement.executeQuery("SELECT EXISTS ( "
                    + "SELECT 1 FROM "+User+" WHERE Email = '"+ Email+"')");
            if (Results.next()){
                // if =1 then there is already a username like it so return false
                if (Results.getInt(1)==1){
                    return false;
                }
                else{
                    // is unique 
                    statement.execute("UPDATE " + User + " SET Email = '" + Email + "' WHERE "+User+"_id = " + ID);
                    return true;
                }
            }
            else{
                return false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // for combo bow gives list of students
    public static ArrayList<String> SetBox(int ClassID){
        ArrayList<String> ListOfNames = new ArrayList<>();
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT username FROM Student WHERE Class_id = " + ClassID);
            while (results.next()){
                String Name = results.getString("username");
                ListOfNames.add(Name);
            }
            return ListOfNames;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // check password when they wanna change it they need their old password to compare and then they can change 
    // basically gets the stored hash from where it = the entered username 
    public boolean checkPasswordToOldOne(String user, int UserID, String EnteredPassword){
        if ("Student".equals(user)) {
            try (Connection connection = TheConnectionToDatabase()){
                Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery("SELECT password_hash FROM Student WHERE Student_id = " +  UserID);
                if (results.next()){
                    String PasswordHash = results.getString("password_hash");
                    boolean correctPassword = BCrypt.checkpw(EnteredPassword,PasswordHash);
                    return correctPassword;
                }
                return false;
            }
            catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        }

        if ("Teacher".equals(user)) {
            try (Connection connection = TheConnectionToDatabase()){
                Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery("SELECT password_hash FROM Teacher WHERE Teacher _id = " +  UserID);
                if (results.next()){
                    String PasswordHash = results.getString("password_hash");
                    boolean correctPassword = BCrypt.checkpw(EnteredPassword,PasswordHash);
                    return correctPassword;
                }
                return false;
            }
            catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        }
        else {
            return false;
        }           
    }
    /// change password
    public boolean ChangePassword(String User, String PasswordHash, int ID){
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            statement.execute("UPDATE " + User + " SET password_hash = '" + PasswordHash + "' WHERE "+User+"_id = " + ID);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //// delete Account
    ///when deleting student account need to get rid of Student, TopicStats, Assigned
    /// teacher get rid of students, assignment info, assigned
    public boolean DeleteAccount(int UserID, String User) {
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            
            if ("Student".equals(User)){
                statement.executeUpdate("DELETE FROM TopicStats WHERE Student_id = "+ UserID);
                statement.executeUpdate("DELETE FROM Assigned WHERE StudentId = "+ UserID);
                statement.executeUpdate("DELETE FROM Student WHERE Student_id = "+ UserID);
                return true;
            }
            else{
                statement.executeUpdate("DELETE FROM TopicStats WHERE Student_id IN ( SELECT Student_id FROM Teacher"+ UserID);
                statement.executeUpdate("DELETE FROM Assigned WHERE StudentId = "+ UserID);
                statement.executeUpdate("DELETE FROM Student WHERE Student_id = "+ UserID);
                statement.execute("DELETE FROM Teacher");
                statement.execute("DELETE FROM Teacher");
                statement.execute("DELETE FROM Teacher WHERE Teacher_id = " + UserID);
                return true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
