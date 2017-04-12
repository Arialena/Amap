package com.example.administrator.amp.register_login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.administrator.amp.R;
import com.example.administrator.amp.adapter.DisclaimerAdatpter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/1/5.
 * 免责声明页
 */

public class DisclaimerActivity extends AppCompatActivity {

    private ImageButton backBtn;
    private ListView disclaimerList;
    private DisclaimerAdatpter disclaimerAdatpter;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.disclaimer_act);

        backBtn = (ImageButton) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisclaimerActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        disclaimerList = (ListView) findViewById(R.id.disclaimerList);
        disclaimerAdatpter = new DisclaimerAdatpter(this, getData());
        disclaimerList.setAdapter(disclaimerAdatpter);
    }

    private List<HashMap<String, String>> getData(){
        List<HashMap<String, String>>  hashMapList = new ArrayList<>();
        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("titleText", getString(R.string.titleText));
        hashMap.put("disclaimer",  getString(R.string.disclaimer));
        hashMapList.add(hashMap);
        return hashMapList;
    }
}
