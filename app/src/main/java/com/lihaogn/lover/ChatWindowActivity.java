package com.lihaogn.lover;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
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
    Button sendMsgButton ;


    @Override
    protected void onCreate( Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatroom_layout);
        Log.d(TAG, "onCreate: is working");

        inputMsgEditput = (EditText) findViewById(R.id.inputmsg_editxt);
        sendMsgButton = (Button) findViewById(R.id.sendmsg_btn);
        showMsgTextView = (TextView) findViewById(R.id.msgshow_textView);

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

        // 发送关闭次client的socket
        MainActivity.mPrintWriter.println("exit this chat");
        MainActivity.mPrintWriter.flush();
        // 清空input文本框数据
        inputMsgEditput.setText("");

    }



}
