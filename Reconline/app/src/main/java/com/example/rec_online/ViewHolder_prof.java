package com.example.rec_online;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;

public class ViewHolder_prof extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView tv_mes_id;
    private TextView tv_mes_status;
    private TextView tv_mes_title;
    private TextView tv_mes_date;
    private Adapter_prof.ItemClickListener clickListener;

    public ViewHolder_prof(View itemView) {
        super(itemView);
        tv_mes_id = itemView.findViewById(R.id.mes_id);
        tv_mes_title = itemView.findViewById(R.id.mes_title);
        tv_mes_status = itemView.findViewById(R.id.mes_status);
        tv_mes_date = itemView.findViewById(R.id.mes_date);
        itemView.setOnClickListener(this);
    }

    public void bind(String id, String ball, String text, String time) {
        tv_mes_id.setText(id);
        tv_mes_title.setText(ball);
        tv_mes_status.setText(text);
        tv_mes_date.setText(time);
    }


    public void setClickListener(Adapter_prof.ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public void onClick(View v) {
        if (clickListener != null) {
            clickListener.onItemClick(v, getAdapterPosition());
        }
    }
}

