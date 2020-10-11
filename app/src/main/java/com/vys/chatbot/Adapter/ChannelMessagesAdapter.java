package com.vys.chatbot.Adapter;

import android.annotation.SuppressLint;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.vys.chatbot.Activity.MainActivity;
import com.vys.chatbot.Class.ApiRequestClass;
import com.vys.chatbot.Models.ChannelMessagesAPI.Message;
import com.vys.chatbot.Models.UserProfileAPI.UserProfileAPI;
import com.vys.chatbot.R;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChannelMessagesAdapter extends RecyclerView.Adapter<ChannelMessagesAdapter.MyViewHolder> {

    List<Message> list;
    ApiRequestClass retrofitCall;

    public ChannelMessagesAdapter(List<Message> data,ApiRequestClass call){
        this.list = data;
        this.retrofitCall = call;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.messages_adapter_layout,parent,false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(list.get(position).getText().startsWith("<@")){
            String user = list.get(position).getText().substring(list.get(position).getText().indexOf("<@") + 2, list.get(position).getText().indexOf(">"));
            if(MainActivity.usersNames.containsKey(user)){
                holder.message.setText("@" + MainActivity.usersNames.get(user) + " joined !!");
            }else{
                Call<UserProfileAPI> callT = retrofitCall.userProfile(MainActivity.BOT_TOKEN,user);
                callT.enqueue(new Callback<UserProfileAPI>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(Call<UserProfileAPI> call, Response<UserProfileAPI> response) {
                        if(response.isSuccessful()){
                            holder.message.setText("@" + response.body().getUser().getName() + " joined via your invite link!");
                        }else{
                            holder.message.setText("New User joined");
                        }
                    }

                    @Override
                    public void onFailure(Call<UserProfileAPI> call, Throwable t) {
                        holder.message.setText("New User joined");
                    }
                });
            }
        }else{
            holder.message.setText(list.get(position).getText());
        }
        holder.time.setText(getDate((long) Double.parseDouble(list.get(position).getTs())));
        if(MainActivity.usersNames.containsKey(list.get(position).getUser())){
            holder.name.setText(MainActivity.usersNames.get(list.get(position).getUser()));
        }else{
            Call<UserProfileAPI> call = retrofitCall.userProfile(MainActivity.BOT_TOKEN,list.get(position).getUser());
            call.enqueue(new Callback<UserProfileAPI>() {
                @Override
                public void onResponse(Call<UserProfileAPI> call, Response<UserProfileAPI> response) {
                    if(response.isSuccessful()){
                        holder.name.setText(response.body().getUser().getName());
                    }else{
                        holder.name.setText(list.get(position).getUser());
                    }
                }

                @Override
                public void onFailure(Call<UserProfileAPI> call, Throwable t) {
                    holder.name.setText(list.get(position).getUser());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        return DateFormat.format("dd-MM hh:mm", cal).toString();
    }

    public void addNewData(Message msg) {
        list.add(msg);
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,message,time;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.messages_adapter_name);
            time = itemView.findViewById(R.id.messages_adapter_time);
            message = itemView.findViewById(R.id.messages_adapter_message);
        }
    }
}
