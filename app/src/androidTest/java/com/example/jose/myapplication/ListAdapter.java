package com.example.jose.myapplication;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jose on 28/03/2015.
 */
  public class ListAdapter extends ArrayAdapter<Item> {

    public ListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListAdapter(Context context, int resource, List<Item> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {

            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.test_itemlistrows, null);

        }

        Item p = getItem(position);

        if (p != null) {

            TextView tt = (TextView) v.findViewById(R.id.id);
            TextView tt1 = (TextView) v.findViewById(R.id.categoryId);
            TextView tt3 = (TextView) v.findViewById(R.id.description);

            if (tt != null) {
                tt.setText(p.getId());
            }
            if (tt1 != null) {

                tt1.setText(p.getCategory());
            }
            if (tt3 != null) {

                tt3.setText(p.getDescription());
            }
        }

        return v;

    }
}
