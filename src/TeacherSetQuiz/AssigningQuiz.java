/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TeacherSetQuiz;

import static ConnectTheDatabase.ConnectTheDatabase.TheConnectionToDatabase;
import Login.Login;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author 4-EBULL
 */
public class AssigningQuiz {
        private static int NumOfStudents;
        private static int AssignmentIdLastUpdated;
        private static int TeacherID = Login.TeacherID();
        
    public ArrayList<String> TopicsForDropDownBox(){
        ArrayList<String> TopicList = new ArrayList<>();
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT Topics FROM Topic");
            while (results.next()){
                String Topic = results.getString("Topics");
                TopicList.add(Topic);
            }
            return TopicList;
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
        
    public static void PuttingQuizDataIntoDataBase(int numOfQuestions, int ClassID,String DueDate, String Topic){
        
        ArrayList<String> StudentList = new ArrayList<>();
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT Student_Id FROM Student WHERE Class_Id = '" + ClassID + "'");
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
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO AssignmentInfo ( ClassID, NumOfQuizQuestions, Title, DueDate) VALUES ( " + ClassID+ "," +numOfQuestions +"," +"'" +Topic + "'" + "," + "'" + DueDate + "'" +");");        

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
    
    //public static void 
}
