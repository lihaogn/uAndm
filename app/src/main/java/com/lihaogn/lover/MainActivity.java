package com.lihaogn.lover;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity{

    // 用于登录的用户帐号和密码信息
    private static final String USERNAME = "lihaogn";
    private static final String PASSWORD="lihao1995";
    private static final String USERNAME1 = "wangyi";
    private static final String PASSWORD1="wangyi1995";

    // 输入帐号和密码
    EditText userNameEditText;
    EditText pwdEditText;
    // 登录错误提示信息
    TextView errorTextView;
    // 显示 聊天信息
    TextView showMsgTextView;

    // 网络部分-----------------------------------------------------------------------------
    // 输出流，用于向服务端发信息
    private PrintWriter mPrintWriter;
    // 服务端IP地址
    private static final String SERVER_IP = "192.168.31.167";
    // 用户标志：用于识别是哪一个用户
    private static final String USER_TAG1= "USER:wangyi";
    private static final String USER_TAG2 = "USER:Lihao";
    // -----------------------------------------------------------------------------网络部分
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameEditText= (EditText) findViewById(R.id.userName_editxt);
        pwdEditText = (EditText) findViewById(R.id.pwd_editxt);
        errorTextView = (TextView) findViewById(R.id.Error_tv);
        Button loginButton = (Button) findViewById(R.id.login_btn);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userNameEditText.getText().toString().equals(USERNAME) && pwdEditText.getText().toString().equals(PASSWORD)) {
                    // 跳转 登录
                    //Toast.makeText(MainActivity.this,"登录成功！",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, ChatWindowActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    errorTextView.setText("帐号或密码输入错误，请重新输入！");
                    userNameEditText.setText("");
                    pwdEditText.setText("");
                }
            }
        });

        userNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (errorTextView.getText().toString() != null) {
                    errorTextView.setText("");
                }

            }
        });
    }

}
