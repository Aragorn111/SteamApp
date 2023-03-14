package com.example.steamapp.model;

public class ListGameInfo {

    public GameInfo[] ListGameInfo;

    public ListGameInfo(int size){
        ListGameInfo=new GameInfo[size];
    }

    public void addGame(String game_appid, String game_name, String game_playtime_forever, String game_img, int id){
        GameInfo game = new GameInfo(game_appid, game_name, game_playtime_forever, game_img);
        ListGameInfo[id]=game;
    }

}
