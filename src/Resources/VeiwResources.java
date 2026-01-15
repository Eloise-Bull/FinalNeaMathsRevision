/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Resources;

import static ConnectTheDatabase.ConnectTheDatabase.TheConnectionToDatabase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import java.util.ArrayList;

/**
 *
 * @author 4-EBULL
 */
public class VeiwResources {
    
    // when click the select button it goes to this method which pulls all the topics resource they selected
    private static int count = 0;
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
    // this is for when the user clicks on the screen it gets topics from databse and puts into combo box
    // this means that new topics and resources can be addd wiht no nee to change the system 
    //takes from database and adds to combo box
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
