package com.example.ShoongCart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by choeyujin on 2017. 8. 25..
 */

public class PostAdapter extends BaseAdapter{

    ArrayList<PostData> datas;
    LayoutInflater inflater;

    public PostAdapter(LayoutInflater inflater, ArrayList<PostData> datas) {
        this.datas= datas;
        this.inflater= inflater;
    }

    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view == null)
            view = inflater.inflate(R.layout.list_row, null);
        TextView text_name= (TextView)view.findViewById(R.id.text_name);
        TextView text_price= (TextView)view.findViewById(R.id.text_price);
        TextView text_lati= (TextView)view.findViewById(R.id.text_latitude);
        TextView text_longi= (TextView)view.findViewById(R.id.text_longitude);

        text_name.setText( datas.get(position).getName() );
        text_price.setText(String.valueOf(datas.get(position).getPrice()));
        text_lati.setText(String.valueOf(datas.get(position).getLatitude()));
        text_longi.setText(String.valueOf(datas.get(position).getLongitude()));

        return view;
    }
}
