package com.example.appfinalcomcielo.views.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appfinalcomcielo.R;
import com.example.appfinalcomcielo.databinding.ActivityAddBinding;
import com.example.appfinalcomcielo.model.Fatura;
import com.example.appfinalcomcielo.model.Usuario;
import com.example.appfinalcomcielo.presenter.AddPresenter;
import com.example.appfinalcomcielo.presenter.MainPresenter;
import com.example.appfinalcomcielo.services.MoneyTextWatcher;
import com.example.appfinalcomcielo.utils.MyDatabaseHelper;
import com.example.appfinalcomcielo.utils.SharedPreferencesHelper;
import com.example.appfinalcomcielo.views.view.AddContract;
import com.example.appfinalcomcielo.views.view.MainContract;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddActivity extends MvpActivity<AddContract, AddPresenter> {

    ActivityAddBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.valorTxt.addTextChangedListener(new MoneyTextWatcher((EditText)binding.valorTxt));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, createList());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.emailTxt.setAdapter(dataAdapter);

        List<String> isCredit = new ArrayList<>(Arrays.asList("C", "D"));
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, isCredit);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.isCredit.setAdapter(dataAdapter1);

        List<String> parcelaTxt = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"));
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, parcelaTxt);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.parcelaTxt.setAdapter(dataAdapter2);


        binding.isCredit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String valorSelecionado = isCredit.get(i);
                if (valorSelecionado.equals("D")){
                    binding.parcelaTxt.setSelection(0);
                    binding.parcelaTxt.setEnabled(false);
                }else {
                    binding.parcelaTxt.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailUser = binding.emailTxt.getSelectedItem().toString();
                String valor = binding.valorTxt.getText().toString();
                String produto = binding.produtoTxt.getText().toString();
                String parcela = binding.parcelaTxt.getSelectedItem().toString();
                String isCredit = binding.isCredit.getSelectedItem().toString();

                boolean checkEmail = createPresenter().myDB.checkEmail(emailUser);
                if(emailUser.equals("") || valor.equals("") || produto.equals("") || parcela.equals("") || isCredit.equals("")){
                    Toast.makeText(AddActivity.this, "Há campos em Branco!", Toast.LENGTH_SHORT).show();
                }else {
                    if (checkEmail == true) {
                        Fatura fatura = new Fatura(emailUser, produto, valor, isCredit, parcela, "");
                        boolean insert = createPresenter().myDB.addInvoice(fatura);
                        if (insert == true){
                            Toast.makeText(AddActivity.this, "Fatura Adicionada!", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(AddActivity.this, "Não foi possível adicionar a Fatura.", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(AddActivity.this, "Email Inválido.", Toast.LENGTH_SHORT).show();
                    }
                }





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
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public AddPresenter createPresenter() {
        return new AddPresenter(this);
    }

    public List<String> createList() {
        List<String> emailList = new ArrayList<>();
        ArrayList<Usuario> users = createPresenter().myDB.selectFuncionarios();
        for (int i =0 ; i< users.size() ; i++) {
            emailList.add(users.get(i).getEmail());
        }
        return emailList;
    }

}