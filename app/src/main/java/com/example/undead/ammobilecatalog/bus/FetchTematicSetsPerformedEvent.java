package com.example.undead.ammobilecatalog.bus;

import com.example.undead.ammobilecatalog.repository.orm.OrmTematicSet;

import java.util.List;

public class FetchTematicSetsPerformedEvent {
    public final List<OrmTematicSet> ormTematicSets;

    public FetchTematicSetsPerformedEvent(List<OrmTematicSet> ormTematicSets) {
        this.ormTematicSets = ormTematicSets;
    }
}