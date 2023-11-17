package com.example.librarymanagement;

import android.app.AlertDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class booksFragment extends Fragment {

    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private List<Book> bookList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(requireContext());
        databaseReference = FirebaseDatabase.getInstance().getReference("books");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_books, container, false);
        recyclerView = rootView.findViewById(R.id.recycler_books);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        bookList = new ArrayList<>();
        bookAdapter = new BookAdapter(bookList);
        recyclerView.setAdapter(bookAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Book book = snapshot.getValue(Book.class);
                    if (book != null) {
                        bookList.add(book);
                    }
                }
                bookAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });

        FloatingActionButton fabAddBook = rootView.findViewById(R.id.fab_add_book);
        fabAddBook.setOnClickListener(view -> showAddBookDialog());

        return rootView;
    }

    private void showAddBookDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add Book");

        View viewInflated = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_book, null);

        final EditText inputBookName = viewInflated.findViewById(R.id.input_book_name);
        final EditText inputQuantity = viewInflated.findViewById(R.id.input_quantity);

        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
            String bookName = inputBookName.getText().toString();
            int quantity = Integer.parseInt(inputQuantity.getText().toString());

            DatabaseReference newBookRef = databaseReference.push(); // Generate unique key
            String bookId = newBookRef.getKey();

            Book newBook = new Book(bookId,bookName, quantity);

            databaseReference.push().setValue(newBook)
                    .addOnSuccessListener(aVoid -> Toast.makeText(requireContext(), "Book added successfully", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(requireContext(), "Failed to add book", Toast.LENGTH_SHORT).show());
        });

        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.cancel());

        builder.show();
    }

    public booksFragment() {
        // Required empty public constructor
    }

    public static booksFragment newInstance(String param1, String param2) {
        booksFragment fragment = new booksFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
