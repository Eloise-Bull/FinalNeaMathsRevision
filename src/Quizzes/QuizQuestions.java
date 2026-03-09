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
 * @author 4-EBULL
 */
public class QuizQuestions {
    private static int Count = 1;
    private static float countStats;
    int NewNumOfQuestions;
    
    
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// on screen stuff
    // for the on screen question count
    public static int count(Boolean reset){
        if (reset){
            Count = 0;
        }
        else {
            Count = Count + 1; 
        }    
        return Count;
    }
    // for the on screen quiz stats
    public static float CountingQuizStats(int Count,Boolean correct, Boolean reset){
        if (reset){
            countStats = 0;
        }
        else{
            if (correct == true){
                countStats = (countStats*(Count-1) + 100)/Count;
            }
            else {
                countStats = countStats*(Count-1)/Count;
            }
            countStats = (Math.round(countStats*100f)/100f);
            
        }
        return countStats;
    }
    
    /// checks answer no matter what for all methods
    public boolean CheckAnswer(String answer, String Question){
        int QuestionID = -1;
        String ActualAnswer = null;
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT Question_id FROM Questions "
                    + "WHERE Question = '" + Question + "'");
            // was suggested coulf be rounding errors for database as to why <=
            if (results.next()){
               QuestionID = results.getInt("Question_id");
            } 
            else{ 
                return false;
            }
            Statement statements = connection.createStatement();
            ResultSet Results = statements.executeQuery("SELECT Answer FROM Answers "
                    + "WHERE Question_id = " + QuestionID );
            if (Results.next()){
                ActualAnswer = Results.getString("Answer");
            }
            else {
                return false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }         
        Quizzes.QuizDetails.RealAnswer = ActualAnswer;
        if ( ActualAnswer != null && ActualAnswer.equals(answer)){
            return true;
        }
        else {
            //if wrong adds to the list of wrong answers for my quiz output
            Quizzes.QuizDetails.ListOfAnswers.add(ActualAnswer);
            Quizzes.QuizDetails.ListOfUserAnswers.add(answer);
            return false;
        }
    }
    ///
    ///
    /////////////////////////////////////////////////////////////////////////////////////////////////////

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
    
    // get rid of UpperCase and spaces
    public String QuizQuestionAnswersToCorrectFormat(String Answer){
        Answer = Answer.toLowerCase();
        String[] parts = Answer.split(" ");
        String PutAnswerBackTogether = "";
        for ( int i = 0; i < parts.length; i ++){
            PutAnswerBackTogether = PutAnswerBackTogether + parts[i] ;
        }
        return PutAnswerBackTogether;
    }

}
