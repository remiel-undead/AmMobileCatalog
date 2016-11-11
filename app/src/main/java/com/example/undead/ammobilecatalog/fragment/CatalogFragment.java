package com.example.undead.ammobilecatalog.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.undead.ammobilecatalog.R;
import com.example.undead.ammobilecatalog.adapter.CatalogAdapter;
import com.example.undead.ammobilecatalog.bus.BusProvider;
import com.example.undead.ammobilecatalog.presenter.CatalogPresenter;
import com.example.undead.ammobilecatalog.presenter.CatalogPresenterImpl;
import com.example.undead.ammobilecatalog.repository.orm.OrmSection;
import com.example.undead.ammobilecatalog.repository.orm.OrmSubsection;
import com.example.undead.ammobilecatalog.repository.orm.OrmSubsectionItem;
import com.example.undead.ammobilecatalog.view_interfaces.CatalogView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CatalogFragment extends Fragment implements CatalogView {
    @BindView(R.id.emptyTextView)
    TextView emptyTextView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.listView)
    ListView catalogListView;

    private Unbinder unbinder;

    private List<OrmSection> sectionList;
    private List<OrmSubsection> subsectionList;
    private List<OrmSubsectionItem> subsectionItemList;

    private CatalogAdapter catalogAdapter;
    private CatalogPresenter catalogPresenter;
    private boolean isCatalogRequested;

    public CatalogFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        catalogPresenter = new CatalogPresenterImpl(this);
        isCatalogRequested = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catalog, container, false);
        unbinder = ButterKnife.bind(this, view);
        catalogAdapter = new CatalogAdapter(new ArrayList<>(), getContext());
        catalogListView.setAdapter(catalogAdapter);
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
        catalogListView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        progressBar.setIndeterminate(false);
    }

    @Override
    public void showEmptyMessage() {
        catalogListView.setVisibility(View.GONE);
        emptyTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setSectionList(List<OrmSection> sections) {
        sectionList = sections;
        hideProgress();
        catalogAdapter.refreshCatalogList(new ArrayList<Object>(sectionList));
        catalogListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showProgress();
                catalogPresenter.fetchSubsections(sectionList.get(position).getSectionID());
            }
        });
    }

    @Override
    public void setSubsectionList(List<OrmSubsection> subsections) {
        subsectionList = subsections;
        hideProgress();
        catalogAdapter.refreshCatalogList(new ArrayList<Object>(subsectionList));
        catalogListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showProgress();
                catalogPresenter.fetchSubsectionItems(subsectionList.get(position).getSectionID());
            }
        });
    }

    @Override
    public void setSubsectionItemsList(List<OrmSubsectionItem> subsectionItems) {
        subsectionItemList = subsectionItems;
        hideProgress();
        catalogAdapter.refreshCatalogList(new ArrayList<Object>(subsectionItemList));
        catalogListView.setOnItemClickListener(null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(catalogPresenter);
        if (!isCatalogRequested) {
            showProgress();
            catalogPresenter.fetchSections();
            isCatalogRequested = true;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(catalogPresenter);
    }
}