package kopfif.faisal.pkp.kopfif.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kopfif.faisal.pkp.kopfif.Activity.DetailBeritaActivity;
import kopfif.faisal.pkp.kopfif.Function.RecyclerViewAnimatorHorizontal;
import kopfif.faisal.pkp.kopfif.Model.DummyModel;
import kopfif.faisal.pkp.kopfif.R;

/**
 * Created by Faisal on 3/8/2017.
 */

public class KabarTerkaitAdapter extends RecyclerView.Adapter<KabarTerkaitAdapter.DataObjectHolder> {
    public ArrayList<DummyModel> mDataSet;
    private Context context;
    private RecyclerViewAnimatorHorizontal mAnimator;

    public KabarTerkaitAdapter(ArrayList<DummyModel> myDataSet, Context context, RecyclerView recyclerView ) {
        mDataSet = myDataSet;
        this.context = context;
        mAnimator = new RecyclerViewAnimatorHorizontal(recyclerView);

    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kabar_terkait_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view, parent);

        mAnimator.onCreateViewHolder(view);

        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        DummyModel dummy = mDataSet.get(position);
        holder.setItem(mDataSet, context);

        holder.name.setText(dummy.name);
        holder.category.setText(dummy.text1);
        Picasso
                .with(holder.photoUrl.getContext())
                .load(dummy.photoUrl)
                .into(holder.photoUrl);
        mAnimator.onBindViewHolder(holder.itemView, position);

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class DataObjectHolder extends
            RecyclerView.ViewHolder {
        public ArrayList<DummyModel> mItemSet;

        ImageView photoUrl;
        TextView name;
        TextView time;
        TextView category;
        Context mCtx;

        public DataObjectHolder(final View view,
                                final ViewGroup viewGroup) {
            super(view);

            photoUrl = (ImageView) view.findViewById(R.id.xphoto);
            name = (TextView) view.findViewById(R.id.xname);
            category = (TextView) view.findViewById(R.id.xcategory);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(mCtx, DetailBeritaActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    Bundle b = new Bundle();
                    b.putString("title", mItemSet.get(getAdapterPosition()).name);
                    b.putString("photoUrl", mItemSet.get(getAdapterPosition()).photoUrl);
                    b.putString("description", String.valueOf(mItemSet.get(getAdapterPosition()).description));
                    b.putString("category", String.valueOf(mItemSet.get(getAdapterPosition()).text1));
                    b.putString("time", String.valueOf(mItemSet.get(getAdapterPosition()).text2));
                    i.putExtras(b);

                    Pair<View, String> p1 = Pair.create((View) photoUrl, "photo");
//                    Pair<View, String> p2 = Pair.create((View) name, "name");
//                    Pair<View, String> p3 = Pair.create((View) category, "category");

                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation((Activity) mCtx, p1);
                    mCtx.startActivity(i, options.toBundle());

                }
            });
        }

        public void setItem(ArrayList<DummyModel> mDataSet, Context ctx) {
            mItemSet = mDataSet;
            mCtx = ctx;
        }
    }


}
