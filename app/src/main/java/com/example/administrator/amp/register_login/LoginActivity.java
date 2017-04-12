package com.example.administrator.amp.register_login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.amp.AmapActivity;
import com.example.administrator.amp.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/1/4.
 */

public class LoginActivity extends AppCompatActivity {
    private EditText account, keying;//手机号和密码
    private Button register, //注册
                    forgetKey,//忘记密码
                    login,//登陆
                    mianze;//免责声明

    private RequestQueue mQueue;
    private boolean valid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        mQueue = Volley.newRequestQueue(this);

        account = (EditText) findViewById(R.id.account);
        keying = (EditText) findViewById(R.id.keying);
        register = (Button) findViewById(R.id.register);
        forgetKey = (Button) findViewById(R.id.forgetKey);
        login = (Button) findViewById(R.id.login);
        mianze = (Button) findViewById(R.id.mianze);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        forgetKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, ForgetActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valodata();
            }
        });
        mianze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, DisclaimerActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private boolean valodata(){
        String acount = account.getText().toString();
        String password = keying.getText().toString();
        if (acount.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(acount).matches() || acount.length() != 11){
            account.setError("手机号输入错误");
            valid = false;
        }else {
            account.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10){
            keying.setError("密码为4到16个字符");
            valid = false;
        }else {
            keying.setError(null);
        }
        if ((acount != null & acount.length() == 1 & android.util.Patterns.EMAIL_ADDRESS.matcher(acount).matches()) &&
                (4 <= password.length() & password.length() <= 16 & android.util.Patterns.EMAIL_ADDRESS.matcher(password).matches())) {
            StringRequest stringRequest = new StringRequest("http://api.zhaibao.cn/login?mobile="+account.getText()+
                    "&password="+keying.getText()+"&version=1.0&deviceType=2",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                JSONObject jsonObject2 = new JSONObject(success);
                                String msg = jsonObject2.getString("message");
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                            Intent intent = new Intent(LoginActivity.this, AmapActivity.class);
                            startActivity(intent);
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("TAG", error.getMessage(), error);
                }
            });
            mQueue.add(stringRequest);
        }else {
            Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
        }
        return valid;
    }
}
