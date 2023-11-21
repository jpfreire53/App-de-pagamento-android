package com.example.appfinalcomcielo.services;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.example.appfinalcomcielo.presenter.SiginPresenter;
import com.example.appfinalcomcielo.views.activity.SiginActivity;

import java.net.ContentHandler;

public class ZipCodeListener implements TextWatcher {
    private SiginPresenter presenter;

    public ZipCodeListener(SiginPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String zipCode = editable.toString();
        Log.e("TAG", "afterTextChanged: " + zipCode);
        if (zipCode.length() == 8) {
            presenter.getAddress(zipCode);
        }

    }
}
