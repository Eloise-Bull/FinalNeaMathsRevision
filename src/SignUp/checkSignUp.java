/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SignUp;

import static ConnectTheDatabase.ConnectTheDatabase.TheConnectionToDatabase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author 4-EBULL
 */
public class checkSignUp {
    public void AddStudent(String name, String Username, String Password, String Email, int ClassCode ){
        
        
        //////////////// STUDENT
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO Student ( S_name, username,email, password_hash, Class_id) VALUES ( '" + name+ "','" + Username +"','" + Email + "','"+Password  + "'," + ClassCode + ")");        
        
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void AddTeacher(String name, String Username, String Password, String Email, String School ){
        
        
        //////////////// TEACHER 
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            // inserting into teacher table
            statement.execute("INSERT INTO Teacher ( T_name, username,email, password_hash) VALUES ( '" + name+ "', '" + Username +"' ,'" + Email + "','"+Password  + "')");        
            // cuase of how i made database need to also add to class table 
            ResultSet results = statement.executeQuery("SELECT LAST_INSERT_ID() AS id;");
            if (results.next()){
               /// this should get the teacher last ~instered in and then get theyre id so i can add to class
                int TeacherId = results.getInt("id");
               statement.execute("INSERT INTO Class (Teacher_id, School) VALUES ('" + TeacherId + "',' " + School + "')"); 
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    
    ///// check username is unique
    ///
    ///
    public boolean UniquenessCheck(String user,String EnteredUsername ){
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT Count(*) FROM "+ user +" WHERE Username = '" + EnteredUsername+ "'");
            if (results.next()){
                int count = results.getInt(1);
                if (count > 0){
                    return false;
                }
                else{ 
                return true;
                }
            } 
            else {
                return false;
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
            // false as smth went  bad
            return false;
        }    
    }
    
    
}
