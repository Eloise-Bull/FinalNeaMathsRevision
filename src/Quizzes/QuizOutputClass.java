/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Quizzes;

import static ConnectTheDatabase.ConnectTheDatabase.TheConnectionToDatabase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author elois
 */
public class QuizOutputClass {
    public String ReturnTopic(String Question){
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT Topic FROM Topic t JOIN Questions q "
                    + "ON t.Topic_id = q.Topic_id WHERE Question = '" + Question + "'");
            String Topic = null;
            if (results.next()){
                Topic = results.getString("Topic");
            }
            return Topic;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
