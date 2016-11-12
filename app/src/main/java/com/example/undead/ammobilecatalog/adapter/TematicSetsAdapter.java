package com.example.undead.ammobilecatalog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.undead.ammobilecatalog.R;
import com.example.undead.ammobilecatalog.repository.orm.OrmTematicSet;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TematicSetsAdapter extends BaseAdapter {
    private final Context context;
    private final List<OrmTematicSet> tematicSetList;

    public TematicSetsAdapter(List<OrmTematicSet> tematicSetList, Context context) {
        this.context = context;
        this.tematicSetList = tematicSetList;
    }

    public void refreshTematicSetList(List<OrmTematicSet> tematicSetList) {
        this.tematicSetList.clear();
        this.tematicSetList.addAll(tematicSetList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return tematicSetList.size();
    }

    @Override
    public Object getItem(int position) {
        return tematicSetList.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_tematic_set, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        OrmTematicSet item = tematicSetList.get(position);
        holder.descriptionTextView.setText(item.getDescription());
        Picasso.with(context).load(item.getImg()).placeholder(R.drawable.side_nav_bar).into(holder.imageView);

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.descriptionTextView)
        TextView descriptionTextView;
        @BindView(R.id.imageView)
        ImageView imageView;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}