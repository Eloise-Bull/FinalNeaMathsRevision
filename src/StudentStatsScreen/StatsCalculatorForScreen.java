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

/**
 *
 * @author 4-EBULL
 */
public class StatsCalculatorForScreen {
    
    public static ArrayList<String> SpecificTopicQuestions(int topic){
        ArrayList<String> QuestionsList = new ArrayList<>();
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT Question FROM Questions WHERE Topic_Id = '" + topic + "'");
            while (results.next()){
                String StatsValue = results.getString("Question");
                QuestionsList.add(StatsValue);
            }
            return QuestionsList;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    public static float CalculatingAverageStats(){
        ArrayList<Float> AllStats = ConnectTheDatabase.ArrayListStudentStats();
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
