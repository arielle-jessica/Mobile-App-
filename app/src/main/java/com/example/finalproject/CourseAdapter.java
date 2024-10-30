package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class CourseAdapter extends SimpleAdapter {
    private Context context;
    private List<? extends Map<String, String>> items;
    private int resource;


    public CourseAdapter(Context context, List<? extends Map<String, String>> items,
                         int resource, String[] from, int[] to) {

        super(context, items, resource, from, to);

        this.context = context;
        this.items = items;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(resource, parent, false);

        Map <String, String> map = items.get(position);

        String title = map.get("cname");
        String subtitle = map.get("cid");

        TextView textView = (TextView) rowView.findViewById(R.id.textid);
        textView.setText(title);
        TextView subTextView = (TextView) rowView.findViewById(R.id.subtextid);
        subTextView.setText(subtitle);
        return rowView;
    }
}
