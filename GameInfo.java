package com.example.steamapp.model;

public class GameInfo {

    int id;
    String game_appid;
    String game_name;
    String game_playtime_forever;
    String game_img;

    public GameInfo(String appid, String nameGame, String playtime_forever, String img){
        this.game_appid = appid;
        this.game_name=nameGame;
        this.game_playtime_forever=playtime_forever;
        this.game_img=img;
    }

    public String getGame_name(){
        return game_name;
    }

    public String getGame_ID(){
        return game_appid;
    }

    public String getGame_playtime(){
        return game_playtime_forever;
    }

    public String getGame_img(){
        return game_img;
    }

}
