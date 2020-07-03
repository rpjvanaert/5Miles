package com.example.failurism.QuoteManagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.failurism.R;

import java.util.List;

public class QuoteAdapter extends RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder>{

    private Context appContext;
    private List<String> quoteList;

    public QuoteAdapter(Context appContext, List<String> quoteList){
        this.appContext = appContext;
        this.quoteList = quoteList;
    }

    @NonNull
    @Override
    public QuoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.quote_item, parent, false);
        QuoteViewHolder viewHolder = new QuoteViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuoteViewHolder holder, int position) {
        String quote = quoteList.get(position);
        holder.textView.setText(quote);
    }

    @Override
    public int getItemCount() {
        return quoteList.size();
    }

    public class QuoteViewHolder extends RecyclerView.ViewHolder {
        public final TextView textView;

        public QuoteViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.quoteTV);
        }
    }
}
