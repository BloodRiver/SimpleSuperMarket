/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbmodels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Sajeed Ahmed Galib Arnob
 */
public class Product extends AbstractDBModel implements Serializable {
    private String productName;
    private float unitPrice;
    private float preDefinedVatRate;
    private int numItemsInStock;

    public Product(String productName, float unitPrice, float preDefinedVatRate, int numItemsInStock) {
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.preDefinedVatRate = preDefinedVatRate;
        this.numItemsInStock = numItemsInStock;
    }
    
    @Override
    public String toString()
    {
        return this.productName;
    }
    
    public static ArrayList<Product> loadAll() throws FileNotFoundException, IOException, ClassNotFoundException
    {
        ArrayList<Product> allProducts = new ArrayList<>();
        ArrayList<AbstractDBModel> allItems = loadAllFromFile("Product.bin");
        if (allItems == null)
        {
            return null;
        }
        for (AbstractDBModel eachItem: allItems)
        {
            allProducts.add((Product) eachItem);
        }

        return allProducts;
    }
    
    @Override
    public void update() throws FileNotFoundException, IOException, ClassNotFoundException
    {
        this.updateToFile("Product.bin");
    }
    
    @Override
    public void delete() throws FileNotFoundException, IOException, ClassNotFoundException
    {
        this.deleteFromFile("Product.bin");
    }
    
    @Override
    public void save() throws FileNotFoundException, IOException
    {
        this.saveToFile("Product.bin");
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public float getPreDefinedVatRate() {
        return preDefinedVatRate;
    }

    public void setPreDefinedVatRate(float preDefinedVatRate) {
        this.preDefinedVatRate = preDefinedVatRate;
    }

    public int getNumItemsInStock() {
        return numItemsInStock;
    }

    public void setNumItemsInStock(int numItemsInStock) {
        this.numItemsInStock = numItemsInStock;
    }
    
    protected boolean isEqual(AbstractDBModel otherObject)
    {
        Product otherProduct = (Product) otherObject;
        return this.getProductName().equals(otherProduct.getProductName());
    }
    
}
