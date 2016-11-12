package com.example.undead.ammobilecatalog.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.undead.ammobilecatalog.R;
import com.example.undead.ammobilecatalog.adapter.TematicSetsAdapter;
import com.example.undead.ammobilecatalog.bus.BusProvider;
import com.example.undead.ammobilecatalog.presenter.TematicSetsPresenter;
import com.example.undead.ammobilecatalog.presenter.TematicSetsPresenterImpl;
import com.example.undead.ammobilecatalog.repository.orm.OrmTematicSet;
import com.example.undead.ammobilecatalog.utils.ObjectUtils;
import com.example.undead.ammobilecatalog.view_interfaces.BaseView;
import com.example.undead.ammobilecatalog.view_interfaces.TematicSetView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class TematicSetsFragment extends BaseFragment implements BaseView, TematicSetView {
    public static final String FRAGMENT_TAG_SETS = "TematicSetsFragment";

    private List<OrmTematicSet> tematicSetList;

    private TematicSetsAdapter tematicSetsAdapter;
    private TematicSetsPresenter tematicSetsPresenter;

    public TematicSetsFragment() {}

    public static TematicSetsFragment newInstance() {
        return new TematicSetsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        tematicSetsPresenter = new TematicSetsPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sets, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        tematicSetsAdapter = new TematicSetsAdapter(new ArrayList(), getContext());
        mListView.setAdapter(tematicSetsAdapter);
        return view;
    }

    @Override
    public void setTematicSetsList(List<OrmTematicSet> tematicSets) {
        tematicSetList = tematicSets;
        hideProgress();
        tematicSetsAdapter.refreshTematicSetList(tematicSetList);
        if (ObjectUtils.isEmpty(tematicSetList)) {
            showEmptyMessage();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(tematicSetsPresenter);
        if (!mIsListRequested) {
            showProgress();
            tematicSetsPresenter.fetchTematicSets();
            mIsListRequested = true;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(tematicSetsPresenter);
    }
}