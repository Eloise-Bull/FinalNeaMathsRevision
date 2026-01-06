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
        
        
        //////////////// unfinished 
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO Student ( Name, Username, Password, Class) VALUES ( '" + name+ "," +Username +"," +"'" +Password + "'" + "," + "'" + Email + "'" + "," + ClassCode + ")");        
        
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
