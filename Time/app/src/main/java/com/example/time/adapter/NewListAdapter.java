package com.example.time.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.time.R;
import com.example.time.been.ScheduleRecord;
import com.example.time.modifySchedule.ModifySchedule;

import java.util.List;

public class NewListAdapter extends ArrayAdapter<ScheduleRecord> {
    private int resourceId;
    private Context context;
    private SQLiteDatabase db;
    public NewListAdapter(Context context, int ViewResourceId, List<ScheduleRecord> objects, SQLiteDatabase db) {
        super(context,ViewResourceId,objects);
        resourceId = ViewResourceId;
        this.context = context;
        this.db = db;
    }

    @Nullable
    @Override
    public ScheduleRecord getItem(int position) {
        return super.getItem(position);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        @SuppressLint("ViewHolder") final ScheduleRecord sr = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        final TextView taskTime = view.findViewById(R.id.time);
        final TextView name = view.findViewById(R.id.text_data);
        TextView level = view.findViewById(R.id.level);
        TextView type = view.findViewById(R.id.type);
        TextView theMessage = view.findViewById(R.id.theMessage);
//        TextView naoZhongSwitch = view.findViewById(R.id.naoZhongSwitch);
        CheckBox check = view.findViewById(R.id.check);
        ImageView xiuGai =view.findViewById(R.id.xiuGai);

        taskTime.setText(sr.getDatetime().substring(11));
        name.setText(sr.getName());
        level.setText(sr.getLevel());
        theMessage.setText(sr.getMessage());
        type.setText(sr.getType());
        check.setChecked(false);
        xiuGai.setOnClickListener(view1 -> {
            Intent intent = new Intent(context, ModifySchedule.class);
            intent.putExtra("name",name.getText().toString());
            intent.putExtra("datetime",sr.getDatetime());
            context.startActivity(intent);
        });
        return view;
    }
}
