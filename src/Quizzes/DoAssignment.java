/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Quizzes;

import static ConnectTheDatabase.ConnectTheDatabase.TheConnectionToDatabase;
import java.sql.Connection;
import java.sql.Statement;

/**
 *
 * @author elois
 */
public class DoAssignment {
    
    // adds on the the num of assigned quesitons done for an assignment
    public void AddOneToQuestionsDone(int AssingmentId, int AssignedID,int CurrentNumOfQuestions){
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            statement.executeQuery("UPDATE Assigned SET NumOfQuestionsDone =  " + CurrentNumOfQuestions
                    + "WHERE AssignedId =" + AssignedID
                    + " AND AssignmentInfoId = " + AssingmentId);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }
 
    public void setAssignmentToDone(int AssignmentId, int AssignedID){
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            statement.execute("UPDATE Assigned SET Done = TRUE "
                    + "WHERE AssignedId =" + AssignedID
                    + " AND AssignmentInfoId = " + AssignmentId);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public void ResetVariables(){
        // are static so i can access them but need to reset when new quiz
        Quizzes.QuizDetails.CurrentStats = 0f;
        Quizzes.QuizDetails.questionsDone = 0;
        QuizQuestions.CountingQuizStats(0, false, true);
        QuizQuestions.count(true);

    } 
}
