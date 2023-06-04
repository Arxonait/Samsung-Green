package com.example.rec_online;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter_newOper extends RecyclerView.Adapter<ViewHolder_newOper> {

    private List<Oper_obj> data ;
    private ItemClickListener clickListener;

    public Adapter_newOper(ItemClickListener clickListener) {
        this.data = new ArrayList<>();
        this.clickListener = clickListener;
    }

    public void setData(List<Oper_obj> data) {
        this.data = data;
        notifyDataSetChanged();
    }


    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder_newOper onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_oper_mess, parent, false);
        return new ViewHolder_newOper(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder_newOper holder, @SuppressLint("RecyclerView") int position) {
        String time = this.data.get(position).time;
        String id = String.valueOf(data.get(position).id);
        String login = String.valueOf(data.get(position).login);
        String name_fact = String.valueOf(data.get(position).name_fact);


        holder.bind(id, login,name_fact, time);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null)
                    clickListener.onItemClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    public interface ItemClickListener {
        void onItemClick(@Nullable View view, int position);
    }

}
