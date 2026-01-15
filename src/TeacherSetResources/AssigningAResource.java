/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TeacherSetResources;
import static ConnectTheDatabase.ConnectTheDatabase.TheConnectionToDatabase;
import Login.Login;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.*;
/**
 *
 * @author 4-EBULL
 */
public class AssigningAResource {
    private static int count = 0;
    private static int AssignmentIdLastUpdated;
    private static int NumOfStudents;
    
    public static ListModel<String> ResourceListToScreen(String ResourceType){
        DefaultListModel<String> ResourceList = new DefaultListModel<>();
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            if ( "All".equals(ResourceType) ){
                ResultSet results = statement.executeQuery("SELECT Resource FROM Resources;");
                while (results.next()){
                    count = count + 1;
                    String StatsValue = results.getString("Resource");
                    ResourceList.addElement(count +": " +StatsValue);
                }
            }
            else {
                ResultSet results = statement.executeQuery("SELECT Resource FROM Resources WHERE Topic_Id = ( SELECT Topic_Id FROM Topic WHERE Topic ='" + ResourceType + "');");
                while (results.next()){
                    count = count + 1;
                    String StatsValue = results.getString("Resource");
                    ResourceList.addElement(count + ": " +StatsValue);
                }
            }
            
            // need to reset count as when i was clicking button multiple times the count was still adding up
            System.out.println(count);
            count = 0;
            return ResourceList;
            
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
        
    }
    
    public static void SetResourceToClassOrStudent(String ResourceType, int num, int classID, String DueDate){
        
        ArrayList<String> List = new ArrayList<>();
        String resource = null;
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            if ( "All".equals(ResourceType) ){
                ResultSet results = statement.executeQuery("SELECT Resource FROM Resources;");
                while (results.next()){
                    String StatsValue = results.getString("Resource");
                    List.add(StatsValue);
                }
                // its minus one cause the displayed number is count + 1 for the first item. 
                resource = List.get(num - 1);
            }
            else {
                ResultSet results = statement.executeQuery("SELECT Resource FROM Resources WHERE Topic_Id = ( SELECT Topic_Id FROM Topic WHERE Topic ='" + ResourceType + "');");
                while (results.next()){
                    String StatsValue = results.getString("Resource");
                    List.add(StatsValue);
                }
                // its minus one cause the displayed number is count + 1 for the first item. 
                resource = List.get(num - 1);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        /////////////////////////////////////////////////////////////////////////////////////
        // this may just return the resourceID 0 ???? CHECK WHEN FICED THE DATABASE TABLE 
        
        int resourceID = 0;
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT ResourceId FROM Resources WHERE resource = '" + resource + "';");
            if (results.next()){
               resourceID = results.getInt("ResourceId");
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        
        // how many students in the class 
        // cant do ordering for this incase student gets deleted so num of ids arent accurate 
        ArrayList<String> StudentList = new ArrayList<>();
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT Student_Id FROM Student WHERE Class_Id = '" + classID + "'");
            while (results.next()){
                String StatsValue = results.getString("Student_Id");
                StudentList.add(StatsValue);
            }
            NumOfStudents = StudentList.size();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        
        
        try (Connection connection = TheConnectionToDatabase()){
            int TeacherID = Login.InfoOfUserForThisLoginSession.TeacherId ;
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO AssignmentInfo ( ClassID, ResourceID, Title, DueDate) VALUES ( " + classID+ "," +resourceID +"," +"'" +ResourceType + "'" + "," + "'" + DueDate + "'" +");");        
            // gets the assignmentinfoid for the last thing added so i can add it into the assigned table 
            ResultSet results = statement.executeQuery("SELECT LAST_INSERT_ID() AS id;");
            if (results.next()){
               AssignmentIdLastUpdated = results.getInt("id");
            }
            // for each studenet in class x insert assigned    
            //insert assigned id assignnmentinfo id form last code, teacher id and false.
            // as login doesnt work yet ive temp set teach id to 1.
            for ( int i = 0; i < NumOfStudents; i++) {
                int StudentIdforClassAssignment = Integer.valueOf(StudentList.get(i));
                statement.execute("INSERT INTO Assigned ( AssignmentInfoId, StudentId, TeacherId, Done) VALUES ( " + AssignmentIdLastUpdated+ "," + StudentIdforClassAssignment+ "," + TeacherID+ "," + false + ");");
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }  
    // resued code from resources as is the same thing. 
    // gets topic for combo box allowing it to be easier when topics / resources are added
    public static ArrayList<String> SetBox(){
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
}
