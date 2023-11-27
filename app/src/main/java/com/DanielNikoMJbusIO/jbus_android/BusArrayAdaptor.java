package com.DanielNikoMJbusIO.jbus_android;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.DanielNikoMJbusIO.jbus_android.model.Bus;

import java.util.ArrayList;
import java.util.List;

public class BusArrayAdaptor extends ArrayAdapter<Bus> {
    private int currentViewType;

    // invoke the suitable constructor of the ArrayAdapter class
    public BusArrayAdaptor(@NonNull Context context, List<Bus> arrayList, int viewType) {
        super(context, 0, arrayList);
        currentViewType = viewType;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            if (currentViewType == 0) {
                currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.bus_view, parent, false);
            } else {
                currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_view, parent, false);
            }
        }

        // get the position of the view from the ArrayAdapter
        Bus currentNumberPosition = getItem(position);

        // then according to the position of the view assign the desired image for the same
        ImageView numbersImage = currentItemView.findViewById(R.id.imageView);
        assert currentNumberPosition != null;
        numbersImage.setImageResource(R.drawable.baseline_directions_bus_filled_24);

        // then according to the position of the view assign the desired TextView 1 for the same
        TextView textView1 = currentItemView.findViewById(R.id.textView1);
        textView1.setText(currentNumberPosition.toString());

        // then according to the position of the view assign the desired TextView 2 for the same
        TextView textView2 = currentItemView.findViewById(R.id.textView2);
        textView2.setText("Type : " + currentNumberPosition.busType);

        // then return the recyclable view
        return currentItemView;
    }
}