package com.example.rec_online;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Adapter_admin extends RecyclerView.Adapter<ViewHolder_admin> {

    private List<Oper_obj> data ;
    private ItemClickListener clickListener;

    public Adapter_admin(ItemClickListener clickListener) {
        this.data = new ArrayList<>();
        this.clickListener = clickListener;
    }

    public void setData(List<Oper_obj> data) {
        this.data = data;
        notifyDataSetChanged();
    }
    public void addData(Oper_obj oper) {
        this.data.add(oper);
        notifyDataSetChanged();
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder_admin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item, parent, false);
        return new ViewHolder_admin(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder_admin holder, @SuppressLint("RecyclerView") int position) {
        String time = this.data.get(position).time;
        String id = String.valueOf(data.get(position).id);
        String user_name = String.valueOf(data.get(position).user_name);
        String name_fact = String.valueOf(data.get(position).name_fact);


        holder.bind(id, user_name,name_fact, time);

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
