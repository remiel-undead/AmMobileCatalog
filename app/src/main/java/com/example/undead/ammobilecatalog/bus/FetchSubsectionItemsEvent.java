package com.example.undead.ammobilecatalog.bus;

public class FetchSubsectionItemsEvent {
    public final int sectionID;

    public FetchSubsectionItemsEvent(int sectionID) {
        this.sectionID = sectionID;
    }
}
