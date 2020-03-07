package com.hashhash.android.androidsocket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button button_connect;
    EditText edit_ip;
    public static String TAG = "MainActivity:";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_connect = (Button)findViewById(R.id.button_connect);
        edit_ip = (EditText)findViewById(R.id.edit_ip);

        button_connect.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String address = edit_ip.getText().toString().trim();// trim blank space
        ConnectThread ct = new ConnectThread(address);

    }
    public class ConnectThread extends Thread {
        String target_addr;

        public ConnectThread(String addr) {
            /* initialize */
            target_addr = addr;
        }

        @Override
        public void run() {
            try {
                //super.run();
                int port = 5124;
                Socket sock = new Socket(target_addr, port);
                if(sock == null) {
                    Log.e(TAG, "socket is null");
                }
                ObjectOutputStream outstream = new ObjectOutputStream(sock.getOutputStream());
                outstream.writeObject("Hello Server!");
                outstream.flush();

                ObjectInputStream instream = new ObjectInputStream(sock.getInputStream());
                Object obj = instream.readObject();
                Log.e(TAG, "From Server = " + obj);

                sock.close();

            } catch(Exception io)
            {
                io.printStackTrace();
            }
        }
    }
}

