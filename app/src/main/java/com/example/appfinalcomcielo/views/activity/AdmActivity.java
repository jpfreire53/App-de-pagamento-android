package com.example.appfinalcomcielo.views.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.appfinalcomcielo.R;
import com.example.appfinalcomcielo.databinding.ActivityAdmBinding;
import com.example.appfinalcomcielo.databinding.ActivityHomeBinding;
import com.example.appfinalcomcielo.utils.MyDatabaseHelper;
import com.example.appfinalcomcielo.utils.SharedPreferencesHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AdmActivity extends AppCompatActivity {
    ActivityAdmBinding binding;
    MyDatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        myDB = new MyDatabaseHelper(getApplicationContext());



        binding.btnAddInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myDB.selectFuncionarios().size() >= 1) {
                    Intent intent = new Intent(AdmActivity.this, AddActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(AdmActivity.this, "Não há usuários Cadastrados.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        binding.btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdmActivity.this, SiginActivity.class);
                startActivity(intent);
            }


        });


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
            Toast.makeText(this, "Logout efetuado", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}