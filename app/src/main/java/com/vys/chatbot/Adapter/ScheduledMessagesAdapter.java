package com.vys.chatbot.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.vys.chatbot.Models.SchedulesMessagesAPI.ScheduledMessage;
import com.vys.chatbot.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static com.vys.chatbot.Class.RandomString.randomText;

public class ScheduledMessagesAdapter extends RecyclerView.Adapter<ScheduledMessagesAdapter.MyViewHolder> {

    List<ScheduledMessage> list;
    Context context;

    public ScheduledMessagesAdapter(Context context,List<ScheduledMessage> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.scheduled_messages_adapter_layout,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try{
            Log.e("Time",String.valueOf(list.get(position).getPostAt()));
            holder.name.setText(list.get(position).getText());
            holder.time.setText(getDate(list.get(position).getPostAt()));
        }catch (Exception e){
            holder.name.setText(randomText());
            holder.scheduledImage.setVisibility(View.GONE);
            holder.time.setText(randomText());
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    private String getDate(int date) {
        java.util.Date d = new java.util.Date(date*1000L);
        return new SimpleDateFormat("dd/MMM - hh:mm aa",Locale.ENGLISH).format(d);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,time;
        ImageView scheduledImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.scheduled_name);
            time = itemView.findViewById(R.id.scheduled_time);
            scheduledImage = itemView.findViewById(R.id.scheduled_iv);
        }
    }
}
