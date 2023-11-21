package com.example.appfinalcomcielo.presenter;

import android.content.Context;

import com.example.appfinalcomcielo.model.Fatura;
import com.example.appfinalcomcielo.utils.MyDatabaseHelper;
import com.example.appfinalcomcielo.views.view.HomeContract;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.ArrayList;

public class HomePresenter extends MvpBasePresenter<HomeContract> {

    private Context context;
    public MyDatabaseHelper myDB;


    public HomePresenter(Context context) {
        this.context = context;
        myDB = new MyDatabaseHelper(context);
    }

    ArrayList<Fatura> selectFaturas(String userEmail) {return  myDB.getFaturasDoUsuario(userEmail);}





}
