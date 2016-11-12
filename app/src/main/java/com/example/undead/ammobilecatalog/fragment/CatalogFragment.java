package com.example.undead.ammobilecatalog.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.undead.ammobilecatalog.R;
import com.example.undead.ammobilecatalog.adapter.CatalogAdapter;
import com.example.undead.ammobilecatalog.bus.BusProvider;
import com.example.undead.ammobilecatalog.presenter.CatalogPresenter;
import com.example.undead.ammobilecatalog.presenter.CatalogPresenterImpl;
import com.example.undead.ammobilecatalog.repository.orm.OrmSection;
import com.example.undead.ammobilecatalog.repository.orm.OrmSubsection;
import com.example.undead.ammobilecatalog.repository.orm.OrmSubsectionItem;
import com.example.undead.ammobilecatalog.utils.ObjectUtils;
import com.example.undead.ammobilecatalog.view_interfaces.CatalogView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class CatalogFragment extends BaseFragment implements CatalogView {
    public static final String FRAGMENT_TAG_CATALOG = "CatalogFragment";
    public static final int LEVEL_SECTION = 0;
    public static final int LEVEL_SUBSECTION = 1;
    public static final int LEVEL_SUBSECTION_ITEM = 2;

    private static final String TAG_LEVEL = "level";
    private static final String TAG_CURRENT_SEC_ID = "current_sec_id";
    private static final String TAG_CURRENT_SUBSEC_ID = "current_subsec_id";

    private List<OrmSection> sectionList;
    private List<OrmSubsection> subsectionList;
    private List<OrmSubsectionItem> subsectionItemList;

    private CatalogAdapter catalogAdapter;
    private CatalogPresenter catalogPresenter;
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
        mIsListRequested = false;
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
        mUnbinder = ButterKnife.bind(this, view);
        catalogAdapter = new CatalogAdapter(new ArrayList<>(), getContext());
        mListView.setAdapter(catalogAdapter);
        return view;
    }

    @Override
    public void showEmptyMessage() {
        if (currentLevel > 0) {
            mEmptyTextView.setText(getString(R.string.sorry_empty));
        } else {
            mEmptyTextView.setText(getString(R.string.sorry_empty_catalog));
        }
        super.showEmptyMessage();
    }

    @Override
    public void setSectionList(List<OrmSection> sections) {
        sectionList = sections;
        hideProgress();
        catalogAdapter.refreshCatalogList(new ArrayList<Object>(sectionList));
        if (ObjectUtils.isEmpty(sectionList)) {
            showEmptyMessage();
        }
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        mListView.setOnItemClickListener(null);
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(catalogPresenter);
        if (!mIsListRequested) {
            showProgress();
            catalogPresenter.fetch(currentLevel, currentSectionId, currentSubsectionId);
            mIsListRequested = true;
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