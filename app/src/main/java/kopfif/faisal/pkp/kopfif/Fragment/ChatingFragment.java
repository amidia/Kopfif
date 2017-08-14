package kopfif.faisal.pkp.kopfif.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kopfif.faisal.pkp.kopfif.Activity.ChatScreenActivity;
import kopfif.faisal.pkp.kopfif.Adapter.ListChatAdapter;
import kopfif.faisal.pkp.kopfif.Function.HttpRequest;
import kopfif.faisal.pkp.kopfif.Function.SessionManagerUtil;
import kopfif.faisal.pkp.kopfif.Model.ChatListModel;
import kopfif.faisal.pkp.kopfif.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChatingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChatingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView listview;
    private ListChatAdapter adapter;
    private ArrayList<ChatListModel> chatListBase = new ArrayList<ChatListModel>();
    private String photoUrl = "http://www.iconsdb.com/icons/preview/black/businessman-xxl.png";
    public static android.view.ActionMode mActionMode;
    private SessionManagerUtil util;
    private OnFragmentInteractionListener mListener;
    private ProgressDialog pDialog;

    public ChatingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatingFragment newInstance(String param1, String param2) {
        ChatingFragment fragment = new ChatingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chating, container, false);
        util = new SessionManagerUtil(getContext());
        listview = (ListView) view.findViewById(R.id.list_view_chat);
        adapter = new ListChatAdapter(getContext(), chatListBase);
        listview.setAdapter(adapter);
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading avaible user");
        pDialog.show();

        HttpRequest http = new HttpRequest(getContext());
        http.chatList(new HttpRequest.SuccessCallback() {
            @Override
            public void onHttpPostSuccess(String result) {
                String myId = util.sessionUserGet("userId");
                try {
                    JSONArray users = new JSONArray(result);
                    adapter.clear();
                    for (int i = 0; i < users.length(); i++) {
                        JSONObject user = new JSONObject(String.valueOf(users.get(i)));
                        ChatListModel chat = new ChatListModel(
                                user.getString("name"),
                                "Test Message " + i,
                                0,
                                photoUrl);
                        chat.id = user.getString("id");
                        if (!chat.id.equals(myId))
                            adapter.add(chat);
                    }
                    pDialog.hide();
                } catch (JSONException e) {
                    e.printStackTrace();
                    pDialog.hide();
                }
                Log.d("", "");
            }
        }, new HttpRequest.ErrorCallback() {
            @Override
            public void onHttpPostError(VolleyError error) {
                Log.d("", "");
                pDialog.hide();
            }
        });
        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listview.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
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
                MenuInflater inflater = getActivity().getMenuInflater();
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
                        SparseBooleanArray sparseBooleanArray = listview.getCheckedItemPositions();

                        for (int i = sparseBooleanArray.size() - 1; i >= 0; i--)
                            chatListBase.remove(sparseBooleanArray.keyAt(i));
                        adapter.notifyDataSetChanged();
                        mode.finish();
                        break;
                    case R.id.select_all:
                        for (int i = 0; i < listview.getAdapter().getCount(); i++) {
                            if (!adapter.isPositionChecked(i)) {
                                listview.setItemChecked(i, true);
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
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                listview.setItemChecked(position, !adapter.isPositionChecked(position));
                return true;
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ChatListModel chat = chatListBase.get(position);
                Toast.makeText(getContext(), chat.name, Toast.LENGTH_LONG).show();
                // mListener.onChatSelected(chat);
                Intent i = new Intent(getContext(), ChatScreenActivity.class);
                Bundle b = new Bundle();
                b.putString("receiverId", chat.id);
                b.putString("receiverName", chat.name);
                i.putExtras(b);
                startActivity(i);
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
