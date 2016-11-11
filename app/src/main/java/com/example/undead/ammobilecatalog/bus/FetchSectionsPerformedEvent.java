package com.example.undead.ammobilecatalog.bus;

import com.example.undead.ammobilecatalog.repository.orm.OrmSection;

import java.util.List;

public class FetchSectionsPerformedEvent {
    public final List<OrmSection> ormSections;

    public FetchSectionsPerformedEvent(List<OrmSection> ormSections) {
        this.ormSections = ormSections;
    }
}
