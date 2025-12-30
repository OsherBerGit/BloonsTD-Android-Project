package com.example.bloonstowerdefense;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Instruction extends AppCompatActivity implements View.OnClickListener {
    Button btnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        btnback = findViewById(R.id.btnBack);
        btnback.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
            finish(); // close the activity
    }
}