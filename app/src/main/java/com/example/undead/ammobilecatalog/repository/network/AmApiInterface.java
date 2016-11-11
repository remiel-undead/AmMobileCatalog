package com.example.undead.ammobilecatalog.repository.network;

import com.example.undead.ammobilecatalog.model.ResponseObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface AmApiInterface {
    @GET("catalog")
    Call<ResponseObject> getCatalog(@Header("Authorization") String authorization);

}
