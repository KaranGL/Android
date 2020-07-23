package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {

    AppCompatActivity parentActivity;
    private ArrayList<Message>  messages = new ArrayList<>();
    private Button sendbtn, receivebtn;
    private EditText txt;
    private ListView msgList;
    public static final String ACTIVITY_NAME = "CHAT_ROOM_ACTIVITY";

    public static final String ITEM_SELECTED = "ITEM";
    public static final String ITEM_MESSAGE = "MESSAGE";
    public static final String ITEM_ID = "ID";
    public static final String ITEM_ISSEND = "ISSEND";

    Message msg;
//    SQLiteDatabase db;
//    ContentValues cv = new ContentValues();
    MyListAdapter adpter = new MyListAdapter();
    MyOpener dbopen = new MyOpener(this);
    Boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(ACTIVITY_NAME,"In function: onCreate");
        setContentView(R.layout.activity_chat_room);

        dbopen = new MyOpener(this);

        msgList = findViewById(R.id.theListView);
        sendbtn = findViewById(R.id.sendbtn);
        receivebtn = findViewById(R.id.receivebtn);
        txt = findViewById(R.id.input);

        isTablet = findViewById(R.id.fragmentLocation) !=null; // to  check if frame layout is loaded
        TextView getmessage = findViewById(R.id.messageView);/////////////////////////////////////////////////

        msgList.setAdapter(adpter);

        if(dbopen.count()>0){
            messages = (ArrayList<Message>) dbopen.read();
            adpter.notifyDataSetChanged();
        }
        sendbtn.setOnClickListener(click-> {
            Message message = new Message(txt.getText().toString(), true);
            adpter.setMessage(message);
            dbopen.insertMessage(message);
            txt.setText("");
        });

        msgList.setOnItemClickListener((parent, view, position, id) -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLocation, new DetailsFragment()).commit();
        });

        receivebtn.setOnClickListener(click-> {
            Message message = new Message(txt.getText().toString(), false);
            adpter.setMessage(message);
            dbopen.insertMessage(message);
            txt.setText("");
        });

        msgList.setOnItemClickListener((list, view, position, id) -> {
            //Create a bundle to pass data to the new fragment
            Bundle dataToPass = new Bundle();
            dataToPass.putString(ITEM_SELECTED, String.valueOf(messages.get(position)));
//            dataToPass.putString(ITEM_MESSAGE, (String) getmessage.getText());  * Not working
            dataToPass.putLong(ITEM_ID, id);
            dataToPass.putBoolean(ITEM_ISSEND, false);

            if(isTablet)
            {
                DetailsFragment dFragment = new DetailsFragment(); //add a DetailFragment
                dFragment.setArguments( dataToPass ); //pass it a bundle for information
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentLocation, dFragment) //Add the fragment in FrameLayout
                        .commit(); //actually load the fragment. Calls onCreate() in DetailFragment
            }
            else //isPhone
            {
                Intent nextActivity = new Intent(this, EmptyActivity.class);
                nextActivity.putExtras(dataToPass); //send data to next activity
                startActivity(nextActivity); //make the transition
            }



        });

        msgList.setOnItemLongClickListener((parent, view, position, id) ->{
            AlertDialog.Builder alrt = new AlertDialog.Builder(this);
            alrt.setTitle("DELETE");
            alrt.setMessage("Do you want to delete this? ")
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            messages.remove(position);
                            dbopen.delete(id);
                            adpter.notifyDataSetChanged();
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }); alrt.create().show();
            return true;
        });
    }

    private class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            Log.e(ACTIVITY_NAME,"In function: getCount");
            return messages.size();
        }

        @Override
        public Object getItem(int position) {
            Log.e(ACTIVITY_NAME,"In function: getItem");
            return messages.get(position);
        }
        @Override
        public long getItemId(int position) {
            Log.e(ACTIVITY_NAME,"In function: getItemId");
            return messages.get(position).getId();
        }

        public void setMessage(Message message){
            Log.e(ACTIVITY_NAME,"In function: setMessage");
            messages.add(message);
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View old, ViewGroup parent) {
            Log.e(ACTIVITY_NAME, "In function: getView");
            msg = messages.get(position);
            View newView = old;
            if (msg.isSide())
                newView = getLayoutInflater().inflate(R.layout.send_layout, parent, false);
            else
                newView = getLayoutInflater().inflate(R.layout.receive_layout, parent, false);

            TextView txtview = newView.findViewById(R.id.messageView);
            txtview.setText(msg.getMessage());
            return newView;
        }
    }
}