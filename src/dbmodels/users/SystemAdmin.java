/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbmodels.users;

import dbmodels.AbstractDBModel;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Sajeed Ahmed Galib Arnob
 */
public final class SystemAdmin extends AbstractBaseUser {
    public static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    
    public SystemAdmin()
    {
        this.username = USERNAME;
        this.password = PASSWORD;
    }
    
    @Override
    public AbstractBaseUser loadUserByName(String username)
    {
        return new SystemAdmin();
    }
    
    public static ArrayList<AbstractDBModel> loadAll() throws FileNotFoundException, IOException, ClassNotFoundException
    {
        return null;
    }
    
    protected boolean isEqual(AbstractDBModel otherObject)
    {
        return this.equals(otherObject);
    }
}
