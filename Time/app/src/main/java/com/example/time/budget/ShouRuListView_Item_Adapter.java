package com.example.time.budget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import com.example.time.R;
import com.example.time.dataBase.DataBaseItem;
import java.util.List;

public class ShouRuListView_Item_Adapter extends ArrayAdapter<DataBaseItem> {
    private int resourceId;
    private Context context;
    private SQLiteDatabase db;

    @Nullable
    @Override
    public DataBaseItem getItem(int position) {
        return super.getItem(position);
    }

    public ShouRuListView_Item_Adapter(Context context, int ViewResourceId, List<DataBaseItem> objects, SQLiteDatabase db){
        super(context,ViewResourceId,objects);
        resourceId = ViewResourceId;
        this.context = context;
        this.db = db;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent){
        final DataBaseItem lm = getItem(position);
        @SuppressLint("ViewHolder") View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);

        ImageView image = view.findViewById(R.id.item_image);
        ImageView costicon =  view.findViewById(R.id.item_icon);
        TextView name =  view.findViewById(R.id.item_name);
        TextView time =  view.findViewById(R.id.item_time);
        TextView cost = view.findViewById(R.id.item_cost);
        TextView costdate = view.findViewById(R.id.item_costdate);
        TextView place = view.findViewById(R.id.item_place);
        TextView note = view.findViewById(R.id.item_note);
        Button button_delete = view.findViewById(R.id.button_DeleteDataBaseItem);

        button_delete.setOnClickListener(view1 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("您确定要移除这条记录吗？");
            builder.setNegativeButton("是的", (dialogInterface, i) -> db.execSQL("delete from yiji where name = ?",new String[]{lm.getName()}));
            builder.setPositiveButton("打扰了",null);
            builder.create().show();
            ShouRuListView_Item_Adapter.this.notifyDataSetChanged();
        });
        Button button_modif = view.findViewById(R.id.button_ModifDataBaseItem);
        button_modif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("WYJ","here");
                Intent intent = new Intent(context, ModifDataBaseItemActivity.class);
                intent.putExtra("name",lm.getName());
                intent.putExtra("type",lm.getType());
                intent.putExtra("note",lm.getNote());
                intent.putExtra("date",lm.getCostdate());
                intent.putExtra("time",lm.getTime());
                intent.putExtra("cost",lm.getCost());
                intent.putExtra("costdate",lm.getCostdate());
                intent.putExtra("place",lm.getPlace());
                intent.putExtra("note",lm.getNote());
                intent.putExtra("image",lm.getImage());
                intent.putExtra("costicon",lm.getCosticon());
                context.startActivity(intent);
            }
        });
        image.setImageResource(lm.getImage());
        costicon.setImageResource(lm.getCosticon());
        name.setText(lm.getName());
        time.setText(lm.getTime());
        cost.setText(String.valueOf( lm.getCost()));
        costdate.setText(lm.getCostdate());
        place.setText(lm.getPlace());
        note.setText(lm.getNote());
        return view;
    }
}
