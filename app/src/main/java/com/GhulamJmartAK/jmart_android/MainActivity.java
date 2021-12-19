package com.GhulamJmartAK.jmart_android;
/**
 * Class untuk menampilkan halaman utama yang berisi list produk atau filter produk
 * dan juga tombol search, create produk, dan detail akun (aboutMe)
 * @author Ghulam Izzul Fuad
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.widget.SearchView;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MenuItem search;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewp);
        tabLayout.setupWithViewPager(viewPager);
        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        //fragment Produk (kiri)
        vpAdapter.addFragment(new productFragment(), "Product");
        //fragment Filter (kanan)
        vpAdapter.addFragment(new filterFragment(), "Filter");
        viewPager.setAdapter(vpAdapter);
    }

    //Memuat top menu yang berisi search, create produk, dan aboutme
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        MenuItem create = menu.findItem(R.id.add_button);
        search = menu.findItem(R.id.search_button);
        searchView = (SearchView) search.getActionView();
        searchView.setQueryHint("Butuh apa hari ini?");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                productFragment.listViewAdapter.getFilter().filter(newText);

                return false;
            }
        });

        create.setVisible(LoginActivity.getLoggedAccount().store != null);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //Jika tombol aboutMe ditekan, halaman akn berganti ke detail akun (aboutMeActivity)
        if (item.getItemId() == R.id.account_button) {
            Toast.makeText(this, "Account Selected", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, AboutMeActivity.class);
            startActivity(intent);
        }
        //Jika tombol create produk ditekan, halaman akan berganti ke createProductActivity
        if (item.getItemId() == R.id.add_button) {
            Toast.makeText(this, "Create Product Selected", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, CreateProductActivity.class);
            startActivity(intent);
        }
        //TOmbol untuk ke personal invoice
        if (item.getItemId() == R.id.history_personal_button) {
            Toast.makeText(this, "History Account Selected", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, PersonalInvoiceActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}