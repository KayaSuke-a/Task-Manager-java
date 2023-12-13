package com.example.bie_daalt_02v;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditNoteActivity extends AppCompatActivity {
    private static final String TAG = "EditNoteActivity";

    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";

    private EditText editTextTitle;
    private EditText editTextDescription;
    private FirebaseFirestore db;
    private Button btnSaveChanges;
    private String noteId; // Assuming you pass the note ID to this activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // Initialize Firebase
        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this);
        }

        // Initialize FirebaseFirestore
        db = FirebaseFirestore.getInstance();

        // Assuming you pass the note ID to this activity
        noteId = getIntent().getStringExtra("NOTE_ID");

        editTextTitle = findViewById(R.id.updateTitle);
        editTextDescription = findViewById(R.id.updateDesc);
        btnSaveChanges = findViewById(R.id.updateButton);

        // Retrieve existing note data and set it in EditText fields for editing
        // (You need to implement this part based on your app logic)

        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateNote();
            }
        });
    }

    private void updateNote() {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please enter both title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> updatedNote = new HashMap<>();
        updatedNote.put(KEY_TITLE, title);
        updatedNote.put(KEY_DESCRIPTION, description);

        // Update the note in Firestore
        db.collection("Notebook")
                .document(noteId)
                .update(updatedNote)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditNoteActivity.this, "Note updated successfully", Toast.LENGTH_SHORT).show();
                        finish(); // Close the activity after successful update
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditNoteActivity.this, "Error updating note", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
