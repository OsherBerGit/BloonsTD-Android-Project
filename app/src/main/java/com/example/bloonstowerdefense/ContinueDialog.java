package com.example.bloonstowerdefense;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class ContinueDialog extends Dialog implements View.OnClickListener {
    Button btnYes, btnNo;
    private Context context;
    private ActivityResultLauncher<Object> activityResultLauncher;

    public ContinueDialog(@NonNull Context context) {  // the Context is the pointer to the GameActivity
        super(context); // קריאה לפעולה הבונה של ה Dialog
        this.context = context;

        setContentView(R.layout.activity_custom_dialog); // show on screen the XML file
        setTitle("Continue the last save?");
        setCancelable(false); // false -> android doesn't close the dialog when press outside

        btnYes = findViewById(R.id.btnMap1);
        btnYes.setOnClickListener(this);
        btnNo = findViewById(R.id.btnMap2);
        btnNo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) { // todo take all data from the firebase and put on
        Intent game = new Intent(context, GameActivity.class);
        if(v == btnYes)
        {
            game.putExtra("continue", "Yes");
            ((Activity)context).startActivity(game);
            ((Activity)context).finish();
        }
        if(v == btnNo)
        {
            game.putExtra("continue", "No");
            ((Activity)context).startActivity(game);
            dismiss();
        }
    }

    private void createDialog(){
        ContinueDialog customDialog = new ContinueDialog(context);
        customDialog.show();
    }
}