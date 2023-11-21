package com.example.appfinalcomcielo.views.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appfinalcomcielo.R;
import com.example.appfinalcomcielo.databinding.ActivitySiginBinding;
import com.example.appfinalcomcielo.model.Address;
import com.example.appfinalcomcielo.presenter.SiginPresenter;
import com.example.appfinalcomcielo.services.CepTextWacher;
import com.example.appfinalcomcielo.utils.CepUtil;
import com.example.appfinalcomcielo.utils.MyDatabaseHelper;
import com.example.appfinalcomcielo.services.ZipCodeListener;
import com.example.appfinalcomcielo.utils.SharedPreferencesHelper;
import com.example.appfinalcomcielo.views.view.SiginContract;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;

public class SiginActivity extends MvpActivity<SiginContract, SiginPresenter> implements SiginContract {

    MyDatabaseHelper myDB;
    ActivitySiginBinding binding;
    CepUtil cepUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySiginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        myDB = new MyDatabaseHelper(this);
        binding.editTextText4.addTextChangedListener(new ZipCodeListener(this.getPresenter()));
        Spinner spStates = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.states, android.R.layout.simple_spinner_item);
        spStates.setAdapter(adapter);

        binding.editTextText5.setEnabled(false);
        binding.editTextText6.setEnabled(false);
        binding.editTextText8.setEnabled(false);
        binding.editTextText9.setEnabled(false);
        binding.spinner.setEnabled(false);


        cepUtil = new CepUtil(this,
                R.id.editTextText4, //cep
                R.id.editTextText5, //rua
                R.id.editTextText6, //numero
                R.id.editTextText8, //cidade
                R.id.editTextText9, //bairro
                R.id.spinner);

        binding.editTextText4.addTextChangedListener(new CepTextWacher(binding.editTextText4));

        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = binding.editTextText.getText().toString();
                String senha = binding.editTextText2.getText().toString();
                String email = binding.editTextText3.getText().toString();
                String rua = binding.editTextText5.getText().toString();
                String cep = binding.editTextText4.getText().toString();
                String numero = binding.editTextText6.getText().toString();
                String cidade = binding.editTextText8.getText().toString();
                String bairro = binding.editTextText9.getText().toString();

                if (nome.equals("") || senha.equals("") || email.equals("") || rua.equals("") || numero.equals("") || cidade.equals("") || bairro.equals("") || cep.equals("")) {
                    Toast.makeText(SiginActivity.this, "Há campos não preenchidos.", Toast.LENGTH_SHORT).show();
                } else if (!email.contains("@") || !email.endsWith(".com")){
                    Toast.makeText(SiginActivity.this, "Email digitado de maneira incorreta.", Toast.LENGTH_SHORT).show();
                } else {
                    boolean checkEmail = myDB.checkEmail(email);
                    if (checkEmail == false) {
                        boolean insert = myDB.addSigin(nome, email, senha);
                        if (insert == true) {
                            Toast.makeText(SiginActivity.this, "Cadastro Bem Sucedido.", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(SiginActivity.this, "Erro ao Cadastrar.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SiginActivity.this, "O Usuário já existe, por favor faça o Login.", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();

        }


    }



    @Override
    public void setDataViews(Address address) {
        binding.editTextText5.setEnabled(true);
        binding.editTextText6.setEnabled(true);
        binding.editTextText8.setEnabled(true);
        binding.editTextText9.setEnabled(true);
        binding.spinner.setEnabled(true);

        setFields(R.id.editTextText5, address.getLogradouro());
        setFields(R.id.editTextText8, address.getLocalidade());
        setFields(R.id.editTextText9, address.getBairro());
        setSpinner(R.id.spinner, R.array.states, address.getUf());
    }

    private void setFields(int id, String data) {
        ((EditText) findViewById(id)).setText(data);
    }

    private void setSpinner(int id, int arrayId, String data) {
        String[] itens = getResources().getStringArray(arrayId);
        for (int i = 0; i < itens.length; i++) {
            if (itens[i].endsWith("(" + data + ")")) {
                ((Spinner) findViewById(id)).setSelection(i);
                return;
            }
        }
        ((Spinner) findViewById(id)).setSelection(0);
    }


    @NonNull
    @Override
    public SiginPresenter createPresenter() {
        return new SiginPresenter(this);
    }

}