package kopfif.faisal.pkp.kopfif.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import kopfif.faisal.pkp.kopfif.Adapter.KabarAdapter;
import kopfif.faisal.pkp.kopfif.Model.DummyModel;
import kopfif.faisal.pkp.kopfif.R;


public class PopulerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private RecyclerView mRecyclerView1;
    private RecyclerView.Adapter mAdapter1;
    private RecyclerView.LayoutManager mLayoutManager1;
    private ArrayList<DummyModel> dummyBase = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    public PopulerFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_populer, container, false);

        for (int i = 0; i < 10; i++) {
            dummyBase.add(new DummyModel(
                    "Raja Salman Selalu Bawa Rombongan Besar Di Setiap Kunjungan",
                    "Media USA Today pernah menulis pada teras beritanya, begini: \"When you're royalty, go big or don't go at all\", yang berarti saat anda raja, pergilah dengan jumlah rombongan yang besar atau jangan pergi sama sekali. Kapolda Bali, Irjen Pertus Reinhard Golose mengatakan, perempuan itu mengaku istri dari Sri Sultan Hamungkubuwono dan nekat ingin menerobos penjagaan petugas untuk bertemu dan bersalaman dengan Raja Salman. Namun, keburu diamankan petugas.\n" +
                            "\n" +
                            "Sebelumnya, seorang perempuan asal Sumatera juga diamankan polisi saat ingin memberi kado kepada Raja Salman. Pertus Reinhard mengatakan, kedua perempuan itu sudah dibawa ke RSJ di Bangli, Bali.\n" +
                            "\n" +
                            "\"Sejauh ini ada dua wanita yang sudah kami amankan. Setelah melalui pemeriksaan keduanya mengalami masalah kejiwaan,\" katanya, Rabu (8/3/2017).\n" +
                            "\n" +
                            "Pemeriksaan dilakukan psikiater sehingga diketahui kedua perempuan mengalami masalah kejiwaan.",
                    "Populer",
                    "12 Menit lalu",
                    "http://ichef.bbci.co.uk/news/660/cpsprodpb/6D63/production/_94830082_6ea434a1-4abb-4451-9f8c-7eff17a16cdf.jpg"));

        }

        mRecyclerView1 = (RecyclerView) view.findViewById(R.id.rv_promosi1);
        mRecyclerView1.setHasFixedSize(true);
        mLayoutManager1 = new GridLayoutManager(getContext(), 1);
        mRecyclerView1.setLayoutManager(mLayoutManager1);
        mAdapter1 = new KabarAdapter(dummyBase, getContext(), mRecyclerView1);
        mRecyclerView1.setAdapter(mAdapter1);

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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
