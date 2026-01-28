/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Settings;

import static ConnectTheDatabase.ConnectTheDatabase.TheConnectionToDatabase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

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
}
