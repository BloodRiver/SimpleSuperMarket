/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mainpkg;

import dbmodels.users.AbstractBaseUser;
import dbmodels.users.Manager;
import dbmodels.users.POSStaff;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import dbmodels.users.SystemAdmin;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import utilityclasses.DialogBoxTools;
import utilityclasses.SceneTools;

/**
 * FXML Controller class
 *
 * @author Sajeed Ahmed Galib Arnob
 */
public class LoginSceneController implements Initializable, SceneTools, DialogBoxTools {

    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordPasswordField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MainApplication.loggedInUser = null;
    }    

    @FXML
    private void loginButtonOnClick(ActionEvent event) throws IOException, FileNotFoundException, ClassNotFoundException {
        AbstractBaseUser user;
        if (usernameTextField.getText().equals(SystemAdmin.USERNAME))
        {
            user = new SystemAdmin();
        }
        else if ((user = AbstractBaseUser.loadUserByName(usernameTextField.getText())) != null)
        {
            // do nothing            
        }
        else
        {
            showError("That user does not exist");
            return;
        }
        
        if (MainApplication.logIn(user, passwordPasswordField.getText()))
        {
            if (user instanceof SystemAdmin)
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Scene Choice");
                alert.setHeaderText("Where to go?");
                alert.setContentText("Would you like to go to Add User Scene or Add Product Scene");

                ButtonType buttonType1 = new ButtonType("Add User Scene");
                ButtonType buttonType2 = new ButtonType("Add Product Scene");

                alert.getButtonTypes().setAll(buttonType1, buttonType2);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == buttonType1)
                {
                    switchScene(event, getClass().getResource("SystemAdminCreateUserScene.fxml"), "Add Products to database");
                }
                else
                {
                    switchScene(event, getClass().getResource("MainScene.fxml"), "Add Products to database");
                }
            }
            else if (user instanceof Manager)
            {
                switchScene(event, getClass().getResource("MainScene.fxml"), "Add Products to database");
            }
            else if (user instanceof POSStaff)
            {
                switchScene(event, getClass().getResource("CheckoutScene.fxml"), "Add Products to Cart and Checkout");
            }
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
