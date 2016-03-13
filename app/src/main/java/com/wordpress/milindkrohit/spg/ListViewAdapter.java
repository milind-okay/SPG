package com.wordpress.milindkrohit.spg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by milind on 18/1/16.
 */
public class ListViewAdapter extends ArrayAdapter<ListViewItem> {

    public ListViewAdapter(Context context, List<ListViewItem> items) {
        super(context, R.layout.fragment_score, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            // inflate the GridView item layout
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.fragment_score, parent, false);

            // initialize the view holder
            viewHolder = new ViewHolder();

            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
            convertView.setTag(viewHolder);
        } else {
            // recycle the already inflated view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // update the item view
        ListViewItem item = getItem(position);

        viewHolder.tvTitle.setText(item.title);
        viewHolder.tvDescription.setText(item.description);

        return convertView;
    }

    /**
     * The view holder design pattern prevents using findViewById()
     * repeatedly in the getView() method of the adapter.
     *
     * @see
     */
    private static class ViewHolder {
        ImageView ivIcon;
        TextView tvTitle;
        TextView tvDescription;
    }
}