package com.example.appfinalcomcielo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;


public class SharedPreferencesHelper {

    private static final String PREF_NAME = "MinhaPreferencia";
    private static final String KEY_NOME_DE_USUARIO = "nome_de_usuario";
    public static final String KEY_SENHA_DO_USUARIO = "senha_do_usuario";


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    public SharedPreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void salvarUsuario(String emailUsuario, String senha) {
        editor.putString(KEY_NOME_DE_USUARIO, emailUsuario);
        editor.putString(KEY_SENHA_DO_USUARIO, senha);
        editor.apply();
    }

    public String getUsuario() {
        return sharedPreferences.getString(KEY_NOME_DE_USUARIO, "");
    }
    public String getSenha() {
        return sharedPreferences.getString(KEY_SENHA_DO_USUARIO, "");
    }

    public boolean isUsuarioLogado() {
        return sharedPreferences.contains(KEY_NOME_DE_USUARIO) && sharedPreferences.contains(KEY_SENHA_DO_USUARIO);
    }

    public void fazerLogout() {
        editor.remove(KEY_NOME_DE_USUARIO);
        editor.remove(KEY_SENHA_DO_USUARIO);
        editor.apply();
    }



}