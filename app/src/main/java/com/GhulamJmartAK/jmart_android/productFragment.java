package com.GhulamJmartAK.jmart_android;
/**
 * Class yang menampilkan list produk yang telah terdaftar di sistem ke mainActivity
 * @Author Ghulam Izzul Fuad
 */

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.GhulamJmartAK.jmart_android.model.Product;
import com.GhulamJmartAK.jmart_android.request.RequestFactory;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


public class productFragment extends Fragment {

    private static final Gson gson = new Gson();
    public static ArrayList<Product> productsList = new ArrayList<>();

    //Jumlah produk yang ditampilkan perhalaman = 15
    final int pageSize = 15;
    static int page = 0;
    public static Product productClicked = null;
    public static ArrayAdapter<Product> listViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View productView = inflater.inflate(R.layout.fragment_product, container, false);
        EditText inputPage = productView.findViewById(R.id.InputPageProduct);
        Button nextButton = productView.findViewById(R.id.ButtonNext);
        Button prevButton = productView.findViewById(R.id.ButtonPrev);
        Button goButton = productView.findViewById(R.id.ButtonGo);

        inputPage.setText(String.valueOf(page + 1), TextView.BufferType.EDITABLE);

        //Next Button, digunakan untuk berpindah ke halaman selanjutnya
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Next Page", Toast.LENGTH_SHORT).show();
                page += 1;
                getActivity().finish();
                getActivity().startActivity(getActivity().getIntent());
            }
        });

        //Previous Button, digunakan untuk berpindah ke halaman sebelumnya
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Prev Page", Toast.LENGTH_SHORT).show();
                page -= 1;
                //error handling, jika halaman < 0 atau negatif akan tetap berada di halam pertama (0)
                if(page < 0){
                    page = 0;
                }
                getActivity().finish();
                getActivity().startActivity(getActivity().getIntent());
            }
        });

        //Go button, digunakan untuk melompat ke halaman sesuai input user
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Go!", Toast.LENGTH_SHORT).show();
                page = Integer.parseInt(inputPage.getText().toString()) - 1;
                getActivity().finish();
                getActivity().startActivity(getActivity().getIntent());
            }
        });

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray object = new JSONArray(response);
                    if (object != null) {
                        productsList = gson.fromJson(object.toString(), new TypeToken<ArrayList<Product>>() {
                        }.getType()); // line 6
                        System.out.println(productsList);
                        listViewAdapter = new ArrayAdapter<Product>(
                                getActivity(),
                                android.R.layout.simple_list_item_1,
                                productsList
                        );
                        ListView lv = (ListView) productView.findViewById(R.id.ProductListView);

                        lv.setAdapter(listViewAdapter);

                        // Saat sebuah produk ditekan akan menuju ke halaman detail produk untuk menampilkan detail informasi produk
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                //productClicked = gson.fromJson(lv.getItemAtPosition(i).toString(),Product.class);
                                productClicked = (Product) lv.getItemAtPosition(i);
                                Toast.makeText(getActivity(),"Product Clicked", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                                startActivity(intent);
                            }
                        });

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(RequestFactory.getPage("product", page, pageSize, listener, null));

        return productView;
    }
}