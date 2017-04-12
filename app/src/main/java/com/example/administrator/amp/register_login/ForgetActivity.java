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

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/1/4.
 * 忘记密码页
 */

public class ForgetActivity extends AppCompatActivity {

private EditText userNumber, code, userNewKey, newKey;
    private Button codeBtn, setBtn;
    private ImageButton backBtn;

    private boolean tag = true;
    private int i = 60;

    private RequestQueue mQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgeter_view);

        mQueue = Volley.newRequestQueue(this);

        userNumber = (EditText) findViewById(R.id.userNumber);
        code = (EditText) findViewById(R.id.code);
        userNewKey = (EditText) findViewById(R.id.userNewKey);
        newKey = (EditText) findViewById(R.id.newKey);

        codeBtn = (Button) findViewById(R.id.forcodeBtn);
        setBtn = (Button) findViewById(R.id.setBtn);
        backBtn = (ImageButton) findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgetActivity.this, LoginActivity.class);
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
                                    Toast.makeText(ForgetActivity.this, "手机号不能为空", Toast.LENGTH_LONG).show();
                                }else if (user != null && user.length() != 11 ){
                                    Toast.makeText(ForgetActivity.this, "手机号位数超出或缺少", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(ForgetActivity.this, "验证码获取成功", Toast.LENGTH_LONG).show();
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

        //重置密码提交、登录
        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              valodata();
            }
        });
    }

    //验证码倒计时
    private void changeBtnGetCode() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                if (tag) {
                    while (i > 0) {
                        i--;
                        if (ForgetActivity.this == null) {
                            break;
                        }

                        ForgetActivity.this.runOnUiThread(new Runnable() {
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
                if (ForgetActivity.this != null) {
                    ForgetActivity.this.runOnUiThread(new Runnable() {
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
        String password1 = userNewKey.getText().toString();
        String password2 = newKey.getText().toString();
        if (acount.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(acount).matches() || acount.length() != 11){
            userNumber.setError("enter a valid account address");//账号输入错误
            valid = false;
        }else {
            userNumber.setError(null);
        }

        if (password1.isEmpty() || password1.length() < 4 || password1.length() > 16){
            userNewKey.setError("between 4 and 16 alphanumeric characters");//密码是4到16个字符
            valid = false;
        }else {
            userNewKey.setError(null);
        }
        if (password2.isEmpty() || password2.length() < 4 || password2.length() > 16){
            newKey.setError("between 4 and 16 alphanumeric characters");
            valid = false;
        }else {
            newKey.setError(null);
        }
         if (android.util.Patterns.EMAIL_ADDRESS.matcher(acount).matches() & password1.equals(password2)){
            StringRequest stringRequest = new StringRequest("http://api.zhaibao.cn/resetPwd?mobile="+userNumber.getText()+"code="+code.getText()+
                    "password="+newKey.getText()+"&version=1.0&deviceType=2",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                JSONObject jsonObject1 = new JSONObject(success);
                                String mgs = jsonObject1.getString("message");
                                int code = mgs.indexOf("code");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(ForgetActivity.this, "密码修改成功", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ForgetActivity.this, LoginActivity.class);
                            startActivity(intent);
                            Log.d("TAG", response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("TAG", error.getMessage(), error);
                }
            });
            mQueue.add(stringRequest);
        }else {
             Toast.makeText(ForgetActivity.this, "密码修改失败", Toast.LENGTH_LONG).show();
        }
        return valid;
    }
}
