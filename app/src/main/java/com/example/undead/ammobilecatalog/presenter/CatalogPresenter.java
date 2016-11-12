package com.example.undead.ammobilecatalog.presenter;

public interface CatalogPresenter {
    void fetchSections();
    void fetchSubsections(int sectionId);
    void fetchSubsectionItems(int sectionId);

    void fetch(int level, int currentSectionId, int currentSubsectionId);
}
