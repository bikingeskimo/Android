package com.example.roger.oving5;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {
    private final HttpWrapper httpWrapper = new HttpWrapper();
    private final String url = "http://tomcat.stud.aitel.hist.no/studtomas/tallspill.jsp";

    private ConnectionThread thread;

    private TextView response;
    private EditText name, creditcard, number;
    private Button send, newGame;

    private Map<String, String> urlParams;
    private final String nameID = "navn";
    private final String kortNR = "kortnummer";
    private final String tall = "tall";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        urlParams = new HashMap<>();
        response = (TextView)findViewById(R.id.response_view);

        name = (EditText)findViewById(R.id.name);
        creditcard = (EditText)findViewById(R.id.creditCard);
        number = (EditText)findViewById(R.id.numberInput);
        number.setEnabled(false);

        send = (Button)findViewById(R.id.btn_send);
        newGame = (Button)findViewById(R.id.btn_newgame);
        newGame.setEnabled(false);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!urlParams.isEmpty())urlParams.clear();

                if(name.isEnabled()) {
                    urlParams.put(nameID, name.getText().toString());
                    urlParams.put(kortNR, creditcard.getText().toString());
                    name.setEnabled(false);
                    creditcard.setEnabled(false);
                    number.setEnabled(true);
                    newGame.setEnabled(true);

                }else if(!name.isEnabled()) {
                    urlParams.put(tall, number.getText().toString());
                }

                thread = new ConnectionThread();
                thread.execute(url, urlParams);

            }
        });

        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!urlParams.isEmpty())urlParams.clear();

                name.setEnabled(true);
                creditcard.setEnabled(true);
                number.setEnabled(false);
                response.setText("");
            }
        });

    }

    private class ConnectionThread extends AsyncTask<Object, String, Boolean>{
        String body = "";
        public ConnectionThread() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        @SuppressWarnings("unchecked")
        protected Boolean doInBackground(Object... objects) {
            boolean result = false;
            try{
                body = MainActivity.this.httpWrapper.httpGet((String)objects[0], (Map)objects[1]);
            }catch (IOException e){

            }
            if(!this.isCancelled()) publishProgress(body);
            return result;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            response.setText(body);
            number.setText("");
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onCancelled(Boolean aBoolean) {
            super.onCancelled(aBoolean);
        }
    }
}