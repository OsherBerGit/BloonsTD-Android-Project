package com.example.bloonstowerdefense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnPlay , btnInst , btnSett; // create buttons
    // private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInst = findViewById(R.id.btnInst); // put in the button the id of the button from the XML
        btnPlay = findViewById(R.id.btnPlay);
        btnSett = findViewById(R.id.btnSett);
        btnPlay.setOnClickListener(this); // create listener adn function to the button
        btnSett.setOnClickListener(this);
        btnInst.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        if (v == btnSett){
//            Intent sett = new Intent(this, Setting.class);
//            // activityResultLauncher.launch(sett);
//        }
        if (v == btnInst) {
            Intent inst = new Intent(this, Instruction.class);
            startActivity(inst);
        }
        if (v == btnPlay) {
            ContinueDialog customDialog = new ContinueDialog(this);
            customDialog.show();
//            Intent Game = new Intent(this, RuleActivity.class); // מעביר בין אקטיביטיס
//            // Game.putExtra("name", content); // מעביר מידע בין אקטיביטיס
//            startActivity(Game); // מפעיל את הintent
        }
    }


}