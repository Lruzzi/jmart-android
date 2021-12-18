package com.GhulamJmartAK.jmart_android;
/**
 * Class yang menampilkan laman yang berisi informasi produk yang dipilih dari main activity
 * @Author GHulam Izzul Fuad
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
import android.widget.TextView;
import android.widget.Toast;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        TextView name = (TextView) findViewById(R.id.productName);
        TextView weight = (TextView) findViewById(R.id.weightProduct);
        TextView conditionProduct = (TextView) findViewById(R.id.conditionProduct);
        TextView price = (TextView) findViewById(R.id.priceProduct);
        TextView discount = (TextView) findViewById(R.id.discountProduct);
        TextView category = (TextView) findViewById(R.id.categoryProduct);
        TextView shipmentPlans = (TextView) findViewById(R.id.shipmentPlansProduct);
        Button buyButton = findViewById(R.id.checkout_button);
        Button backButton = findViewById(R.id.back_button);

        name.setText(productFragment.productClicked.name);
        weight.setText(String.valueOf(productFragment.productClicked.weight));
        conditionProduct.setText(convertConditionUsed(productFragment.productClicked.conditionUsed));
        price.setText("Rp." + String.valueOf(productFragment.productClicked.price));
        discount.setText(String.valueOf(productFragment.productClicked.discount));
        category.setText(String.valueOf(productFragment.productClicked.category));
        shipmentPlans.setText(convertShipmentPlans(productFragment.productClicked.shipmentPlans));

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetailActivity.this, PaymentActivity.class);
                startActivity(intent);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private String convertShipmentPlans(byte shipment){
        switch (shipment) {
            case 0:
                return "INSTANT";
            case 1:
                return "SAME DAY";
            case 2:
                return "NEXT DAY";
            case 3:
                return "REGULER";
            default:
                return "CARGO";
        }
    }

    private String convertConditionUsed(boolean conditionUsed){
        if (conditionUsed) {
            return "Used";
        }else{
            return "New";
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
            Intent intent = new Intent(ProductDetailActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}