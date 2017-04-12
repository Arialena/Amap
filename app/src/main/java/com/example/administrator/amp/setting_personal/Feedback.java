package com.example.administrator.amp.setting_personal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.administrator.amp.R;

/**
 * Created by Administrator on 2017/1/6.
 * 意见反馈
 */

public class Feedback extends Activity {
    private ImageButton backBtn;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_cc);

        backBtn = (ImageButton) findViewById(R.id.backBtn);
        submit = (Button) findViewById(R.id.submit);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Feedback.this, Setting.class);
                startActivity(intent);
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Feedback.this, "提交成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Feedback.this, Setting.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
