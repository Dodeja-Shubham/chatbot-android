package com.vys.chatbot.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vys.chatbot.R;

public class ScheduleArrayAdapter extends ArrayAdapter<String> {

    String[] list;
    Context context;

    public ScheduleArrayAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull String[] objects) {
        super(context, resource, textViewResourceId, objects);
        this.list = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.spinner_list_item,parent,false);
        TextView item = view.findViewById(R.id.spinner_tv);
        item.setText(list[position]);
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.spinner_list_item,parent,false);
        TextView item = view.findViewById(R.id.spinner_tv);
        item.setText(list[position]);
        return view;
    }
}
