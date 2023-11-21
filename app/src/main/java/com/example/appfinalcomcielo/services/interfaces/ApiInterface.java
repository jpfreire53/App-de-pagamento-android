package com.example.appfinalcomcielo.services.interfaces;

import com.example.appfinalcomcielo.model.Address;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {


    @GET("ws/{cep}/json/")
    Call<Address> setCep(@Path("cep") String cep);




}
