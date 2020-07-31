package com.example.time.budget;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.time.R;
import com.example.time.circleImageView.RoundImageView;
import com.example.time.dataBase.DataBaseItem;
import com.example.time.myGridView.GridImageAdapter;
import com.example.time.myGridView.MyGridView;

import java.util.Calendar;

public class M_RecycleViewAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private DataBaseItem dataBaseItem;
    private Context context;
    public M_RecycleViewAdapter(Context context, DataBaseItem dataBaseItem) {
        super();
        this.context = context;
        this.dataBaseItem = dataBaseItem;
    }
    private enum M_ITEM_TYPE{
        firstfloor,
        secondfloor,
        thirdfloor,
        fouthfloor,
        fifthfloor,
        sixthfloor,
        seventhfloor
    }
    @Override
    public int getItemViewType(int position) {
        return M_JudgeType(position);
    }
    private int M_JudgeType(int position) {
        if (position == 0) {
            return M_ITEM_TYPE.firstfloor.ordinal();
        } else if (position == 1) {
            return M_ITEM_TYPE.secondfloor.ordinal();
        } else if (position == 2) {
            return M_ITEM_TYPE.thirdfloor.ordinal();
        } else if (position == 3) {
            return M_ITEM_TYPE.fouthfloor.ordinal();
        } else if (position == 4) {
            return M_ITEM_TYPE.fifthfloor.ordinal();
        } else if (position == 5) {
            return M_ITEM_TYPE.sixthfloor.ordinal();
        } else{
            return M_ITEM_TYPE.seventhfloor.ordinal();
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==M_ITEM_TYPE.firstfloor.ordinal()){
            return new M_firstfloorViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.jiyibi_recycleview_item1, parent, false));
        }else if(viewType==M_ITEM_TYPE.secondfloor.ordinal()){
            return new M_secondfloorViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.jiyibi_recycleview_item2, parent, false));
        }else if(viewType==M_ITEM_TYPE.thirdfloor.ordinal()){
            return new M_thirdfloorViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.jiyibi_recycleview_item3, parent, false));
        }else if(viewType==M_ITEM_TYPE.fouthfloor.ordinal()){
            return new M_fouthfloorViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.jiyibi_recycleview_item4, parent, false));
        } else if(viewType==M_ITEM_TYPE.fifthfloor.ordinal()){
            return new M_fifthfloorViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.jiyibi_recycleview_item5, parent, false));
        }else if(viewType==M_ITEM_TYPE.sixthfloor.ordinal()){
            return new M_sixthfloorViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.jiyibi_recycleview_item6, parent, false));
        }else if(viewType==M_ITEM_TYPE.seventhfloor.ordinal()){
            return new M_sevenfloorViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.jiyibi_recycleview_item7, parent, false));
        }else{
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof M_firstfloorViewHolder) {
            ((M_firstfloorViewHolder) holder).leibie.setText("类被");
            ((M_firstfloorViewHolder) holder).image.setImageResource(R.drawable.wenhao);
            ((M_firstfloorViewHolder) holder).name.setText(dataBaseItem.getName());
            ((M_firstfloorViewHolder) holder).number.setText(String.valueOf(dataBaseItem.getCost()));
            ((M_firstfloorViewHolder) holder).number.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void afterTextChanged(Editable editable) {
                    dataBaseItem.setCost(Double.valueOf(((M_firstfloorViewHolder) holder).number.getText().toString()));
                }
            });
            ((M_firstfloorViewHolder) holder).name.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void afterTextChanged(Editable editable) {
                    dataBaseItem.setName(((M_firstfloorViewHolder) holder).name.getText().toString());
                }
            });
        }else if(holder instanceof M_secondfloorViewHolder) {
            ((M_secondfloorViewHolder) holder).b1.setText("支出");
            ((M_secondfloorViewHolder) holder).b2.setText("收入");
        }else if(holder instanceof M_thirdfloorViewHolder) {
            final int[] listicon = {dataBaseItem.getCosticon()};
            final String[] listname = {dataBaseItem.getType()};
            ((M_thirdfloorViewHolder) holder).icon_group.setNumColumns(1);
            ((M_thirdfloorViewHolder) holder).icon_group.setAdapter(new GridImageAdapter(context,listicon,listname));
            ((M_thirdfloorViewHolder) holder).icon_group.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
            ((M_thirdfloorViewHolder) holder).icon_group.setItemChecked(0,true);
            ((M_thirdfloorViewHolder) holder).icon_group.setSelection(0);
        }else if(holder instanceof M_fouthfloorViewHolder) {
            ((M_fouthfloorViewHolder) holder).datavalue.setText(dataBaseItem.getCostdate());
            ((M_fouthfloorViewHolder) holder).timevalue.setText(dataBaseItem.getTime());
            ((M_fouthfloorViewHolder) holder).dataicon.setImageResource(R.drawable.timeclock);

            ((M_fouthfloorViewHolder) holder).datavalue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar c = Calendar.getInstance();
                    new DatePickerDialog(context,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    ((M_fouthfloorViewHolder) holder).datavalue.setText(year + "." + dataformat(monthOfYear) + "." + dataformat(dayOfMonth) + ".");
                                    dataBaseItem.setCostdate(year + "." + dataformat(monthOfYear) + "." + dataformat(dayOfMonth));
                                }
                            },c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
            ((M_fouthfloorViewHolder) holder).timevalue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar c = Calendar.getInstance();
                    new TimePickerDialog(context,
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    ((M_fouthfloorViewHolder) holder).timevalue.setText(hourOfDay + ":" + dataformat(minute));
                                    dataBaseItem.setTime(hourOfDay + ":" + minute);
                                }
                            },c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
                }
            });

        }else if(holder instanceof M_fifthfloorViewHolder) {
            ((M_fifthfloorViewHolder) holder).dingwei.setImageResource(R.drawable.dingwei);
            ((M_fifthfloorViewHolder) holder).place.setText(dataBaseItem.getPlace());
            ((M_fifthfloorViewHolder) holder).place.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void afterTextChanged(Editable editable) {
                    dataBaseItem.setPlace(((M_fifthfloorViewHolder) holder).place.getText().toString());
                }
            });
        }else if(holder instanceof M_sixthfloorViewHolder) {
            ((M_sixthfloorViewHolder) holder).sisflooricon.setImageResource(R.drawable.beizhu);
            ((M_sixthfloorViewHolder) holder).beizhu.setText(dataBaseItem.getNote());
            ((M_sixthfloorViewHolder) holder).beizhu.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void afterTextChanged(Editable editable) {
                    dataBaseItem.setNote(((M_sixthfloorViewHolder) holder).beizhu.getText().toString());
                }
            });
        }else{
            ((M_sevenfloorViewHolder) holder).sevenflooricon.setImageResource(R.drawable.camera);
            ((M_sevenfloorViewHolder) holder).shangchuntupian.setText(R.string.shangchuantupianhuopaishezhaopian);
            dataBaseItem.setImage(dataBaseItem.getImage());
        }
    }

    @Override
    public int getItemCount() {
        return 7;
    }
    public static class M_firstfloorViewHolder extends RecyclerView.ViewHolder {
        TextView leibie;
        RoundImageView image;
        EditText number;
        EditText name;
        M_firstfloorViewHolder(@NonNull View view) {
            super(view);
            name = view.findViewById(R.id.data_name);
            leibie = view.findViewById(R.id.leibie);
            image = view.findViewById(R.id.firstfloorlogo);
            number = view.findViewById(R.id.number);
        }
    }
    public static class M_secondfloorViewHolder extends RecyclerView.ViewHolder {
        Button b1;
        Button b2;
        M_secondfloorViewHolder(@NonNull View view) {
            super(view);
            b1 = view.findViewById(R.id.jiyibi_zhichu);
            b2 = view.findViewById(R.id.jiyibi_shouru);
        }
    }
    public static class M_thirdfloorViewHolder extends RecyclerView.ViewHolder {
        MyGridView icon_group;
        M_thirdfloorViewHolder(@NonNull View view) {
            super(view);
            icon_group = view.findViewById(R.id.icon_group);
        }
    }
    public static class M_fouthfloorViewHolder extends RecyclerView.ViewHolder {
        ImageView dataicon;
        TextView datavalue;
        TextView timevalue;
        M_fouthfloorViewHolder(@NonNull View view) {
            super(view);
            dataicon = view.findViewById(R.id.dataicon);
            datavalue = view.findViewById(R.id.datavalue);
            timevalue = view.findViewById(R.id.timevalue);
        }
    }

    public static class M_fifthfloorViewHolder extends RecyclerView.ViewHolder {
        ImageView dingwei;
        EditText place;
        M_fifthfloorViewHolder(@NonNull View view) {
            super(view);
            dingwei = view.findViewById(R.id.dingwei);
            place = view.findViewById(R.id.item_place);
        }
    }
    public static class M_sixthfloorViewHolder extends RecyclerView.ViewHolder {
        ImageView sisflooricon;
        EditText beizhu;
        M_sixthfloorViewHolder(@NonNull View view) {
            super(view);
            sisflooricon = view.findViewById(R.id.sisflooricon);
            beizhu = view.findViewById(R.id.beizhu);
        }
    }
    public static class M_sevenfloorViewHolder extends RecyclerView.ViewHolder {
        ImageView sevenflooricon;
        TextView shangchuntupian;
        M_sevenfloorViewHolder(@NonNull View view) {
            super(view);
            sevenflooricon =  view.findViewById(R.id.sevenflooricon);
            shangchuntupian = view.findViewById(R.id.shangchuntupian);
        }
    }
    public static String dataformat(int num){
        if(num<10){
            return "0"+num;
        }
        return num+"";
    }
}