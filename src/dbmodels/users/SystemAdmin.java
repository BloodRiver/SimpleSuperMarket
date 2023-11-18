/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbmodels.users;

import dbmodels.AbstractDBModel;
import java.io.FileNotFoundException;
import java.io.IOException;

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
    
    public SystemAdmin(String username, String password)
    {
        this.username = USERNAME;
        this.password = PASSWORD;
    }
    
    public static SystemAdmin loadUserByName(String username)
    {
        return new SystemAdmin();
    }
    
    public static void setPassword(AbstractBaseUser user, String password) throws IOException, FileNotFoundException, ClassNotFoundException
    {
        user.password = password;
        user.update();
    }
}
