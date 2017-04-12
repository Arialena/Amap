package com.example.administrator.amp.setting_personal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.administrator.amp.R;

/**
 * Created by Administrator on 2017/1/6.
 * 订单详情
 */

public class Order extends Activity {
    private ImageButton backBtn;
    private Button myOrder,//我的订单
                    theSingle;//接单
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_cc);

        backBtn = (ImageButton) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Order.this, PersonCenter.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
