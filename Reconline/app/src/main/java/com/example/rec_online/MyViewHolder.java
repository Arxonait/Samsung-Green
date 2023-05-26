package com.example.rec_online;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView id_gift;
    private TextView gift_status;
    private TextView gift_ball;
    private MyAdapter.ItemClickListener clickListener;

    public MyViewHolder(View itemView) {
        super(itemView);
        id_gift = itemView.findViewById(R.id.id_gift);
        gift_ball = itemView.findViewById(R.id.gift_ball);
        gift_status = itemView.findViewById(R.id.gift_status);
        itemView.setOnClickListener(this);
    }

    public void bind(String id, String ball, int status) {
        id_gift.setText(id);
        gift_ball.setText(ball);

        String text_status;
        int color;
        if(status == 1){
            text_status = "На рассмотрении";
            color = Color.parseColor("#cccc00");
        }
        else if(status == 10) {
            text_status = "Отклоненно";
            color = Color.RED;
        }
        else {
            text_status = "Принято";
            color = Color.GREEN;
        }
        gift_status.setText(text_status);
        gift_status.setTextColor(color);
    }


    public void setClickListener(MyAdapter.ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public void onClick(View v) {
        if (clickListener != null) {
            clickListener.onItemClick(v, getAdapterPosition());
        }
    }
}

