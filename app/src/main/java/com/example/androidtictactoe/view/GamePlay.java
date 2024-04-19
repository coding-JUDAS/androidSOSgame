package com.example.androidtictactoe.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidtictactoe.R;
import com.example.androidtictactoe.controller.Controller;

public class GamePlay extends AppCompatActivity {

    private TicTacToeBoard board;
    private Controller controller;
    private String[] playerNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        Intent intent = getIntent();
        if(intent != null){
            Bundle extras = intent.getExtras();
            if(extras != null){
                playerNames = extras.getStringArray("Player_Names");
            }
        }

        // obtain controls
        board = findViewById(R.id.ticTacToeBoard);

        Button btn_home = findViewById(R.id.btnHome);
        btn_home.setVisibility(View.GONE);

        Button btn_play = findViewById(R.id.btnPlayAgain);
        btn_play.setVisibility(View.GONE);

        TextView txtGameState = findViewById(R.id.txtPlayerTurn);


        // Instantiate and initialize Controller.
        controller = Controller.getInstance();
        controller.connectToUI(btn_play, btn_home, txtGameState, playerNames);
        board.attachController(controller);

    }

    public void onBtnPlayAgainClicked(View view) {
        controller.resetBoard();
        board.invalidate();
    }

    public void onBtnHomeClicked(View view) {
        controller.resetBoard();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}