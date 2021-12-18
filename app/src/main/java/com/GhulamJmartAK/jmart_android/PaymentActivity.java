package com.GhulamJmartAK.jmart_android;

/**
 * Class yang berfungsi untuk halaman pembelian product yang ingin dibeli pengguna
 * @author Ghulam Izzul Fuad
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.GhulamJmartAK.jmart_android.model.Payment;
import com.GhulamJmartAK.jmart_android.request.PaymentRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentActivity extends AppCompatActivity {

    private static final Gson gson = new Gson();
    private static Payment paid = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        TextView name = (TextView) findViewById(R.id.nameProductPayment);
        TextView shipmentPlans = (TextView) findViewById(R.id.shipmentPlansPayment);
        TextView price = (TextView) findViewById(R.id.priceProductPayment);
        EditText productCount = (EditText) findViewById(R.id.payment_product_count);
        EditText address = (EditText) findViewById(R.id.address_payment);
        Button buy = (Button) findViewById(R.id.buy_payment);
        name.setText(productFragment.productClicked.name);
        switch (productFragment.productClicked.shipmentPlans) {
            case 0:
                shipmentPlans.setText("INSTANT");
                break;
            case 1:
                shipmentPlans.setText("SAME DAY");
                break;
            case 2:
                shipmentPlans.setText("NEXT DAY");
                break;
            case 3:
                shipmentPlans.setText("REGULER");
                break;
            default:
                shipmentPlans.setText("KARGO");
                break;
        }
        price.setText(String.valueOf(productFragment.productClicked.price));
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object != null){
                                paid = gson.fromJson(object.toString(),Payment.class);
                                System.out.println(paid);
                                Intent intent = new Intent(PaymentActivity.this,MainActivity.class);
                                LoginActivity.loggedAccount.balance -= productFragment.productClicked.price*Double.parseDouble(productCount.getText().toString())*(1 - productFragment.productClicked.discount/100);
                                Toast.makeText(PaymentActivity.this, "Pembayaran Berhasil!", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                            if(LoginActivity.loggedAccount.id == productFragment.productClicked.accountId) {
                                Toast.makeText(PaymentActivity.this, "Pembayaran Error, Anda membeli Produk Toko Anda Sendiri!", Toast.LENGTH_SHORT).show();
                            }
                            else if(LoginActivity.loggedAccount.balance < productFragment.productClicked.price*Double.parseDouble(productCount.getText().toString())*(1 - productFragment.productClicked.discount/100)) {
                                Toast.makeText(PaymentActivity.this, "Pembayaran Error, Saldo Tidak Cukup!", Toast.LENGTH_SHORT).show();
                            }

                            else{
                                Toast.makeText(PaymentActivity.this, "Pembayaran Error!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                };
                PaymentRequest paymentRequest = new PaymentRequest(productCount.getText().toString(),address.getText().toString(),listener,null);
                RequestQueue requestQueue = Volley.newRequestQueue(PaymentActivity.this);
                requestQueue.add(paymentRequest);
            }
        });
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
            Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}