package kopfif.faisal.pkp.kopfif.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import kopfif.faisal.pkp.kopfif.Adapter.KabarTerkaitAdapter;
import kopfif.faisal.pkp.kopfif.Adapter.KontakAdapter;
import kopfif.faisal.pkp.kopfif.Function.DividerItemDecoration;
import kopfif.faisal.pkp.kopfif.Model.DummyModel;
import kopfif.faisal.pkp.kopfif.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link KontakFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link KontakFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KontakFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView1;
    private RecyclerView.Adapter mAdapter1;
    private RecyclerView.LayoutManager mLayoutManager1;
    private ArrayList<DummyModel> dummyBase = new ArrayList<>();
    private OnFragmentInteractionListener mListener;

    public KontakFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment KontakFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KontakFragment newInstance(String param1, String param2) {
        KontakFragment fragment = new KontakFragment();
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
        View view = inflater.inflate(R.layout.fragment_kontak, container, false);

        for (int i = 0; i < 10; i++) {
            dummyBase.add(new DummyModel(
                    "Kontak name "+i,
                    "082100040"+i,
                    "http://www.arabnewtech.com/wp-content/uploads/avatar-1.png"));

        }

        mRecyclerView1 = (RecyclerView) view.findViewById(R.id.rv_kontak);
        mLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false);
        mRecyclerView1.setLayoutManager(mLayoutManager1);
        mAdapter1 = new KontakAdapter(dummyBase, getContext(), mRecyclerView1);


        mRecyclerView1.setAdapter(mAdapter1);
        mRecyclerView1.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));


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
