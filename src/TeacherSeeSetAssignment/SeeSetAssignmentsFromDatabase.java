/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TeacherSeeSetAssignment;

import static ConnectTheDatabase.ConnectTheDatabase.TheConnectionToDatabase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author elois
 */
public class SeeSetAssignmentsFromDatabase {
    /// this does the table contents basically pulls from database important things about the stats
    /// reused and edited code from teacehr stats package
    /// @param ClassID
    /// @return 
    ///
    public static DefaultTableModel DueNext(int ClassID){

        DefaultTableModel AssignmentTable = new DefaultTableModel();
        
        
        // due next needs ot be after or = to today. so i need todays date 
        LocalDate today = LocalDate.now();
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
                    + "WHERE ai.ClassID = " + ClassID + " AND ai.DueDate = ( SELECT DueDate FROM AssignmentInfo "
                    + "WHERE ClassID = " + ClassID + " AND DueDate >= '"+today+"' ORDER BY DueDate DESC LIMIT 1)");

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
        
    public static DefaultTableModel BothCompletedAndUncompletedAssignmentsTable(int ClassID, String TypeOfAssignment){
        
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
            ResultSet results = statement.executeQuery("SELECT AssignmentInfo_Id, AssignedId,username, S_Name, Title, Resource, NumOfQuestionsDone,"
                    +" NumOfQuizQuestions,Done, DueDate FROM Assigned a JOIN Student s ON a.StudentId = s.Student_id "
                    +"JOIN AssignmentInfo ai ON a.AssignmentInfoId = ai.AssignmentInfo_id " 
                    +"LEFT JOIN Resources r ON ai.ResourceID = r.ResourceId " 
                    +"WHERE ai.ClassID = "+ClassID+" ORDER BY DueDate ASC");
            
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
                
                // change from boolean to String to make readability easier for user
                String completed = "Not Done";
                if (Done) {
                    completed = "Done";
                }
                String DueDate = results.getString("DueDate");

                if (!Done&&"Uncompleted".equals(TypeOfAssignment)) {
                    
                    AssignmentTable.addRow(new Object [] {AssignmentID+"-"+AssignedID,Username,Name,Topic,Resource,NumOfQuestionsDone,NumOfQuizQuestions,
                    completed,DueDate });    
                }
                else if (Done && "Completed".equals(TypeOfAssignment)){
                    AssignmentTable.addRow(new Object [] {AssignmentID+"-"+AssignedID,Username,Name,Topic,Resource,NumOfQuestionsDone,NumOfQuizQuestions,
                    completed,DueDate });  
                }
            }
            
            return AssignmentTable;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
        
    public static DefaultTableModel AllAssignments(int ClassID){

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
            ResultSet results = statement.executeQuery("SELECT AssignmentInfo_Id, AssignedId,username, S_Name, Title, Resource, NumOfQuestionsDone, NumOfQuizQuestions,"
                    + " Done, DueDate FROM Assigned a "
                    + "JOIN Student s ON a.StudentId = s.Student_id "
                    + "JOIN AssignmentInfo ai ON a.AssignmentInfoId = ai.AssignmentInfo_id "
                    + "LEFT JOIN Resources r ON ai.ResourceID = r.ResourceId "
                    + "WHERE ai.ClassID = " + ClassID
                    + " ORDER BY DueDate ASC");

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
    
        
    public static DefaultTableModel SpecificStudentAssignment(String Name, int ClassID){

        DefaultTableModel AssignmentTable = new DefaultTableModel();
        
        // kept columns in order the whole class so as to not mess around the 
        AssignmentTable.addColumn("Id");
        AssignmentTable.addColumn("Username");
        AssignmentTable.addColumn("Name");
        AssignmentTable.addColumn("Topic");
        AssignmentTable.addColumn("resource");
        AssignmentTable.addColumn("NumOfQuestionsDone");
        AssignmentTable.addColumn("Num of quiz questions");
        AssignmentTable.addColumn("Done");
        AssignmentTable.addColumn("DueDate");
        

        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            // so basicallt it gets from 3 different tables and JOINS them and then uses where class from whatever studentid is  
            //then orders by topic
            // i also put the statement over 3 lines cause it was soo long
            ResultSet results = statement.executeQuery("SELECT AssignmentInfo_Id, AssignedId,username, S_Name, Title, Resource,NumOfQuestionsDone, NumOfQuizQuestions,"
                    + " Done, DueDate FROM Assigned a "
                    + "JOIN Student s ON a.StudentId = s.Student_id "
                    + "JOIN AssignmentInfo ai ON a.AssignmentInfoId = ai.AssignmentInfo_id "
                    + "LEFT JOIN Resources r ON ai.ResourceID = r.ResourceId "
                    + "WHERE S_Name = '" + Name
                    + "' AND s.Class_id = " + ClassID
                    + " ORDER BY DueDate ASC");
            
            //////FROM Assigned a "
                    //+ "JOIN Student s ON a.StudentId = s.Student_id "
                   // + "JOIN AssignmentInfo ai ON a.AssignmentInfoId = ai.AssignmentInfo_id "
                   // + "LEFT JOIN Resources r ON ai.ResourceID = r.ResourceId "
                    //+ "WHERE s.Student_id = " +StudentID+" ORDER BY DueDate ASC;");

            while (results.next()){
                int AssignmentID = results.getInt("AssignmentInfo_Id");
                int AssignedID = results.getInt("AssignedId");
                String username = results.getString("username");
                String S_Name = results.getString("S_Name");
                String Topic = results.getString("Title");
                String Resource = results.getString("Resource");
                int NumOfQuizQuestions = results.getInt("NumOfQuizQuestions");
                int NumOfQuestionsDone = results.getInt("NumOfQuestionsDone");
                Boolean Done = results.getBoolean("Done");
                System.out.println(Done);
                // change from boolean to String to make readability easier for user
                String completed = "Not Done";
                if (Done) {
                    completed = "Done";
                }
                String DueDate = results.getString("DueDate");
                AssignmentTable.addRow(new Object [] {AssignmentID+"-"+AssignedID,username,S_Name,Topic,Resource,NumOfQuestionsDone, NumOfQuizQuestions,completed,DueDate });
            }
            return AssignmentTable;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static DefaultTableModel OverDue(int ClassID, LocalDate Date){

        DefaultTableModel AssignmentTable = new DefaultTableModel();

        // kept columns in order the whole class so as to not mess around the 
        AssignmentTable.addColumn("Id");
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
            ResultSet results = statement.executeQuery("SELECT AssignmentInfo_Id, AssignedId, Title, Resource, NumOfQuestionsDone, NumOfQuizQuestions,"
                    + " Done, DueDate FROM Assigned a "
                    + "JOIN Student s ON a.StudentId = s.Student_id "
                    + "JOIN AssignmentInfo ai ON a.AssignmentInfoId = ai.AssignmentInfo_id "
                    + "LEFT JOIN Resources r ON ai.ResourceID = r.ResourceId "
                    + "WHERE s.Class_id = " + ClassID 
                    + " AND Done = false AND DueDate < '"+Date+"' ORDER BY DueDate");

            while (results.next()){
                int AssignmentID = results.getInt("AssignmentInfo_Id");
                int AssignedID = results.getInt("AssignedId");
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
                AssignmentTable.addRow(new Object [] {AssignmentID+"-"+AssignedID,Topic,Resource,NumOfQuestionsDone, NumOfQuizQuestions,completed,DueDate });
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
    
    public static Boolean UpdateDateForAssignment(int days, int AssignmentID){
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            statement.execute("UPDATE AssignmentInfo SET DueDate = "
                    + "DATE_ADD(DueDate, INTERVAL "+days+ " day ) WHERE AssignmentInfo_id = " + AssignmentID);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static Boolean DeleteAssignment(int AssignmentID){
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM Assigned WHERE AssignmentInfoId = " + AssignmentID);
            statement.execute("DELETE FROM AssignmentInfo WHERE AssignmentInfo_id = " + AssignmentID);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
