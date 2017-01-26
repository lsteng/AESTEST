package com.aes.aestest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private EditText mET;
    private TextView eTV, dTV;
    private Button eBTN, dBTN;
    private Spinner spinner;
    public static int pos = 0;

    public final static String IvAES = "&xA&1Id1NMpqj6YZ" ; //加密用密碼
    public final static String KeyAES = "GXNLjhZ4yx9ZzcDQ"; //輸出偏移量
    private final static String TextAES = "文字內容AES加密測試"; //加密內容

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mET = (EditText) findViewById(R.id.ET);
        eTV = (TextView) findViewById(R.id.eTV);
        dTV = (TextView) findViewById(R.id.dTV);
        eBTN = (Button) findViewById(R.id.eBTN);
        dBTN = (Button) findViewById(R.id.dBTN);
        spinner = (Spinner)findViewById(R.id.spinner);

        String[] keys = {"新聞 AES key", "最強音 AES key"};
        ArrayAdapter<String> keyList = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, keys);
        spinner.setAdapter(keyList);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                pos = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {}
        });

        secret(TextAES);

        eBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secret(mET.getText().toString());
            }
        });
        dBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solve(mET.getText().toString());
            }
        });
    }

    private void secret(String s){
        try {
            eTV.setText(new AESCrypt().encrypt(s));
            dTV.setText(new AESCrypt().decrypt(eTV.getText().toString()));
        } catch (Exception e) {
            eTV.setText(e.toString());
        }
    }

    private void solve(String s){
        try {
            dTV.setText(new AESCrypt().decrypt(s));
            eTV.setText(new AESCrypt().encrypt(dTV.getText().toString()));
        } catch (Exception e) {
            dTV.setText(e.toString());
        }
    }
}
