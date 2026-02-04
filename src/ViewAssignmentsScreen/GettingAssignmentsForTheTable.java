/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewAssignmentsScreen;

import static ConnectTheDatabase.ConnectTheDatabase.TheConnectionToDatabase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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
        // if compkleted u dont need the do column 
        if (completed){
            AssignmentTable.addColumn("Id");
            AssignmentTable.addColumn("Topic");
            AssignmentTable.addColumn("resource");
            AssignmentTable.addColumn("Num Of Questions done");
            AssignmentTable.addColumn("Num of quiz questions");
            AssignmentTable.addColumn("Num Of Questions done");
            AssignmentTable.addColumn("Done");
            AssignmentTable.addColumn("DueDate");
        }
        else {
            AssignmentTable.addColumn("Id");
            AssignmentTable.addColumn("Topic");
            AssignmentTable.addColumn("resource");
            AssignmentTable.addColumn("Num Of Questions done");
            AssignmentTable.addColumn("Num of quiz questions");
            AssignmentTable.addColumn("Done");
            AssignmentTable.addColumn("DueDate");
            AssignmentTable.addColumn("Do");
        }
        

        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            // so basicallt it gets from 3 different tables and JOINS them and then uses where class from whatever studentid is  
            //then orders by topic
            // i also put the statement over 3 lines cause it was soo long
            ResultSet results = statement.executeQuery("SELECT AssignedId,AssignmentInfo_Id, Title, Resource, NumOfQuizQuestions,NumOfQuestionsDone, Done, "
                    + "DueDate FROM Assigned a "
                    + "JOIN Student s ON a.StudentId = s.Student_id "
                    + "JOIN AssignmentInfo ai ON a.AssignmentInfoId = ai.AssignmentInfo_id "
                    + "LEFT JOIN Resources r ON ai.ResourceID = r.ResourceId "
                    + "WHERE s.Student_id = " +StudentID+" ORDER BY DueDate ASC;");

            while (results.next()){
                int Assignedid = results.getInt("AssignedId");
                int Assignemntinfoid = results.getInt("AssignmentInfo_Id");
                String Topic = results.getString("Title");
                String Resource = results.getString("Resource");
                int NumOfQuizQuestions = results.getInt("NumOfQuizQuestions");
                int QuestionsDone = results.getInt("NumOfQuestionsDone");
                Boolean Done = results.getBoolean("Done");
                String DueDate = results.getString("DueDate");
                // change from boolean to String to make readability easier for user
                if (!completed) {
                    if (!Done) {
                        AssignmentTable.addRow(new Object [] {Assignedid+"-"+Assignemntinfoid, Topic,Resource,QuestionsDone,NumOfQuizQuestions,
                        completed,DueDate,"Select"}); 
                    }   
                }
                else{
                    if (Done) {
                        AssignmentTable.addRow(new Object [] {Assignedid+"-"+Assignemntinfoid, Topic,Resource,QuestionsDone,NumOfQuizQuestions,
                        completed,DueDate });
                        
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
    
    public static DefaultTableModel DueNext(int StudentID){

        DefaultTableModel AssignmentTable = new DefaultTableModel();

        // kept columns in order the whole class so as to not mess around the 
        AssignmentTable.addColumn("Id");
        AssignmentTable.addColumn("Username");
        AssignmentTable.addColumn("Name");
        AssignmentTable.addColumn("Topic");
        AssignmentTable.addColumn("resource");
        AssignmentTable.addColumn("Num Of Questions done");
        AssignmentTable.addColumn("Num of quiz questions");
        AssignmentTable.addColumn("Done");
        AssignmentTable.addColumn("DueDate");
        
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            // so basicallt it gets from 3 different tables and JOINS them and then uses where class from whatever studentid is  
            //then orders by topic
            // i also put the statement over 3 lines cause it was soo long
            ResultSet results = statement.executeQuery("SELECT AssignmentInfo_Id, AssignedId, username, S_Name, Title, Resource, NumOfQuestionsDone, NumOfQuizQuestions,"
                    + " Done, DueDate FROM Assigned a "
                    + "JOIN Student s ON a.StudentId = s.Student_id "
                    + "JOIN AssignmentInfo ai ON a.AssignmentInfoId = ai.AssignmentInfo_id "
                    + "LEFT JOIN Resources r ON ai.ResourceID = r.ResourceId "
                    + "WHERE Student_id = " +StudentID 
                    + " ORDER BY DueDate DESC LIMIT 1");

            while (results.next()){
                int AssignmentID = results.getInt("AssignmentInfo_Id");
                int AssignedID = results.getInt("AssignedId");
                String Username = results.getString("username");
                String Name = results.getString("S_Name");
                String Topic = results.getString("Title");
                String Resource = results.getString("Resource");
                int NumOfQuizQuestions = results.getInt("NumOfQuizQuestions");
                int NumOfQuestionsDone = results.getInt("NumOfQuestionsDone");
                Boolean Done = results.getBoolean("Done");
                String DueDate = results.getString("DueDate");
                String completed = "Not Done";
                if (Done) {
                    completed = "Done";
                }
                AssignmentTable.addRow(new Object [] {AssignmentID+"-"+AssignedID,Username,Name,Topic,Resource,NumOfQuestionsDone, NumOfQuizQuestions,completed,DueDate });
            }
            return AssignmentTable;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    

    //////////////// getting the num of questions to do
    ///
    public static int questionsLeftToDo(int AssignmentID, int AssignedID){
        // need to get the number of quesitons done and questions left
        int QuestionsLeftToDo = -1;
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT NumOfQuestionsDone, "
                    + "NumOfQuizQuestions FROM Assigned a "
                    + "JOIN AssignmentInfo ai "
                    + "WHERE ai.AssignmentInfo_id = " + AssignmentID
                    + " AND a.AssignedId = " + AssignedID);
            if (results.next()){
                int NumOfQuestionsDone= results.getInt("NumOfQuestionsDone");
                int NumOfQuizQuestionsSet =results.getInt("NumOfQuizQuestions");
                QuestionsLeftToDo = NumOfQuizQuestionsSet - NumOfQuestionsDone;
               
            }
            return QuestionsLeftToDo;
        }
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    
}