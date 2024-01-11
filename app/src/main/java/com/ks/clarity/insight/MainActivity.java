package com.ks.clarity.insight;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void printMessage(String out){
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.message);
        textView.setText(out);
    }
}