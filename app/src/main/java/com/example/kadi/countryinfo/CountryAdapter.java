package com.example.kadi.countryinfo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Kadi on 16/04/2016.
 */
public class CountryAdapter extends ArrayAdapter<CountryData> {
    private Context mContext;

    public CountryAdapter(Context context, List<CountryData> datas) {
        super(context, 0, datas);
        this.mContext = context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_country,parent, false);
        }
        CountryViewHolder viewHolder = (CountryViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new CountryViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.country_name);
            viewHolder.text = (TextView) convertView.findViewById(R.id.text);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(viewHolder);
        }
        CountryData data = getItem(position);

        Picasso.with(mContext).load(data.getName())
                .error(R.drawable.roche)
                .placeholder(R.drawable.roche)
                .into(viewHolder.image);
        viewHolder.name.setText(data.getName());
        viewHolder.text.setText(data.getWikipediaLink());

        return convertView;
    }



    private class CountryViewHolder{
        public TextView name;
        public TextView text;
        public ImageView image;
    }
}
