/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tablemodels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import dbmodels.Product;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author Sajeed Ahmed Galib Arnob
 */
public class ProductTableData {
    private SimpleBooleanProperty selected;
    private SimpleStringProperty productName;
    private SimpleFloatProperty unitPrice;
    private SimpleFloatProperty preDefinedVatRate;
    private SimpleIntegerProperty numItemsInStock;
    private Product productInstance;

    public ProductTableData(Product productInstance) {
        this.productInstance = productInstance;
        
        this.selected = new SimpleBooleanProperty(this, "selected");
        
        this.productName = new SimpleStringProperty(this, "productName");
        this.productName.setValue(productInstance.getProductName());
        
        this.unitPrice = new SimpleFloatProperty(this, "unitPrice");
        this.unitPrice.setValue(productInstance.getUnitPrice());
        
        this.preDefinedVatRate = new SimpleFloatProperty(this, "preDefinedVatRate");
        this.preDefinedVatRate.setValue(productInstance.getPreDefinedVatRate());
        
        this.numItemsInStock = new SimpleIntegerProperty(this, "numItemsInStock");
        this.numItemsInStock.setValue(productInstance.getNumItemsInStock());
    }
    
    public static ObservableList<ProductTableData> loadAll() throws IOException, FileNotFoundException, ClassNotFoundException
    {
        ArrayList<Product> allProducts = Product.loadAll();
        
        ObservableList<ProductTableData> allProductTableData = FXCollections.observableArrayList();
        
        if (allProducts != null)
        {
            for (Product eachProduct: allProducts)
            {
                allProductTableData.add(new ProductTableData(eachProduct));
            }
        }
        
        return allProductTableData;
    }
    
    public SimpleBooleanProperty selectedProperty()
    {
        return this.selected;
    }
    
    public boolean isSelected()
    {
        return this.selected.getValue();
    }
    
    public void setSelected(boolean selected)
    {
        this.selected.setValue(selected);
    }

    public String getProductName() {
        return this.productName.getValue();
    }

    public void setProductName(String productName) {
        this.productName.setValue(productName);
        this.productInstance.setProductName(productName);
    }

    public float getUnitPrice() {
        return this.unitPrice.getValue();
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice.setValue(unitPrice);
        this.productInstance.setUnitPrice(unitPrice);
    }

    public float getPreDefinedVatRate() {
        return this.preDefinedVatRate.getValue();
    }

    public void setPreDefinedVatRate(float preDefinedVatRate) {
        this.preDefinedVatRate.setValue(preDefinedVatRate);
        this.productInstance.setPreDefinedVatRate(preDefinedVatRate);
    }
    
    public int getNumItemsInStock()
    {
        return this.numItemsInStock.getValue();
    }
    
    public void setNumItemsInStock(int numItemsInStock)
    {
        this.numItemsInStock.setValue(numItemsInStock);
        this.productInstance.setNumItemsInStock(numItemsInStock);
    }

    @Override
    public String toString() {
        return this.productName.getValue();
    }
    
    public void save() throws IOException
    {
        this.productInstance.save();
    }
    
    public void update() throws IOException, FileNotFoundException, ClassNotFoundException
    {
        this.productInstance.update();
    }
    
    public void delete() throws IOException, FileNotFoundException, ClassNotFoundException
    {
        this.productInstance.delete();
    }
}
