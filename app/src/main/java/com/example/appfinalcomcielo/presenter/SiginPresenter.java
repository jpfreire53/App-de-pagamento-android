package com.example.appfinalcomcielo.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.appfinalcomcielo.model.Address;
import com.example.appfinalcomcielo.model.Usuario;
import com.example.appfinalcomcielo.services.RetrofitClient;
import com.example.appfinalcomcielo.services.interfaces.ApiInterface;
import com.example.appfinalcomcielo.utils.CepUtil;
import com.example.appfinalcomcielo.utils.MyDatabaseHelper;
import com.example.appfinalcomcielo.views.view.SiginContract;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SiginPresenter extends MvpBasePresenter<SiginContract> {
    private MyDatabaseHelper myDB;
    Usuario usuario;
    CepUtil util;


    public SiginPresenter(Context context) {
        myDB = new MyDatabaseHelper(context);
    }

    public void addSignin(String name, String email, String password, Context context){
        usuario = new Usuario(name, email, password);
        if(usuario.getNome() == null || usuario.getNome().equals("")) {
            Toast.makeText(context, "Há campos não prenchidos", Toast.LENGTH_SHORT).show();
        }else if(usuario.getEmail() == null || usuario.getEmail().equals("") && usuario.getEmail().contains("@") == false && usuario.getEmail().endsWith(".com") == false){
            Toast.makeText(context, "Email inválido", Toast.LENGTH_SHORT).show();
        } else if (usuario.getSenha() == null || usuario.getSenha().equals("")) {
            Toast.makeText(context, "Há campos não prenchidos", Toast.LENGTH_SHORT).show();
        } else {
            myDB.addSigin(name, email, password);
        }
    }

    public void getAddress(String cep) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<Address> call = apiInterface.setCep(cep);

        final Address[] address = {new Address()};

        call.enqueue(new Callback<Address>() {
            @Override
            public void onResponse(Call<Address> call, Response<Address> response) {
                address[0] = response.body();
                ifViewAttached(false, view -> view.setDataViews(address[0]));
            }

            @Override
            public void onFailure(Call<Address> call, Throwable t) {
                Log.e("RETROFIT", "ERRO: " + t.getMessage());
            }
        });
    }
}



