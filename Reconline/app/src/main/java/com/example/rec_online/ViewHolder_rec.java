package com.example.rec_online;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder_rec extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView id_gift;
    private TextView gift_status;
    private TextView gift_ball;
    private Adapter_rec.ItemClickListener clickListener;

    public ViewHolder_rec(View itemView) {
        super(itemView);
        id_gift = itemView.findViewById(R.id.oper_id);
        gift_ball = itemView.findViewById(R.id.oper_title);
        gift_status = itemView.findViewById(R.id.oper_status);
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


    public void setClickListener(Adapter_rec.ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public void onClick(View v) {
        if (clickListener != null) {
            clickListener.onItemClick(v, getAdapterPosition());
        }
    }
}

