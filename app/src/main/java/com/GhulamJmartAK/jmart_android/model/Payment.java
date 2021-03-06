package com.GhulamJmartAK.jmart_android.model;
/**
 * Class payment dibuat sama dengan back-end untuk mensinkronkan data yang di passing
 * @author Ghulam Izzul Fuad
 */


import java.util.ArrayList;
import java.util.Date;

public class Payment extends Invoice{
    public Shipment shipment;
    public int productCount;
    public ArrayList<Record> history = new ArrayList<Record>();

    public  static class Record {
        public final Date date;
        public String massage;
        public Status status;


        public Record( Status status, String massage) {
            this.date = java.util.Calendar.getInstance().getTime();
            this.status = status;
            this.massage = massage;
        }

    }

    @Override
    public String toString() {
        return String.valueOf(this.productId);
    }
}
