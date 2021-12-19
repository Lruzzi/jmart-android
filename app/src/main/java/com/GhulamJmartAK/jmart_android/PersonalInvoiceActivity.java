package com.GhulamJmartAK.jmart_android;

/**
 * Class yang menampilkan Payment Activity
 * berisi produck detail dan meminta input jumlah produck yang akan dibeli
 * dan alamat pengiriman
 * @author Ghulam Izzul Fuad
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.GhulamJmartAK.jmart_android.model.Payment;
import com.GhulamJmartAK.jmart_android.model.Product;
import com.GhulamJmartAK.jmart_android.request.RequestFactory;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PersonalInvoiceActivity extends AppCompatActivity {


    public static ArrayList<Payment> paymentList = new ArrayList<>();
    public static ArrayList<Product> products = new ArrayList<>();
    private static final Gson gson = new Gson();
    static int pageSize = 20;
    static int page = 0;
    private static int sentinel = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_invoice);

        Button back = findViewById(R.id.buttonBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PersonalInvoiceActivity.this,"Menu!", Toast.LENGTH_SHORT).show();
                products.clear();
                sentinel = 0;
                Intent intent = new Intent(PersonalInvoiceActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button refresh = findViewById(R.id.buttonRefresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sentinel == 0){
                    Toast.makeText(PersonalInvoiceActivity.this,"Refresh!", Toast.LENGTH_SHORT).show();
                    PersonalInvoiceActivity.this.finish();
                    PersonalInvoiceActivity.this.overridePendingTransition(0,0);
                    PersonalInvoiceActivity.this.startActivity(PersonalInvoiceActivity.this.getIntent());
                    sentinel += 1;
                }
                else if(sentinel > 0){
                    Toast.makeText(PersonalInvoiceActivity.this,"Already Refreshed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray object = new JSONArray(response);
                    if (object != null) {
                        paymentList = gson.fromJson(object.toString(), new TypeToken<ArrayList<Payment>>() {
                        }.getType());
                        convertPayment();
                        ArrayAdapter<Product> listViewAdapter = new ArrayAdapter<Product>(
                                PersonalInvoiceActivity.this,
                                android.R.layout.simple_list_item_1,
                                products
                        );
                        ListView lv = (ListView) findViewById(R.id.ListPersonalHistory);

                        lv.setAdapter(listViewAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(PersonalInvoiceActivity.this);
        requestQueue.add(RequestFactory.getPage("payment", page, pageSize, listener, null));
    }

    private void convertPayment() {
        for (Payment each : paymentList) {
            if(each.buyerId == LoginActivity.loggedAccount.id){
                Response.Listener<String> listenerConvert = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject objectConvert = new JSONObject(response);
                            if (objectConvert != null) {
                                Product temp = gson.fromJson(objectConvert.toString(),Product.class);
                                products.add(temp);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(PersonalInvoiceActivity.this);
                requestQueue.add(RequestFactory.getById("product", each.productId, listenerConvert, null));
            }
        }
    }
}