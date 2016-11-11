package com.example.undead.ammobilecatalog.repository;

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

    public CatalogRepository(Bus bus) {
        mBus = bus;
        mCloudDataSource = new CloudDataSource();
        mBus.register(mCloudDataSource);
        mCacheDataSource = new CacheDataSource();
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
        // TODO get subsection for section from cache
        return null;
    }

    @Override
    public List<OrmSubsectionItem> getSubsectionItems(int sectionId) {
        // TODO get items for subsection from cache
        return null;
    }

    @Subscribe
    public void onLoginPerformed(LoginPerformedEvent loginPerformedEvent) {
        List<Section> sectionList = loginPerformedEvent.catalog.getSections();
        if (!ObjectUtils.isEmpty(sectionList)) {
            mCacheDataSource.updateSections(MappingUtils.convertIntoOrmSections(loginPerformedEvent.catalog.getSections()));
            for (Section section : sectionList){
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

}
