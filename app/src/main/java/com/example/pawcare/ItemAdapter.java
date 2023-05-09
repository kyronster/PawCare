package com.example.pawcare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<Item> {
    private Context context;
    private ArrayList<Item> itemList;

    public ItemAdapter(Context context, ArrayList<Item> itemList) {
        super(context, R.layout.item_product, itemList);
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_product, parent, false);

        Item item = itemList.get(position);

        ImageView imageView = view.findViewById(R.id.imageView3);
        imageView.setImageResource(item.getImageResId());

        TextView nameTextView = view.findViewById(R.id.textView3);
        nameTextView.setText(item.getName());

        TextView locationTextView = view.findViewById(R.id.textView4);
        locationTextView.setText(item.getLocation());

        return view;
    }
}