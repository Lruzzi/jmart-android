package com.GhulamJmartAK.jmart_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import java.util.List;

public class PersonalInvoiceActivity extends AppCompatActivity {

    private static final Gson gson = new Gson();
    public static ArrayList<Payment> paymentList = new ArrayList<>();
    public static ArrayList<Product> products = new ArrayList<>();
    static int pageSize = 5;
    static Integer page = 0;
    static Product paymentClicked = null;
    static int checker = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_invoice);

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray object = new JSONArray(response);
                    if (object != null) {
                        paymentList = gson.fromJson(object.toString(), new TypeToken<ArrayList<Payment>>() {
                        }.getType());
                        System.out.println(paymentList);
                        convertPayment();
                        System.out.println(products);
                        ArrayAdapter<Product> listViewAdapter = new ArrayAdapter<Product>(
                                PersonalInvoiceActivity.this, android.R.layout.simple_list_item_1, products
                        );

                        ListView lv = (ListView) findViewById(R.id.storeHistory);

                        lv.setAdapter(listViewAdapter);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(PersonalInvoiceActivity.this);
        requestQueue.add(RequestFactory.getPage("payment", page, pageSize, listener, null));
        Button refresh = findViewById(R.id.refreshButton);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checker == 0) {
                    Toast.makeText(PersonalInvoiceActivity.this, "Refreshed", Toast.LENGTH_SHORT).show();
                    PersonalInvoiceActivity.this.finish();
                    PersonalInvoiceActivity.this.overridePendingTransition(0, 0);
                    PersonalInvoiceActivity.this.startActivity(PersonalInvoiceActivity.this.getIntent());
                    checker += 1;
                } else if (checker > 0) {
                    Toast.makeText(PersonalInvoiceActivity.this, "Up to Date", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                products.clear();
                checker = 0;
                Intent intent = new Intent(PersonalInvoiceActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void convertPayment() {
        int i = 0;
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //Jika tombol aboutMe ditekan, halaman akn berganti ke Main Activity
        if (item.getItemId() == R.id.homeButton) {
            products.clear();
            checker = 0;
            Toast.makeText(this, "Home Selected", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(PersonalInvoiceActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}