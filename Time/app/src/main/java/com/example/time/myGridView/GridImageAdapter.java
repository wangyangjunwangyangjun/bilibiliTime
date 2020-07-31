package com.example.time.myGridView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.time.R;

public class GridImageAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    String[] name;
    int[] iconarray;
    public GridImageAdapter(Context context, int[] iconarray, String[] name ) {
        this.inflater = LayoutInflater.from(context);
        this.name = name;
        this.iconarray = iconarray;
    }
    @Override
    public int getCount() {
        return name.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

//    @Override
//    public View getDropDownView(int position, View convertView, ViewGroup parent) {
//        return super.getDropDownView(position, convertView, parent);
//    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;
        if (convertView==null) {
            holder=new ViewHolder();
            convertView=this.inflater.inflate(R.layout.type_gridview_item, null);
            holder.iv=(ImageView) convertView.findViewById(R.id.main_grid_item_iv);
            holder.tv=(TextView) convertView.findViewById(R.id.main_grid_item_tv);
            convertView.setTag(holder);
        }
        else {
            holder=(ViewHolder) convertView.getTag();
        }
        holder.iv.setImageResource(iconarray[position]);
        holder.tv.setText(name[position]);
        return convertView;
    }
    private class ViewHolder{
        ImageView iv;
        TextView tv;
    }

}
