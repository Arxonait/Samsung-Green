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

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private List<Gift_obj> data;
    private ItemClickListener clickListener;

    public MyAdapter(ItemClickListener clickListener) {
        this.data = new ArrayList<>();
        this.clickListener = clickListener;
    }

    public void setData(List<Gift_obj> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String id = String.valueOf(position + 1 );
        String ball = String.valueOf(data.get(position).ball);
        int status = data.get(position).status;
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
