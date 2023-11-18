/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbmodels;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import utilityclasses.AppendableObjectOutputStream;

/**
 *
 * @author Sajeed Ahmed Galib Arnob
 */
public abstract class AbstractDBModel implements Serializable {
    protected int id;
    
    public int getId()
    {
        return this.id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public AbstractDBModel() throws IOException, FileNotFoundException, ClassNotFoundException
    {
        this.id = this.count() + 1;
    }
    
    protected static final ArrayList<AbstractDBModel> loadAllFromFile(String filename) throws FileNotFoundException, IOException, ClassNotFoundException
    {
        File myFile = new File("databases");
        
        if (!myFile.exists())
        {
            return null;
        }
        
        myFile = new File("databases/" + filename);
        
        if (!myFile.exists())
        {
            return null;
        }
        
        ArrayList<AbstractDBModel> allItems = new ArrayList<>();
        
        FileInputStream fis = new FileInputStream("databases/" + filename);
        ObjectInputStream ois = new ObjectInputStream(fis);
        
        try
        {
            while (true)
            {
                AbstractDBModel item = (AbstractDBModel) ois.readObject();
                allItems.add(item);
            }
        }
        catch (EOFException ex)
        {
            System.out.println("Reached end of " + filename + " file");
        }
        
        
        ois.close();
        fis.close();

        return allItems;
    }
    
    protected final void saveToFile(String filename) throws FileNotFoundException, IOException
    {
        File myFile = new File("databases");
        
        if (!myFile.exists())
        {
            myFile.mkdir();
        }
        
        myFile = new File("databases/" + filename);
        
        FileOutputStream fos;
        ObjectOutputStream oos;
        
        if (!myFile.exists())
        {
            fos = new FileOutputStream("databases/" + filename);
            oos = new ObjectOutputStream(fos);
        }
        else
        {
            fos = new FileOutputStream("databases/" + filename, true);
            oos = new AppendableObjectOutputStream(fos);
        }
        
        oos.writeObject(this);
        
        oos.close();
        fos.close();
    }
    
    protected final void deleteFromFile(String filename) throws IOException, FileNotFoundException, ClassNotFoundException
    {
        File myFile = new File("databases");
        
        if (!myFile.exists())
        {
            return;
        }
        
        ArrayList<AbstractDBModel> allItems = loadAllFromFile(filename);
        
        FileOutputStream fos = new FileOutputStream("databases/" + filename);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        
        for (AbstractDBModel eachItem: allItems)
        {
            if (this.isEqual(eachItem))
            {
                continue;
            }
            oos.writeObject(eachItem);
        }
        
        oos.close();
        fos.close();
    }
    
    protected final void updateToFile(String filename) throws IOException, FileNotFoundException, ClassNotFoundException
    {
        File myFile = new File("databases");
        
        if (!myFile.exists())
        {
            return;
        }
        
        ArrayList<AbstractDBModel> allItems = loadAllFromFile(filename);

        FileOutputStream fos = new FileOutputStream("databases/" + filename);
        ObjectOutputStream oos = new ObjectOutputStream(fos);        
        
        for (AbstractDBModel eachItem: allItems)
        {
            if (this.isEqual(eachItem))
            {
                oos.writeObject(this);
                continue;
            }
            oos.writeObject(eachItem);
        }
        
        oos.close();
        fos.close();
    }
    
    protected final int countFromFile(String filename) throws FileNotFoundException, IOException, ClassNotFoundException
    {
        File myFile = new File("databases");
        
        if (!myFile.exists())
        {
            return 0;
        }
        
        myFile = new File("databases/" + filename);
        
        if (!myFile.exists())
        {
            return 0;
        }
        
        FileInputStream fis = new FileInputStream("databases/" + filename);
        ObjectInputStream ois = new ObjectInputStream(fis);
        
        int count = 0;
        
        try
        {
            while (true)
            {
                AbstractDBModel obj = (AbstractDBModel) ois.readObject();
                count++;
            }
        }
        catch (EOFException ex)
        {
            ois.close();
            fis.close();
            return count;
        }
    }
    
    public abstract void save() throws IOException, FileNotFoundException;
    
    public abstract void delete() throws IOException, FileNotFoundException, ClassNotFoundException;
    
    public abstract void update() throws IOException, FileNotFoundException, ClassNotFoundException;
    
    public abstract int count() throws IOException, FileNotFoundException, ClassNotFoundException;
    
    public boolean isEqual(AbstractDBModel otherObject)
    {
        return this.id == otherObject.id;
    }
}
