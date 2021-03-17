package com.example.t2shop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.t2shop.R;

public class SplashScreen extends AppCompatActivity {

    private Handler handler;
    private Runnable r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        setContentView(R.layout.activity_splash_screen);
        handler = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                Intent it = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(it);
                overridePendingTransition(R.anim.anim_fade_in,  R.anim.anim_fade_out);
                finish();
            }
        };
        handler.postDelayed(r, 3000);
    }
}