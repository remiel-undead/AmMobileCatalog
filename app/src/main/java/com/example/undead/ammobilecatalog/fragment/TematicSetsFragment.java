package com.example.undead.ammobilecatalog.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TematicSetsFragment extends Fragment implements BaseView, TematicSetView {
    public static final String FRAGMENT_TAG_SETS = "TematicSetsFragment";

    @BindView(R.id.emptyTextView)
    TextView emptyTextView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.listView)
    ListView setsListView;

    private Unbinder unbinder;

    private List<OrmTematicSet> tematicSetList;

    private TematicSetsAdapter tematicSetsAdapter;
    private TematicSetsPresenter tematicSetsPresenter;
    private boolean isSetRequested;

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
        unbinder = ButterKnife.bind(this, view);
        tematicSetsAdapter = new TematicSetsAdapter(new ArrayList(), getContext());
        setsListView.setAdapter(tematicSetsAdapter);
        return view;
    }


    @Override
    public void showErrorMessage(String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        emptyTextView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
    }

    @Override
    public void hideProgress() {
        emptyTextView.setVisibility(View.GONE);
        setsListView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        progressBar.setIndeterminate(false);
    }

    @Override
    public void showEmptyMessage() {
        setsListView.setVisibility(View.GONE);
        emptyTextView.setVisibility(View.VISIBLE);
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(tematicSetsPresenter);
        if (!isSetRequested) {
            showProgress();
            tematicSetsPresenter.fetchTematicSets();
            isSetRequested = true;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(tematicSetsPresenter);
    }

}