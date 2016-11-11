package com.example.undead.ammobilecatalog.bus;

import com.example.undead.ammobilecatalog.repository.orm.OrmSubsectionItem;

import java.util.List;

public class FetchSubsectionItemsPerformedEvent {
    public final List<OrmSubsectionItem> ormSubsectionItems;

    public FetchSubsectionItemsPerformedEvent(List<OrmSubsectionItem> ormSubsectionItems) {
        this.ormSubsectionItems = ormSubsectionItems;
    }
}
