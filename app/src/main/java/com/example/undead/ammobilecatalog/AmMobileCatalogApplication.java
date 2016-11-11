package com.example.undead.ammobilecatalog;

import android.app.Application;

import com.example.undead.ammobilecatalog.bus.BusProvider;
import com.example.undead.ammobilecatalog.repository.CatalogRepository;
import com.orm.SchemaGenerator;
import com.orm.SugarContext;
import com.orm.SugarDb;
import com.squareup.otto.Bus;

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
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }
}
