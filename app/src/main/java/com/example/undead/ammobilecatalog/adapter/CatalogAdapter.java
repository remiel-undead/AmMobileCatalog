package com.example.undead.ammobilecatalog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.undead.ammobilecatalog.R;
import com.example.undead.ammobilecatalog.repository.orm.OrmSection;
import com.example.undead.ammobilecatalog.repository.orm.OrmSubsection;
import com.example.undead.ammobilecatalog.repository.orm.OrmSubsectionItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CatalogAdapter extends BaseAdapter {
    private final Context context;
    private final List<Object> catalogList;

    public CatalogAdapter(List<Object> catalogList, Context context) {
        this.catalogList = catalogList;
        this.context = context;
    }

    public void refreshCatalogList(List<Object> catalogList) {
        this.catalogList.clear();
        this.catalogList.addAll(catalogList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return catalogList.size();
    }

    @Override
    public Object getItem(int position) {
        return catalogList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_catalog, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        Object item = catalogList.get(position);
        if (item instanceof OrmSection) {
            holder.nameTextView.setText(((OrmSection) item).getName());
        } else if (item instanceof OrmSubsection) {
            holder.nameTextView.setText(((OrmSubsection) item).getName());
        } else if (item instanceof OrmSubsectionItem) {
            holder.nameTextView.setText(((OrmSubsectionItem) item).getName());
        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.nameTextView)
        TextView nameTextView;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}