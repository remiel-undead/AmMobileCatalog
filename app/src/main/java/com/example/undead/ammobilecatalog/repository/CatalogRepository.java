package com.example.undead.ammobilecatalog.repository;

import android.os.Handler;
import android.os.Looper;

import com.example.undead.ammobilecatalog.bus.BusProvider;
import com.example.undead.ammobilecatalog.bus.FetchSectionsEvent;
import com.example.undead.ammobilecatalog.bus.FetchSectionsPerformedEvent;
import com.example.undead.ammobilecatalog.bus.FetchSubsectionItemsEvent;
import com.example.undead.ammobilecatalog.bus.FetchSubsectionItemsPerformedEvent;
import com.example.undead.ammobilecatalog.bus.FetchSubsectionsEvent;
import com.example.undead.ammobilecatalog.bus.FetchSubsectionsPerformedEvent;
import com.example.undead.ammobilecatalog.bus.LoginPerformedEvent;
import com.example.undead.ammobilecatalog.model.Section;
import com.example.undead.ammobilecatalog.model.Subsection;
import com.example.undead.ammobilecatalog.repository.orm.OrmSection;
import com.example.undead.ammobilecatalog.repository.orm.OrmSubsection;
import com.example.undead.ammobilecatalog.repository.orm.OrmSubsectionItem;
import com.example.undead.ammobilecatalog.repository.orm.OrmTematicSet;
import com.example.undead.ammobilecatalog.utils.MappingUtils;
import com.example.undead.ammobilecatalog.utils.ObjectUtils;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

public class CatalogRepository implements DataStorage {
    private Bus mBus;
    private CloudDataSource mCloudDataSource;
    private CacheDataSource mCacheDataSource;
    private Handler mHandler;

    public CatalogRepository(Bus bus) {
        mBus = bus;
        mCloudDataSource = new CloudDataSource();
        mBus.register(mCloudDataSource);
        mCacheDataSource = new CacheDataSource();
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public List<OrmSection> getSections() {
        return mCacheDataSource.getSections();
    }

    @Override
    public List<OrmTematicSet> getTematicSets() {
        return mCacheDataSource.getTematicSets();
    }

    @Override
    public List<OrmSubsection> getSubsections(int sectionId) {
        return mCacheDataSource.getSubsections(sectionId);
    }

    @Override
    public List<OrmSubsectionItem> getSubsectionItems(int sectionId) {
        return mCacheDataSource.getSubsectionItems(sectionId);
    }

    @Subscribe
    public void onLoginPerformed(final LoginPerformedEvent loginPerformedEvent) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Section> sectionList = loginPerformedEvent.catalog.getSections();
                if (!ObjectUtils.isEmpty(sectionList)) {
                    mCacheDataSource.updateSections(MappingUtils.convertIntoOrmSections(loginPerformedEvent.catalog.getSections()));
                    for (Section section : sectionList) {
                        List<Subsection> subsectionList = section.getSubsections();
                        if (!ObjectUtils.isEmpty(subsectionList)) {
                            mCacheDataSource.updateSubsections(MappingUtils.convertIntoOrmSubsections(section.getSubsections(), section));
                            for (Subsection subsection : subsectionList) {
                                mCacheDataSource.updateSubsectionItems(MappingUtils.convertIntoOrmSubsectionItems(subsection.getItems(), subsection, section));
                            }
                        }
                    }
                }
                mCacheDataSource.updateTematicSets(MappingUtils.convertIntoOrmTematicSets(loginPerformedEvent.catalog.getTematicSets()));
            }
        }).start();
    }

    @Subscribe
    public void onFetchSectionsEvent(FetchSectionsEvent fetchSectionsEvent) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<OrmSection> ormSections = getSections();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        BusProvider.getInstance().post(new FetchSectionsPerformedEvent(ormSections));
                    }
                });
            }
        }).start();
    }

    @Subscribe
    public void onFetchSubsectionsEvent(final FetchSubsectionsEvent fetchSubsectionsEvent) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<OrmSubsection> ormSubsections = getSubsections(fetchSubsectionsEvent.sectionID);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        BusProvider.getInstance().post(new FetchSubsectionsPerformedEvent(ormSubsections));
                    }
                });
            }
        }).start();
    }

    @Subscribe
    public void onFetchSubsectionItemsEvent(final FetchSubsectionItemsEvent fetchSubsectionItemsEvent) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<OrmSubsectionItem> subsectionItems = getSubsectionItems(fetchSubsectionItemsEvent.sectionID);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        BusProvider.getInstance().post(new FetchSubsectionItemsPerformedEvent(subsectionItems));
                    }
                });
            }
        }).start();
    }
}
