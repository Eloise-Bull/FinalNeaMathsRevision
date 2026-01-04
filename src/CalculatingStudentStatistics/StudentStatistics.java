/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CalculatingStudentStatistics;
import ConnectTheDatabase.ConnectTheDatabase;
import java.util.ArrayList;
import ConnectTheDatabase.ConnectTheDatabase;
import Login.Login;

/**
 *
 * @author 4-EBULL
 */
public class StudentStatistics {
    
    private int count;
    private int grade;
    private int least;
    
    
    public void Stats (){
        this.count = 0;
        this.grade = 0;
    }
    
    public void addNewScore(int NewStat){
        this.count = count + 1;
        grade = this.grade + NewStat;        
        this.grade = grade / this.count; 
    }
    
    static ArrayList<Float> SortedTopicStatsList;
    
    /// resets the list everytime u do question from targeted
    public static ArrayList<Float> getNewTopicStatsList(int studentId) {
        return ConnectTheDatabase.ArrayListTopicStats();
    }
    
    // bubble sorting topic statistics for the quiz.
    // sort it 
    // keep both array lists
    //pick worst topics from stats
    // 
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //////////////////////////////////////////////
    ///both of these unused but there for just in case later
    public static ArrayList<Float> SortingTheStats (ArrayList<Float> TopicStatslist){
        // did this so that it doesnt vhange the original topic stats list for next time
        ArrayList<Float> list = new ArrayList<>(TopicStatslist);
        // temp to check working
        System.out.println(list);
        for ( int i = 0; i < list.size()- 1; i ++) {
            float temp;
            for ( int j = 0; j < list.size() - (i+1); j ++) {
                if ( list.get(j) > list.get(j+1)){
                temp = list.get(j);
                list.set(j, list.get(j+1));
                list.set(j+1, temp);
                }
            }
            
        }
        return list;

    }
    
    
    
    public static float PushScore(){
        int Student_id = Login.StudentID();
        ArrayList<Float> UpdatedList = getNewTopicStatsList(Student_id);
        ArrayList<Float> SortedList = SortingTheStats(UpdatedList);
        float score = SortedList.get(0);
        return score;
    }
    
            
        

}

