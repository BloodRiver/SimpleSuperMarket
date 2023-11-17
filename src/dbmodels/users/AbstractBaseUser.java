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

/**
 *
 * @author Sajeed Ahmed Galib Arnob
 */
public abstract class AbstractBaseUser extends AbstractDBModel implements Serializable {
    protected String username;
    protected String password;
    
    public String getUsername()
    {
        return this.username;
    }
    
    public boolean checkPassword(String password)
    {
        return this.password.equals(password);
    }
    
    public AbstractBaseUser loadUserByName(String username) throws FileNotFoundException, IOException, ClassNotFoundException
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
}
