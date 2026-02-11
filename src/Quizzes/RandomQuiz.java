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
import java.util.Random;
import Quizzes.Quizzes.QuizDetails;

/**
 *
 * @author elois
 */
public class RandomQuiz {
    
    
    // uses the output of a random number in range of questions avalible
    // then it calls all the questions
    public String RandomQuiz(){
        int num = RandomNumForQuiz();
        String Question = RandomQuiz.ArrayListQuestions().get(num);
        if (Question.equals(Quizzes.QuizDetails.LastQuestionAnswered)) {
            return RandomQuiz();
        }
        else{
            return Question;
        }
    }  

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// for the quizzes creats an array list all the answers and the questions i then search for them
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
    private int RandomNumForQuiz(){
        int size =(RandomQuiz.ArrayListQuestions()).size();
        Random random = new Random();
        int randomInt;
        randomInt = random.nextInt(size);
        return randomInt;
        }

}
