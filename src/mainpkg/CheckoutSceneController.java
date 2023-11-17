/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package mainpkg;

import dbmodels.Product;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import tablemodels.CartItem;
import utilityclasses.SceneTools;

/**
 *
 * @author Sajeed Ahmed Galib Arnob - 2121104 - Sec 3
 */
public class CheckoutSceneController implements Initializable, SceneTools {

    @FXML
    private ComboBox<Product> selectProductComboBox;
    @FXML
    private ComboBox<Integer> quantityComboBox;
    @FXML
    private Label unitPriceLabel;
    @FXML
    private Label predefinedVatLabel;
    
    @FXML
    private TableView<CartItem> cartItemTable;
    @FXML
    private TableColumn<CartItem, String> productNameTableColumn;
    @FXML
    private TableColumn<CartItem, Float> unitPriceTableColumn;
    @FXML
    private TableColumn<CartItem, Integer> quantityTableColumn;
    @FXML
    private TableColumn<CartItem, Float> totalPriceTableColumn;
    @FXML
    private TableColumn<CartItem, Float> vatTableColumn;
    @FXML
    private TableColumn<CartItem, Float> vatAmountTableColumn;
    @FXML
    private TableColumn<CartItem, Float> totalPriceWithVatTableColumn;
    @FXML
    private Label totalAmountOutputLabel;
    @FXML
    private Label numItemsInStockLabel;
    @FXML
    private Label usernameDisplayLabel;
    
    private String username;
    @FXML
    private TableColumn<CartItem, Boolean> selectAllCheckBoxTableColumn;
    
    @FXML
    private CheckBox selectAllCheckBox;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for (int i=1; i<=10; i++)
        {
            quantityComboBox.getItems().add(i);
        }
        
        quantityComboBox.getSelectionModel().selectFirst();
        
        
//        selectProductComboBox.getItems().addAll(
//                new Product("Lux Soap White 100gm", 10.0f, 2.0f),
//                new Product("Potato Crackers Chips", 10.0f, 1.0f),
//                new Product("Digestive Chocolate Biscuit", 20.0f, 1.2f),
//                new Product("Mister Twist Chips", 15.0f, 1.1f),
//                new Product("Bisk Club Potato Chips", 30.0f, 2.5f),
//                new Product("Aarong Cheese", 220.0f, 1.5f),
//                new Product("Aarong Milk 1 litre", 120.0f, 2.2f),
//                new Product("Starship Chocolate Milk", 35.0f, 1.3f),
//                new Product("Cadbury Dairy Milk Silk 250gm", 250.0f, 2.3f),
//                new Product("Polar Mango Ice Cream 500gm", 100.0f, 1.0f)
//        );

        ArrayList<Product> allProducts;
        try {
            allProducts = Product.loadAll();
            selectProductComboBox.getItems().addAll(allProducts);
            selectProductComboBox.getSelectionModel().selectFirst();

        } catch (IOException ex) {
            Logger.getLogger(CheckoutSceneController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CheckoutSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        updateProductInfo();
        
        selectAllCheckBoxTableColumn.setCellValueFactory(new PropertyValueFactory<>("selected"));
        selectAllCheckBoxTableColumn.setCellFactory(cell ->
        {
            CheckBoxTableCell<CartItem, Boolean> myCell = new CheckBoxTableCell<>();
            
            return myCell;
        });
        
        productNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        unitPriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        
        quantityTableColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityTableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        
        totalPriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        vatTableColumn.setCellValueFactory(new PropertyValueFactory<>("vatRate"));
        vatAmountTableColumn.setCellValueFactory(new PropertyValueFactory<>("vatAmount"));
        totalPriceWithVatTableColumn.setCellValueFactory(new PropertyValueFactory<>("totalPriceWithVat"));
        
        cartItemTable.setEditable(true);
    }
    
    private void updateProductInfo()
    {
        unitPriceLabel.setText(Float.toString(selectProductComboBox.getSelectionModel().getSelectedItem().getUnitPrice()));
        predefinedVatLabel.setText(Float.toString(selectProductComboBox.getSelectionModel().getSelectedItem().getPreDefinedVatRate()));
        numItemsInStockLabel.setText(Integer.toString(selectProductComboBox.getSelectionModel().getSelectedItem().getNumItemsInStock()));
    }

    @FXML
    private void addProductToCartButtonOnClick(ActionEvent event) {
        Product selectedProduct = selectProductComboBox.getSelectionModel().getSelectedItem();
        int quantity = quantityComboBox.getSelectionModel().getSelectedItem();
        
        float totalAmount = 0.0f;
        
        boolean selectedProductAlreadyInCart = false;
        
        for (CartItem eachItem: cartItemTable.getItems())
        {
            totalAmount += eachItem.getTotalPriceWithVat();
            if (eachItem.getProductName().equals(selectedProduct.getProductName()))
            {
                selectedProductAlreadyInCart = true;
                int newQuantity = eachItem.getQuantity() + quantity;
                
                if (newQuantity <= selectedProduct.getNumItemsInStock())
                {
                    eachItem.setQuantity(newQuantity);
                    cartItemTable.refresh();
                }
                else
                {
                    showError("Chosen quantity exceeds number of items in stock");
                }
            }
        }
        
        if (!selectedProductAlreadyInCart)
        {
            if (quantity <= selectedProduct.getNumItemsInStock())
            {
                CartItem newItem = new CartItem(selectedProduct, quantity);
                cartItemTable.getItems().add(newItem);
                totalAmount += newItem.getTotalPriceWithVat();
            }
            else
            {
                showError("Chosen quantity exceeds number of items in stock");
            }
        }

        totalAmountOutputLabel.setText("Total Amount with Vat of all products: " + totalAmount);
    }

    @FXML
    private void selectProductComboBoxOnItemSelected(ActionEvent event) {
        updateProductInfo();
    }

    @FXML
    private void checkOutButtonOnClick(ActionEvent event) throws IOException, FileNotFoundException, ClassNotFoundException {
        for (CartItem eachCartItem: cartItemTable.getItems())
        {
            eachCartItem.updateProduct();
        }
        
        updateProductInfo();
        
        cartItemTable.getItems().clear();
    }

    @FXML
    private void goBackButtonOnClick(ActionEvent event) throws IOException {
        HashMap<Object, Object> sceneData = new HashMap<>();
        sceneData.put("username", username);
        switchScene(event, getClass().getResource("MainScene.fxml"), "Add Products to Database", sceneData);
    }
    
    @Override
    public void initializeScene(HashMap<Object, Object> sceneData)
    {
        username = (String) sceneData.get("username");
        usernameDisplayLabel.setText(username);
    }

    @FXML
    private void quantityTableColumnOnEditCommit(TableColumn.CellEditEvent<CartItem, Integer> event) {
        CartItem currentRow = event.getRowValue();
        
        if (event.getNewValue() > currentRow.getProductInstance().getNumItemsInStock())
        {
            showError("Quantity exceeds number of items in stock");
            cartItemTable.refresh();
        }
        else if (event.getNewValue() < 1)
        {
            cartItemTable.getItems().remove(currentRow);
            cartItemTable.refresh();
        }
        else
        {
            currentRow.setQuantity(event.getNewValue());
        }
    }

    @FXML
    private void selectAllCheckBoxOnClick(ActionEvent event) {
        for (CartItem eachItem: cartItemTable.getItems())
        {
            eachItem.setSelected(selectAllCheckBox.isSelected());
        }
    }

    @FXML
    private void removeSelectedButtonOnClick(ActionEvent event) {
        int i = 0;
        
        while (i < cartItemTable.getItems().size())
        {
            if (cartItemTable.getItems().get(i).isSelected())
            {
                cartItemTable.getItems().remove(i);
            }
            else
            {
                i++;
            }
        }

        selectAllCheckBox.setSelected(false);
        cartItemTable.refresh();
    }
    
}
