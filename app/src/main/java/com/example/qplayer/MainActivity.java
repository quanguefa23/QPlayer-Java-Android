package com.example.qplayer;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.cardview.widget.CardView;

public class MainActivity extends BaseActivity {

    CardView cv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("State", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cv1 = findViewById(R.id.cv1);
        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListSongActivity.class);
                intent.putExtra("playlist_name", "US-UK");
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        showDialog();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("State", "onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("State", "onDestroy");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("State", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("State", "onResume");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d("State", "onStop");
    }

    private void showDialog() {
        Log.d("State", "showDialog");
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_out);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button bt_cancel = dialog.findViewById(R.id.cancel_button);
        Button bt_ok = dialog.findViewById(R.id.ok_button);

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                MainActivity.super.onBackPressed();
            }
        });

        dialog.show();
    }
}