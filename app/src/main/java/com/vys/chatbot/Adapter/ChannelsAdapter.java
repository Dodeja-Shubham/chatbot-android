package com.vys.chatbot.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.vys.chatbot.Activity.MessagesActivity;
import com.vys.chatbot.Models.ChannelsAPI.Channel;
import com.vys.chatbot.R;

import org.w3c.dom.Text;

import java.util.List;


public class ChannelsAdapter extends RecyclerView.Adapter<ChannelsAdapter.MyViewHolder> {

    List<Channel> list;
    Context context;

    public ChannelsAdapter(List<Channel> listData, Context context) {
        this.list = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.channels_adapter_layout, parent, false);
        return new MyViewHolder((view));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        if(list.get(position).getIsPrivate()){
            holder.channelPrivate.setText("℗");
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView channelPrivate;
        MaterialRippleLayout rippleLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.channels_adapter_name_tv);
            rippleLayout = itemView.findViewById(R.id.channel_adapter_ripple);
            channelPrivate = itemView.findViewById(R.id.channels_adapter_prefix);
        }
    }
}
