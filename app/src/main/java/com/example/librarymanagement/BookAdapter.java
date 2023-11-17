package com.example.librarymanagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookViewHolder> {
    private List<Book> bookList; // Assuming Book is your model class

    public BookAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.textBookName.setText(book.getName());
        holder.textBookQuantity.setText(String.valueOf(book.getQuantity()));
        // Set other data or listeners if needed
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }
}
