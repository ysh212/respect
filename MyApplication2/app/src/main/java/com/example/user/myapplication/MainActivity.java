package com.example.user.myapplication;

import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity{
    private String html = "";
    private Handler mHandler;

    //TextView tv = null;
    //EditText editText = null;

    private Socket socket;

    private BufferedReader networkReader;
    private BufferedWriter networkWriter;

    private String ip = "172.30.1.7"; // IP
    //InetAddress ip = InetAddress.getByName(SocketServer.ServerIP);
    private int port = 9000; // PORT번호

    @Override
    protected void onStop() {
        super.onStop();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        MainThread thread = new MainThread();
        thread.setDaemon(true);
        thread.start();

        /*
        try {
            setSocket(ip, port);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        */

        checkUpdate.start();

        final EditText et = (EditText) findViewById(R.id.EditText01);
        Button btn = (Button) findViewById(R.id.Button01);
        final TextView tv = (TextView) findViewById(R.id.TextView01);
        //editText = (EditText)findViewById(R.id.EditText02);

        //버튼을 누르면, EditText에 적힌 문구가 서버로 전송
        btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (et.getText().toString() != null || !et.getText().toString().equals("")) {
                    PrintWriter out = new PrintWriter(networkWriter, true);
                    String return_msg = et.getText().toString();
                    out.println(return_msg);
                }
            }
        });
    }

    //서버에서 오는 데이터를 계속 받음
    private Thread checkUpdate = new Thread() {

        public void run() {
            try {
                String line;
                Log.w("ChattingStart", "Start Thread");
                while (true) {
                    Log.w("Chatting is running", "chatting is running");
                    line = networkReader.readLine();
                    Log.d(this.getClass().getName(), line);
                    html = line;
                    //editText.setText(html);
                    mHandler.post(showUpdate);
                }
            } catch (Exception e) {

            }
        }
    };

    //받은 데이터를 Toast 기능을 통해 출력
    private Runnable showUpdate = new Runnable() {

        public void run() {
            Toast.makeText(MainActivity.this, "Coming word: " + html, Toast.LENGTH_SHORT).show();
        }

    };

    public void setSocket(String ip, int port) throws IOException {

        try {
            socket = new Socket(ip, port);
            networkWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            networkReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }

    }

    class MainThread extends Thread{
        public void run(){
            try{
                setSocket(ip, port);
            }catch (IOException e1){
                e1.printStackTrace();
            }
        }
    }

}
