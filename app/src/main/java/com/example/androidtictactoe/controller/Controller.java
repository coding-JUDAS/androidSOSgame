package com.example.androidtictactoe.controller;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidtictactoe.model.GameLogic;

public class Controller {
    /**
     * Controller class for enforcing MVC pattern.
     */
    @SuppressLint("StaticFieldLeak")
    private static Controller controller = null;
    private final GameLogic game;
    private Button btn_play;
    private Button btn_home;
    private TextView txtGameState;
    private int player;
    private String[] playerIDs;
    private final String[] defaultPlayerNames = {"Player 1", "Player 2"};
    private String text = defaultPlayerNames[0];

    /**
     * Constructor
     * Made private by default to implement SINGLETON pattern for this class.
     */
    private Controller(){

        game = new GameLogic();
        player = game.getPlayer();
    }

    /**
     * Method ensures that only a single instance of the Controller class is available at
     * any given time.
     * @return an instance of this Controller.class.
     */
    public static Controller getInstance() {
        if(controller == null)
            controller = new Controller();
        return controller;
    }

    /**
     * Method is used to pass reset functionality between Model and View.
     */
    public void resetBoard() {
        game.reset();
        btn_play.setVisibility(View.GONE);
        btn_home.setVisibility(View.GONE);
        text = playerIDs[0] + "'s turn";
        txtGameState.setText(text);
    }

    public void connectToUI(Button btn_play, Button btn_home, TextView txtGameState, String[] playersIDs) {
        this.btn_play = btn_play;
        this.btn_home = btn_home;
        this.txtGameState = txtGameState;
        this.playerIDs = (playersIDs[0].length() < 2 || playersIDs[1].length() < 2) ? defaultPlayerNames: playersIDs;
        String text = playerIDs[0] + "'s turn";
        txtGameState.setText(text);

    }

    public boolean updateGame(int row, int col) {
        boolean play = false;
        if(game.updateGame(row, col)){
            updatePlayer();
            play = true;
        }
        return play;
    }

    public void updatePlayer() {
        if(!game.gameState()){
            if(game.getPlayer() == 1){
                player = 2;
                game.setPlayer(player);
                text = playerIDs[1] + "'s turn";
                txtGameState.setText(text);
            }
            else if(game.getPlayer() == 2){
                player = 1;
                game.setPlayer(player);
                text = playerIDs[0] + "'s turn";
                txtGameState.setText(text);
            }
        }
        else{
            btn_play.setVisibility(View.VISIBLE);
            btn_home.setVisibility(View.VISIBLE);
            if(game.isGameWon()){
                text = playerIDs[player-1] + " Wins";
                txtGameState.setText(text);
            }
            else{
                text = "DRAW !";
                txtGameState.setText(text);
            }
        }

    }

    //region Getters
    public int[][] getGameBoard(){
        return game.getGameBoard();
    }

    public int getPlayer() {
        return player;
    }
    public boolean endState(){
        return game.isGameWon();
    }

    public int[] getWinningLine() {
        return game.getWinningLine();
    }
    //endregion
}
