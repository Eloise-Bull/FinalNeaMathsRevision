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
public class RandomQuiz {
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// for the quizzes creats an array list all the answers and the questions i then search for them
    
    public static ArrayList<String> ArrayListAnswers(){
        ArrayList<String> AnswersList = new ArrayList<>();
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT Answer FROM Answers");
            while (results.next()){
                String StatsValue = results.getString("Answer");
                AnswersList.add(StatsValue);
            }
            return AnswersList;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    //might not need an array for questions as wont search through it ??? idk yet 
    public static ArrayList<String> ArrayListQuestions(){
        ArrayList<String> QuestionsList = new ArrayList<>();
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT Question FROM Questions");
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
