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
    
}

    public Boolean checkusername(){
        
        float score = StudentStatistics.PushScore();
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT Topic_Id FROM TopicStats WHERE Student_id = " + Student_id + " ORDER BY Score ASC LIMIT 1");
            // was suggested coulf be rounding errors for database as to why <=
            if (results.next()){
               int topicID = results.getInt("Topic_Id");
               return topicID ; 
            } 
            else{ 
                //cause results.next checks if query returns at least one row, need this here
                return 0;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            
            //fix later idk what else to put for that
            
            return 0;
        }         
    } 
    }
    public Boolean checkpassword(){
        
    }
