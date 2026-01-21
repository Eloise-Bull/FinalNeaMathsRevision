/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Quizzes;
import static ConnectTheDatabase.ConnectTheDatabase.TheConnectionToDatabase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;
import java.util.ArrayList;

/**
 *
 * @author 4-EBULL
 */
public class QuizQuestions {
    private int num;
    private static int Count = 1;
    private static float countStats;
    private static ArrayList<String> TopicQuestionsList;  
    private int RandomINT;
    int NewNumOfQuestions;
    
    
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// on screen stuff
    // for the on screen question count
    public static int count(){
            Count = Count + 1;  
        return Count;
    }
    // for the on screen quiz stats
    public static float CountingQuizStats(int Count,Boolean correct){
        if (correct == true){
            countStats = (countStats*(Count-1) + 100)/Count;
        }
        else {
            countStats = countStats*(Count-1)/Count;
            
        }
        countStats = (Math.round(countStats*100f)/100f);
        return countStats;
    }
    
    //////////////////////////////////////////////////////////////////////////////////////////////////
    /// for random quiz
    ///
        
       // random nums for both quiz and answers
    private int RandomNumForQuiz(){
        int size =(RandomQuiz.ArrayListQuestions()).size();
        Random random = new Random();
        int randomInt;
        randomInt = random.nextInt(size);
        return randomInt;
        }
        //from database use random pick to pick question
        // when do make sure nums in range of topic id /  question id
    
    // uses the output of a random number in range of questions avalible
    // then it calls all the questions
    public String RandomQuiz(){
        num = RandomNumForQuiz();
        String Question = RandomQuiz.ArrayListQuestions().get(num);
        return Question;
    }
    
    // checks answer as it keeps the num used for the question id
    // as questionid = answer id
    public boolean CheckAnswer(String answer){
        String CorrectAnswer = RandomQuiz.ArrayListAnswers().get(num);
        System.out.println(CorrectAnswer);
        if ( CorrectAnswer.equals(answer)) {
            return true;
        }
        else {
            //if wrong adds to the list of wrong answers for my quiz output
            Quizzes.QuizDetails.ListOfAnswers.add(CorrectAnswer);
            return false;
            
        }
    }    
        
    //////////////////////////////////////////////////////////////////
    /// for Targeted quiz
    ///
    ///// gets random number within the range of questions within that topic
    public int RandomNumForTargetedQuiz(){
        int CurrentTopicId = TargetedQuiz.ReturnTopicID();
        TopicQuestionsList = TargetedQuiz.SpecificTopicQuestions(CurrentTopicId);
        int size =(TopicQuestionsList).size();
        Random random = new Random();
        int randomInt = random.nextInt(size);
        return randomInt;
    }    
    
    
    // uses the random number to get the question. 
    public String TargetedQuestions(){
        RandomINT = RandomNumForTargetedQuiz();
        String Question = TopicQuestionsList.get(RandomINT);
        return Question;
    }    
    
    // cant use question id = answer as the index in the list earlier is not the id number
    // just get question and find the id for it 
    // then return the answer using that id
    public boolean CheckTargetedAnswer(String answer, String Question){
        int Question_id = TargetedQuiz.ReturnQuestionIdForTargeted(Question);
        String ActualAnswer = TargetedQuiz.ReturnAnswerForTargeted(Question_id);
        if ( ActualAnswer.equals(answer)) {
            return true;
        }
        else {
            //if wrong adds to the list of wrong answers for my quiz output
            Quizzes.QuizDetails.ListOfAnswers.add(ActualAnswer);
            return false;
            
        }
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////
    ///THIS WHOLE SECTIONS IS TO CALCULATE THE STATS FOR EACH TOPIC FOR THE STUDNET 
    ///
    ///
    ///return topicID for question
    public int ReturnTopicID(String Question){
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT Topic_id FROM Questions WHERE Question = '" + Question + "';");
            if (results.next()){
               return results.getInt("Topic_id");
            }
            else {
                return 0;
            }
        }catch(Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    ///
    ///
    //////add on to the number of quiz questions done
    public int AddOneToNumOfQuestionsDone(int TopicID, int StudentID) {
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT questions_done FROM TopicStats "
                    + "WHERE Student_id = " + StudentID + " AND Topic_Id = " +TopicID+ ";");
            if (results.next()){
               int numOfQuestionsForThisTopic = results.getInt("questions_done");
               NewNumOfQuestions = numOfQuestionsForThisTopic+1;
               statement.execute("UPDATE TopicStats SET questions_done = " + NewNumOfQuestions + " "
                       + "WHERE Student_id = '" + StudentID + "' AND Topic_Id = " +TopicID+ ";");
               
            }
            else{
                // this is for if the student hasnt doen topic before so inserts new thign for it
                // fixed this so when they log in it auto gives them 0
                // so could be unnescassary but will keep as precaution
                NewNumOfQuestions = 1;
                statement.executeUpdate ("INSERT INTO TopicStats ( Student_id, Topic_id, Score, questions_done) "
                        + "VALUES (" + StudentID + "," + TopicID + ", 0," + NewNumOfQuestions+ ")" );
                
            }
            return NewNumOfQuestions;
        }catch(Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// the stats side of it
    /// gets the Topic Stats and calculates new one
    public float CalculateNewStats(boolean Correct, int TopicID, int Student_id){
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT NumOfCorrectAnswers FROM TopicStats "
                    + "WHERE Student_id = '" + Student_id + "' AND Topic_Id = " +TopicID+ ";");
            if (results.next()){
               float NumOfCorrectAnswers = results.getFloat("NumOfCorrectAnswers");
                if (Correct){
                    statement.executeUpdate("UPDATE TopicStats SET NumOfCorrectAnswers =" + (NumOfCorrectAnswers +1)+ " "
                            + "WHERE Student_id = " + Student_id + " AND Topic_Id = " +TopicID);
            
                    float NewStat = ((NumOfCorrectAnswers+1)/ ( float )NewNumOfQuestions) * 100;
                    NewStat = (Math.round(NewStat*100f)/100f);
                    return NewStat;
                }
                else {  
                    float NewStat = ((NumOfCorrectAnswers)/ ( float )NewNumOfQuestions) * 100;
                    NewStat = (Math.round(NewStat*100f)/100f);
                    return NewStat;
                }   
            }
            else {
                return 0;
            }
            
        }catch(Exception e) {
            e.printStackTrace();
            return 0;
        }
        
    }
    
    // uses new stats just calculated to put new score in. 
    //change the stats in the database and work it out
    /// this is so the target quiz is updated 
    public void updateStats(boolean Correct, int TopicID, int Student_id){
        float nowAccurateStatistic = CalculateNewStats(Correct,TopicID,Student_id);
        
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            // UPDATE TopicStats SET Score = 100 WHERE Student_id = 1 AND Topic_id = 1;
            statement.execute("UPDATE TopicStats SET Score = " + nowAccurateStatistic + " "
                    + "WHERE Student_id = " + Student_id + " AND Topic_id = " +TopicID);
         
        }catch(Exception e) {
            e.printStackTrace();
        }
    } 

}
