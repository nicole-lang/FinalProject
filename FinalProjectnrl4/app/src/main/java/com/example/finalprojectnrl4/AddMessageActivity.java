package com.example.finalprojectnrl4;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddMessageActivity extends AppCompatActivity {
    private EditText msgInput;
    private Button btnConfirm;
    private String selectedTag;
    private ArrayList<String> enabledTags;
    private RadioGroup selectTag;
    private ArrayList<Message> messageList;
    private int buddyColor;
    private int buddyFace;
    Intent intent;
    private static final String TAG = "AddMessageActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmessage);
        selectedTag = "General";
        intent = getIntent();
        messageList = intent.getParcelableArrayListExtra("messages");
        enabledTags = intent.getStringArrayListExtra("enabledTags");
        buddyColor = intent.getIntExtra("color", 1);
        buddyFace = intent.getIntExtra("face", 2);
        //Get references to views
        msgInput = findViewById(R.id.msgInput);
        btnConfirm = findViewById(R.id.btnConfirm);
        selectTag = findViewById(R.id.selectTag);
        selectTag.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId)
                {
                    case R.id.btnGeneral:
                        selectedTag = "General";
                    case R.id.btnEncouragement:
                        selectedTag = "Encouragement";
                    case R.id.btnReminder:
                        selectedTag = "Reminder";
                }
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putStringArrayListExtra("enabledTags", enabledTags);
                intent.putExtra("color", buddyColor);
                intent.putExtra("face", buddyFace);

                Log.d(TAG, "onClick: message is '" + msgInput.getText().toString() + "'");
                Log.d(TAG, "onClick: selected tag is '" + selectedTag + "'");
                if (msgInput.getText().toString().length() > 0) {
                    //If the user wrote a message then add it to the list

                    intent.putExtra("message", msgInput.getText().toString());
                    intent.putExtra("tag", selectedTag);
                    Toast.makeText(AddMessageActivity.this, "Message saved!", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(intent);
                } else {
                    //if no message is provided, return to main activity without doing any processing
                    Toast.makeText(AddMessageActivity.this, "Adding new message cancelled.", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(intent);
                }
            }
        });
    }
}
