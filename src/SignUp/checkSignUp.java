/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SignUp;

import static ConnectTheDatabase.ConnectTheDatabase.TheConnectionToDatabase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author 4-EBULL
 */
public class checkSignUp {
    public Boolean AddStudent(String name, String Username, String Email, String Password, int ClassCode ){
        
        
        //////////////// STUDENT
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            boolean worked = statement.execute("INSERT INTO Student ( S_name, username,email, password_hash, Class_id) VALUES ( '" + name+ "','" + Username +"','" + Email + "','"+Password  + "'," + ClassCode + ")");        
            int StudentID = getLastInsertedID();
            // get num of topics so and for all topics set stats to zero so that then targeted works straight away
            ArrayList<Integer> Topics = returnTopicIds();
            
            if (worked) {
                for ( int i = 0; i <= Topics.size()-1; i++){
                    statement.execute("INSERT INTO TopicStats(Student_id, Topic_id, Score, questions_done, NumOfCorrectAnswers) VALUES (" + StudentID +"," + Topics.get(i) + ",0,0,0)");
                    System.out.println(Topics.get(i));
                }
                return true;
                
            }
            else {
                return false;
            }
            
            
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /// little method for my adding into student database cause keeps having error java.sql.SQLException: Operation not allowed after ResultSet closed
    /// assuming its cause when one result set stats stops other one. do sperate mthods to fix it. 
    ///so imma do a few different methods here
    ///
    public int getLastInsertedID(){
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT Student_id FROM Student ORDER BY Student_id DESC LIMIT 1");
                if (rs.next()){
                    int Studentid = rs.getInt("Student_id");
                    return Studentid;
                }
                return 0;
        }       
        catch(Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public ArrayList<Integer> returnTopicIds(){
        ArrayList<Integer> Topics = new ArrayList<>();
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT Topic_id FROM Topic");
            while (results.next()){
                int Topic = results.getInt("Topic_id");
                Topics.add(Topic);
            }
            return Topics;
        }      
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    ///
    ///
    ///
    ///    
    public void AddTeacher(String name, String Username, String Email, String Password, String School ){
        
        
        //////////////// TEACHER 
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            // inserting into teacher table
            statement.execute("INSERT INTO Teacher ( T_name, username,email, password_hash) VALUES ( '" + name+ "', '" + Username +"' ,'" + Email + "','"+Password  + "')");        
            // cuase of how i made database need to also add to class table 
            ResultSet results = statement.executeQuery("SELECT LAST_INSERT_ID() AS id;");
            if (results.next()){
               /// this should get the teacher last ~instered in and then get theyre id so i can add to class
                int TeacherId = results.getInt("id");
                statement.execute("INSERT INTO Class (Teacher_id, School) VALUES ('" + TeacherId + "',' " + School + "')"); 
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    
    ///// check username is unique
    ///
    ///
    public boolean UsernameUniquenessCheck(String user,String EnteredUsername ){
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet Results = statement.executeQuery("SELECT EXISTS ( "
                    + "SELECT 1 FROM "+user+" WHERE username = '"+ EnteredUsername+"')");
            if (Results.next()){
                // if =1 then there is already a username like it so return false
                if (Results.getInt(1)==1){
                    return false;
                }
                else {
                    return true;
                }
            }
            else {
                return false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // check classc code exists
    public boolean CheckClassCodeExists(int Classcode ){
        try (Connection connection = TheConnectionToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet Results = statement.executeQuery("SELECT EXISTS ( "
                    + "SELECT 1 FROM Class WHERE Class_id = '"+ Classcode+"')");
            if (Results.next()){
                // if =1 then there is already a username like it so return false
                if (Results.getInt(1)==1){
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
