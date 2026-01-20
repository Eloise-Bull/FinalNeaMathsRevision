/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Settings;

import static ConnectTheDatabase.ConnectTheDatabase.TheConnectionToDatabase;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author 4-EBULL
 */
public class GetOrChangeUserInfo {
    // finish the array
    public Array[] GetUserInfo(int ClassID, String User, int Studentid,int TeacherId){
        int id = -1;
        String idFormat;
        if ("Student".equals(User)){
            id = Studentid;  
        }
        else {
            id = TeacherId;
        }
        
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT username, Email, Class_id  "
                    + "FROM "+ User +" WHERE " + User+ "_id = " + id);

            while (results.next()){

                String Username = results.getString("username");
                String Topic = results.getString("Email");
                String NAME = results.getString("S_name");
                Float Score = results.getFloat("Score");

            }
            return 
            
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    }
    
}
