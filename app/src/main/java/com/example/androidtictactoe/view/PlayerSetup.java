package com.example.androidtictactoe.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.androidtictactoe.R;

public class PlayerSetup extends AppCompatActivity {
    private TextView player_1_id;
    private TextView player_2_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_setup);

        player_1_id = findViewById(R.id.edtTxtPlayer1);
        player_2_id = findViewById(R.id.edtTxtPlayer2);
    }

    public void onBtnStartClicked(View view) {
        String p1 = player_1_id.getText().toString();
        String p2 = player_2_id.getText().toString();

        Intent intent = new Intent(this, GamePlay.class);
        intent.putExtra("Player_Names", new String[] {p1, p2});
        startActivity(intent);
    }
}