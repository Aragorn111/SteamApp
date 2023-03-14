package com.example.steamapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.steamapp.model.ListGameInfo;
import com.example.steamapp.RecentlyPlayedGamesActivity;

public class MyAdapter extends RecyclerView.Adapter <MyAdapter.MyViewHolder>{
    private ListGameInfo listGameInfo;
    private int mNumberItems;
    public static final String TAG = "MyAdapter";

    public MyAdapter(ListGameInfo listGameInfo, int length){

        this.mNumberItems=length;
        this.listGameInfo=listGameInfo;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_item, parent,false);
        MyViewHolder viewHolder=new MyViewHolder(view);
        Log.d(TAG, "onCreateViewHolder: called");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

    private TextView gameName;
    private TextView gamePlaytime;

    private TextView gameID;
    private ImageView gameImg;


    public MyViewHolder(View itemView){
        super(itemView);
        gameName = itemView.findViewById(R.id.name);
        gamePlaytime = itemView.findViewById(R.id.playtime);
        gameImg = itemView.findViewById(R.id.img);
        gameID = itemView.findViewById(R.id.ID);
    }


    void bind (int position){
        String game_name = listGameInfo.ListGameInfo[position].getGame_name();
        String game_playtime = listGameInfo.ListGameInfo[position].getGame_playtime();
        String game_appid = listGameInfo.ListGameInfo[position].getGame_ID();
        String game_img = listGameInfo.ListGameInfo[position].getGame_img();

        game_appid = "ID: "+game_appid;
        game_playtime = "Время в игре: "+game_playtime;

        gameName.setText(game_name);
        gamePlaytime.setText(game_playtime);
        gameID.setText(game_appid);

        Glide.with(itemView.getContext())
                .load(game_img)
                .into(gameImg);


    }
}
}
