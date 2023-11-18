/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package utilityclasses;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Sajeed Ahmed Galib Arnob
 */
public interface SceneTools {
    
    default void switchScene(Event event, URL fxmlDocumentLocation) throws IOException
    {
        switchScene(event, fxmlDocumentLocation, "", null);
    }
    
    default void switchScene(Event event, URL fxmlDocumentLocation, String windowTitle) throws IOException
    {
        switchScene(event, fxmlDocumentLocation, windowTitle, null);
    }
    
    default void switchScene(Event event, URL fxmlDocumentLocation, String windowTitle, HashMap<Object, Object> sceneData) throws IOException
    {
        Node sourceNode = (Node) event.getSource();
        Scene currentScene = sourceNode.getScene();
        Stage currentStage = (Stage) currentScene.getWindow();
        
        FXMLLoader loader = new FXMLLoader(fxmlDocumentLocation);
        Parent root = loader.load();
        
        SceneTools controller = loader.getController();
        controller.initializeScene(sceneData);
        
        Scene newScene = new Scene(root);
        
        currentStage.setScene(newScene);
        currentStage.setTitle(windowTitle);
    }
    
    public void initializeScene(HashMap<Object, Object> sceneData);
    
}
