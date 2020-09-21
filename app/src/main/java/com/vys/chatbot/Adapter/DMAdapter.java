package com.vys.chatbot.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.vys.chatbot.Class.ApiRequestClass;
import com.vys.chatbot.Models.ChannelsAPI.Channel;
import com.vys.chatbot.Models.UserProfileAPI.UserProfileAPI;
import com.vys.chatbot.R;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DMAdapter extends RecyclerView.Adapter<DMAdapter.MyViewHolder> {

    List<Channel> list;
    Context context;
    ApiRequestClass retrofitCall;

    public DMAdapter(List<Channel> listData,Context context,ApiRequestClass call){
        this.list = listData;
        this.context = context;
        this.retrofitCall = call;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.channels_adapter_layout,parent,false);
        return new MyViewHolder((view));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.prefix.setText("@");
        Call<UserProfileAPI> call = retrofitCall.userProfile(context.getString(R.string.slack_bot_token),list.get(position).getUser());
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

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,prefix;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.channels_adapter_name_tv);
            prefix = itemView.findViewById(R.id.channels_adapter_prefix);
        }
    }
}