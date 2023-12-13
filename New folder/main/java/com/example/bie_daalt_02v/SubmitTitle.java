package com.example.bie_daalt_02v;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class SubmitTitle extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";

    private EditText editTextTitle;
    private EditText editTextDescription;
    private FirebaseFirestore db;
    Button btnBack , btnReset;
    String userName;
    TextView textViewUserName;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase
        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this);
        }

        // Initialize FirebaseFirestore
        db = FirebaseFirestore.getInstance();

        userName = getUserNameFromPreferences();

        // Find the TextView in your layout
        textViewUserName = findViewById(R.id.textViewUserName);

        // Set the text of the TextView with the retrieved user's name
        textViewUserName.setText(userName);


        btnBack = findViewById(R.id.btnBack);
        btnReset = findViewById(R.id.btnReset);
        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);

        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(SubmitTitle.this,Menu.class);
                startActivities(new Intent[]{intent});
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetFields();
            }
        });
    }
    private String getUserNameFromPreferences() {
        // Retrieve the name from SharedPreferences
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return preferences.getString("userName", "");
    }
    private void resetFields() {
        // Clear the text in both the title and description EditTexts
        editTextTitle.setText("");
        editTextDescription.setText("");
        Toast.makeText(this, "Fields reset", Toast.LENGTH_SHORT).show();
    }

    public void saveNote(View v) {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please enter both title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> note = new HashMap<>();
        note.put(KEY_TITLE, title);
        note.put(KEY_DESCRIPTION, description);

        db.collection("Notebook")
                .add(note)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(SubmitTitle.this, "Note saved with ID: " + documentReference.getId(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SubmitTitle.this, DetailActivity.class);
                        intent.putExtra("Title", title);
                        intent.putExtra("Description", description);
                        intent.putExtra("NoteId", documentReference.getId());
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SubmitTitle.this, "Error saving note", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Error saving note", e);
                    }
                });
    }
}