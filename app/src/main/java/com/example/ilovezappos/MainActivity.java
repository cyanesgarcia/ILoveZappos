package com.example.ilovezappos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button bFirst, bSecond, bThird;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bFirst= (Button) findViewById(R.id.bFirst);
        bFirst.setOnClickListener(this);

        bSecond= (Button) findViewById(R.id.bSecond);
        bSecond.setOnClickListener(this);

        bThird= (Button) findViewById(R.id.bThird);
        bThird.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        if(view.getId()== R.id.bFirst){
            intent= new Intent(this, FirstActivity.class);
        }else if(view.getId()== R.id.bSecond){
            intent= new Intent(this, SecondActivity.class);
        }else{
            intent= new Intent(this, ThirdActivity.class);
        }
        startActivity(intent);
    }
}
