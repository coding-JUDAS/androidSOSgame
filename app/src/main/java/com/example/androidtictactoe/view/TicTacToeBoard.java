package com.example.androidtictactoe.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.androidtictactoe.R;
import com.example.androidtictactoe.controller.Controller;

public class TicTacToeBoard extends View {
    private int boardColor;
    private int xColor;
    private int oColor;
    private int winningLineColor;
    private final Paint paint = new Paint();
    private int cellSize = getWidth()/3;
    private Controller controller=null;

    public TicTacToeBoard(Context context) {
        super(context);
    }

    public TicTacToeBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        // Typed array used to store an array of values that are retrieved with
        // Resource.Theme().obtainStyleableAttributes.
        TypedArray res = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.TicTacToeBoard,
                0,
                0);

        try {
            boardColor = res.getInt(R.styleable.TicTacToeBoard_boardColor, 0);
            xColor = res.getInt(R.styleable.TicTacToeBoard_xColor, 0);
            oColor = res.getInt(R.styleable.TicTacToeBoard_oColor, 0);
            winningLineColor = res.getInt(R.styleable.TicTacToeBoard_winningLineColor, 0);

        }
        finally {
            res.recycle();
        }
    }
    public void attachController(Controller controller){
        this.controller = controller;
    }

    public TicTacToeBoard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int width, int height){
        super.onMeasure(width, height);

        int dimension = Math.min(getMeasuredWidth(), getMeasuredHeight());
        cellSize = dimension / 3;
        setMeasuredDimension(dimension, dimension);
    }
    @Override
    protected void onDraw(Canvas canvas){
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        drawGameBoard(canvas);
        drawMarkers(canvas);
        if(controller.endState()){
            drawWinningLine(canvas, controller.getWinningLine());
        }
    }

    private void drawMarkers(Canvas canvas) {
        for(int row = 0; row < 3; row++){
            for(int col= 0; col < 3; col++){
                if(controller.getGameBoard()[row][col] != 0){
                    if(controller.getGameBoard()[row][col] == 1){
                        drawX(canvas, row, col);
                    }
                    if(controller.getGameBoard()[row][col] == 2){
                        drawO(canvas, row, col);
                    }
                    invalidate();
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            int row = (int) Math.ceil(event.getY()/cellSize);
            int col = (int) Math.ceil(event.getX()/cellSize);
            if(controller.updateGame(row, col)){

                invalidate();
                //controller.updatePlayer();
                return true;
            }
        }
        return false;
    }
    private void drawGameBoard(Canvas canvas){
        paint.setColor(boardColor);
        paint.setStrokeWidth(16);
        for(int col = 1; col < 3; col++){
            canvas.drawLine(col*cellSize, 0, col*cellSize, canvas.getWidth(), paint);
        }
        for(int row = 1; row < 3; row++){
            canvas.drawLine(0, cellSize*row, canvas.getWidth(), cellSize*row, paint);
        }
    }
    private void drawX(Canvas canvas, int row, int col){
        paint.setColor(xColor);

        canvas.drawLine((float)((col+1)*cellSize - cellSize*0.2),
                (float)(row*cellSize + cellSize*0.2),
                (float)(col*cellSize + cellSize*0.2),
                (float)((row+1)*cellSize - cellSize*0.2),
                paint);

        canvas.drawLine((float)(col*cellSize + cellSize*0.2),
                (float)(row*cellSize + cellSize*0.2),
                (float)((col+1)*cellSize - cellSize*0.2),
                (float)((row+1)*cellSize - cellSize*0.2),
                        paint);
    }
    private void drawO(Canvas canvas, int row, int col){
        paint.setColor(oColor);

        canvas.drawOval((float)(col*cellSize +cellSize*0.2),
                (float)(row*cellSize + cellSize*0.2),
                (float)(((col+1)*cellSize) - cellSize*0.2),
                (float)(((row+1)*cellSize) - cellSize*0.2),
                paint);
    }
    public void drawWinningLine(Canvas canvas, int[] row_col_type_combo){
        // [0] -> row [1], -> col [2], -> type
        int row = row_col_type_combo[0];
        int col = row_col_type_combo[1];
        paint.setColor(winningLineColor);
        switch (row_col_type_combo[2]){
            case 1:
            case 3:
            case 2:
                drawHorizontalWinningLine(canvas, row, col);
                break;
            case 4:
            case 5:
            case 6:
                drawVerticalWinningLine(canvas, row, col);
                break;
            case 7:
                drawDiagonalWinningLine(canvas);
                break;
            case 8:
                drawOffDiagonalWinningLine(canvas);
        }
    }

    private void drawHorizontalWinningLine(Canvas canvas, int row, int col){
        canvas.drawLine(0, (float)(row*cellSize + cellSize/2),
                cellSize*3, (float)(row*cellSize + cellSize/2),
                paint);
    }
    private void drawVerticalWinningLine(Canvas canvas, int row, int col){
        canvas.drawLine((float) (col*cellSize + cellSize/2), 0,
                (float) (col*cellSize + cellSize/2), cellSize*3,
                paint);
    }
    private void drawDiagonalWinningLine(Canvas canvas){
        canvas.drawLine(0, 0, cellSize*3, cellSize*3, paint);
    }
    private void drawOffDiagonalWinningLine(Canvas canvas){
        canvas.drawLine(0, cellSize*3, cellSize*3, 0, paint);
    }
}
