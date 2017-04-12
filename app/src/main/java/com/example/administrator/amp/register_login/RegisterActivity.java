package com.example.administrator.amp.register_login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.amp.R;

/**
 * Created by Administrator on 2017/1/4.
 */

public class RegisterActivity extends AppCompatActivity {

private EditText userNumber, code, userKey;
    private Button codeBtn, register, zhuce_denglu;
    private ImageButton backBtn;

    private RequestQueue mQueue;

    private boolean tag = true;
    private int i = 60;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_ietm);

        mQueue = Volley.newRequestQueue(this);

        userNumber = (EditText) findViewById(R.id.userNumber);//手机号码
        code = (EditText) findViewById(R.id.code);//验证码
        userKey = (EditText) findViewById(R.id.userKey);

        codeBtn = (Button) findViewById(R.id.codeBtn);//获取验证码
        register = (Button) findViewById(R.id.register);//注册
        backBtn = (ImageButton) findViewById(R.id.backBtn);//返回
        zhuce_denglu = (Button) findViewById(R.id.zhuce_denglu);//用已有账号登陆

        //返回
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //获取验证码
        codeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest("http://api.zhaibao.cn/sendRegCode?mobile=" + userNumber.getText() + "&version=1.0&deviceType=2",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String user = userNumber.getText().toString();
                                if (user == null || user.equals("")){
                                    Toast.makeText(RegisterActivity.this, "手机号不能为空", Toast.LENGTH_LONG).show();
                                }else if (user != null && user.length() != 11 ){
                                    Toast.makeText(RegisterActivity.this, "手机号位数超出或缺少", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(RegisterActivity.this, "验证码获取成功", Toast.LENGTH_LONG).show();
                                    changeBtnGetCode();
                                }
                                Log.d("TAG", response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", error.getMessage(), error);
                    }
                });
                mQueue.add(stringRequest);
            }
        });

        //注册
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  String code = codeText.getText().toString();
                valodata();
            }
        });
        //用已有账号登陆
        zhuce_denglu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    //获取验证码倒计时
    private void changeBtnGetCode() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                if (tag) {
                    while (i > 0) {
                        i--;
                        if (RegisterActivity.this == null) {
                            break;
                        }

                        RegisterActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                codeBtn.setText("获取验证码(" + i + ")");
                                codeBtn.setClickable(false);
                            }
                        });
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    tag = false;
                }
                i = 60;
                tag = true;
                if (RegisterActivity.this != null) {
                    RegisterActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            codeBtn.setText("获取验证码");
                            codeBtn.setClickable(true);
                        }
                    });
                }
            };
        };
        thread.start();
    }

    private boolean valodata(){
        boolean valid = true;

        String acount = userNumber.getText().toString();
        String password = userKey.getText().toString();
        if (acount.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(acount).matches()){
            userNumber.setError("enter a valid account address");
            valid = false;
        }else {
            userNumber.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10){
            userKey.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        }else {
            userKey.setError(null);
        }
        return valid;
    }
}

