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

/**
 *
 * @author 4-EBULL
 */
public class GettingAssignmentsForTheTable {
    // to set column names
    // get topics. 
    //jTableName.addItem 
    // wjhat i want in the table = id, done, resource or quiz, num of questoins, percent done, date, topic.
    
    
    
    ///////////// THIS IS UNFINISHED I DIDNT HAVE ACCESS TO MY SQL SO FIX IT !!
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    // so im using this for the colum names
    public static ArrayList<String> ColumsInTable(int StudentID){
        ArrayList<String> ListOfColumnNames = new ArrayList<>();
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT AssignmentInfoId,Resource, NumOfQuizQuestions, quiz_percentage, DueDate, Done FROM AssignmentInfo WHERE Assigned_id = (SELECT AssignedId FROM Assigned WHERE StudentId = " + StudentID + ")");
            while (results.next()){
                String TopicForList = results.getString("Topic");
                ListOfColumnNames.add(TopicForList);
            }
            return ListOfColumnNames;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
