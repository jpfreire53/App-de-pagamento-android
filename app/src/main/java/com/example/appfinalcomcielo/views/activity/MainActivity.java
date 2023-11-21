package com.example.appfinalcomcielo.views.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appfinalcomcielo.databinding.ActivityMainBinding;
import com.example.appfinalcomcielo.model.Usuario;
import com.example.appfinalcomcielo.presenter.MainPresenter;
import com.example.appfinalcomcielo.utils.CieloUtil;
import com.example.appfinalcomcielo.utils.MyDatabaseHelper;
import com.example.appfinalcomcielo.utils.SharedPreferencesHelper;
import com.example.appfinalcomcielo.views.view.MainContract;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import java.util.ArrayList;

public class MainActivity extends MvpActivity<MainContract, MainPresenter> {

    MyDatabaseHelper myDB;
    ActivityMainBinding binding;
    SharedPreferencesHelper preferencesHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        myDB = new MyDatabaseHelper(this);
        preferencesHelper = new SharedPreferencesHelper(MainActivity.this);
        String padrao = "@adm.com";
        ArrayList<Usuario> usuarios =  myDB.selectAll();
        if(usuarios.size() == 0 || usuarios == null){
            myDB.addSigin("Joao Pedro", "jp@adm.com", "1234");
        }

        if (preferencesHelper.isUsuarioLogado()) {
            String emailUser = preferencesHelper.getUsuario();
            String senhaUser = preferencesHelper.getSenha();

            if (emailUser.contains(padrao)){
                Intent intent = new Intent(getApplicationContext(), AdmActivity.class);
                startActivity(intent);
            }else {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        }else {
            Toast.makeText(this, "Usuário Não Logado!", Toast.LENGTH_SHORT).show();
        }

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.editTextText.getText().toString();
                String senha = binding.editTextText2.getText().toString();
                String padrao = "@adm.com";



                if (email.equals("") || senha.equals("")) {
                    Toast.makeText(MainActivity.this, "Há campos não preenchidos.", Toast.LENGTH_SHORT).show();
                } else {
                    boolean checkCredentials = myDB.checkEmailPassword(email, senha);
                    if (checkCredentials) {
                        preferencesHelper.salvarUsuario(email, senha);
                        if (email.contains(padrao)) {
                            Toast.makeText(MainActivity.this, "Login Bem Sucedido.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), AdmActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Login Bem Sucedido.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Cadastro Inválido.", Toast.LENGTH_SHORT).show();
                        binding.editTextText2.setText("");
                    }
                }
            }

        });


    }

    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }
}