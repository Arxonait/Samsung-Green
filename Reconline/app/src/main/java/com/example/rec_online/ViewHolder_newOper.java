package com.example.rec_online;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder_newOper extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView tv_item_id;

    private TextView tv_item_user;
    private TextView tv_item_fact;
    private TextView tv_item_date;
    private Adapter_newOper.ItemClickListener clickListener;

    public ViewHolder_newOper(View itemView) {
        super(itemView);
        tv_item_id = itemView.findViewById(R.id.item_id);
        tv_item_user = itemView.findViewById(R.id.item_user);
        tv_item_fact= itemView.findViewById(R.id.item_fact);
        tv_item_date = itemView.findViewById(R.id.item_date);
        itemView.setOnClickListener(this);
    }

    public void bind(String id, String name_user, String name_fact, String time) {
        tv_item_id.setText(id);
        tv_item_user.setText(name_user);
        tv_item_fact.setText(name_fact);
        tv_item_date.setText(time);
    }


    public void setClickListener(Adapter_newOper.ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public void onClick(View v) {
        if (clickListener != null) {
            clickListener.onItemClick(v, getAdapterPosition());
        }
    }
}

