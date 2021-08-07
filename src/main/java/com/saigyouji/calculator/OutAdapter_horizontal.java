package com.saigyouji.calculator;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OutAdapter_horizontal extends RecyclerView.Adapter<OutAdapter_horizontal.ViewHolder>
{
    private static final String TAG = "OutAdapter_horizontal";
    private List<Out> mOutList;
    private StringBuilder input;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView textView;
        public ViewHolder(View v)
        {
            super(v);
            textView = v.findViewById(R.id.textView);
        }

        public TextView getTextView() {
            return textView;
        }


    }
    public OutAdapter_horizontal(List<Out> OutList)
    {
        mOutList = OutList;
    }
    @NonNull
    @Override
    public OutAdapter_horizontal.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.out_frame, parent, false);
        return new ViewHolder(v);
    }

    public void onBindViewHolder(@NonNull @NotNull OutAdapter_horizontal.ViewHolder holder, int position)
    {
        Log.d(TAG, "onBindViewHolder: Element" + position + "set.");
        holder.getTextView().setText(mOutList.get(position).getOut());
    }
    @Override
    public int getItemCount()
    {
        return mOutList.size();
    }
}