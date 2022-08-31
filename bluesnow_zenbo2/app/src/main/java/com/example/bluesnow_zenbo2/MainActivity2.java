package com.example.bluesnow_zenbo2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.asus.robotframework.API.RobotCallback;
import com.asus.robotframework.API.RobotCmdState;
import com.asus.robotframework.API.RobotCommand;
import com.asus.robotframework.API.RobotErrorCode;
import com.asus.robotframework.API.RobotFace;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends RobotActivity {
    TextView textt;
    TextView text;
    String ip,port;
    Socket socket;
    String JSONout = "";
    //String text="fuck";
    Button face,front,right,left,stop,back;

    public MainActivity2(RobotCallback robotCallback, RobotCallback.Listen robotListenCallback) {
        super(robotCallback, robotListenCallback);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initViewElement();
        //robotAPI.robot.setExpression(RobotFace.DOUBTING);
        Robot_Action robot_action = new Robot_Action();
        robot_action.setact("");
        robot_action.setmsg("");
        // get data from MainActivity
        Intent it = this.getIntent();
        if(it != null){
            Bundle bundle = it.getExtras();
            ip = bundle.getString("ip");
            port = bundle.getString("port");
        }
        connect();

        face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(socket.isConnected()){
                    //text.setText("yes");
                    robot_action.setact("face");
                    JSONout = new JSONObject(robot_action.Mapping()).toString();
                    Robot_Write(JSONout);
                }
                else{
                    Log.d("sssss","connect fail");
                }
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(socket.isConnected()){
                    //text.setText("yes");
                    robot_action.setact("stop");
                    JSONout = new JSONObject(robot_action.Mapping()).toString();
                    Robot_Write(JSONout);
                }
                else{
                    Log.d("sssss","connect fail");
                }
            }
        });
        front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(socket.isConnected()){
                    //text.setText("yes");
                    robot_action.setact("front");
                    JSONout = new JSONObject(robot_action.Mapping()).toString();
                    Robot_Write(JSONout);
                }
                else{
                    Log.d("sssss","connect fail");
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(socket.isConnected()){
                    //text.setText("yes");
                    robot_action.setact("back");
                    JSONout = new JSONObject(robot_action.Mapping()).toString();
                    Robot_Write(JSONout);
                }
                else{
                    Log.d("sssss","connect fail");
                }
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(socket.isConnected()){
                    //text.setText("yes");
                    robot_action.setact("right");
                    JSONout = new JSONObject(robot_action.Mapping()).toString();
                    Robot_Write(JSONout);
                }
                else{
                    Log.d("sssss","connect fail");
                }
            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(socket.isConnected()){
                    //text.setText("yes");
                    robot_action.setact("left");
                    JSONout = new JSONObject(robot_action.Mapping()).toString();
                    Robot_Write(JSONout);
                }
                else{
                    Log.d("sssss","connect fail");
                }
            }
        });
    }
    public void connect()
    {
        Runnable runnable1=new Runnable() {
            @Override
            public void run() {
                try{
                    socket = new Socket(ip, Integer.parseInt(port));
                }
                catch ( IOException e)
                {
                    e.printStackTrace();
                }
            }
        };
        Thread thread1 = new Thread(runnable1);
        thread1.start();
    }

    void Robot_Write(String string){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    //text.setText(string);
                    BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    writer.write(string);
                    writer.newLine();
                    writer.flush();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void initViewElement(){
        face = (Button) findViewById(R.id.face);
        front = (Button) findViewById(R.id.front);
        back = (Button) findViewById(R.id.back);
        right = (Button) findViewById(R.id.right);
        left = (Button) findViewById(R.id.left);
        stop = (Button) findViewById(R.id.stop);
        text = (TextView) findViewById(R.id.text4);
    }


    @Override
    protected void onPause() {
        super.onPause();

        //mCountDownTimer.cancel();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {

        if (hasFocus) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            );
        }
    }



    public static RobotCallback robotCallback = new RobotCallback() {
        @Override
        public void onResult(int cmd, int serial, RobotErrorCode err_code, Bundle result) {
            super.onResult(cmd, serial, err_code, result);

            Log.d("RobotDevSample", "onResult:"
                    + RobotCommand.getRobotCommand(cmd).name()
                    + ", serial:" + serial + ", err_code:" + err_code
                    + ", result:" + result.getString("RESULT"));
        }

        @Override
        public void onStateChange(int cmd, int serial, RobotErrorCode err_code, RobotCmdState state) {
            super.onStateChange(cmd, serial, err_code, state);
        }

        @Override
        public void initComplete() {
            super.initComplete();

        }
    };

    public static RobotCallback.Listen robotListenCallback = new RobotCallback.Listen() {
        @Override
        public void onFinishRegister() {

        }

        @Override
        public void onVoiceDetect(JSONObject jsonObject) {

        }

        @Override
        public void onSpeakComplete(String s, String s1) {

        }

        @Override
        public void onEventUserUtterance(JSONObject jsonObject) {

        }

        @Override
        public void onResult(JSONObject jsonObject) {

        }

        @Override
        public void onRetry(JSONObject jsonObject) {

        }
    };
    public MainActivity2() {
        super(robotCallback, robotListenCallback);
    }

}

class Robot_Action
{
    String act,msg;
    public Robot_Action(){
        super();
    }

    public Robot_Action(String act, String msg)
    {
        super();
        this.act=act;
        this.msg=msg;
    }
    public void setact(String act)
    {
        this.act=act;
    }
    public  void setmsg(String msg)
    {
        this.msg=msg;
    }
    public Map Mapping(){
        Map<String,String> map = new HashMap<String,String>();
        map.put("act",act);
        map.put("msg",msg);
        return map;
    }

}
