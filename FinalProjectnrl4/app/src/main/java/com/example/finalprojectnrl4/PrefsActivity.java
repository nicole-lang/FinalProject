package com.example.finalprojectnrl4;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PrefsActivity extends AppCompatActivity {
    private Intent intent;
    private int buddyColor;
    private int buddyFace;
    private final String COLOR_KEY = "color";
    private final String FACE_KEY = "face";
    private ArrayList<Message> messageList;
    private CheckBox chkGeneral;
    private CheckBox chkEncouragement;
    private CheckBox chkReminder;
    private ImageView face;
    private ImageView color;
    private RadioGroup faces;
    private RadioGroup colors;
    private RadioButton face1;
    private RadioButton face2;
    private RadioButton face3;
    private RadioButton color1;
    private RadioButton color2;
    private RadioButton color3;
    private Button save;
    private ArrayList<String> enabledTags;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefs);

        intent = getIntent();
        enabledTags = new ArrayList<String>();
        enabledTags = intent.getStringArrayListExtra("enabledTags");
        messageList = intent.getParcelableArrayListExtra("messages");

        chkGeneral = findViewById(R.id.chkGeneral);
        chkEncouragement = findViewById(R.id.chkEncouragement);
        chkReminder = findViewById(R.id.chkReminder);

        //initialize checkboxes (default is false)
        if(enabledTags.contains("General"))
            chkGeneral.setChecked(true);
        if(enabledTags.contains("Encouragement"))
            chkEncouragement.setChecked(true);
        if(enabledTags.contains("Reminder"))
            chkReminder.setChecked(true);

        chkGeneral.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkedChanged(buttonView, isChecked);
            }
        });
        chkEncouragement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkedChanged(buttonView, isChecked);
            }
        });
        chkReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkedChanged(buttonView, isChecked);
            }
        });

        face = findViewById(R.id.img_face);
        faces = findViewById(R.id.faces);
        face1 = findViewById(R.id.btnFace1);
        face2 = findViewById(R.id.btnFace2);
        face3 = findViewById(R.id.btnFace3);

        color = findViewById(R.id.img_color);
        colors = findViewById(R.id.colors);
        color1 = findViewById(R.id.btnColor1);
        color2 = findViewById(R.id.btnColor2);
        color3 = findViewById(R.id.btnColor3);
        save = findViewById(R.id.buttonSave);

        //set up listeners for the radio groups
        faces.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(group.getCheckedRadioButtonId()){
                    case R.id.btnFace1:
                        face.setImageDrawable(getDrawable(R.drawable.face1));
                        buddyFace = 1;
                        break;
                    case R.id.btnFace2:
                        face.setImageDrawable(getDrawable(R.drawable.face2));
                        buddyFace = 2;
                        break;
                    case R.id.btnFace3:
                        face.setImageDrawable(getDrawable(R.drawable.face3));
                        buddyFace = 3;
                        break;
                }
            }
        });
        colors.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.btnColor1:
                        color.setImageDrawable(getDrawable(R.drawable.bluecircle));
                        buddyColor = 1;
                        break;
                    case R.id.btnColor2:
                        color.setImageDrawable(getDrawable(R.drawable.pinkcircle));
                        buddyColor = 2;
                        break;
                    case R.id.btnColor3:
                        color.setImageDrawable(getDrawable(R.drawable.greencircle));
                        buddyColor = 3;
                        break;
                }
            }
        });
        //initialize selected options and preview
        buddyColor = intent.getIntExtra("color", 0);
        buddyFace = intent.getIntExtra("face", 0);
        switch(buddyColor){
            case 1:
                color1.setSelected(true);
                break;
            case 2:
                color2.setSelected(true);
                break;
            case 3:
                color3.setSelected(true);
                break;
        }
        switch(buddyFace) {
            case 1:
                face1.setSelected(true);
                break;
            case 2:
                face2.setSelected(true);
                break;
            case 3:
                face3.setSelected(true);
                break;
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("face", buddyFace);
                intent.putExtra("color", buddyColor);
                intent.putStringArrayListExtra("enabledTags", enabledTags);
                intent.putParcelableArrayListExtra("messages", messageList);
                startActivity(intent);
            }
        });
    }
    private void checkedChanged(CompoundButton cb, boolean checked)
    {   //add or remove clicked checkbox's tag from list
        String item = cb.getText().toString();
        if(enabledTags.contains(item) && checked == false)
        {
            enabledTags.remove(item);
            Toast.makeText(this, "Disabled " + item + " tag", Toast.LENGTH_SHORT).show();
        }else{
            enabledTags.add(item);
            Toast.makeText(this, "Enabled " + item + " tag", Toast.LENGTH_SHORT).show();
        }
    }
}
