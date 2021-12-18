package com.GhulamJmartAK.jmart_android.model;
/**
 * Class account Product sama dengan back-end untuk mensinkronkan data yang di passing
 * @author Ghulam Izzul Fuad
 */


public class Product extends Serializable {
    public int accountId;
    public ProductCategory category;
    public boolean conditionUsed;
    public double discount;
    public String name;
    public double price;
    public byte shipmentPlans;
    public int weight;

    public String toString() {
        return this.name;
    }
}
