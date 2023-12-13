package com.example.bie_daalt_02v;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private String noteId;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        db = FirebaseFirestore.getInstance();

        // Retrieve data from Intent
        String title = getIntent().getStringExtra("Title");
        String description = getIntent().getStringExtra("Description");
        noteId = getIntent().getStringExtra("NoteId");

        // Set data to the views
        TextView detailTitle = findViewById(R.id.detailTitle);
        TextView detailDesc = findViewById(R.id.detailDesc);

        detailTitle.setText(title);
        detailDesc.setText(description);

        // Button click listeners
        Button editButton = findViewById(R.id.editButton);
        Button deleteButton = findViewById(R.id.deleteButton);


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle edit button click
                Toast.makeText(DetailActivity.this, "Edit button clicked", Toast.LENGTH_SHORT).show();
                // Add your edit action logic here
                Intent intent = new Intent(DetailActivity.this,EditNoteActivity.class);
                startActivities(new Intent[]{intent});
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle delete button click
                deleteNote();
            }
        });
    }
    private void deleteNote() {
        // Delete the note from Firestore
        db.collection("Notebook")
                .document(noteId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(DetailActivity.this, "Note deleted successfully", Toast.LENGTH_SHORT).show();
                        finish(); // Close the activity after successful deletion
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(DetailActivity.this, "Error deleting note", Toast.LENGTH_SHORT).show());
    }
}
