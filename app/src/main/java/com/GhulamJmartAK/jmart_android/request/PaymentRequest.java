package com.GhulamJmartAK.jmart_android.request;
/**
 * Class PaymentRequest berfungsi untuk menghubungkan ke back-end
 * sehingga pengguna dapat melakukan pembayaran melalui aplikasi dan tersimpan di sistem back-end
 * @author Ghulam Izzul Fuad
 */

import androidx.annotation.Nullable;

import com.GhulamJmartAK.jmart_android.LoginActivity;
import com.GhulamJmartAK.jmart_android.productFragment;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PaymentRequest extends StringRequest {
    public static final String URL = "http://10.0.2.2:8080/payment/create";
    public final Map<String,String> params;

    public PaymentRequest(String productCount, String shipmentAddress, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("buyerId",String.valueOf(LoginActivity.loggedAccount.id));
        params.put("productId",String.valueOf(productFragment.productClicked.id));
        params.put("productCount",productCount);
        params.put("shipmentAddress",shipmentAddress);
        params.put("shipmentPlan",String.valueOf(productFragment.productClicked.shipmentPlans));
    }

    public Map<String,String> getParams(){
        return params;
    }
}
