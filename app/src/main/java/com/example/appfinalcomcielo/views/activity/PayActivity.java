package com.example.appfinalcomcielo.views.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.appfinalcomcielo.R;
import com.example.appfinalcomcielo.databinding.ActivityMainBinding;
import com.example.appfinalcomcielo.databinding.ActivityPayBinding;
import com.example.appfinalcomcielo.model.Fatura;
import com.example.appfinalcomcielo.presenter.PayPresenter;
import com.example.appfinalcomcielo.services.MoneyTextWatcher;
import com.example.appfinalcomcielo.utils.CieloUtil;
import com.example.appfinalcomcielo.utils.MyDatabaseHelper;
import com.example.appfinalcomcielo.views.view.PayContract;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;

public class PayActivity extends MvpActivity<PayContract, PayPresenter> {

    ActivityPayBinding binding;
    CieloUtil cieloUtil;
    Fatura fatura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        String userEmail = intent.getStringExtra("user_email");
        String produto = intent.getStringExtra("produto");
        String valor = intent.getStringExtra("valor");
        String isCredit = intent.getStringExtra("is_Credit");
        String parcela = intent.getStringExtra("parcela");
        String id = intent.getStringExtra("_id");
        cieloUtil.getInstance(this).iniciar();

        binding.txtEmail.setText(userEmail);
        binding.txtProduto.setText(produto);
        binding.txtPreco.setText(valor);
        binding.txtIsCredit.setText(isCredit);
        binding.txtParcela.setText(parcela);
        binding.txtID.setText(id);

        if (binding.txtIsCredit.getText().equals("C")) {
            binding.txtIsCredit.setText("Crédito");
        }if (binding.txtIsCredit.getText().equals("D")) {
            binding.txtIsCredit.setText("Débito");
        }
        fatura = new Fatura(userEmail,
                produto,
                valor,
                isCredit,
                parcela,
                id
        );


        binding.btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPresenter().pagar(PayActivity.this, fatura);

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
    public PayPresenter createPresenter() {
        return new PayPresenter();
    }
}