package com.example.appfinalcomcielo.views.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.appfinalcomcielo.R;
import com.example.appfinalcomcielo.databinding.ActivityHomeBinding;
import com.example.appfinalcomcielo.model.Fatura;
import com.example.appfinalcomcielo.presenter.HomePresenter;
import com.example.appfinalcomcielo.services.CustomAdapter;
import com.example.appfinalcomcielo.utils.MyDatabaseHelper;
import com.example.appfinalcomcielo.utils.SharedPreferencesHelper;
import com.example.appfinalcomcielo.views.view.AddContract;
import com.example.appfinalcomcielo.views.view.HomeContract;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import java.util.ArrayList;

public class HomeActivity extends MvpActivity<HomeContract, HomePresenter> {

    RecyclerView recyclerView;
    CustomAdapter customAdapter;
    ActivityHomeBinding binding;
    SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferencesHelper = new SharedPreferencesHelper(getApplicationContext());
        recyclerView = findViewById(R.id.recyclerView);
        ArrayList<Fatura> faturas = createPresenter().myDB.getFaturasDoUsuario(sharedPreferencesHelper.getUsuario());
        if (faturas.size() <= 0) {
            binding.boxEmpty.setVisibility(View.VISIBLE);
            binding.emptyInvoice.setVisibility(View.VISIBLE);
        }else {
            customAdapter = new CustomAdapter(HomeActivity.this, this, createPresenter().myDB.getFaturasDoUsuario(sharedPreferencesHelper.getUsuario()));
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(customAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            // Lidar com o clique no item de logout aqui
            SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(this);
            sharedPreferencesHelper.fazerLogout();

            // Redirecionar de volta para a tela de login ou onde quer que deseje
            Toast.makeText(this, "Logout efetuado.", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public HomePresenter createPresenter() {
        return new HomePresenter(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }

    }

}
