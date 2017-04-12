package com.example.administrator.amp.setting_personal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.administrator.amp.AmapActivity;
import com.example.administrator.amp.R;

/**
 * Created by Administrator on 2017/1/10.
 * 个人中心
 */

public class PersonCenter extends Activity {
    private Button accounts, point, setting, order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_center_cc);
        ImageButton backBtn = (ImageButton) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PersonCenter.this, AmapActivity.class);
                startActivity(intent);
                finish();
            }
        });

        accounts = (Button) findViewById(R.id.iTunes);
        accounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PersonCenter.this, Accounts.class);
                startActivity(intent);
                finish();
            }
        });

        setting = (Button) findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PersonCenter.this, Setting.class);
                startActivity(intent);
                finish();
            }
        });

        point = (Button) findViewById(R.id.pos);
        point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PersonCenter.this, Point.class);
                startActivity(intent);
                finish();
            }
        });

        order = (Button) findViewById(R.id.lineItem);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PersonCenter.this, Order.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
