/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TeacherSeeSetAssignment;

import static ConnectTheDatabase.ConnectTheDatabase.TheConnectionToDatabase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author elois
 */
public class SeeSetAssignmentsFromDatabase {
    ///  this does the table contents basically pulls from database important things about the stats
    /// reused and edited code from teacehr stats package
    ///
    
        
        
        
        ///// UNFINISHED as i need to modifY CODE. 
        
        
        
        public static DefaultTableModel RowsInTable(int StudentID){

        DefaultTableModel StatsTable = new DefaultTableModel();
        
        
        StatsTable.addColumn("Topic");
        StatsTable.addColumn("Title, DueDate, Username, name, num of quiz questions, reosurce, percentage of quiz done, done");
        StatsTable.addColumn("Questions Correct");
        StatsTable.addColumn("Questions Done");
        

        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            // so basicallt it gets from 3 different tables and JOINS them and then uses where class from whatever studentid is  
            //then orders by topic
            // i also put the statement over 3 lines cause it was soo long
            ResultSet results = statement.executeQuery("SELECT Topic, Score, questions_done, NumOfCorrectAnswers FROM TopicStats ts "
                    + "JOIN Student s "
                    + "ON ts.Student_id = s.Student_id JOIN Topic t ON ts.Topic_id = t.Topic_id  WHERE s.Student_id = "+ StudentID +" "
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
