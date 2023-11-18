/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mainpkg;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import utilityclasses.SceneTools;
import dbmodels.users.AbstractBaseUser;
import dbmodels.users.Manager;
import dbmodels.users.POSStaff;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import utilityclasses.DialogBoxTools;

/**
 * FXML Controller class
 *
 * @author Sajeed Ahmed Galib Arnob
 */
public class SystemAdminCreateUserSceneController implements Initializable, SceneTools, DialogBoxTools {

    @FXML
    private ComboBox<String> userTypeComboBox;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private TableView<UserTableData> userAccountsTableView;
    @FXML
    private TableColumn<UserTableData, Boolean> selectAllCheckBoxTableColumn;
    @FXML
    private CheckBox selectAllCheckBox;
    @FXML
    private TableColumn<UserTableData, String> userNameTableColumn;
    @FXML
    private TableColumn<UserTableData, String> userTypeTableColumn;
    @FXML
    private TableColumn<UserTableData, String> controlTableColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userTypeComboBox.getItems().addAll(
                "Manager",
                "POS Staff"
        );
        
        userTypeComboBox.getSelectionModel().selectFirst();
        
        selectAllCheckBoxTableColumn.setCellValueFactory(new PropertyValueFactory<>("selected"));
        selectAllCheckBoxTableColumn.setCellFactory(cell -> {
            CheckBoxTableCell<UserTableData, Boolean> myCell = new CheckBoxTableCell<>();
            
            return myCell;
        });
        
        userNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        userNameTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        
        userTypeTableColumn.setCellValueFactory(new PropertyValueFactory<>("userType"));
        userTypeTableColumn.setCellFactory(cell -> {
            ComboBoxTableCell<UserTableData, String> myCell = new ComboBoxTableCell<>();
            
            return myCell;
        });
        
        controlTableColumn.setCellValueFactory(new PropertyValueFactory<>("changePasswordButton"));
        
        
        userAccountsTableView.setEditable(true);
        
        try {
            ObservableList<UserTableData> allUserData = UserTableData.loadAll();
            
            if (allUserData.size() > 0)
            {
                userAccountsTableView.setItems(allUserData);
            }
        } catch (IOException ex) {
            Logger.getLogger(SystemAdminCreateUserSceneController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SystemAdminCreateUserSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void createUserButtonOnClick(ActionEvent event) throws IOException {
        String userType = userTypeComboBox.getSelectionModel().getSelectedItem();
        String username = usernameTextField.getText();
        String password = passwordPasswordField.getText();
        
        if (username.length() < 4)
        {
            showError("Username must be at least 4 characters long");
            return;
        }
        
        if (password.length() < 5)
        {
            showError("Password must be at least 5 characters long");
            return;
        }
        
        AbstractBaseUser newUser = null;
        
        if (userType.equals("Manager"))
        {
            newUser = new Manager(username, password);
        }
        else if (userType.equals("POS Staff"))
        {
            newUser = new POSStaff(username, password);
        }
        
        newUser.save();
        
        UserTableData newUserData = new UserTableData(username, userType);
        
        userAccountsTableView.getItems().add(newUserData);
    }

    @FXML
    private void logoOutButtonOnClick(ActionEvent event) throws IOException {
        switchScene(event, getClass().getResource("LoginScene.fxml"));
    }

    @FXML
    private void selectAllCheckBoxOnClick(ActionEvent event) {
    }

    @FXML
    private void userNameTableColumnOnEditCommit(TableColumn.CellEditEvent<UserTableData, String> event) {
    }

    @FXML
    private void userTypeTableColumnOnEditCommit(TableColumn.CellEditEvent<UserTableData, String> event) {
    }

    @Override
    public void initializeScene(HashMap<Object, Object> sceneData) {
    }
    
}
