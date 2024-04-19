package com.example.androidtictactoe.model;

public class GameLogic {
    /**
     * GameLogic class stores data structures, for storing the game related data, algorithms
     * that perform certain operations that manipulate the data.
     * It keeps track of Player's turn, game state, and number of moves made by both players. Data structure
     * uses 2D int array as representation of the game board.
     * A zero indicates a vacant spot on the board, else a 'one' or a 'two'.
     * One -> piece placed by player 1.
     * Two -> piece placed by player 2.
     */

    //region Global Variables
    private int [][]gameBoard;
    private int[] winningLine = {-1, -1, -1};
    private int player;
    private int moveCounter;
    private boolean gameWon;
    private boolean gameOver;
    //endregion

    /**
     * Constructor.
     */
    public GameLogic(){
        //initialize variables.
        this.gameWon =false;
        this.gameOver = false;
        this.player = 1;
        moveCounter = 0;

        // Populate game board 2D structure with zeros.
        gameBoard = new int[3][3];
        for(int row = 0; row < 3; row++){
            for(int col= 0; col < 3; col++){
                gameBoard[row][col] = 0;
            }
        }
    }

    // region Getters and Setters
    /**
     *
     * @return 2D array representing game board.
     */
    public int[][] getGameBoard() {
        return gameBoard;
    }
    public int getPlayer(){
        return player;
    }
    public void setPlayer(int player){
        this.player = player;
    }
    public int getMoveCount(){
        return moveCounter;
    }

    public boolean isGameWon() {
        return gameWon;
    }
    public boolean gameState(){
        return gameOver;
    }
    public int[] getWinningLine(){
        return winningLine;
    }
    // endregion

    /**
     * Method updates the game by placing a piece on the board using row-column coordinates.
     * This is a move that is made by the current player.
     * @param row in which to place a piece.
     * @param col in which to place a piece.
     * @return true if piece is placed in appropriate LOCATION, false otherwise.
     */
    public boolean updateGame(int row, int col) {
        // check game state - attempt to place piece if game is not over.
        if(!gameOver){
            //Return TRUE if placed successfully, update game variables.
            if(gameBoard[row-1][col-1] == 0 && moveCounter < 10){
                //place piece using player ID
                gameBoard[row-1][col-1] = player;
                moveCounter++;
                // check for a win and update gameWon...
                gameWon = checkWin();
                if(gameWon || moveCounter == 9)
                    gameOver = true;
                return true;
            }
        }

        return false;
    }

    /**
     * Method is used to check all possible combinations on GRID for a winning line after a player
     * has placed a piece on the board.
     * Possible combinations: Horizontal(rows) -  {[(0,0),(0,1),(0,2)], [(1,0),(1,1),(1,2),], [(2,0),(2,1),(2,2),]}
     * Vertical(columns) - {[(0,0),(1,0),(2,0)], [(0,1),(1,1),(2,1),], [(0,2),(1,2),(2,2),]}
     * and Diagonal() - {[(0,0),(1,1),(2,2)], [(0,2),(1,1),(2,0),]}.
     * @return TRUE if combination found, false otherwise.
     */
    private boolean checkWin() {
        // check rows
        if(gameBoard[0][0] == gameBoard[0][1] && gameBoard[0][1] == gameBoard[0][2] && gameBoard[0][0] !=0){
            winningLine = new int[]{0, 0, 1};
            return true;
        }
        if(gameBoard[1][0] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[1][2] && gameBoard[1][0] !=0){
            winningLine = new int[]{1, 0, 2};
            return true;
        }
        if(gameBoard[2][0] == gameBoard[2][1] && gameBoard[2][1] == gameBoard[2][2] && gameBoard[2][0] !=0){
            winningLine = new int[]{2, 0, 3};
            return true;
        }

        // check columns
        if(gameBoard[0][0] == gameBoard[1][0] && gameBoard[1][0] == gameBoard[2][0] && gameBoard[0][0] !=0){
            winningLine = new int[]{0, 0, 4};
            return true;
        }
        if(gameBoard[0][1] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][1] && gameBoard[0][1] !=0){
            winningLine = new int[]{0, 1, 5};
            return true;
        }
        if(gameBoard[0][2] == gameBoard[1][2] && gameBoard[1][2] == gameBoard[2][2] && gameBoard[0][2] !=0){
            winningLine = new int[]{0, 2, 6};
            return true;
        }

        // check diagonals
        if(gameBoard[0][0] == gameBoard[1][1] && gameBoard[0][0] == gameBoard[2][2] && gameBoard[0][0] !=0){
            winningLine = new int[]{0, 0, 7};
            return true;
        }
        if(gameBoard[0][2] == gameBoard[1][1] && gameBoard[0][2] == gameBoard[2][0] && gameBoard[0][2] !=0){
            winningLine = new int[]{0, 0, 8};
            return true;
        }

        return false;
    }

    /**
     * Method is used to reset the entire GAME, setting it up for a new GAME.
     */
    public void reset(){
        for(int row = 0; row < 3; row++){
            for(int col= 0; col < 3; col++){
                gameBoard[row][col] = 0;
            }
        }
        player = 1;
        moveCounter = 0;
        gameOver = false;
        gameWon = false;
    }

}
