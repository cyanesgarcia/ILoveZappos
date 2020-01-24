package com.example.ilovezappos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Second_Adapter extends RecyclerView.Adapter<Second_Adapter.ExampleViewHolder> {
    private Context context;
    private ArrayList<Second_Item> arrayList;

    public Second_Adapter(Context a_context, ArrayList<Second_Item> a_arrayList) {
        context = a_context;
        arrayList = a_arrayList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.sactivity_item, parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        Second_Item currentItem = arrayList.get(position);

        String segundo = currentItem.getmSegundo();

        holder.textView.setText(segundo);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ExampleViewHolder(View view) {
            super(view);
            textView = itemView.findViewById(R.id.text_view_creator);
        }
    }
}
