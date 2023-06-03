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

public class Adapter_rec extends RecyclerView.Adapter<ViewHolder_rec> {

    private List<Oper_obj> data ;
    private ItemClickListener clickListener;

    public Adapter_rec(ItemClickListener clickListener) {
        this.data = new ArrayList<>();
        this.clickListener = clickListener;
    }

    public void setData(List<Oper_obj> data) {
        this.data = data;
        notifyDataSetChanged();
    }
    public void addData(Oper_obj new_gift) {
        this.data.add(new_gift);
        notifyDataSetChanged();
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder_rec onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.oper_history_item, parent, false);
        return new ViewHolder_rec(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder_rec holder, @SuppressLint("RecyclerView") int position) {
        String id = String.valueOf(data.get(position).id);
        String ball = String.valueOf(data.get(position).ball);
        int status = data.get(position).codeStatus;
        holder.bind(id, ball, status);

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
