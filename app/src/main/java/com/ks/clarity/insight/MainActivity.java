package com.ks.clarity.insight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        findViewById(R.id.execute).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText code = (EditText) findViewById(R.id.code);
                Switch Englishenable = (Switch) findViewById(R.id.englishenable);
                priming priming = new priming();
                if(Englishenable.isChecked() == false) {
                    je je = new je();
                    String translatedLines = je.jeTranslate(code.getText().toString());
                    priming.main(translatedLines,context,code.getLineCount());
                }else if(Englishenable.isChecked() == true) {
                    priming.main(code.getText().toString(),context,code.getLineCount());
                }
            }
        });

        findViewById(R.id.translate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText code = (EditText) findViewById(R.id.code);
                je je = new je();
                String translatedLines = je.jeTranslate(code.getText().toString());
                code.setText(translatedLines);

            }
        });
    }

}