/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StudentStatsScreen;

import ConnectTheDatabase.ConnectTheDatabase;
import static ConnectTheDatabase.ConnectTheDatabase.TheConnectionToDatabase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import Login.Login;

/**
 *
 * @author 4-EBULL
 */
public class StatsCalculatorForScreen {
    
    public static ArrayList<Float> averageStatsForStudent(int StudentID){
        ArrayList<Float> StatsList = new ArrayList<>();
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT Score FROM TopicStats WHERE Student_id = " + StudentID );
            while (results.next()){
                Float StatsValue = results.getFloat("Score");
                StatsList.add(StatsValue);
            }
            return StatsList;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    public static float CalculatingAverageStats(){
        int Student_id = Login.InfoOfUserForThisLoginSession.StudentId ;
        System.out.println(Student_id);
        ArrayList<Float> AllStats = averageStatsForStudent(Student_id);
        System.out.println(AllStats);
        float AverageStats = 0;
        int count = 0;
        for ( int i = 0; i < AllStats.size(); i ++){
            AverageStats = AllStats.get(i) + AverageStats;
           count = count + 1;
        }
        AverageStats =  AverageStats/count;
        AverageStats = (Math.round(AverageStats*100f)/100f);
        return AverageStats;
    }
}
