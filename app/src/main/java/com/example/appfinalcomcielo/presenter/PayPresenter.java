package com.example.appfinalcomcielo.presenter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.appfinalcomcielo.model.Fatura;
import com.example.appfinalcomcielo.utils.CieloUtil;
import com.example.appfinalcomcielo.utils.MyDatabaseHelper;
import com.example.appfinalcomcielo.views.activity.HomeActivity;
import com.example.appfinalcomcielo.views.view.PayContract;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

public class PayPresenter extends MvpBasePresenter<PayContract> {
    Context context;
    MyDatabaseHelper myDB;
    String id;



    public void pagar(Context context, Fatura fatura) {
        this.context = context;
        realizarPagamento(fatura);
    }

    private boolean realizarPagamento(Fatura fatura) {
        CieloUtil.getInstance(context).criarPedido();
        CieloUtil.getInstance(context).adicionarPedidos(Long.parseLong(fatura.getValue()), fatura.getProduct());
        CieloUtil.getInstance(context).liberarPedido();
        CieloUtil.getInstance(context).pagamento(fatura);

        return true;
    }
    private void apagarFaturaDoBancoDeDados(Context context, String faturaId) {
        MyDatabaseHelper myDB = new MyDatabaseHelper(context);
        SQLiteDatabase db = myDB.getWritableDatabase();

        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(faturaId)};

        db.delete("my_invoice", whereClause, whereArgs);

        db.close();
    }
}
