package com.example.undead.ammobilecatalog.presenter;

import com.example.undead.ammobilecatalog.bus.BusProvider;
import com.example.undead.ammobilecatalog.bus.FetchTematicSetsEvent;
import com.example.undead.ammobilecatalog.bus.FetchTematicSetsPerformedEvent;
import com.example.undead.ammobilecatalog.view_interfaces.TematicSetView;
import com.squareup.otto.Subscribe;

public class TematicSetsPresenterImpl implements TematicSetsPresenter{
private TematicSetView tematicSetView;

    public TematicSetsPresenterImpl(TematicSetView tematicSetView) {
        this.tematicSetView = tematicSetView;
    }

    @Override
    public void fetchTematicSets() {
        BusProvider.getInstance().post(new FetchTematicSetsEvent());
    }

    @Subscribe
    public void onFetchTematicSetsPerformedEvent(FetchTematicSetsPerformedEvent fetchTematicSetsPerformedEvent) {
        tematicSetView.setTematicSetsList(fetchTematicSetsPerformedEvent.ormTematicSets);
    }
}