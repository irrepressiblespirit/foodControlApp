package com.skibnev.foodcontrolapp.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ProductsSuggestAdapter extends ArrayAdapter<String> implements Filterable {

    private List<String> mListData;

    public ProductsSuggestAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        mListData = new ArrayList<>();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    public void setData(List<String> list) {
        mListData.clear();
        mListData.addAll(list);
    }

    public String getObject(int position) {
        return mListData.get(position);
    }

    @NonNull
    @Override
    public Filter getFilter() {

        Filter dataFilter = new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    filterResults.values = mListData;
                    filterResults.count = mListData.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && (results.count > 0)) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return dataFilter;
    }
}
