package com.example.roger.oving6;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import android.util.Log;

class Client extends Thread {
    private final static String TAG = "ServerThread";
    private static int port = 12345;
    private int a, b;
    private String ip;
    private Socket connection;
    private ObjectOutputStream objectOutputStream;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;
    Callback cb;


    Client(int a, int b, Callback cb) {
        this.a = a;
        this.b = b;
        this.ip = "10.0.0.2";
        this.cb = cb;
    }

    public void run() {

        try{
            connection = new Socket(ip, port);
            objectOutputStream = new ObjectOutputStream(connection.getOutputStream());
            inputStreamReader = new InputStreamReader(connection.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            Expression exp = new Expression(a, b, Operand.ADD);
            objectOutputStream.writeObject(exp);
            String response = bufferedReader.readLine();
            cb.callback(response);

        } catch (IOException e) {
            Log.e(TAG, "ERROR:" + e);
            e.printStackTrace();

        }finally{
            Log.i(TAG, "closing connection");
            try{
                bufferedReader.close();
                inputStreamReader.close();
                objectOutputStream.close();
                connection.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}