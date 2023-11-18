/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbmodels.users;

import dbmodels.AbstractDBModel;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Sajeed Ahmed Galib Arnob
 */
public abstract class AbstractBaseUser extends AbstractDBModel implements Serializable {
    protected String username;
    protected String password;
    
    public AbstractBaseUser() throws IOException, FileNotFoundException, ClassNotFoundException
    {
        super();
    }
    
    public AbstractBaseUser(String username, String password) throws IOException, FileNotFoundException, ClassNotFoundException
    {
        super();
        this.username = username;
        this.password = password;
    }
    
    public String getUsername()
    {
        return this.username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public boolean checkPassword(String password)
    {
        return this.password.equals(password);
    }
    
    public static AbstractBaseUser loadUserByName(String username) throws FileNotFoundException, IOException, ClassNotFoundException
    {
        File myFile = new File("databases");
        
        if (!myFile.exists())
        {
            return null;
        }
        
        myFile = new File("databases/User.bin");
        
        if (!myFile.exists())
        {
            return null;
        }
        
        FileInputStream fis = new FileInputStream("databases/User.bin");
        ObjectInputStream ois = new ObjectInputStream(fis);
        
        try
        {
            while (true)
            {
                AbstractBaseUser user = (AbstractBaseUser) ois.readObject();
                
                if (user.getUsername().equals(username))
                {
                    ois.close();
                    fis.close();
                    return user;
                }
            }
        }
        catch (EOFException ex)
        {
            System.out.println("Reached end of User.bin file but user \"" + username + "\" not found");
        }
        
        ois.close();
        fis.close();
        
        return null;
    }
    
    public static ArrayList<AbstractBaseUser> loadAll() throws IOException, FileNotFoundException, ClassNotFoundException
    {
        ArrayList<AbstractDBModel> allItems = loadAllFromFile("User.bin");
        
        if (allItems == null)
        {
            return null;
        }
        
        ArrayList<AbstractBaseUser> allUsers = new ArrayList<>();
        
        for (AbstractDBModel eachItem: allItems)
        {
            allUsers.add((AbstractBaseUser) eachItem);
        }
        
        return allUsers;
    }
    
    @Override
    public final void save() throws IOException, FileNotFoundException
    {
        this.saveToFile("User.bin");
    }
    
    @Override
    public final void update() throws IOException, FileNotFoundException, ClassNotFoundException
    {
        this.updateToFile("User.bin");
    }
    
    @Override
    public final void delete() throws IOException, FileNotFoundException, ClassNotFoundException
    {
        this.deleteFromFile("User.bin");
    }
    
    @Override
    public int count() throws IOException, FileNotFoundException, ClassNotFoundException
    {
        return this.countFromFile("User.bin");
    }
}
