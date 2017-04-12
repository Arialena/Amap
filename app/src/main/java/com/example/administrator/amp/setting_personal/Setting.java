package com.example.administrator.amp.setting_personal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.administrator.amp.R;

/**
 * Created by Administrator on 2017/1/5.
 * 设置
 */

public class Setting extends Activity {
    private Button feedback;
    private ImageButton backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_cc);

        backBtn = (ImageButton) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setting.this, PersonCenter.class);
                startActivity(intent);
                finish();
            }
        });

        feedback = (Button) findViewById(R.id.feedback);
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Setting.this, Feedback.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
