/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbmodels.users;

import dbmodels.AbstractDBModel;
import java.io.Serializable;

/**
 *
 * @author Sajeed Ahmed Galib Arnob
 */
public class Manager extends AbstractBaseUser implements Serializable {

    public Manager(String username, String password) {
        super(username, password);
    }
    
}
