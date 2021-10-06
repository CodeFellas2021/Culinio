package com.example.cibo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class cancel_alarm extends AppCompatActivity {
    Button stop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_alarm);
        stop = findViewById(R.id.stop_btn);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view==stop)
                {Intent s = new Intent(cancel_alarm.this, sound_service.class);
                stopService(s);}
            }
        });
    }

}