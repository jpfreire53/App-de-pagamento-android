package com.example.appfinalcomcielo.utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.appfinalcomcielo.model.Fatura;
import com.example.appfinalcomcielo.model.Usuario;

import java.util.ArrayList;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    public static final String DATABASE_NAME = "SiginLibrary.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "my_sigin";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "sigin_name";
    public static final String COLUMN_PASSWORD = "sigin_password";
    public static final String COLUMN_EMAIL = "sigin_email";


    public static final String TABLE_NAME2 = "my_invoice";
    public static final String COLUMN_ID2 = "_id";
    public static final String COLUMN_USER = "email_user";
    public static final String COLUMN_VALUE = "invoice_value";
    public static final String COLUMN_PRODUCT = "invoice_product";
    public static final String COLUMN_CREDIT = "invoice_credit";
    public static final String COLUMN_PARCEL = "invoice_parcel";

    public SQLiteDatabase db;
    private Usuario user;


    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        db = getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PASSWORD + " INTEGER);";
        db.execSQL(query);

        String invoice = "CREATE TABLE " + TABLE_NAME2 +
                " (" + COLUMN_ID2 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER + " TEXT, " +
                COLUMN_VALUE + " TEXT, " +
                COLUMN_PARCEL + " TEXT, " +
                COLUMN_CREDIT + " TEXT, " +
                COLUMN_PRODUCT + " TEXT);";
        db.execSQL(invoice);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        onCreate(db);
    }

    public boolean  addSigin(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_EMAIL, email);
        cv.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            //Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            //Toast.makeText(context, "Successfully", Toast.LENGTH_SHORT).show();
            return true;
        }


    }

    public boolean addInvoice(Fatura fatura) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();


        cv.put(COLUMN_USER, fatura.getUserEmail());
        cv.put(COLUMN_PRODUCT, fatura.getProduct());
        cv.put(COLUMN_VALUE, fatura.getValue());
        cv.put(COLUMN_PARCEL, fatura.getParcel());
        cv.put(COLUMN_CREDIT, fatura.getIsCredit());
        long result = db.insert(TABLE_NAME2, null, cv);
        if (result == -1) {
            //Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            //Toast.makeText(context, "Successfully", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    @SuppressLint("Range")
    public ArrayList<Fatura> getFaturasDoUsuario(String userEmail) {
        ArrayList<Fatura> faturasDoUsuario = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME2 + " WHERE " + COLUMN_USER + " = ?";
        String[] selectionArgs = {userEmail};
        Cursor cursor;
        cursor = db.rawQuery(query, selectionArgs);

        if (cursor.moveToFirst()) {
            do {
                Fatura fatura = new Fatura(cursor.getString(cursor.getColumnIndex(COLUMN_USER)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_VALUE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_CREDIT)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_PARCEL)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_ID2))
                );
                faturasDoUsuario.add(fatura);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return faturasDoUsuario;
    }

    @SuppressLint("Range")
    public ArrayList<Usuario> selectFuncionarios() {
        ArrayList<Usuario> result = new ArrayList<>();
        Cursor cursor;
        Usuario user;
        String selectSql = "SELECT *  FROM " + TABLE_NAME;
        cursor = db.rawQuery(selectSql, null);

        if (cursor.moveToFirst()) {
            do {
                try {
                    if (!cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)).contains("@adm.com")) {
                        user = new Usuario(
                                cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                                cursor.getInt(cursor.getColumnIndex(COLUMN_PASSWORD)) + ""
                        );
                        result.add(user);
                    }

                } catch (Exception e) {
                    Toast.makeText(context, "Deu erro! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            } while (cursor.moveToNext());
        }

        cursor.close();
        return result;

    }
    @SuppressLint("Range")
    public ArrayList<Usuario> selectAll() {
        ArrayList<Usuario> result = new ArrayList<>();
        Cursor cursor;
        Usuario user;
        String selectSql = "SELECT *  FROM " + TABLE_NAME;
        cursor = db.rawQuery(selectSql, null);

        if (cursor.moveToFirst()) {
            do {
                try {
                        user = new Usuario(
                                cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                                cursor.getInt(cursor.getColumnIndex(COLUMN_PASSWORD)) + ""
                        );
                        result.add(user);

                } catch (Exception e) {
                    Toast.makeText(context, "Deu erro! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            } while (cursor.moveToNext());
        }

        cursor.close();
        return result;

    }


    public boolean checkEmail(String email) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM " + MyDatabaseHelper.TABLE_NAME + " WHERE " + MyDatabaseHelper.COLUMN_EMAIL + " = ?", new String[]{email});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
    public void deleteOneRow(String row_id){
        //int id = Integer.valueOf(row_id);
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME2, "_id=?", new String[]{row_id});
        if(result == - 1){
            //Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();

        }else {
            //Toast.makeText(context, "Successfuly Deleted.", Toast.LENGTH_SHORT).show();
        }

    }
    public boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM " + MyDatabaseHelper.TABLE_NAME + " WHERE " + MyDatabaseHelper.COLUMN_EMAIL + " = ? AND " + MyDatabaseHelper.COLUMN_PASSWORD + " = ?", new String[]{email, password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }


}
