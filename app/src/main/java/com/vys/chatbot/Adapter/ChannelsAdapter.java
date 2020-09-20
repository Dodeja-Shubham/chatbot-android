package com.vys.chatbot.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vys.chatbot.Models.ChannelsAPI.Channel;
import com.vys.chatbot.R;

import java.util.List;


public class ChannelsAdapter extends RecyclerView.Adapter<ChannelsAdapter.MyViewHolder> {

    List<Channel> list;

    public ChannelsAdapter(List<Channel> listData){
        this.list = listData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.channels_adapter_layout,parent,false);
        return new MyViewHolder((view));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.desc.setText(list.get(position).getPurpose().getValue());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,desc;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.channels_adapter_name_tv);
            desc = itemView.findViewById(R.id.channels_adapter_desc_tv);
        }
    }
}
