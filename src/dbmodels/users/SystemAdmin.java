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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author Sajeed Ahmed Galib Arnob
 */
public final class SystemAdmin extends AbstractBaseUser {
    public static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    
    public SystemAdmin() throws IOException, FileNotFoundException, ClassNotFoundException
    {
        super();
        this.username = USERNAME;
        this.password = PASSWORD;
    }
    
    public SystemAdmin(String username, String password) throws IOException, FileNotFoundException, ClassNotFoundException
    {
        super();
        this.username = USERNAME;
        this.password = PASSWORD;
    }
    
    public static SystemAdmin loadUserByName(String username) throws IOException, FileNotFoundException, ClassNotFoundException
    {
        return new SystemAdmin();
    }
    
    public static void setPassword(AbstractBaseUser user, String password) throws IOException, FileNotFoundException, ClassNotFoundException
    {
        user.password = password;
        user.update();
    }
    
    public static void changeUserAccountType(AbstractBaseUser user, String newUserType) throws FileNotFoundException, IOException, ClassNotFoundException
    {
        File myFile = new File("databases");
        
        if (!myFile.exists())
        {
            throw new FileNotFoundException("User.bin does not exist (databases folder does not exist)");
        }
        
        myFile = new File("databases/User.bin");
        
        if (!myFile.exists())
        {
            throw new FileNotFoundException("User.bin does not exist in databases folder");
        }
        
        FileInputStream fis = new FileInputStream("databases/User.bin");
        ObjectInputStream ois = new ObjectInputStream(fis);
        FileOutputStream fos = new FileOutputStream("databases/User_temp.bin");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        
        try
        {
            while (true)
            {
                AbstractBaseUser obj = (AbstractBaseUser) ois.readObject();
                
                if (obj.isEqual(user))
                {
                    AbstractBaseUser newUser = null;
                    if (newUserType.equals("Manager"))
                    {
                        newUser = new Manager(user.getUsername(), user.password);
                    }
                    else if (newUserType.equals("POS Staff"))
                    {
                        newUser = new POSStaff(user.getUsername(), user.password);
                    }
                    newUser.setId(user.getId());
                    oos.writeObject(newUser);
                }
                else
                {
                    oos.writeObject(obj);
                }
            }
        }
        catch (EOFException ex)
        {
            
        }
        
        ois.close();
        fis.close();
        oos.close();
        fos.close();
        
        myFile.delete();
        myFile = new File("databases/User_temp.bin");
        myFile.renameTo(new File("databases/User.bin"));
    }

    @Override
    public int count() throws IOException, FileNotFoundException, ClassNotFoundException {
        return 1;
    }
}
