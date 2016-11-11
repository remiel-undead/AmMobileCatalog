package com.example.undead.ammobilecatalog;

import android.app.Application;

import com.example.undead.ammobilecatalog.bus.BusProvider;
import com.example.undead.ammobilecatalog.repository.CatalogRepository;
import com.squareup.otto.Bus;

public class AmMobileCatalogApplication extends Application {
    private CatalogRepository mCatalogRepository;
    private Bus mBus;

    @Override
    public void onCreate() {
        super.onCreate();
        mBus = BusProvider.getInstance();
        mCatalogRepository = new CatalogRepository(mBus);
        mBus.register(mCatalogRepository);
    }
}
