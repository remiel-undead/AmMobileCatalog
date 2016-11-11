package com.example.undead.ammobilecatalog.repository;

import com.example.undead.ammobilecatalog.bus.BusProvider;
import com.example.undead.ammobilecatalog.bus.ErrorEvent;
import com.example.undead.ammobilecatalog.bus.LoginEvent;
import com.example.undead.ammobilecatalog.bus.LoginPerformedEvent;
import com.example.undead.ammobilecatalog.model.ResponseObject;
import com.example.undead.ammobilecatalog.repository.network.AmApiInterface;
import com.example.undead.ammobilecatalog.utils.CryptUtils;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.io.IOException;

import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CloudDataSource {
    private static final String BASE_URL = "http://dev1-am.d.aeroidea.ru/api/";
    private static final String DEFAULT_ERROR_MESSAGE = "Failed to process the request.";

    private AmApiInterface mApi;
    private Bus mBus;

    public CloudDataSource() {
        mBus = BusProvider.getInstance();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApi = retrofit.create(AmApiInterface.class);
    }

    @Subscribe
    public void onLogin(LoginEvent loginEvent) {
        String authString = Credentials.basic(loginEvent.login, CryptUtils.decrypt(loginEvent.passwordEncrypted));
        mApi.getCatalog(authString).enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                if (!response.isSuccessful()) {
                    try {
                        mBus.post(new ErrorEvent(response.errorBody().string()));
                    } catch (IOException e) {
                        mBus.post(new ErrorEvent(DEFAULT_ERROR_MESSAGE));
                    }
                } else {
                        mBus.post(new LoginPerformedEvent(response.body().getCatalog()));
                    }
             }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable t) {
                mBus.post(new ErrorEvent(DEFAULT_ERROR_MESSAGE));
            }
        });
    }
}
