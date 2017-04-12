package com.example.administrator.amp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.amp.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/4/10.
 */

public class DisclaimerAdatpter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<HashMap<String, String>> hashMapList;
    public DisclaimerAdatpter(Context context,List<HashMap<String, String>> hashMapList ){
        this.layoutInflater = LayoutInflater.from(context);
        this. hashMapList = hashMapList;


    }
    @Override
    public int getCount() {
        return this.hashMapList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DisclaimerTag disclaimerTag;
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.disclaime_item, null);
            disclaimerTag = new DisclaimerTag();
            disclaimerTag.titleText = (TextView) convertView.findViewById(R.id.titleText);
            disclaimerTag.disclaimer_text = (TextView) convertView.findViewById(R.id.disclaimer_text);
            convertView.setTag(disclaimerTag);
        }else {
            disclaimerTag = (DisclaimerTag) convertView.getTag();
        }

        disclaimerTag.titleText.setText(hashMapList.get(position).get("titleText"));
        disclaimerTag.disclaimer_text.setText(hashMapList.get(position).get("disclaimer"));
        return convertView;
    }

    class DisclaimerTag{
        TextView titleText,disclaimer_text;
    }
}
