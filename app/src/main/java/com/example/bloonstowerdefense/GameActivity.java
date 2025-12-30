package com.example.bloonstowerdefense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    BoardGame boardGame;
    String con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent game = getIntent();
        con = game.getStringExtra("continue");

        boardGame = new BoardGame(this);
        setContentView(boardGame);

        if(con.equals("Yes")) {
            boardGame.fb.getCoins();
            boardGame.fb.getLife();
            boardGame.fb.getRound();
            boardGame.fb.getTowers();
        }
    }

    public void setNewCoins(int coins) {
        boardGame.setNewCoins(coins);
    }

    public void setNewLife(int life) {
        boardGame.setNewLife(life);
    }

    public void setNewRound(int round) {
        boardGame.setNewRound(round);
    }

    public void setNewTowers(ArrayList<TowertoFb> towers){
        boardGame.setNewTowers(towers);
    }
}