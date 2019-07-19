package com.example.sunrinton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Lobby_CustomAdapter extends BaseAdapter {
    List<Lobby_datas> datas = new ArrayList<>();
    @Override
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
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.chatting_lobby_items, parent, false);

        TextView tv_lobby_name = convertView.findViewById(R.id.lobby_name);
        TextView tv_lobby_preview = convertView.findViewById(R.id.lobby_preview);
        tv_lobby_name.setText(datas.get(position).getName());
        tv_lobby_preview.setText(datas.get(position).getPreview());

        return convertView;
    }
}
