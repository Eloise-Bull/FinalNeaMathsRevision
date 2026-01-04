
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConnectTheDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
// would've been for the catch but i did just exception keep for later incase its messed up
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.Statement;
// couldve used java.sql.*; - idk if need more sql imports change it
import CalculatingStudentStatistics.StudentStatistics;
import Login.Login;
/**
 *
 * @author elois
 */
public class ConnectTheDatabase {
    public static void main (String[] args) {
        System.out.println(ArrayListAnswers());
        
                
    }
    ///////////////////////////////////////////////////////////////////////////
    // creates the connection
    public static Connection TheConnectionToDatabase(){
        //4-ebull@10.211.78.2
        //4-ebull@10.211.78.2
        //3306
        //bh-computing
        String url = "jdbc:mysql://185.156.138.148:3306/4-ebull";
        String user = "4-ebull";
        String password = "Aware-Simply7-Known";
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("conection good");
            return connection;
        } catch (Exception e){
            // stack trace shows errors 
            e.printStackTrace();
            System.out.println("not successful");
            return null;
        }
    }
    
    
    // making array lists for the stuff so that can call upon method instead of in code
    //// FOR LOGIN / CHANGE USERNAME /PASSWORD / DEATILS#
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void ArrayListTeacherUsernames(){
        
    }
    
    public void ArrayListStudentUsernames(){
        
    }
    
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
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// creates an array list for the student stats so i can calculate the averages of a class

    // returns the array for the stats. temporaty storage, a float cause stats = decimal
    // temporary class_id = set to one cause then i can test wihtout them loging in 
    
    static int Class_id = Login.classId() ;
    public static ArrayList<Float> ArrayListStudentStats(){
        ArrayList<Float> StudentStatslist = new ArrayList<>();
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT Score FROM TopicStats WHERE Student_id IN ( SELECT Student_id FROM Student WHERE Class_id = " + Class_id+")");
            while (results.next()){
                float StatsValue = results.getFloat("Score");
                StudentStatslist.add(StatsValue);
            }
            return StudentStatslist;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///outputs topic stats for the student and from that i can work out their worst topic 
    // temp set studnet_id to one so i font have to do log in for my prototpyr but will change later
    // just to test it works
    static int Student_id = Login.StudentID();
    public static ArrayList<Float> ArrayListTopicStats(){
        ArrayList<Float> TopicStatslist = new ArrayList<>();
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT Score FROM TopicStats WHERE Student_id = '" + Student_id + "'");
            while (results.next()){
                float StatsValue = results.getFloat("Score");
                TopicStatslist.add(StatsValue);
            }
            return TopicStatslist;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }         
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///
    // for the specific questions thingy it return the the topic id
    public static int ReturnTopicID(){
        
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
    
    
        
        // do one to check what topic it is input question output topic id 
    public static int ReturnTopicIDForPushStats(String Question){
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT Topic_Id FROM Questions WHERE Question = '" + Question + "'");
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
            
            
            
            return 0;
        }         
    }
      
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // for setting assignments
    
    
    
}
