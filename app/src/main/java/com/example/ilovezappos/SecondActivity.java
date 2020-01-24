package com.example.ilovezappos;


import android.app.Activity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SecondActivity extends Activity {
    private RequestQueue requestQueue;
    private RecyclerView mRecyclerView1, mRecyclerView2;
    private ArrayList<Second_Item> first_list,second_list;
    private Second_Adapter adapter1, adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mRecyclerView1 = findViewById(R.id.recycler_view1);
        mRecyclerView1.setHasFixedSize(true);
        mRecyclerView1.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView2 = findViewById(R.id.recycler_view2);
        mRecyclerView2.setHasFixedSize(true);
        mRecyclerView2.setLayoutManager(new LinearLayoutManager(this));

        first_list = new ArrayList<>();
        second_list = new ArrayList<>();


        requestQueue = Volley.newRequestQueue(this);
        parseJSON();
    }

    private void parseJSON() {
        String url = "https://www.bitstamp.net/api/v2/order_book/btcusd/";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray1 = response.getJSONArray("bids");
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                String bid=jsonArray1.getString(i);

                                first_list.add(new Second_Item( bid));
                            }

                            JSONArray jsonArray2 = response.getJSONArray("asks");
                            for (int i = 0; i < jsonArray2.length(); i++) {
                                String asks=jsonArray2.getString(i);

                                second_list.add(new Second_Item( asks));
                            }


                            adapter1 = new Second_Adapter(SecondActivity.this, first_list);
                            mRecyclerView1.setAdapter(adapter1);

                            adapter2 = new Second_Adapter(SecondActivity.this, second_list);
                            mRecyclerView2.setAdapter(adapter2);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(request);
    }
}
