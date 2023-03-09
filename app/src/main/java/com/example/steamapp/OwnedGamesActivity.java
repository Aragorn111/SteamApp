package com.example.steamapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OwnedGamesActivity extends Activity implements View.OnClickListener, GetOwnedGames.AsyncResponse {

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
        URL url = buildUrl(searchField.getText().toString());
        new GetOwnedGames(this).execute(url);
    }

    private URL buildUrl(String city) {

        String BASE_URL = "http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002";
        String PARAM_KEY = "key";
        String key_value = "C0A9812104269C6354AF22380F232DDA";
        String PARAM_APPID = "steamids";
        String appid_value = "76561198366621124";

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

            JSONObject resultJSON = new JSONObject(output);
            JSONObject player = resultJSON.getJSONObject("main");
            JSONObject response = resultJSON.getJSONObject("response");
            JSONObject players = resultJSON.getJSONObject("players");

            TextView personaname = findViewById(R.id.name);
            String personame = players.getString("personame");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
