package com.example.undead.ammobilecatalog.bus;

import com.example.undead.ammobilecatalog.repository.orm.OrmSubsection;

import java.util.List;

public class FetchSubsectionsPerformedEvent {
    public final List<OrmSubsection> ormSubsections;

    public FetchSubsectionsPerformedEvent(List<OrmSubsection> ormSubsections) {
        this.ormSubsections = ormSubsections;
    }
}
