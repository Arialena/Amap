package com.example.administrator.amp.setting_personal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.administrator.amp.R;
import com.example.administrator.amp.register_login.LoginActivity;

/**
 * Created by Administrator on 2017/1/5.
 * 账号用户管理
 */

public class Accounts extends Activity {
    private ImageButton backBtn;
    private Button head,//图像
                    nickname,//昵称
                    pwdChange,//修改密码
                    logOut;//退出登录
    PopupWindow mPopupWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accounts_cc);

        backBtn = (ImageButton) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Accounts.this, PersonCenter.class);
                startActivity(intent);
                finish();
            }
        });

        logOut = (Button) findViewById(R.id.exit_login);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customView();
            }
        });
    }

    private void customView() {
        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int yOffset = frame.top;
        if (null == mPopupWindow) {
            //初始化PopupWindow的布局
            View popView = getLayoutInflater().inflate(R.layout.custom_main, null);
            //popView即popupWindow的布局，ture设置focusAble.
            mPopupWindow = new PopupWindow(popView,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, true);
            //必须设置BackgroundDrawable后setOutsideTouchable(true)才会有效
            mPopupWindow.setBackgroundDrawable(new ColorDrawable());
            //点击外部关闭。
            mPopupWindow.setOutsideTouchable(true);
            //设置一个动画。
            mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
            //设置Gravity，让它显示在中间靠底部。
            mPopupWindow.showAtLocation(backBtn, Gravity.CENTER | Gravity.BOTTOM, 0, yOffset);
            popView.findViewById(R.id.customChange).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 Intent intent = new Intent(Accounts.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
            popView.findViewById(R.id.customExit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 finish();
                }
            });
            popView.findViewById(R.id.customCancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Accounts.this, "点击取消", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            mPopupWindow.showAtLocation(backBtn, Gravity.CENTER | Gravity.BOTTOM, 0, yOffset);
        }
    }
}
