package com.example.steamapp;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;



import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class PlayerActivity extends Activity implements View.OnClickListener, GetDataFromInternet.AsyncResponse {

    private static final String TAG = "PlayerActivity";
    private Button searchButton;
    private EditText searchField;
    private TextView ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        searchField=findViewById(R.id.searchField);

        ID=findViewById(R.id.ID);

        searchButton=findViewById(R.id.buttonSearch);
        searchButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        URL url = buildUrl(searchField.getText().toString());
        ID.setText(searchField.getText().toString());
        new GetDataFromInternet(this).execute(url);
    }

    private URL buildUrl (String city){

        String BASE_URL="http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002";
        String PARAM_KEY="key";
        String key_value="C0A9812104269C6354AF22380F232DDA";
        String PARAM_APPID="steamids";
        String appid_value=searchField.getText().toString();

        Uri builtUri= Uri.parse(BASE_URL).buildUpon().appendQueryParameter(PARAM_KEY, key_value).appendQueryParameter(PARAM_APPID,appid_value).build();
        URL url = null;

        try{
            url=new URL (builtUri.toString());
        }catch(MalformedURLException e){
            e.printStackTrace();
        }
        Log.d(TAG, "buildUrl: "+url);
        return url;
    }
    @Override
    public void processFinish(String output)
    {
        Log.d(TAG, "proccessFinish: "+output);
        try{

            JSONObject resultJSON=new JSONObject(output);

            JSONObject response = resultJSON.getJSONObject("response");
            JSONObject players=response.getJSONArray("players").getJSONObject(0);

            TextView personaname = findViewById(R.id.name);
            TextView nickname = findViewById(R.id.realName);
            TextView state = findViewById(R.id.state);
            ImageView img = findViewById(R.id.avatar);

            String personame=players.getString("personaname");
            String nick=players.getString("realname");
            String personastate=players.getString("personastate");
            String avatar=players.getString("avatarfull");



            personaname.setText(personame);
            nickname.setText(nick);
            switch (personastate){
                case "0":
                    state.setText("Оффлайн");
                    break;
                case "1":
                    state.setText("Онлайн");
                    break;
                case "2":
                    state.setText("Занят");
                    break;
                case "3":
                    state.setText("В гостях");
                    break;
            }


            Glide.with(this)
                    .load(avatar)
                    .into(img);


        }catch (JSONException e){
            e.printStackTrace();
        }
    }



}
