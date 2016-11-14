package com.example.undead.ammobilecatalog.repository.network;

import com.example.undead.ammobilecatalog.utils.CryptUtils;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class PicassoOkHttpClientAuthenticator implements Authenticator {
    private String login;
    private String passwordEncrypted;

    public PicassoOkHttpClientAuthenticator(String login, String passwordEncrypted) {
        this.login = login;
        this.passwordEncrypted = passwordEncrypted;
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        String authString = Credentials.basic(login, CryptUtils.decrypt(passwordEncrypted));
        return response.request().newBuilder()
                .header("Authorization", authString)
                .build();
    }
}