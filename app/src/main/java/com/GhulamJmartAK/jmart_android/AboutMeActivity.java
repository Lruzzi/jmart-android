package com.GhulamJmartAK.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AboutMeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        TextView name = (TextView) findViewById(R.id.nameOutputAccount);
        name.setText("" + LoginActivity.getLoggedAccount().name);

        TextView email = (TextView) findViewById(R.id.emailOutputAccount);
        email.setText("" + LoginActivity.getLoggedAccount().email);

        TextView balance = (TextView) findViewById(R.id.balanceOutputAccount);
        balance.setText("" + LoginActivity.getLoggedAccount().balance);
    }

}