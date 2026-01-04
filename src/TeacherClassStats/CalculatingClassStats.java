/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TeacherClassStats;
import ConnectTheDatabase.ConnectTheDatabase;
import java.util.ArrayList;
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
    
    
             
        
}
