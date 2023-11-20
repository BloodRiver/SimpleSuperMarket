/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tablemodels;

import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import dbmodels.Product;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author Sajeed Ahmed Galib Arnob - 2121104 - Sec 3
 */
public class CartItem {
    private SimpleBooleanProperty selected;
    private SimpleStringProperty productName;
    private SimpleIntegerProperty quantity;
    private SimpleFloatProperty unitPrice, totalPrice, vatAmount, vatRate, totalPriceWithVat;
    private Product productInstance;

    public CartItem(Product productInstance, int quantity)
    {
        this.selected = new SimpleBooleanProperty(this, "selected");
        this.productInstance = productInstance;
        this.productName = new SimpleStringProperty(this, "productName");
        this.productName.setValue(productInstance.getProductName());
        
        this.quantity = new SimpleIntegerProperty(this, "quantity");
        this.quantity.setValue(quantity);
        
        this.unitPrice = new SimpleFloatProperty(this, "unitPrice");
        this.unitPrice.setValue(productInstance.getUnitPrice());
        
        this.totalPrice = new SimpleFloatProperty(this, "totalPrice");        
        this.vatAmount = new SimpleFloatProperty(this, "vatAmount");
        this.vatRate = new SimpleFloatProperty(this, "vatRate");
        this.vatRate.setValue(productInstance.getPreDefinedVatRate());
        
        this.totalPrice.setValue(this.calculateTotalPrice());
        this.vatAmount.setValue(this.calculateVatAmount());
        
        this.totalPriceWithVat = new SimpleFloatProperty(this, "totalPriceWithVat");
        this.totalPriceWithVat.setValue(this.calculateTotalPriceWithVat());
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
    
    public Product getProductInstance()
    {
        return this.productInstance;
    }
    
    private float calculateTotalPrice()
    {
        return this.getUnitPrice() * (float) this.getQuantity();
    }
    
    private float calculateVatAmount()
    {
        return this.calculateTotalPrice() * (this.getVatRate() / 100);
    }
    
    private float calculateTotalPriceWithVat()
    {
        return this.calculateTotalPrice() + this.calculateVatAmount();
    }
    
    public String getProductName()
    {
        return this.productName.getValue();
    }
    
    public void setProductName(String productName)
    {
        this.productName.setValue(productName);
    }
    
    public int getQuantity()
    {
        return this.quantity.getValue();
    }
    
    public void setQuantity(int quantity)
    {
        this.quantity.setValue(quantity);
        this.totalPrice.setValue(this.calculateTotalPrice());
        this.vatAmount.setValue(this.calculateVatAmount());
        this.totalPriceWithVat.setValue(this.calculateTotalPriceWithVat());
    }
    
    public float getUnitPrice()
    {
        return this.unitPrice.getValue();
    }
    
    public float getTotalPrice()
    {
        return this.totalPrice.getValue();
    }
    
    public void setTotalPrice(float totalPrice)
    {
        this.totalPrice.setValue(totalPrice);
    }
    
    public float getVatAmount()
    {
        return this.vatAmount.getValue();
    }
    
    public void setVatAmount(float vatAmount)
    {
        this.vatAmount.setValue(vatAmount);
    }
    
    public float getVatRate()
    {
        return this.vatRate.getValue();
    }
    
    public void setVatRate(float vatRate)
    {
        this.vatRate.setValue(vatRate);
    }
    
    public float getTotalPriceWithVat()
    {
        return this.totalPriceWithVat.getValue();
    }
    
    public void setTotalPriceWithVat(float totalPriceWithVat)
    {
        this.totalPriceWithVat.setValue(totalPriceWithVat);
    }
    
    public void updateProduct() throws IOException, FileNotFoundException, ClassNotFoundException
    {
        this.productInstance.setNumItemsInStock(this.productInstance.getNumItemsInStock() - this.getQuantity());
        this.productInstance.update();
    }

    @Override
    public String toString() {
        return "CartItem{" + "productName=" + this.getProductName() + ", quantity=" + this.getQuantity() + ", unitPrice=" + this.getUnitPrice() + ", totalPrice=" + this.getTotalPrice() + ", vatAmount=" + this.getVatAmount() + ", vatRate=" + this.getVatRate() + ", totalPriceWithVat=" + this.getTotalPriceWithVat() + '}';
    }
    
}
