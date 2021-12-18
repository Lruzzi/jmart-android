package com.GhulamJmartAK.jmart_android.request;
/**
 * Class Create ProcuctRequest berfungsi untuk menghubungkan ke back-end \
 * sehingga pengguna dapat medaftarkan product yang ingin dijual
 * @Author Ghulam Izzul Fuad
 */

import androidx.annotation.Nullable;

import com.GhulamJmartAK.jmart_android.LoginActivity;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CreateProductRequest extends StringRequest {

    public static final String URL = "http://10.0.2.2:8080/product/create";
    public final Map<String,String> params;

    public CreateProductRequest(String name, String weight, String conditionUsed, String price, String discount, String category, String shipmentPlans, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        Integer i = LoginActivity.loggedAccount.id;
        params.put("accountId", i.toString());
        params.put("name",name);
        params.put("weight",weight);
        params.put("conditionUsed",conditionUsed);
        params.put("price", price);
        params.put("discount", discount);
        params.put("category", category);
        params.put("shipmentPlans", shipmentPlans);
    }

    public Map<String,String> getParams(){return params;}
}