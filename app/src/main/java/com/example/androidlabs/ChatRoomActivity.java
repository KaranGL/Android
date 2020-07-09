package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {
    private ArrayList<Message>  messages = new ArrayList<>();
    private Button sendbtn, receivebtn;
    private EditText txt;
    private ListView msgList;
    public static final String ACTIVITY_NAME = "CHAT_ROOM_ACTIVITY";
    Message msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(ACTIVITY_NAME,"In function: onCreate");
        setContentView(R.layout.activity_chat_room);

        msgList = findViewById(R.id.theListView);
        sendbtn = findViewById(R.id.sendbtn);
        receivebtn = findViewById(R.id.receivebtn);
        txt = findViewById(R.id.input);

        MyListAdapter adpter = new MyListAdapter();
        msgList.setAdapter(adpter);

        sendbtn.setOnClickListener(click-> {
            adpter.setMessage(new Message(txt.getText().toString(), true));
            txt.setText("");
        });

        receivebtn.setOnClickListener(click-> {
            adpter.setMessage(new Message(txt.getText().toString(), false));
            txt.setText("");
        });

        msgList.setOnItemLongClickListener((parent, view, position, id) ->{
            AlertDialog.Builder alrt = new AlertDialog.Builder(this);
            alrt.setTitle("Delete");
            alrt.setMessage("Do you want to delete this? ");
            alrt.setMessage("The selected row is: "+position+"\n The database ID is:"+id)
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            messages.remove(position);
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
            return 0;
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
            //make a new row:
            //set what the text should be for this row:
            //                TextView tView = newView.findViewById(R.id.textGoesHere);
            //                tView.setText(getItem(position).toString());
            //return it to be put in the table
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