package com.example.appfinalcomcielo.presenter;

import android.content.Context;

import com.example.appfinalcomcielo.model.Usuario;
import com.example.appfinalcomcielo.utils.MyDatabaseHelper;
import com.example.appfinalcomcielo.views.view.AddContract;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.ArrayList;
import java.util.List;

public class AddPresenter extends MvpBasePresenter<AddContract> {
    private Context context;
    public MyDatabaseHelper myDB;


    public AddPresenter(Context context) {
        this.context = context;
        myDB = new MyDatabaseHelper(context);
    }


    ArrayList<Usuario> selectUsers(){
        return myDB.selectFuncionarios();
    }




}
