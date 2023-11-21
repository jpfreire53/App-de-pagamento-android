package com.example.appfinalcomcielo.views.view;

import com.example.appfinalcomcielo.model.Address;
import com.hannesdorfmann.mosby3.mvp.MvpView;

public interface SiginContract extends MvpView {
    void setDataViews(Address address);
}
