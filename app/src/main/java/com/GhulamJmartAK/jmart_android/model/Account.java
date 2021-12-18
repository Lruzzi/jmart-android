package com.GhulamJmartAK.jmart_android.model;

/**
 * Class account dibuat sama dengan back-end untuk mensinkronkan data yang di passing
 * @author Ghulam Izzul Fuad
 */

public class Account extends Serializable{
    public double balance;
    public String email;
    public String name;
    public String password;
    public Store store;
}
