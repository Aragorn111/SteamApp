package com.example.steamapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button playerButton;

    private Button ownedGamesButton;

    private Button recentlyPlayedGamesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        playerButton = (Button)findViewById(R.id.buttonPlayer);
        playerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
                startActivity(intent);
            }
        });

        ownedGamesButton = (Button)findViewById(R.id.buttonGetOwnedGames);
        ownedGamesButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, OwnedGamesActivity.class);
                startActivity(intent);
            }
        });

        recentlyPlayedGamesButton = (Button)findViewById(R.id.buttonRecentlyPlayedGames);
        recentlyPlayedGamesButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, RecentlyPlayedGamesActivity.class);
                startActivity(intent);
            }
        });

    }

}
