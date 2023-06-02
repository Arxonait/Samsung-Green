package com.example.rec_online;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder_newMess extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView tv_id;

    private TextView tv_user;
    private TextView tv_title;
    private TextView tv_date;
    private Adapter_newMess.ItemClickListener clickListener;

    public ViewHolder_newMess(View itemView) {
        super(itemView);
        tv_id = itemView.findViewById(R.id.item_id);
        tv_user = itemView.findViewById(R.id.item_user);
        tv_title= itemView.findViewById(R.id.item_fact);
        tv_date = itemView.findViewById(R.id.item_date);
        itemView.setOnClickListener(this);
    }

    public void bind(String id, String name_user, String title, String time) {
        tv_id.setText(id);
        tv_user.setText(name_user);
        tv_title.setText(title);
        tv_date.setText(time);
    }


    public void setClickListener(Adapter_newMess.ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public void onClick(View v) {
        if (clickListener != null) {
            clickListener.onItemClick_mess(v, getAdapterPosition());
        }
    }
}

