package com.lihaogn.lover;

import android.content.ComponentName;
import android.content.Intent;

import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;


public class MainActivity extends AppCompatActivity {

    // 任务实例
    private SocketClientTask mSocketClientTask;
    // 输出流，用于向服务端发信息
    static PrintWriter mPrintWriter;
    // 定义一个socket，客户端socket
    static Socket mClientSocket;

    // 连接成功标志
    static boolean linkedOkTAG=false;

    // 服务端IP地址
    private static final String SERVER_IP = "192.168.31.167";
    // 用户标志：用于识别是哪一个用户
     static final String USER_TAG2= "USER:wangyi";
     static final String USER_TAG1 = "USER:Lihao";

    static String userId;
    String password;


    private static final String TAG = "MainActivity";

    // 用于登录的用户帐号和密码信息
    private static final String USERNAME1 = "lihaogn";
    private static final String PASSWORD1="lihao1995";
    private static final String USERNAME2 = "wangyi";
    private static final String PASSWORD2="wangyi1995";

    // 输入帐号和密码
    private EditText userNameEditText;
    private EditText pwdEditText;
    // 登录错误提示信息
    private TextView errorTextView;

    //-------------------- 我的方法 ----------------------------------------------------------
    private void sendClientTAG() {
        // 首先传送客户端标志，用来识别客户
        if (userId.equals(USERNAME1)) {
            mPrintWriter.println(USER_TAG1);
        } else if (userId.equals(USERNAME2)) {
            mPrintWriter.println(USER_TAG2);
        }
        mPrintWriter.flush();
    }

    // 连接服务端
    void linkServer() {
        try {
            mClientSocket= new Socket(SERVER_IP, 52121);
            // --------------获取输出流，用于传信息给server------------------------------
            // os = socket.getOutputStream();
            if (mClientSocket.isConnected()) {
                linkedOkTAG=true;
                mPrintWriter = new PrintWriter(
                        new BufferedWriter(
                                new OutputStreamWriter(mClientSocket.getOutputStream(), "utf-8")));
                //传送客户端标志，用来识别客户
                sendClientTAG();

                // -------------客户端启动服务线程，不断读取来自服务器的数据------------------
                // 如果线程为空,则启动线程工作
                if (mSocketClientTask == null) {
                    // 新建实例并启动线程
                    mSocketClientTask = new SocketClientTask();
                    mSocketClientTask.execute(mClientSocket);
                }
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //------------------------------------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameEditText= (EditText) findViewById(R.id.userName_editxt);
        pwdEditText = (EditText) findViewById(R.id.pwd_editxt);
        errorTextView = (TextView) findViewById(R.id.Error_tv);
        Button loginButton = (Button) findViewById(R.id.login_btn);

        //----------- 登录按钮功能 ----------
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userId=userNameEditText.getText().toString();
                password=pwdEditText.getText().toString();
                // 认证 用户名与密码
                if ((userId.equals(USERNAME2) && password.equals(PASSWORD2))
                        ||(userId.equals(USERNAME1)&&password.equals(PASSWORD1))) {
                    // 与服务端连接
                    //----------- 进行socket连接 ------------------------------------------------------------------------------------------
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // 连接服务端
                            linkServer();

                        }
                    }).start();
                        // 转到聊天活动界面
                        Intent chatIntent = new Intent(MainActivity.this, ChatWindowActivity.class);
                        startActivity(chatIntent);

                    //------------------------------------------------------------------------------
                } else {
                    errorTextView.setVisibility(View.VISIBLE);
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
                    errorTextView.setVisibility(View.GONE);
                }

            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: is working");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: working");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: working");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: working");
        // 结束主页面
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: working");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: is working......");
    }
}
