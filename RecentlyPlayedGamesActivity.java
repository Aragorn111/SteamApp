package com.example.steamapp;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.steamapp.model.ListGameInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.net.MalformedURLException;
import java.net.URL;


public class RecentlyPlayedGamesActivity extends Activity implements View.OnClickListener, GetDataFromInternet.AsyncResponse {

    private static final String TAG = "RecentlyPlayedGamesActivity";
    private Button searchButton;
    private EditText searchField;
    private ListGameInfo listGameInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recentlyplayedgames);

        searchField = findViewById(R.id.searchField);

        searchButton = findViewById(R.id.buttonSearch);
        searchButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        URL url = buildUrl();
        new GetDataFromInternet(this).execute(url);
    }

    private URL buildUrl() {

        String BASE_URL = "https://api.steampowered.com/IPlayerService/GetRecentlyPlayedGames/v1";
        String PARAM_KEY = "key";
        String key_value = "C0A9812104269C6354AF22380F232DDA";
        String PARAM_APPID="steamid";
        String appid_value=searchField.getText().toString();

        Uri builtUri = Uri.parse(BASE_URL).buildUpon().appendQueryParameter(PARAM_KEY, key_value).appendQueryParameter(PARAM_APPID, appid_value).build();
        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "buildUrl: " + url);
        return url;
    }

    @Override
    public void processFinish(String output) {
        Log.d(TAG, "proccessFinish: " + output);
        try {

            JSONObject resultJSON=new JSONObject(output);
            JSONObject response = resultJSON.getJSONObject("response");
            JSONArray games=response.getJSONArray("games");
            int length=games.length();

            listGameInfo = new ListGameInfo(length);

            for(int i=0;i<length;i++){
                JSONObject obj=games.getJSONObject(i);
                String appid=obj.getString("appid");
                String nameGame=obj.getString("name");
                String playtime_forever=obj.getString("playtime_forever");
                String img_icon_url=obj.getString("img_icon_url");


                int playtime = Integer.parseInt (playtime_forever);

                int hours = playtime / 60;
                int minutes = playtime % 60;

                String playtime_forever_hours = Integer.toString(hours);
                String playtime_forever_minutes = Integer.toString(minutes);

                playtime_forever = playtime_forever_hours + " часа(ов) " + playtime_forever_minutes + " минут(ы)";

                String img = "http://media.steampowered.com/steamcommunity/public/images/apps/"+appid+"/"+img_icon_url+".jpg";

                listGameInfo.addGame(appid, nameGame, playtime_forever, img, i);
            }
            RecyclerView recyclerView = findViewById(R.id.recycleView);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(new MyAdapter(listGameInfo, length));


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
