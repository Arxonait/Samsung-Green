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

public class Adapter_newMess extends RecyclerView.Adapter<ViewHolder_newMess> {

    private List<Mes_obj> data ;
    private ItemClickListener clickListener;

    public Adapter_newMess(ItemClickListener clickListener) {
        this.data = new ArrayList<>();
        this.clickListener = clickListener;
    }

    public void setData(List<Mes_obj> data) {
        this.data = data;
        notifyDataSetChanged();
    }
    public void addData(Mes_obj mess) {
        this.data.add(mess);
        notifyDataSetChanged();
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder_newMess onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_mess_item, parent, false);
        return new ViewHolder_newMess(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder_newMess holder, @SuppressLint("RecyclerView") int position) {
        String time = this.data.get(position).time;
        String id = String.valueOf(data.get(position).id);
        String user_name = data.get(position).user_name;
        String title = data.get(position).title;


        holder.bind(id, user_name, title, time);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null)
                    clickListener.onItemClick_mess(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    public interface ItemClickListener {
        void onItemClick_mess(@Nullable View view, int position);
    }

}
