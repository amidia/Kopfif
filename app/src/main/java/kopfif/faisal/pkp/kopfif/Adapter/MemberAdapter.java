package kopfif.faisal.pkp.kopfif.Adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kopfif.faisal.pkp.kopfif.Model.DummyModel;
import kopfif.faisal.pkp.kopfif.R;

/**
 * Created by Faisal on 3/8/2017.
 */

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.DataObjectHolder> {
    public ArrayList<DummyModel> mDataSet;
    private Context context;
//    private RecyclerViewAnimator mAnimator;

    public MemberAdapter(ArrayList<DummyModel> myDataSet, Context context, RecyclerView recyclerView) {
        mDataSet = myDataSet;
        this.context = context;
//        mAnimator = new RecyclerViewAnimator(recyclerView);

    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.member_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view, parent);

//        mAnimator.onCreateViewHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        DummyModel dummy = mDataSet.get(position);
        holder.setItem(mDataSet, context);
        holder.name.setText(dummy.name);
        holder.description.setText(dummy.description);
        Picasso
                .with(holder.photoUrl.getContext())
                .load(dummy.photoUrl)
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
        TextView description;
        Context mCtx;
        LinearLayout submenuSimulasi;
        LinearLayout submenuPengajuan;
        LinearLayout submenuPencairan;
        LinearLayout submenuTiket;
        LinearLayout submenuSetting;
        LinearLayout main;

        public DataObjectHolder(final View view,
                                final ViewGroup viewGroup) {
            super(view);

            photoUrl = (ImageView) view.findViewById(R.id.photo);
            name = (TextView) view.findViewById(R.id.name);
            description = (TextView) view.findViewById(R.id.description);
            submenuSimulasi = (LinearLayout) view.findViewById(R.id.submenu_simulasi);
            submenuPengajuan = (LinearLayout) view.findViewById(R.id.submenu_pengajuan);
            submenuPencairan = (LinearLayout) view.findViewById(R.id.submenu_pencairan);
            submenuTiket = (LinearLayout) view.findViewById(R.id.submenu_tiket);
            submenuSetting = (LinearLayout) view.findViewById(R.id.submenu_setting);
            main = (LinearLayout) view.findViewById(R.id.main);
//            photoUrl.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    revealEffect();
//                }
//            }, 300);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent i = new Intent(mCtx, KontakActivity.class);
//                    //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                    Bundle b = new Bundle();
//                    b.putString("name", mItemSet.get(getAdapterPosition()).name);
//                    b.putString("photoUrl", mItemSet.get(getAdapterPosition()).photoUrl);
//                    b.putString("text1", mItemSet.get(getAdapterPosition()).text1);
//                    i.putExtras(b);
//                    ActivityOptionsCompat options = ActivityOptionsCompat.
//                            makeSceneTransitionAnimation((Activity)mCtx, (View)photoUrl, "photo");
//                    mCtx.startActivity(i, options.toBundle());

                    LinearLayout submenu;
                    switch (mItemSet.get(getAdapterPosition()).name) {
                        case "Pengajuan":
                            submenu = submenuPengajuan;
                            break;
                        case "Pencairan":
                            submenu = submenuPencairan;
                            break;
                        case "Tiket":
                            submenu = submenuTiket;
                            break;
                        case "Setting":
                            submenu = submenuSetting;
                            break;
                        default:
                            submenu = submenuSimulasi;
                            break;

                    }

                    if (submenu.getVisibility() == View.GONE) {
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            int cx = main.getLeft();
                            int cy = main.getLeft();

                            int finalRadius = (int) Math.hypot(main.getWidth(), main.getHeight());
                            Animator a = ViewAnimationUtils.createCircularReveal(submenu, cy, cx, 0, finalRadius);
                            a.setDuration(500);
                            a.setInterpolator(new DecelerateInterpolator());
                            main.setVisibility(View.GONE);
                            submenu.setVisibility(View.VISIBLE);
                            a.start();
                        } else {

                            submenu.setVisibility(View.VISIBLE);
                            main.setVisibility(View.GONE);
                        }
                    } else {
//                        if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.LOLLIPOP) {
//                            int cx = submenu.getLeft();
//                            int cy = submenu.getLeft();
//
//                            int finalRadius = (int) Math.hypot(submenu.getWidth(), submenu.getHeight());
//                            Animator a = ViewAnimationUtils.createCircularReveal(main, cy, cx, 0, finalRadius);
//                            a.setDuration(500);
//                            a.setInterpolator(new DecelerateInterpolator());
//                            submenu.setVisibility(View.GONE);
//                            main.setVisibility(View.VISIBLE);
//                            a.start();
//                        } else {
                        submenu.setVisibility(View.GONE);
                        main.setVisibility(View.VISIBLE);
                        //}

                    }


                }
            });
            view.setLayoutParams(new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.WRAP_CONTENT));
        }

//        public void revealEffect() {
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//                int cx = photoUrl.getMeasuredWidth() / 2;
//                int cy = photoUrl.getMeasuredHeight() / 2;
//                int finalRadius = Math.max(photoUrl.getWidth(), photoUrl.getHeight());
//
//                Animator a = ViewAnimationUtils.createCircularReveal(photoUrl, cx, cy, 0, finalRadius);
//                a.setDuration(1000);
//                photoUrl.setVisibility(View.VISIBLE);
//                a.start();
//            }
//        }

        public void setItem(ArrayList<DummyModel> mDataSet, Context ctx) {
            mItemSet = mDataSet;
            mCtx = ctx;
        }
    }


}
