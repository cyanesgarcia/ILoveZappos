package com.example.ilovezappos;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Trigger;

import java.util.ArrayList;

public class ThirdActivity extends Activity implements View.OnClickListener {
    Bundle bundle;
    FirebaseJobDispatcher jobDispatcher;
    ArrayList<String> array = new ArrayList<String>();
    EditText editText;
    ListView listView;
    Button button;


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        editText = (EditText) findViewById(R.id.edittext);
        listView = (ListView) findViewById(R.id.listview);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            String value =" ";
            Double number= 0.0;
            String getInput = editText.getText().toString();

            if(getInput == null || getInput.trim().equals("")) {
                Toast.makeText(getBaseContext(), "Edit Text is empty", Toast.LENGTH_LONG).show();
            } else {
                array.add(0, getInput);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ThirdActivity.this, android.R.layout.simple_list_item_1, array);
                listView.setAdapter(adapter);
                value = (String) listView.getItemAtPosition(0);
                number = Double.parseDouble(value);
                ((EditText) findViewById(R.id.edittext)).setText(" ");
            }


            bundle = new Bundle();
            bundle.putDouble("number", number);

            jobDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(ThirdActivity.this));
            jobDispatcher.mustSchedule(
                    jobDispatcher.newJobBuilder()
                            .setService(TMyService.class)
                            .setExtras(bundle)
                            .setTag("DeviceSpaceService")
                            .setRecurring(true)
                            .setReplaceCurrent(true)
                            .setTrigger(Trigger.executionWindow(3600, 3610))
                            .build()
            );

        }
    }
}
