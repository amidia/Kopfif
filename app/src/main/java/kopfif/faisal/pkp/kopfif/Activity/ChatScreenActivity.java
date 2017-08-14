package kopfif.faisal.pkp.kopfif.Activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import kopfif.faisal.pkp.kopfif.Adapter.ChatAdapter;
import kopfif.faisal.pkp.kopfif.Function.HttpRequest;
import kopfif.faisal.pkp.kopfif.Function.LocalBroadcastConstant;
import kopfif.faisal.pkp.kopfif.Function.SessionManagerUtil;
import kopfif.faisal.pkp.kopfif.Model.ChatMessageModel;
import kopfif.faisal.pkp.kopfif.R;

import static java.util.UUID.randomUUID;

public class ChatScreenActivity extends AppCompatActivity {
    private EditText messageET;
    private ListView messagesContainer;
    private ImageButton sendBtn;
    private ChatAdapter adapter;
    private ArrayList<ChatMessageModel> chatHistoryBase;
    private ArrayList<ChatMessageModel> chatBase = new ArrayList<>();
    public static android.view.ActionMode mActionMode;
    private HttpRequest http;
    private String receiverId;
    private String receiverName;
    private SessionManagerUtil util;
//    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        util = new SessionManagerUtil(getApplicationContext());


        Bundle b = getIntent().getExtras();
        if (b != null) {
            receiverId = b.getString("receiverId");
            receiverName = b.getString("receiverName");
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(receiverName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);


        adapter = new ChatAdapter(this, chatBase);
        http = new HttpRequest(getApplicationContext());

        messagesContainer = (ListView) findViewById(R.id.messagesContainer);
        messagesContainer.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        messagesContainer.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            private int nr = 0;

            @Override
            public void onItemCheckedStateChanged(android.view.ActionMode mode, int position, long id, boolean checked) {
                if (checked) {
                    nr++;
                    adapter.setNewSelection(position, checked);
                } else {
                    nr--;
                    adapter.removeSelection(position);
                }
                mode.setTitle(nr + " selected");
            }

            @Override
            public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
                mActionMode = mode;
                nr = 0;
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.contextual_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_delete:
                        nr = 0;
                        adapter.clearSelection();
                        SparseBooleanArray sparseBooleanArray = messagesContainer.getCheckedItemPositions();

                        for (int i = sparseBooleanArray.size() - 1; i >= 0; i--)
                            chatBase.remove(sparseBooleanArray.keyAt(i));
                        adapter.notifyDataSetChanged();
                        mode.finish();
                        break;
                    case R.id.select_all:
                        for (int i = 0; i < messagesContainer.getAdapter().getCount(); i++) {
                            if (!adapter.isPositionChecked(i)) {
                                messagesContainer.setItemChecked(i, true);
                            }
                        }
                        break;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(android.view.ActionMode mode) {
                mActionMode = null;
                adapter.clearSelection();
            }
        });
        messagesContainer.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                messagesContainer.setItemChecked(position, !adapter.isPositionChecked(position));
                return true;
            }
        });
        messagesContainer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ChatMessageModel chat = adapter.getItem(position);
                Toast.makeText(getApplicationContext(), chat.getMessage(), Toast.LENGTH_LONG).show();
                // mListener.onChatSelected(chat);
//                startActivity(new Intent(getApplicationContext(), ChatScreenActivity.class));
            }
        });

        initControls();

    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver((mMessageReceiver), new IntentFilter(LocalBroadcastConstant.NEW_CHAT));
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(mMessageReceiver);

    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle data = intent.getExtras();
            try {
                JSONObject sender = new JSONObject(data.getString("sender"));
                JSONObject message = new JSONObject(data.getString("message"));

                if (receiverId.equals(sender.getString("id"))) {
                    ChatMessageModel msg = new ChatMessageModel();
                    msg.setId(message.getString("id"));
                    msg.setMessage(message.getString("text"));
                    msg.status = message.getString("status");
                    String str_date = new JSONObject(message.getString("date_time")).getString("date");

                    DateFormat formatter;
                    Date date = null;
                    formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    try {
                        date = formatter.parse(str_date);
                        msg.setDate(date);
                    } catch (ParseException e) {

                    }


                    displayMessage(msg);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

    private void initControls() {
        messageET = (EditText) findViewById(R.id.messageEdit);
        sendBtn = (ImageButton) findViewById(R.id.chatSendButton);

        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
        loadHistory();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }

                UUID id = randomUUID();

                ChatMessageModel chatMessage = new ChatMessageModel();
                chatMessage.setId(id.toString());
                chatMessage.setMessage(messageText);
                chatMessage.setDate(new Date());
                chatMessage.setMe(true);
                chatMessage.status = "1";
                messageET.setText("");
                displayMessage(chatMessage);

                http.sendMessage(id.toString(), receiverId, messageText, "", new HttpRequest.SuccessCallback() {
                    @Override
                    public void onHttpPostSuccess(String result) {
                        try {
                            if (new JSONObject(result).getString("status").equals("1")) {
                                JSONObject message = new JSONObject(new JSONObject(result).getString("data"));
                                String id = message.getString("id");
                                for (int i = 0; i < chatBase.size(); i++) {
                                    if (chatBase.get(i).getId().equals(id)) {
                                        chatBase.get(i).status = "2";
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new HttpRequest.ErrorCallback() {
                    @Override
                    public void onHttpPostError(VolleyError error) {
                        Log.d("", "");
                    }
                });


            }
        });
    }

    public void displayMessage(ChatMessageModel message) {
        chatBase.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }

    private void loadHistory() {
        final String myId = util.sessionUserGet("userId");
        chatHistoryBase = new ArrayList<ChatMessageModel>();
//        pDialog = new ProgressDialog(getBaseContext());
//        pDialog.setMessage("Loading history");
//        pDialog.show();

        http.chatHistory(receiverId, new HttpRequest.SuccessCallback() {
            @Override
            public void onHttpPostSuccess(String result) {
                try {
                    if (new JSONObject(result).getString("status").equals("1")) {
                        JSONArray messages = new JSONArray(new JSONObject(result).getString("data"));


                        for (int i = 0; i < messages.length(); i++) {
                            ChatMessageModel msg = new ChatMessageModel();


                            String senderId = new JSONObject(String.valueOf(messages.get(i))).getString("sender_id");
                            String message = new JSONObject(String.valueOf(messages.get(i))).getString("text");
                            String str_date = new JSONObject(String.valueOf(messages.get(i))).getString("date_time");
                            String id = new JSONObject(String.valueOf(messages.get(i))).getString("id");
                            String status = new JSONObject(String.valueOf(messages.get(i))).getString("status");

                            DateFormat formatter;
                            Date date = null;
                            formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            try {
                                date = formatter.parse(str_date);
                                msg.setDate(date);
                            } catch (ParseException e) {

                            }

                            msg.setId(id);
                            msg.setMessage(message);
                            msg.status = status;
                            if (myId.equals(senderId)) {
                                msg.setMe(true);
                            } else {
                                msg.setMe(false);
                            }
                            chatHistoryBase.add(msg);
                        }

                        messagesContainer.setAdapter(adapter);
                        for (int i = 0; i < chatHistoryBase.size(); i++) {
                            ChatMessageModel message = chatHistoryBase.get(i);
                            displayMessage(message);
                        }

//                        pDialog.hide();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
//                    pDialog.hide();
                }
            }
        }, new HttpRequest.ErrorCallback() {
            @Override
            public void onHttpPostError(VolleyError error) {
                Log.d("", "");
//                pDialog.hide();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
