/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Quizzes;

import static ConnectTheDatabase.ConnectTheDatabase.TheConnectionToDatabase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author elois
 */
public class DoAssignment {
    public static ArrayList<String> GetQuestionsForQuizAssignment(int topic){
        ArrayList<String> QuestionsList = new ArrayList<>();
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT Question FROM Questions WHERE Topic_Id = '" + topic + "'");
            while (results.next()){
                String StatsValue = results.getString("Question");
                QuestionsList.add(StatsValue);
            }
            return QuestionsList;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
}
