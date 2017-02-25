package com.lihaogn.lover;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by lihaogn on 2017/2/21.
 */

public class ChatWindowActivity extends AppCompatActivity {

    private static final String TAG = "ChatWindowActivity";
    // 输入聊天信息
    EditText inputMsgEditput;
    // 显示 聊天信息
    static TextView showMsgTextView;
    // 发送按钮
    Button sendMsgButton;
    // 滑动菜单
    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatroom_layout);
        Log.d(TAG, "onCreate: is working");

        inputMsgEditput = (EditText) findViewById(R.id.inputmsg_editxt);
        sendMsgButton = (Button) findViewById(R.id.sendmsg_btn);
        showMsgTextView = (TextView) findViewById(R.id.msgshow_textView);
        // 标题工具栏
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        // 滑动菜单
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // 获取actionbar实例，设置导航按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_start);
        }
        // 侧边栏菜单
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_relogin:
                        Intent intent = new Intent(ChatWindowActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.nav_quit:
                        finish();
                        break;
                    default:
                        mDrawerLayout.closeDrawers();
                }
                return true;
            }
        });
        //----------- ----------------------------------------------------------------------------------------------------------------
        // 发送按钮的监听事件
        sendMsgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = inputMsgEditput.getText().toString();
                // 如果输入不为空
                if (msg != null) {
                    // 清空input文本框数据
                    inputMsgEditput.setText("");
                    // 向服务端发送 聊天信息
                    try {
                        // 将用户在文本框内输入的内容写入网络
                        //os.write((input.getText().toString() + "\r\n").getBytes());
                        MainActivity.mPrintWriter.println(msg);
                        MainActivity.mPrintWriter.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy: is working----------------------------------------------------------------------------");
        // 发送关闭次client的socket
        MainActivity.mPrintWriter.println("exit this chat");
        MainActivity.mPrintWriter.flush();
        // 清空input文本框数据
        inputMsgEditput.setText("");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // 点击到滑动菜单导航按钮时，展示菜单
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }
}
