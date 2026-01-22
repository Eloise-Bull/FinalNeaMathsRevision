/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewAssignmentsScreen;

import static ConnectTheDatabase.ConnectTheDatabase.TheConnectionToDatabase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 4-EBULL
 */
public class GettingAssignmentsForTheTable {
    // to set column names
    // get topics. 
    //jTableName.addItem 
    // wjhat i want in the table = id, done, resource or quiz, num of questoins, percent done, date, topic.
    
    
    ///  this does the table contents basically pulls from database important things about the stats
    /// reused and edited code from teacehr stats package
    ///
        
    public static DefaultTableModel RowsInTable(int StudentID, boolean completed){

        DefaultTableModel AssignmentTable = new DefaultTableModel();
        
        // kept columns in order the whole class so as to not mess around the 
        AssignmentTable.addColumn("Topic");
        AssignmentTable.addColumn("resource");
        AssignmentTable.addColumn("Num of quiz questions");
        AssignmentTable.addColumn("percentage of quiz done");
        AssignmentTable.addColumn("Done");
        AssignmentTable.addColumn("DueDate");
        

        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            // so basicallt it gets from 3 different tables and JOINS them and then uses where class from whatever studentid is  
            //then orders by topic
            // i also put the statement over 3 lines cause it was soo long
            ResultSet results = statement.executeQuery("SELECT Title, Resource, NumOfQuizQuestions,PercentageOfQuizDone, Done, "
                    + "DueDate FROM Assigned a "
                    + "JOIN Student s ON a.StudentId = s.Student_id "
                    + "JOIN AssignmentInfo ai ON a.AssignmentInfoId = ai.AssignmentInfo_id "
                    + "LEFT JOIN Resources r ON ai.ResourceID = r.ResourceId "
                    + "WHERE s.Student_id = " +StudentID+" ORDER BY DueDate ASC;");

            while (results.next()){
                String Topic = results.getString("Title");
                String Resource = results.getString("Resource");
                int NumOfQuizQuestions = results.getInt("NumOfQuizQuestions");
                Float PercentageOfQuizDone = results.getFloat("PercentageOfQuizDone");
                Boolean Done = results.getBoolean("Done");
                System.out.println(Done);
                String DueDate = results.getString("DueDate");
                // change from boolean to String to make readability easier for user
                if (!completed) {
                    if (!Done) {
                        AssignmentTable.addRow(new Object [] {Topic,Resource,NumOfQuizQuestions,
                        PercentageOfQuizDone,completed,DueDate }); 
                    }   
                }
                else{
                    if (Done) {
                        AssignmentTable.addRow(new Object [] {Topic,Resource,NumOfQuizQuestions,
                        PercentageOfQuizDone,completed,DueDate });  
                    }
                }
            }
            
            return AssignmentTable;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
        
    }
}