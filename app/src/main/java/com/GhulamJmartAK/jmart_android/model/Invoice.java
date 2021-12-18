package com.GhulamJmartAK.jmart_android.model;
/**
 * Class invoice dibuat sama dengan back-end untuk mensinkronkan data yang di passing
 * @author Ghulam Izzul Fuad
 */


import java.util.Date;

public class Invoice extends Serializable{
    public static enum Status{
        WAITING_CONFIRMATION, CANCELLED, ON_PROGRESS, ON_DELIVERY, COMPLAINT, FINISHED, FAILED;
    }

    public static enum Rating{
        NONE,BAD,NEUTRAL,GOOD;
    }

    public Date date;
    public int buyerId;
    public int productId;
    public int complaintId;
    public Rating rating;
}
