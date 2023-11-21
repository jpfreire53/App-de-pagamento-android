package com.example.appfinalcomcielo.presenter;

import android.database.Cursor;
import android.widget.EditText;

import com.example.appfinalcomcielo.utils.MyDatabaseHelper;
import com.example.appfinalcomcielo.views.view.MainContract;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

public class MainPresenter extends MvpBasePresenter<MainContract> {

    private MyDatabaseHelper myDB;

    public boolean isCredentialsOk(String email, String senha) {
        String query = "SELECT * FROM " + MyDatabaseHelper.COLUMN_NAME + " WHERE " + MyDatabaseHelper.COLUMN_EMAIL + " = ? AND " + MyDatabaseHelper.COLUMN_PASSWORD + " = ?";
        String[] whereArgs = {email, senha};
        myDB.getReadableDatabase();
        Cursor cursor = myDB.getReadableDatabase().rawQuery(query, whereArgs);
        int count = cursor.getCount();
        cursor.close();
        myDB.close();
        return count >= 1;

    }


}
