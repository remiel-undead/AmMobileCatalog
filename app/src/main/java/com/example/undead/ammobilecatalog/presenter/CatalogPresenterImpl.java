package com.example.undead.ammobilecatalog.presenter;

import com.example.undead.ammobilecatalog.bus.BusProvider;
import com.example.undead.ammobilecatalog.bus.FetchSectionsEvent;
import com.example.undead.ammobilecatalog.bus.FetchSectionsPerformedEvent;
import com.example.undead.ammobilecatalog.bus.FetchSubsectionItemsEvent;
import com.example.undead.ammobilecatalog.bus.FetchSubsectionItemsPerformedEvent;
import com.example.undead.ammobilecatalog.bus.FetchSubsectionsEvent;
import com.example.undead.ammobilecatalog.bus.FetchSubsectionsPerformedEvent;
import com.example.undead.ammobilecatalog.view_interfaces.CatalogView;
import com.squareup.otto.Subscribe;

public class CatalogPresenterImpl implements CatalogPresenter {
    private CatalogView catalogView;

    public CatalogPresenterImpl(CatalogView catalogView) {
        this.catalogView = catalogView;
    }

    @Override
    public void fetchSections() {
        BusProvider.getInstance().post(new FetchSectionsEvent());
    }

    @Override
    public void fetchSubsections(int sectionId) {
        BusProvider.getInstance().post(new FetchSubsectionsEvent(sectionId));
    }

    @Override
    public void fetchSubsectionItems(int sectionId) {
        BusProvider.getInstance().post(new FetchSubsectionItemsEvent(sectionId));
    }

    @Subscribe
    public void onFetchSectionsPerformed(FetchSectionsPerformedEvent event) {
        catalogView.setSectionList(event.ormSections);
    }

    @Subscribe
    public void onFetchSubsectionsPerformed(FetchSubsectionsPerformedEvent event) {
        catalogView.setSubsectionList(event.ormSubsections);
    }

    @Subscribe
    public void onFetchSubsectionItemsPerformed(FetchSubsectionItemsPerformedEvent event) {
        catalogView.setSubsectionItemsList(event.ormSubsectionItems);
    }
}
