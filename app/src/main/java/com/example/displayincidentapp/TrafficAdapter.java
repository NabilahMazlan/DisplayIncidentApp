package com.example.displayincidentapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TrafficAdapter extends ArrayAdapter<TrafficClass> {

    private ArrayList<TrafficClass> alTraffic;
    private Context context;
    private TextView tvTitle, tvMessage;

    public TrafficAdapter(@NonNull Context context, int resource, @NonNull ArrayList<TrafficClass> objects) {
        super(context, resource, objects);

        alTraffic = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.custom_list_view, parent, false);
        tvTitle = convertView.findViewById(R.id.textViewTitle);
        tvMessage = convertView.findViewById(R.id.textViewMessage);
        TrafficClass currentTrafic = alTraffic.get(position);

        tvTitle.setText(currentTrafic.getName());
        tvMessage.setText(currentTrafic.getMessage());


        return convertView;


    }
}
