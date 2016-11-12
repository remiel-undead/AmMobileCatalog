package com.example.undead.ammobilecatalog.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.undead.ammobilecatalog.R;

public class TematicSetsFragment extends Fragment {
    public TematicSetsFragment() {}

    public static TematicSetsFragment newInstance() {
        return new TematicSetsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tematic_sets, container, false);
    }

}