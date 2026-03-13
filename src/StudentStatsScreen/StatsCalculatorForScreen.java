/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StudentStatsScreen;

import static ConnectTheDatabase.ConnectTheDatabase.TheConnectionToDatabase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import Login.Login;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 4-EBULL
 */
public class StatsCalculatorForScreen {
    
    // returns the list of all the students scores for each topic to later calucalte their average statistic 
    public static ArrayList<Float> averageStatsForStudent(int StudentID){
        ArrayList<Float> StatsList = new ArrayList<>();
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT Score FROM TopicStats WHERE Student_id = " + StudentID );
            while (results.next()){
                Float StatsValue = results.getFloat("Score");
                StatsList.add(StatsValue);
            }
            return StatsList;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    // uses the list of topic stats to calculat ethe average statistic for the student - to 2 dp
    public static float CalculatingAverageStats(){
        int Student_id = Login.InfoOfUserForThisLoginSession.StudentId ;
        ArrayList<Float> AllStats = averageStatsForStudent(Student_id);
        float AverageStats = 0;
        int count = 0;
        for ( int i = 0; i < AllStats.size(); i ++){
            AverageStats = AllStats.get(i) + AverageStats;
           count = count + 1;
        }
        AverageStats =  AverageStats/count;
        AverageStats = (Math.round(AverageStats*100f)/100f);
        return AverageStats;
    }
    
    
    
    ///  this does the table contents basically pulls from database important things about the stats
    /// reused and edited code from teacehr stats package
    ///
    public static DefaultTableModel RowsInTable(int StudentID){

        DefaultTableModel StatsTable = new DefaultTableModel();
        
        
        StatsTable.addColumn("Topic");
        StatsTable.addColumn("Topic Stats ( % ) ");
        StatsTable.addColumn("Questions Correct");
        StatsTable.addColumn("Questions Done");
        

        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            // so basicallt it gets from 3 different tables and JOINS them and then uses where class from whatever studentid is  
            //then orders by topic
            // i also put the statement over 3 lines cause it was soo long
            ResultSet results = statement.executeQuery("SELECT Topic, Score, questions_done, NumOfCorrectAnswers FROM TopicStats ts "
                    + "JOIN Student s "
                    + "ON ts.Student_id = s.Student_id JOIN Topic t "
                    + "ON ts.Topic_id = t.Topic_id  WHERE s.Student_id = "+ StudentID +" "
                    + "ORDER BY t.Topic ASC ");

            while (results.next()){
                String Topic = results.getString("Topic");
                Float Score = results.getFloat("Score");
                String CorrectNumOfQuesitons = results.getString("NumOfCorrectAnswers");
                String QuestionsDone = results.getString("questions_done");
                
                
                StatsTable.addRow(new Object [] {Topic ,Score,CorrectNumOfQuesitons,  QuestionsDone}); 
            }
            
            return StatsTable;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
