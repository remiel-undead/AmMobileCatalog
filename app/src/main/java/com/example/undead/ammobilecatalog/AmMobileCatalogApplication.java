package com.example.undead.ammobilecatalog;

import android.app.Application;

import com.example.undead.ammobilecatalog.bus.BusProvider;
import com.example.undead.ammobilecatalog.repository.CatalogRepository;
import com.example.undead.ammobilecatalog.repository.network.PicassoOkHttpClientAuthenticator;
import com.example.undead.ammobilecatalog.utils.SharedPrefUtils;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.orm.SchemaGenerator;
import com.orm.SugarContext;
import com.orm.SugarDb;
import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;

public class AmMobileCatalogApplication extends Application {
    private CatalogRepository mCatalogRepository;
    private Bus mBus;

    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
        // create table if not exists
        SchemaGenerator schemaGenerator = new SchemaGenerator(this);
        schemaGenerator.createDatabase(new SugarDb(this).getDB());

        mBus = BusProvider.getInstance();
        mCatalogRepository = new CatalogRepository(mBus);
        mBus.register(mCatalogRepository);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .authenticator(new PicassoOkHttpClientAuthenticator(SharedPrefUtils.getSharedPrefLogin(this),
                                SharedPrefUtils.getSharedPrefPasswordEncrypted(this))
                ).build();
        Picasso picasso = new Picasso.Builder(this)
                .downloader(new OkHttp3Downloader(okHttpClient))
                .build();
        Picasso.setSingletonInstance(picasso);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }
}
