package com.example.roger.oving6;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements Callback {
    Button btn_client, btn_send;
    TextView answer, status, pluss, equals;
    NumberPicker number1;
    NumberPicker number2;
    int a, b;

    //The client requires that the server is already running

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        number1 = (NumberPicker) findViewById(R.id.number1);
        number1.setMinValue(0);
        number1.setMaxValue(10);
        number2 = (NumberPicker) findViewById(R.id.number2);
        number2.setMinValue(0);
        number2.setMaxValue(10);


        answer = (TextView)findViewById(R.id.answer);
        btn_client = (Button)findViewById(R.id.btn_client);
        btn_send = (Button)findViewById(R.id.btn_send);
        pluss = (TextView) findViewById(R.id.textView);
        equals = (TextView) findViewById(R.id.textView2);
        status = (TextView) findViewById(R.id.status);

        number1.setVisibility(View.INVISIBLE);
        number2.setVisibility(View.INVISIBLE);
        btn_send.setVisibility(View.INVISIBLE);
        answer.setVisibility(View.INVISIBLE);
        pluss.setVisibility(View.INVISIBLE);
        equals.setVisibility(View.INVISIBLE);

        /*
        number1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker p, int oldVal, int newVal){
                a = newVal;
            }
        });
        */
    }


    public void onStartClick(View view){
        String statustxt = "This is the client";
        status.setText(statustxt);

        number1.setVisibility(View.VISIBLE);
        number2.setVisibility(View.VISIBLE);
        btn_send.setVisibility(View.VISIBLE);
        btn_send.setEnabled(true);
        pluss.setVisibility(View.VISIBLE);
        equals.setVisibility(View.VISIBLE);
        answer.setVisibility(View.VISIBLE);
    }

    public void onSendClick(View view){
        try {
            a = number1.getValue();
            b = number2.getValue();

        } catch (Exception e) {
            Toast.makeText(this, "Choose two numbers", Toast.LENGTH_LONG).show();
            return;
        }
        // start new thread
        new Client(a, b, this).start();
    }

    @Override
    public void callback(String result) {answer.setText(result);}
}
