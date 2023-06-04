package com.example.rec_online;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder_newOper extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView tv_item_id;

    private TextView tv_item_user;
    private TextView tv_item_text;
    private TextView tv_item_date;
    private Adapter_newOper.ItemClickListener clickListener;

    public ViewHolder_newOper(View itemView) {
        super(itemView);
        tv_item_id = itemView.findViewById(R.id.item_id);
        tv_item_user = itemView.findViewById(R.id.item_user);
        tv_item_text = itemView.findViewById(R.id.item_text);
        tv_item_date = itemView.findViewById(R.id.item_date);
        itemView.setOnClickListener(this);
    }

    public void bind(String id, String login, String text, String time) {
        tv_item_id.setText(id);
        tv_item_user.setText(login);
        tv_item_text.setText(text);
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

