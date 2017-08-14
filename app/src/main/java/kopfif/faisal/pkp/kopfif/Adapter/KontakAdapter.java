package kopfif.faisal.pkp.kopfif.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kopfif.faisal.pkp.kopfif.Activity.KontakActivity;
import kopfif.faisal.pkp.kopfif.Function.CircleTransform;
import kopfif.faisal.pkp.kopfif.Model.DummyModel;
import kopfif.faisal.pkp.kopfif.R;

/**
 * Created by Faisal on 3/8/2017.
 */

public class KontakAdapter extends RecyclerView.Adapter<KontakAdapter.DataObjectHolder> {
    public ArrayList<DummyModel> mDataSet;
    private Context context;
//    private RecyclerViewAnimator mAnimator;

    public KontakAdapter(ArrayList<DummyModel> myDataSet, Context context, RecyclerView recyclerView) {
        mDataSet = myDataSet;
        this.context = context;
//        mAnimator = new RecyclerViewAnimator(recyclerView);

    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kontak_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view, parent);

//        mAnimator.onCreateViewHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        DummyModel dummy = mDataSet.get(position);
        holder.setItem(mDataSet, context);

        holder.name.setText(dummy.name);
        Picasso
                .with(holder.photoUrl.getContext())
                .load(dummy.photoUrl)
                .transform(new CircleTransform())
                .into(holder.photoUrl);
//        mAnimator.onBindViewHolder(holder.itemView, position);

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
        Context mCtx;

        public DataObjectHolder(final View view,
                                final ViewGroup viewGroup) {
            super(view);

            photoUrl = (ImageView) view.findViewById(R.id.photo);
            name = (TextView) view.findViewById(R.id.name);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(mCtx, KontakActivity.class);
                    //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    Bundle b = new Bundle();
                    b.putString("name", mItemSet.get(getAdapterPosition()).name);
                    b.putString("photoUrl", mItemSet.get(getAdapterPosition()).photoUrl);
                    b.putString("text1", mItemSet.get(getAdapterPosition()).text1);
                    i.putExtras(b);
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation((Activity)mCtx, (View)photoUrl, "photo");
                    mCtx.startActivity(i, options.toBundle());

                }
            });
            view.setLayoutParams(new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.WRAP_CONTENT));
        }

        public void setItem(ArrayList<DummyModel> mDataSet, Context ctx) {
            mItemSet = mDataSet;
            mCtx = ctx;
        }
    }


}
