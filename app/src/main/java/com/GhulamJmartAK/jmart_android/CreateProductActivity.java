package com.GhulamJmartAK.jmart_android;
/**
 * Class ini untuk mendaftarkan produk yang ingin di jual sehingga terdaftar di sistem
 * @author Ghulam Izzul Fuad
 *
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.GhulamJmartAK.jmart_android.model.Product;
import com.GhulamJmartAK.jmart_android.request.CreateProductRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateProductActivity extends AppCompatActivity {

    private static final Gson gson = new Gson();
    private static  Product product = null;

    //menghubungkan XML dengan kelas CrateProductActivity.java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);
        EditText nameInput = (EditText) findViewById(R.id.nameInputCreate);
        EditText weightInput = (EditText) findViewById(R.id.weightInputCreate);
        EditText priceInput = (EditText) findViewById(R.id.priceInputCreate);
        EditText discountInput = (EditText) findViewById(R.id.discountInputCreate);
        CheckBox newCheck = (CheckBox) findViewById(R.id.newCheckCreate);
        CheckBox usedCheck = (CheckBox) findViewById(R.id.usedCheckCreate);
        Spinner category = (Spinner) findViewById(R.id.categoryDrawCreate);
        Spinner shipmentPlans = (Spinner) findViewById(R.id.shipmentDrawCreate);
        Button create = (Button) findViewById(R.id.create_product_button);
        Button clear = (Button) findViewById(R.id.clear_product_button);

        newCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    usedCheck.setChecked(false);
                }
            }
        });

        usedCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    newCheck.setChecked(false);
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameInput.setText("");
                weightInput.setText("");
                priceInput.setText("");
                discountInput.setText("");
                usedCheck.setChecked(false);
                newCheck.setChecked(false);
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            product = gson.fromJson(object.toString(),Product.class);
                            Toast.makeText(CreateProductActivity.this,"Product Terdaftar",Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                        System.out.println(product);
                    }
                };
                CreateProductRequest requestProduct = new CreateProductRequest(nameInput.getText().toString(),weightInput.getText().toString(),String.valueOf(newCheck.isChecked()),priceInput.getText().toString(),discountInput.getText().toString(),category.getSelectedItem().toString(),convertShipmentPlans(shipmentPlans.getSelectedItem().toString()),listener,null);
                RequestQueue requestQueue = Volley.newRequestQueue(CreateProductActivity.this);
                requestQueue.add(requestProduct);
            }
        });
    }

    //Menassign metode shipment menjadi angka dalam bentuk string
    protected String convertShipmentPlans(String shipment){
        switch (shipment) {
            case "INSTANT":
                return "0";
            case "SAME DAY":
                return "1";
            case "NEXT DAY":
                return "2";
            case "REGULER":
                return "3";
            default:
                return "4";
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
            Toast.makeText(this, "Home Selected", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CreateProductActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}