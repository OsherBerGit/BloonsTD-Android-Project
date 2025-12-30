package com.example.bloonstowerdefense;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;

public class ExitDialog extends Dialog implements View.OnClickListener {
    Button btnYes, btnNo;
    private Context context;
    private ActivityResultLauncher<Object> activityResultLauncher;

    public ExitDialog(@NonNull Context context) {  // the Context is the pointer to the GameActivity
        super(context); // קריאה לפעולה הבונה של ה Dialog
        this.context = context;

        setContentView(R.layout.activity_custom_dialog); // show on screen the XML file
        setTitle("Are you wanna exit?");
        setCancelable(false); // false -> android doesn't close the dialog when press outside

        btnYes = findViewById(R.id.btnMap1);
        btnYes.setOnClickListener(this);
        btnNo = findViewById(R.id.btnMap2);
        btnNo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) { // todo add all data to the firebase
        if(v == btnYes)
        {
            ((GameActivity)context).boardGame.SaveGame();
            ((Activity)context).finish();
        }
        if(v == btnNo)
        {
            dismiss(); // הפעולה מוחקת את החלוהית של הדיאלוג
        }
    }

    private void createDialog1(){
        ExitDialog customDialog = new ExitDialog(context);
        customDialog.show();
    }
}