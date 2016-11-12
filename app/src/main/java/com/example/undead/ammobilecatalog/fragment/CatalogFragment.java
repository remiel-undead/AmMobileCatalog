package com.example.undead.ammobilecatalog.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.example.undead.ammobilecatalog.utils.ObjectUtils;
import com.example.undead.ammobilecatalog.view_interfaces.BaseView;
import com.example.undead.ammobilecatalog.view_interfaces.CatalogView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CatalogFragment extends Fragment implements BaseView, CatalogView {
    public static final String FRAGMENT_TAG_CATALOG = "CatalogFragment";
    public static final int LEVEL_SECTION = 0;
    public static final int LEVEL_SUBSECTION = 1;
    public static final int LEVEL_SUBSECTION_ITEM = 2;

    private static final String TAG_LEVEL = "level";
    private static final String TAG_CURRENT_SEC_ID = "current_sec_id";
    private static final String TAG_CURRENT_SUBSEC_ID = "current_subsec_id";

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
    private int currentLevel;
    private int currentSectionId;
    private int currentSubsectionId;

    public CatalogFragment() {
    }

    public static CatalogFragment newInstance() {
        return new CatalogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        catalogPresenter = new CatalogPresenterImpl(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        currentLevel = 0;
        currentSectionId = -1;
        currentSubsectionId = -1;
        isCatalogRequested = false;
        if (savedInstanceState != null) {
            currentLevel = savedInstanceState.getInt(TAG_LEVEL, 0);
            currentSectionId = savedInstanceState.getInt(TAG_CURRENT_SEC_ID, -1);
            currentSubsectionId = savedInstanceState.getInt(TAG_CURRENT_SUBSEC_ID, -1);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(TAG_LEVEL, currentLevel);
        outState.putInt(TAG_CURRENT_SEC_ID, currentSectionId);
        outState.putInt(TAG_CURRENT_SUBSEC_ID, currentSubsectionId);
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
        if (currentLevel > 0) {
            emptyTextView.setText(getString(R.string.sorry_empty));
        } else {
            emptyTextView.setText(getString(R.string.sorry_empty_catalog));
        }
        emptyTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setSectionList(List<OrmSection> sections) {
        sectionList = sections;
        hideProgress();
        catalogAdapter.refreshCatalogList(new ArrayList<Object>(sectionList));
        if (ObjectUtils.isEmpty(sectionList)) {
            showEmptyMessage();
        }
        catalogListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showProgress();
                currentSectionId = sectionList.get(position).getSectionID();
                currentLevel = 1;
                catalogPresenter.fetchSubsections(sectionList.get(position).getSectionID());
            }
        });
    }

    @Override
    public void setSubsectionList(List<OrmSubsection> subsections) {
        subsectionList = subsections;
        hideProgress();
        catalogAdapter.refreshCatalogList(new ArrayList<Object>(subsectionList));
        if (ObjectUtils.isEmpty(subsectionList)) {
            showEmptyMessage();
        }
        catalogListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showProgress();
                currentSubsectionId = sectionList.get(position).getSectionID();
                currentLevel = 2;
                catalogPresenter.fetchSubsectionItems(subsectionList.get(position).getSectionID());
            }
        });
    }

    @Override
    public void setSubsectionItemsList(List<OrmSubsectionItem> subsectionItems) {
        subsectionItemList = subsectionItems;
        hideProgress();
        catalogAdapter.refreshCatalogList(new ArrayList<Object>(subsectionItemList));
        if (ObjectUtils.isEmpty(subsectionItemList)) {
            showEmptyMessage();
        }
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
            catalogPresenter.fetch(currentLevel, currentSectionId, currentSubsectionId);
            isCatalogRequested = true;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(catalogPresenter);
    }

    public void onBackPressed() {
        showProgress();
        if (currentLevel > 0) {
            --currentLevel;
            catalogPresenter.fetch(currentLevel, currentSectionId, currentSubsectionId);
        } else {
            hideProgress();
            getActivity().finish();
        }
    }
}