package com.lihaogn.lover;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by lihaogn on 2017/2/21.
 */

public class ChatWindowActivity extends AppCompatActivity {

    EditText inputMsgEditput;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatroom_layout);

        inputMsgEditput = (EditText) findViewById(R.id.inputmsg_editxt);
        Button sendMsgButton = (Button) findViewById(R.id.sendmsg_btn);

        // 发送按钮的监听事件
        sendMsgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 如果输入不为空
                if (inputMsgEditput.getText().toString() != null) {
                    String msg = inputMsgEditput.getText().toString();
                    inputMsgEditput.setText("");
                    Toast.makeText(ChatWindowActivity.this,"msg:"+msg,Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
