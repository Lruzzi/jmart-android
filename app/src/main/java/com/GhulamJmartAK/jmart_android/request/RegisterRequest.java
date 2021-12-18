package com.GhulamJmartAK.jmart_android.request;
/**
 * Class RegisterRequest berfungsi untuk menghubungkan ke back-end
 * sehingga pengguna dapat mendaftar akun melalui aplikasi dan tersimpan di sistem back-end
 * @author Ghulam Izzul Fuad
 */

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    private static final String URL =  "http://10.0.2.2:8080/account/register";
    private final Map<String , String> params;

    public RegisterRequest(String name, String email, String password,
                           Response.Listener<String> listener, Response.ErrorListener errorListener)
    {
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("name", name);
        params.put("email", email);
        params.put("password", password);
    }

    public Map<String , String> getParams() {
        return params;
    }
}
