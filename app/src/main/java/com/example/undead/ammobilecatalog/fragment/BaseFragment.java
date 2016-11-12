package com.example.undead.ammobilecatalog.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.undead.ammobilecatalog.R;
import com.example.undead.ammobilecatalog.view_interfaces.BaseView;

import butterknife.BindView;
import butterknife.Unbinder;

public class BaseFragment extends Fragment implements BaseView {
    public BaseFragment() {}

    @BindView(R.id.emptyTextView)
    protected TextView mEmptyTextView;
    @BindView(R.id.progressBar)
    protected ProgressBar mProgressBar;
    @BindView(R.id.listView)
    protected ListView mListView;

    protected Unbinder mUnbinder;

    protected boolean mIsListRequested;

    @Override
    public void showErrorMessage(String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        mEmptyTextView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.setIndeterminate(true);
    }

    @Override
    public void hideProgress() {
        mEmptyTextView.setVisibility(View.GONE);
        mListView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mProgressBar.setIndeterminate(false);
    }

    @Override
    public void showEmptyMessage() {
        mListView.setVisibility(View.GONE);
        mEmptyTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

}
