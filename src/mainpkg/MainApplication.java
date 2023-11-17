/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package mainpkg;

import dbmodels.users.AbstractBaseUser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Sajeed Ahmed Galib Arnob - 2121104 - Sec 3
 */
public class MainApplication extends Application {
    public static AbstractBaseUser loggedInUser;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LoginScene.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Simple Super Market");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public static boolean logIn(AbstractBaseUser user, String password)
    {
        if (user.checkPassword(password))
        {
            loggedInUser = user;
            return true;
        }
        return false;
    }
    
}
