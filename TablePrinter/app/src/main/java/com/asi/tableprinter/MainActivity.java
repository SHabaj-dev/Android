package com.asi.tableprinter;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button Table;
    EditText et;
    TextView tv;
    int num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Table = findViewById(R.id.button);
        et = findViewById(R.id.editText);
        tv = findViewById(R.id.textView);

        String str = et.getText().toString();

        if(!"".equals(str)){
            num = Integer.parseInt(str);
        }

        Table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 1; i <= 10; i++){
                    int pro = num * i;
                    String s = num + "x" + i + "=" + pro;
                    tv.setText(s);

                }
            }
        });

    }
}