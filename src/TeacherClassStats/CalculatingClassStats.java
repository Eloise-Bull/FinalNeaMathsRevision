/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TeacherClassStats;
import ConnectTheDatabase.ConnectTheDatabase;
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
public class CalculatingClassStats {
    private static float AverageClass;
    
    public static float CalculatingAverageClassStats(){
        ArrayList<Float> AllStudents = ConnectTheDatabase.ArrayListStudentStats();
        int count = 0;
        for ( int i = 0; i < AllStudents.size(); i ++){
            AverageClass = AllStudents.get(i) + AverageClass;
           count = count + 1;
        }
        AverageClass =  AverageClass/count;
        AverageClass = (Math.round(AverageClass*100f)/100f);
        return AverageClass;
    }
    
    
    // this creates a default table so that i can put it in the table
    
    public static DefaultTableModel RowsInTable(int ClassID){
        ArrayList<String> row = new ArrayList<>();
        ArrayList<String> DataFromTopicStats = new ArrayList<>();
        DefaultTableModel StatsTable = new DefaultTableModel();
        StatsTable.addColumn("Topic");
        StatsTable.addColumn("Student Username");
        StatsTable.addColumn("Student Name");
        StatsTable.addColumn("Topic Stats");
        
        
        int count = 0;
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            // to get -  TOpicId, Studentid, Score
            ResultSet results = statement.executeQuery("SELECT Topic_Id, Student_id, Score FROM TopicStats WHERE Student_id IN (SELECT Student_id FROM Student WHERE Class_id = " + ClassID + ")");

            while (results.next()){
                    
                int Topicid = results.getInt("Topic_Id");
                int StudentID = results.getInt("Student_id");
                Float Score = results.getFloat("Score");
                
                DataFromTopicStats.add();
                String Topic = returnTopic(Topicid);
                String Username = null;
                String Name = null;
                
                ResultSet Results2 = statement.executeQuery("SELECT Topic FROM Topic WHERE Topic_Id = " + Topicid);
                    if (Results2.next()){
                        Topic = results.getString("Topic");
                    }
                    
                
                ResultSet Results3 = statement.executeQuery("SELECT username, S_name FROM Student WHERE StudentId = " + StudentID);
                    if (Results3.next()){
                        Username = results.getString("username");
                        Name = results.getString("S_name");
                    }
                
                count = count + 1;
                if ( count == 4) {
                    StatsTable.addRow(new Object [] { Topic, Username,Name, Score});
                    row = null;
                }
                
            }
            
            return StatsTable;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // had to make two more methods cause basically u cant have 2 results sets cause the first one has to be finished 
    //java.sql.SQLException: Operation not allowed after ResultSet closed
    public static String returnTopic (int Topicid) {
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT Topic FROM Topic WHERE Topic_Id = " + Topicid);
            String Topic = null;
            if (results.next()){
                Topic = results.getString("Topic");
            }
            return Topic;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static String[] returnStudentData (int StudentID) {
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT username, S_name FROM Student WHERE StudentId = " + StudentID);
            String username = null;
            String name = null;
            String [] userNameThenName = new String[2];
            if (results.next()){
                username = results.getString("username");
                name = results.getString("S_name");
                userNameThenName[1] = username;
                userNameThenName[2] = name;
            }
            // cant return 2 thigns from a method so i just made it a tiny array
            return userNameThenName;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
             
        
}
