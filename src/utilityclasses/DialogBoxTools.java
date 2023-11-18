/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package utilityclasses;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

/**
 *
 * @author Sajeed Ahmed Galib Arnob
 */
public interface DialogBoxTools {
    default void showInfo(String message)
    {
        showInfo(message, "");
    }
    
    default void showInfo(String message, String title)
    {
        showDialog(Alert.AlertType.INFORMATION, message, title);
    }
    
    default void showError(String message)
    {
        showError(message, "");
    }
    
    default void showError(String message, String title)
    {
        showDialog(Alert.AlertType.ERROR, message, title);
    }
    
    default void showDialog(Alert.AlertType alertType, String message)
    {
        showDialog(alertType, message, "");
    }
    
    default void showDialog(Alert.AlertType alertType, String message, String title)
    {
        Alert msgbox = new Alert(alertType);
        msgbox.setContentText(message);
        msgbox.setTitle(title);
        msgbox.showAndWait();
    }
    
    default boolean askYesNo(String message)
    {
        return askYesNo("", "", message);
    }
    
    default boolean askYesNo(String title, String headerText, String contentText)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeYes)
        {    
            return true;
        }
        else
        {
            return false;
        }
    }
    
    default String AlertInput(String message, String title)
    {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText("Header text of the dialog");
        dialog.setContentText(message);

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            return result.get();
        }
        else
        {
            return null;
        }
    }
    
}
