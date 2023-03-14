package com.example.steamapp;


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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class OwnedGamesActivity extends Activity implements View.OnClickListener, GetDataFromInternet.AsyncResponse {

    private static final String TAG = "OwnedGamesActivity";
    private Button searchButton;
    private EditText searchField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ownedgames);

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

        String BASE_URL = "http://api.steampowered.com/IPlayerService/GetOwnedGames/v0001";
        String PARAM_KEY = "key";
        String key_value = "C0A9812104269C6354AF22380F232DDA";
        String PARAM_APPID="steamid";
        String appid_value=searchField.getText().toString();
        String PARAM_INFO = "include_appinfo";
        String info_value = "1";
        String PARAM_FORMAT="format";
        String format_value="json";

        Uri builtUri = Uri.parse(BASE_URL).buildUpon().appendQueryParameter(PARAM_KEY, key_value).appendQueryParameter(PARAM_APPID, appid_value).appendQueryParameter(PARAM_INFO, info_value).appendQueryParameter(PARAM_FORMAT, format_value).build();
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
            ArrayList <String> namesGames=new ArrayList<String>();
            for(int i=0;i<length;i++){
                JSONObject obj=games.getJSONObject(i);
                String nameGame=obj.getString("name");

                namesGames.add(nameGame);
            }
            Log.d(TAG, "Name: "+namesGames);
            TextView count = findViewById(R.id.count);
            String game_count=response.getString("game_count");

            ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list_item, namesGames);
            ListView listGames = findViewById(R.id.listGames);

            listGames.setAdapter(adapter);

            count.setText(game_count);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


