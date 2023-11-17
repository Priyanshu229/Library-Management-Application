package com.example.librarymanagement;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class BookViewHolder extends RecyclerView.ViewHolder {
    public TextView textBookName, textBookQuantity;

    public BookViewHolder(View itemView) {
        super(itemView);
        textBookName = itemView.findViewById(R.id.text_book_name);
        textBookQuantity = itemView.findViewById(R.id.text_book_quantity);
    }
}
