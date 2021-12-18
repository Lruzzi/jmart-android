package com.GhulamJmartAK.jmart_android;
/**
 * Class untuk menampilkan halaman Login akun dengan email dan password
 * @author Ghulam Izzul Fuad
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.GhulamJmartAK.jmart_android.model.Store;
import com.android.volley.*;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.GhulamJmartAK.jmart_android.request.LoginRequest;
import com.GhulamJmartAK.jmart_android.model.Account;

import org.json.*;

public class LoginActivity extends AppCompatActivity {
    private static final Gson gson = new Gson();
    public static Account loggedAccount = null;

    //Digunakan untuk mendapatkan informasi akun yang telah login
    public static Account getLoggedAccount(){
        return loggedAccount;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView register = findViewById(R.id.textToRegister);
        //register button, akan menuju ke laman register jika pengguna menekan tombol ini
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        EditText textemail = findViewById(R.id.emailLogin);
        EditText textpassword = findViewById(R.id.passwordLogin);
        Button buttonlogin = findViewById(R.id.buttonLogin);
        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object != null){
                                Toast.makeText(LoginActivity.this, "Login Success!", Toast.LENGTH_SHORT);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                loggedAccount = gson.fromJson(object.toString(), Account.class);
                                startActivity(intent);
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Login Error!", Toast.LENGTH_SHORT);
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(textemail.getText().toString(), textpassword.getText().toString(), listener, null);
                RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                requestQueue.add(loginRequest);
            }
        });
    }

    //digunakan di About Me activity untuk mengambil data store
    public static void insertLoggedAccountStore(String response){
        Store newStore = gson.fromJson(response, Store.class);
        loggedAccount.store = newStore;
    }
}