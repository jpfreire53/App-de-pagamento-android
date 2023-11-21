package com.example.appfinalcomcielo.services;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class CepTextWacher implements TextWatcher {

    private final EditText editText;

    public CepTextWacher(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // Não é necessário fazer nada antes da mudança de texto.
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // Não é necessário fazer nada durante a mudança de texto.
    }

    @Override
    public void afterTextChanged(Editable editable) {
        // Remove qualquer traço existente no texto.
        String text = editable.toString().replaceAll("-", "");

        // Verifica se o texto tem 8 dígitos (##### ###).
        if (text.length() == 8) {
            // Adiciona o traço na posição correta.
            text = text.substring(0, 5) + "-" + text.substring(5);
        }

        // Define o texto formatado no EditText.
        editText.removeTextChangedListener(this);
        editText.setText(text);
        editText.setSelection(text.length());
        editText.addTextChangedListener(this);
    }
}
