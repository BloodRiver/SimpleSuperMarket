/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mainpkg;

import dbmodels.users.AbstractBaseUser;
import dbmodels.users.Manager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import dbmodels.users.SystemAdmin;
import java.io.IOException;
import java.util.HashMap;
import utilityclasses.SceneTools;

/**
 * FXML Controller class
 *
 * @author Sajeed Ahmed Galib Arnob
 */
public class LoginSceneController implements Initializable, SceneTools {

    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordPasswordField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void loginButtonOnClick(ActionEvent event) throws IOException {
        AbstractBaseUser user;
        if (usernameTextField.getText().equals(SystemAdmin.USERNAME))
        {
            user = new SystemAdmin();
        }
        else if (true)
        {
            user = new Manager();
            
        }
        else
        {
            showError("That user does not exist");
            return;
        }
        
        if (MainApplication.logIn(user, passwordPasswordField.getText()))
        {
            HashMap<Object, Object> sceneData = new HashMap<>();
            sceneData.put("username", user.getUsername());

            switchScene(event, getClass().getResource("MainScene.fxml"), "Add Products to database", sceneData);
        }
        else
        {
            showError("Incorrect Password");
        }
        
    }
    
    @Override
    public void initializeScene(HashMap<Object, Object> sceneData)
    {
        
    }
    
}
