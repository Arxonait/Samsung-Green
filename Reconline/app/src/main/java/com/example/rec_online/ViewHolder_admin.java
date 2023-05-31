package com.example.rec_online;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewHolder_admin extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView tv_item_id;

    private TextView tv_item_type;
    private TextView tv_item_date;
    private Adapter_admin.ItemClickListener clickListener;

    public ViewHolder_admin(View itemView) {
        super(itemView);
        tv_item_id = itemView.findViewById(R.id.item_id);
        tv_item_type = itemView.findViewById(R.id.item_type);
        tv_item_date = itemView.findViewById(R.id.item_date);
        itemView.setOnClickListener(this);
    }

    public void bind(String id, String type, Date time) {
        tv_item_id.setText(id);
        tv_item_type.setText(type);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateString = dateFormat.format(time);
        tv_item_date.setText(dateString);
    }


    public void setClickListener(Adapter_admin.ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public void onClick(View v) {
        if (clickListener != null) {
            clickListener.onItemClick(v, getAdapterPosition());
        }
    }
}

