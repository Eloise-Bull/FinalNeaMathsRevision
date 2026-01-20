/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Quizzes;

import static ConnectTheDatabase.ConnectTheDatabase.TheConnectionToDatabase;
import Login.Login;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author elois
 */
public class TargetedQuiz {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // for the specific questions thingy it return the the topic id
    public static int ReturnTopicID(){
        int Student_id = Login.InfoOfUserForThisLoginSession.StudentId ;
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT Topic_Id FROM TopicStats "
                    + "WHERE Student_id = " + Student_id + " ORDER BY Score ASC LIMIT 1");
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
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// outputs questions from only a specific topic 
    public static ArrayList<String> SpecificTopicQuestions(int topic){
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
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// to find the answer for the question i put in 
    public static int ReturnQuestionIdForTargeted(String Question ){
        
        
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT Question_Id FROM Questions WHERE Question = '" + Question + "'");
            if (results.next()){
               int QuestionID = results.getInt("Question_Id");
               return QuestionID ; 
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
        
        public static String ReturnAnswerForTargeted(int Question_Id ){
        
        
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT Answer FROM Answers WHERE Answer_id = '" + Question_Id + "'");
            if (results.next()){
               String Answer = results.getString("Answer");
               return Answer ; 
            } 
            else{ 
                //cause results.next checks if query returns at least one row, need this here
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            
            //fix later idk what else to put for that
            
            return null;
        } 
        
    
        
        
    }   
}
