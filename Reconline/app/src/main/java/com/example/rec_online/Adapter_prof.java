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

public class Adapter_prof extends RecyclerView.Adapter<ViewHolder_prof> {

    private List<Mes_obj> data ;
    private ItemClickListener clickListener;

    public Adapter_prof(ItemClickListener clickListener) {
        this.data = new ArrayList<>();
        this.clickListener = clickListener;
    }

    public void setData(List<Mes_obj> data) {
        this.data = data;
        notifyDataSetChanged();
    }
    public void addData(Mes_obj new_mess) {
        this.data.add(new_mess);
        notifyDataSetChanged();
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder_prof onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prof_mess, parent, false);
        return new ViewHolder_prof(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder_prof holder, @SuppressLint("RecyclerView") int position) {
        String time = this.data.get(position).time;
        String id = String.valueOf(data.get(position).id);
        String title = String.valueOf(data.get(position).title);
        boolean is_read = data.get(position).is_read;
        String text;
        if(is_read){
            text = "Прочитано";
        }else {
            text = "Новое";
        }

        holder.bind(id, title, text, time);

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
