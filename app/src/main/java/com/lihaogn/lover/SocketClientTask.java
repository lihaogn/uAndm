package com.lihaogn.lover;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by lihaogn on 2017/2/23.
 */

public class SocketClientTask extends AsyncTask<Socket,String,Boolean> {

    // 该线程所处理的Socket所对应的输入流
    BufferedReader br = null;

    @Override
    protected void onPreExecute() {

    }

    // 后台运行程序
    @Override
    protected Boolean doInBackground(Socket... params) {

        // 获取传进来的参数
        Socket socket = params[0];
        // 聊天内容文本
        String content = null;

        try {
            // 获得server传来的数据流
            br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
            // 不断读取Socket输入流的内容，接收sever传来的文本
            while ((content = br.readLine()) != null) {
                // 返回文本数据
                publishProgress(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        // 在这里可以更新ui上的信息
        String content = values[0];
        ChatWindowActivity.showMsgTextView.append("\n"+content);
    }
}
