/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TeacherClassStats;
import static ConnectTheDatabase.ConnectTheDatabase.TheConnectionToDatabase;
import Login.Login;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author 4-EBULL
 */
public class CalculatingClassStats {
    private static float AverageClass;
    
    public static float CalculatingAverageClassStats(){
        ArrayList<Float> AllStudents = ArrayListStudentStats();
        int count = 0;
        for ( int i = 0; i < AllStudents.size(); i ++){
            AverageClass = AllStudents.get(i) + AverageClass;
           count = count + 1;
        }
        AverageClass =  AverageClass/count;
        AverageClass = (Math.round(AverageClass*100f)/100f);
        return AverageClass;
    }
    
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// creates an array list for the student stats so i can calculate the averages of a class

    // returns the array for the stats. temporaty storage, a float cause stats = decimal
    // temporary class_id = set to one cause then i can test wihtout them loging in         
    public static ArrayList<Float> ArrayListStudentStats(){
        int Class_id = Login.InfoOfUserForThisLoginSession.UserClassID ;
        ArrayList<Float> StudentStatslist = new ArrayList<>();
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT Score FROM TopicStats "
                    + "WHERE Student_id IN ( SELECT Student_id FROM Student WHERE Class_id = " + Class_id+")");
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
    
    // this creates a default table so that i can put it in the table
    
    public static DefaultTableModel RowsInTable(int ClassID){

        DefaultTableModel StatsTable = new DefaultTableModel();
        
        StatsTable.addColumn("Student Username");
        StatsTable.addColumn("Student Name");
        StatsTable.addColumn("Topic");
        StatsTable.addColumn("Topic Stats");

        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            // so basicallt it gets from 3 different tables and JOINS them and then uses where class from whatever class 
            //teacher was and then orders by name so that the students names / scores and stuff are together
            // i also put the statement over 3 lines cause it was soo long
            ResultSet results = statement.executeQuery("SELECT Score, username, S_Name, Topic FROM TopicStats ts JOIN Student s "
                    + "ON ts.Student_id = s.Student_id JOIN Topic t ON ts.Topic_id = t.Topic_id  WHERE s.Class_id = "+ ClassID +" "
                            + "ORDER BY s.S_Name ASC ");

            while (results.next()){
                String Topic = results.getString("Topic");
                String NAME = results.getString("S_name");
                Float Score = results.getFloat("Score");
                String Username = results.getString("username");
                
                StatsTable.addRow(new Object [] {Username ,NAME,  Topic, Score}); 
            }
            
            return StatsTable;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    // does the combo box for the Topics
    public static ArrayList<String> SetTopicsForBox(){
        ArrayList<String> ListOfTopicNames = new ArrayList<>();
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT Topic FROM Topic");
            while (results.next()){
                String TopicForList = results.getString("Topic");
                ListOfTopicNames.add(TopicForList);
            }
            return ListOfTopicNames;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static ArrayList<String> SetUsernamesForBox(int ClassID){
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
    
    public DefaultTableModel GetTheStatsForUsernameOrTopic(int ClassID, String HowToSort){
        DefaultTableModel StatsTable = new DefaultTableModel();
        
        StatsTable.addColumn("Student Username");
        StatsTable.addColumn("Student Name");
        StatsTable.addColumn("Topic");
        StatsTable.addColumn("Topic Stats");

        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            // so basicallt it gets from 3 different tables and JOINS them and then uses where class from whatever class 
            //teacher was and then orders by name so that the students names / scores and stuff are together
            // i also put the statement over 3 lines cause it was soo long
            ResultSet Results = statement.executeQuery("SELECT EXISTS ( "
                    + "SELECT 1 FROM Student WHERE username = '"+ HowToSort+"')");
            if ( Results.next()){
                // means there is a username so get their stats
                ResultSet results = statement.executeQuery("SELECT Score, username, S_Name, Topic FROM TopicStats ts JOIN Student s "
                    + "ON ts.Student_id = s.Student_id JOIN Topic t ON ts.Topic_id = t.Topic_id  WHERE s.Class_id = "+ ClassID +" "
                            + "ORDER BY s.S_Name ASC ");
                while (results.next()){
                String Topic = results.getString("Topic");
                String NAME = results.getString("S_name");
                Float Score = results.getFloat("Score");
                String Username = results.getString("username");
                
                StatsTable.addRow(new Object [] {Username ,NAME,  Topic, Score}); 
                }
            }
            else {
                // its a topic not a username 
                ResultSet results = statement.executeQuery("SELECT Score, username, S_Name, Topic FROM TopicStats ts JOIN Student s "
                    + "ON ts.Student_id = s.Student_id JOIN Topic t ON ts.Topic_id = t.Topic_id  WHERE s.Class_id = "+ ClassID +" "
                            + "ORDER BY s.S_Name ASC ");
                while (results.next()){
                String Topic = results.getString("Topic");
                String NAME = results.getString("S_name");
                Float Score = results.getFloat("Score");
                String Username = results.getString("username");
                
                StatsTable.addRow(new Object [] {Username ,NAME,  Topic, Score}); 
                }
            }
            return StatsTable;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}