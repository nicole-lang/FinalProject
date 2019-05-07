package com.example.finalprojectnrl4;

import android.content.Intent;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private int buddyColor;
    private int buddyFace;
    private final String COLOR_KEY = "color";
    private final String FACE_KEY = "face";
    private Intent intent;
    private ArrayList<String> enabledTags;
    private ArrayList<Message> messageList;
    private ArrayList<Message> enabledMessages;
    TextView messageText;
    View buddy;
    ImageView[] buddyElements;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        messageList = new ArrayList<Message>();
        enabledMessages = new ArrayList<Message>();
        enabledTags = new ArrayList<String>();

        //on load, initialize enabled tags
        enabledTags.add("General");
        enabledTags.add("Reminder");
        enabledTags.add("Encouragement");
        
        messageText = findViewById(R.id.txt_message);
        //The clickable object in the activity is the frame layout containing the components of frame_char
        buddy = findViewById(R.id.frame_Char);
        //ImageView array containing face as element 0 and shape as element 1
        buddyElements = new ImageView[]{findViewById(R.id.img_face), findViewById(R.id.img_shape)};
        buddy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SayMessage();
            }
        });
        // Restore the saved instance state.
        if (savedInstanceState != null) {
            //if reloading suspended main activity
            messageList = savedInstanceState.getParcelableArrayList("message");
        }
        buddyColor = 1;
        buddyFace = 2;

        if (getIntent() != null) {
            intent = getIntent();
            if (intent.getExtras() != null) {
                if (intent.getExtras().containsKey("message")) {
                    String msg = intent.getStringExtra("message");
                    String tag = intent.getStringExtra("tag");
                    Message newMsg = new Message(msg, tag);
                    messageList.add(newMsg);
                }

                if(intent.getExtras().getStringArrayList("enabledTags") != null)
                    enabledTags = intent.getStringArrayListExtra("enabledTags");

                buddyFace = intent.getIntExtra("face", 2);
                buddyColor = intent.getIntExtra("color", 1);

            }
            if (messageList.isEmpty())
                AddMessages();
        }
        for (Message m : messageList) {//create list of messages to choose from based on enabled tags
            if (enabledTags.contains(m.tag))
                Log.d(TAG, "onCreate: added " + m.tag + m.msg);
            enabledMessages.add(m);
        }
        LoadBuddy();
        Log.d(TAG, "onCreate: ready");
    }

    private void AddMessages() {
        //Add default messages
        messageList = new ArrayList<Message>();
        messageList.add(new Message("How's your day? Hope it's going well!", "Encouragement"));
        messageList.add(new Message("If you're feeling down, try talking to a friend!", "Encouragement"));
        messageList.add(new Message("You can do whatever you set your mind to!", "Encouragement"));
        messageList.add(new Message("Have you gone outside lately? Fresh air is good for you.", "Reminder"));
        messageList.add(new Message("Remember to eat three meals a day!", "Reminder"));
        messageList.add(new Message("Did you get all your homework done?", "Reminder"));
        Log.d(TAG, "AddMessages: message list ready");
    }

    private void SayMessage() {
        //Display random message from under the enabled tags
        Random rand = new Random();
        int index;
            if(enabledMessages.size() == 0)
                Toast.makeText(this, "No messages found matching the enabled tags", Toast.LENGTH_LONG).show();
            else
            {
                index = rand.nextInt(enabledMessages.size());
                messageText.setText(messageList.get(index).msg);
            }
    }

    public void LoadBuddy()
    {
        switch(buddyFace)
        {
            case 1:
                buddyElements[0].setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.face1, null));
                break;
            case 2:
                buddyElements[0].setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.face2, null));
                break;
            case 3:
                buddyElements[0].setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.face3, null));
                break;
        }
        switch(buddyColor)
        {
            case 1:
                buddyElements[1].setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.bluecircle, null));
                break;
            case 2:
                buddyElements[1].setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.pinkcircle, null));
                break;
            case 3:
                buddyElements[1].setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.greencircle, null));
                break;
        }
        Log.d(TAG, "LoadBuddy: Buddy loaded");
    }
    @Override
    public void onPause()
    {
        super.onPause();

    }
    //Action bar item code
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelableArrayList("messages", messageList);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_addMessage) {
            //pass tags list to AddMessage activity
            intent = new Intent(getApplicationContext(), AddMessageActivity.class);
            //pass these so they don't change after adding new message
            intent.putStringArrayListExtra("enabledTags", enabledTags);
            intent.putExtra(FACE_KEY, buddyFace);
            intent.putExtra(COLOR_KEY, buddyColor);
            startActivity(intent);
        }else if(id == R.id.action_settings) {
            //pass current buddy appearance and enabled tags to settings activity
            intent = new Intent(getApplicationContext(), PrefsActivity.class);
            intent.putStringArrayListExtra("enabledTags", enabledTags);
            intent.putExtra(FACE_KEY, buddyFace);
            intent.putExtra(COLOR_KEY, buddyColor);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
