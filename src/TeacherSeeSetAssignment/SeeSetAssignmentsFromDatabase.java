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
        
        public static DefaultTableModel BothCompletedAndUncompletedAssignmentsTable(int ClassID, String TypeOfAssignment){

        DefaultTableModel AssignmentTable = new DefaultTableModel();
        
        // kept columns in order the whole class so as to not mess around the 
        AssignmentTable.addColumn("Username");
        AssignmentTable.addColumn("Name");
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
            ResultSet results = statement.executeQuery("SELECT username, S_Name, Title, Resource, NumOfQuizQuestions,"
                    + "PercentageOfQuizDone, Done, DueDate FROM Assigned a "
                    + "JOIN Student s ON a.StudentId = s.Student_id "
                    + "JOIN AssignmentInfo ai ON a.AssignmentInfoId = ai.AssignmentInfo_id "
                    + "LEFT JOIN Resources r ON ai.ResourceID = r.ResourceId "
                    + "WHERE ai.ClassID = " + ClassID
                    + " ORDER BY DueDate DESC ;");

            while (results.next()){
                String Username = results.getString("username");
                String Name = results.getString("S_Name");
                String Topic = results.getString("Title");
                String Resource = results.getString("Resource");
                int NumOfQuizQuestions = results.getInt("NumOfQuizQuestions");
                Float PercentageOfQuizDone = results.getFloat("PercentageOfQuizDone");
                Boolean Done = results.getBoolean("Done");
                // change from boolean to String to make readability easier for user
                String completed = "Not Done";
                if (Done=true) {
                    completed = "Done";
                }
                String DueDate = results.getString("DueDate");
                
                // wants completed assignments and gives ones that are done
                if ("Completed".equals(TypeOfAssignment)&&Done){
                    AssignmentTable.addRow(new Object [] {Username,Name,Topic,Resource,NumOfQuizQuestions,
                    PercentageOfQuizDone,completed,DueDate });
                }
                // wants uncompleted assignments and gives ones that are not done
                if (("Uncompleted".equals(TypeOfAssignment)&&Done)){
                    AssignmentTable.addRow(new Object [] {Username,Name,Topic,Resource,NumOfQuizQuestions,
                    PercentageOfQuizDone,completed,DueDate });
                }
 
            }
            
            return AssignmentTable;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
        
        
        
        
        public static ArrayList<String> SetBox(int ClassID){
        ArrayList<String> ListOfTopicNames = new ArrayList<>();
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT S_Name FROM Student WHERE Class_id = "+ ClassID);
            while (results.next()){
                String TopicForList = results.getString("S_Name");
                ListOfTopicNames.add(TopicForList);
            }
            return ListOfTopicNames;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
