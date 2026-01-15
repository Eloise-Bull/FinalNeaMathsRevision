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
    
    
    
    ///////////// THIS IS UNFINISHED I DIDNT HAVE ACCESS TO MY SQL SO FIX IT !!
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    // so im using this for the colum names
    public static DefaultTableModel RowsInTable(int StudentID, boolean completed){
        ArrayList<String> row = new ArrayList<>();
        DefaultTableModel AssignmentsTable = new DefaultTableModel();
        AssignmentsTable.addColumn("Id");
        AssignmentsTable.addColumn("Number Of Quiz Questions");
        AssignmentsTable.addColumn("Title");
        AssignmentsTable.addColumn("Due-Date");
        
        int count = 0;
        try (Connection connection = TheConnectionToDatabase()){
            System.out.println("Done");
            if (completed){
                Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery("SELECT AssignmentInfo_Id,NumOfQuizQuestions,Title, DueDate FROM AssignmentInfo WHERE AssignmentInfo_Id IN (SELECT AssignmentInfo_Id FROM Assigned WHERE StudentId = " + StudentID + ")");
                System.out.println("Done");
                while (results.next()){
                    //(results).getClass();
                    //Object Value = results.getObject("count");
                    //System.out.println("Done");
                    //row.add(String.valueOf(Value));
                    
                    int id = results.getInt("AssignmentInfo_Id");
                    int NumOfQuizQuestions = results.getInt("NumOfQuizQuestions");
                    String Title = results.getString("Title");
                    String duedate = results.getString("DueDate");
                    System.out.println("Done");
                    count = count + 1;
                    if ( count == 5) {
                        AssignmentsTable.addRow(new Object [] { id, NumOfQuizQuestions, Title, duedate});
                        row = null;
                        System.out.println("Done");
                    }
                }
            }
            
            return AssignmentsTable;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

public static DefaultTableModel TheRowsInTable(int StudentID, boolean completed){
        ArrayList<String> row = new ArrayList<>();
        DefaultTableModel AssignmentsTable = new DefaultTableModel();
        AssignmentsTable.addColumn("Id");
        AssignmentsTable.addColumn("Title");
        AssignmentsTable.addColumn("Resource");
        AssignmentsTable.addColumn("Number Of Quiz Questions");
        AssignmentsTable.addColumn("Percentage Done");
        AssignmentsTable.addColumn("Done");
        AssignmentsTable.addColumn("Due-Date");
        
        int count = 0;
        try (Connection connection = TheConnectionToDatabase()){
            
                Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery("SELECT * FROM AssignmentInfo WHERE AssignmentInfo_Id IN (SELECT AssignmentInfo_Id FROM Assigned WHERE StudentId = " + 1 + ")");
                System.out.println("Done");
                while (results.next()){
                    row.add(results.getString("*"));
                    count = count + 1;
                    if ( count == 5) {
                        AssignmentsTable.addRow(new Object [] {});
                        row = null;
                        System.out.println("Done");
                    }
                }
                return AssignmentsTable;
                
            }
            
            catch (Exception e) {
                e.printStackTrace();
                return null;
        }
    }
}
