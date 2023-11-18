/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tablemodels;

import dbmodels.users.AbstractBaseUser;
import dbmodels.users.SystemAdmin;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import utilityclasses.DialogBoxTools;
import utilityclasses.SceneTools;

/**
 *
 * @author Sajeed
 */
public class UserTableData implements DialogBoxTools {
    private SimpleBooleanProperty selected;
    private SimpleStringProperty username;
    private SimpleStringProperty userType;
    private Button changePasswordButton;
    private AbstractBaseUser userInstance;

    public SimpleBooleanProperty selectedProperty()
    {
        return selected;
    }
    
    public boolean isSelected() {
        return selected.getValue();
    }

    public void setSelected(boolean selected) {
        this.selected.setValue(selected);
    }

    public String getUsername() {
        return this.username.getValue();
    }

    public void setUsername(String username) {
        this.userInstance.setUsername(username);
        this.username.setValue(username);
    }

    public String getUserType() {
        return this.userType.getValue();
    }

    public void setUserType(String userType) {
        this.userType.setValue(userType);
    }

    public Button getChangePasswordButton() {
        return changePasswordButton;
    }

    public void setChangePasswordButton(Button changePasswordButton) {
        this.changePasswordButton = changePasswordButton;
    }
    
    public AbstractBaseUser getUser()
    {
        return this.userInstance;
    }
    
    private void initializeFields()
    {
        this.selected = new SimpleBooleanProperty(this, "selected");
        this.username = new SimpleStringProperty(this, "username");
        this.userType = new SimpleStringProperty(this, "userType");
        this.changePasswordButton = new Button("Change Password");
        
        this.changePasswordButton.setOnAction(event -> {
            String newPassword = AlertInput("Enter new password", "Change Password of user");
            
            if (newPassword == null)
            {
                return;
            }
            
            if (newPassword.length() >= 5)
            {
                try {
                    SystemAdmin.setPassword(userInstance, newPassword);
                } catch (IOException ex) {
                    Logger.getLogger(UserTableData.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(UserTableData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
            {
                showError("Password must be at least 5 characters long");
            }
        });
    }
    
    public UserTableData(AbstractBaseUser userInstance)
    {
        this.initializeFields();
        this.username.setValue(userInstance.getUsername());
        this.userType.setValue(userInstance.getClass().getSimpleName());
        this.userInstance = userInstance;
    }

    public UserTableData(String username, String userType) {
        this.initializeFields();
        this.username.setValue(username);
        this.userType.setValue(userType);
    }
    
    public static ObservableList<UserTableData> loadAll() throws IOException, FileNotFoundException, ClassNotFoundException
    {
        ArrayList<AbstractBaseUser> allUsers = AbstractBaseUser.loadAll();
        
        ObservableList<UserTableData> allUserData = FXCollections.observableArrayList();
        
        if (allUsers == null)
        {
            return allUserData;
        }
        
        for (AbstractBaseUser eachUser: allUsers)
        {
            allUserData.add(new UserTableData(eachUser));
        }
        
        return allUserData;
    }

    @Override
    public String toString() {
        return "UserTableData{" + "selected=" + this.isSelected() + ", username=" + this.getUsername() + ", userType=" + this.getUserType() + '}';
    }
    
    public void update() throws IOException, FileNotFoundException, ClassNotFoundException
    {
        this.userInstance.update();
    }
    
    public void delete() throws IOException, FileNotFoundException, ClassNotFoundException
    {
        this.userInstance.delete();
    }
}
