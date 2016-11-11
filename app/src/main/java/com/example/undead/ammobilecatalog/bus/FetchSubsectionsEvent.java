package com.example.undead.ammobilecatalog.bus;

public class FetchSubsectionsEvent {
    public final int sectionID;

    public FetchSubsectionsEvent(int sectionID) {
        this.sectionID = sectionID;
    }
}
