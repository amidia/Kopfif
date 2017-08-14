package kopfif.faisal.pkp.kopfif.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import kopfif.faisal.pkp.kopfif.Adapter.KabarAdapter;
import kopfif.faisal.pkp.kopfif.Adapter.KabarTerkaitAdapter;
import kopfif.faisal.pkp.kopfif.Model.DummyModel;
import kopfif.faisal.pkp.kopfif.R;
import kopfif.faisal.pkp.kopfif.View.SquareImageView;

public class DetailBeritaActivity extends AppCompatActivity {

    private String title;
    private String description;
    private String time;
    private String photoUrl;
    private String category;

    private TextView mTitle;
    private TextView mDescription;
    private SquareImageView mImage;

    private RecyclerView mRecyclerView1;
    private RecyclerView.Adapter mAdapter1;
    private RecyclerView.LayoutManager mLayoutManager1;
    private ArrayList<DummyModel> dummyBase = new ArrayList<>();
    private SliderLayout mHomeSlider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_berita);


        Bundle b = getIntent().getExtras();
        if (b != null) {
            title = b.getString("title");
            description = b.getString("description");
            time = b.getString("time");
            photoUrl = b.getString("photoUrl");
            category = b.getString("category");
        }

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(category);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);

        mTitle = (TextView) findViewById(R.id.title);
        mDescription = (TextView) findViewById(R.id.description);
        mImage = (SquareImageView) findViewById(R.id.image);


        mTitle.setText(title);
        mDescription.setText(description);
        Picasso.with(getApplicationContext())
                .load(photoUrl)
                .into(mImage);

        for (int i = 0; i < 10; i++) {
            dummyBase.add(new DummyModel(
                    "Menlu Retno Bantah Sembunyikan Pedang Emas Pemberian Raja Salman",
                    "TRIBUNNEWS.COM, JAKARTA - Menteri Luar Negeri Retno Marsudi membenarkan Kerajaan Arab Saudi memberikan sebilah pedang berwarna kuning keemasan. Meski demikian, Retno menegaskan bahwa pedang itu bukan untuk dirinya sebagai personal, melainkan untuk negara.\n" +
                            "\n" +
                            "\"Pedang itu adalah lambang persahabatan dari negara Arab Saudi dengan Indonesia,\" ujar Retno di Kompleks Istana Presiden, Rabu (8/3/2017).\n" +
                            "\n" +
                            "\"Pedang itu tidak diberikan kepada saya,\" lanjut dia.\n" +
                            "\n" +
                            "Baca: Mabes Polri Laporkan Pemberian Pedang Emas dari Kedubes Arab Saudi ke KPK\n" +
                            "\n" +
                            "Baca: Pedang Kerajaan Arab Lambang Persahabatan dengan Indonesia\n" +
                            "\n" +
                            "Lagipula, setelah menerima pedang itu, Retno langsung berkoordinasi dengan Inspektorat Jenderal Kementerian Luar Negeri. Kepada Irjen kementeriannya, Retno melaporkan penerimaan pedang itu.\n" +
                            "\n" +
                            "\"Oleh Irjen Kemenlu nantinya, apakah akan dicatatkan sebagai barang milik negara dan sebagainya, itu silakan,\" ujar Retno.\n" +
                            "\n" +
                            "Retno menampik menutup-nutupi pemberian pedang oleh Saudi itu. Bahkan setelah menerimanya, Retno memfoto pedang dan menyebarkannya melalui ponselnya.\n" +
                            "\n" +
                            "\"Kalau misalnya ada sesuatu yang ingin saya sembunyikan, enggak usah saya potret toh? Kan kalau dipotret ada buktinya,\" ujar Retno.\n" +
                            "\n" +
                            "Retno juga heran ada masyarakat yang ribut bahwa pedang itu terbuat dari emas. Padahal, Retno sendiri belum memastikan apakah pedang itu benar terbuat dari emas atau hanya berwarna keemasan.\n" +
                            "\n" +
                            "Soal terbuat dari emas itu sendiri, Retno mengaku penasaran. Sebab, saat menerimanya, ia sama sekali tidak berpikir pedang itu terbuat atau dilapis emas.\n" +
                            "\n" +
                            "\"Makanya saya heran pada saat sudah ribut, baru tadi pagi saya lihat lagi. Memang warnanya kuning. Saya sendiri tidak melihat itu sejak diberikan,\" ujar Retno.\n" +
                            "\n" +
                            "Penulis: Fabian Januarius Kuwado",
                    "Populer",
                    "12 Menit lalu",
                    "https://images.babe.co.id/files/11189681/images/720/0/20170308/67b68cb7473614c96e0162e433c1bbff.jpeg"));
        }


        mRecyclerView1 = (RecyclerView) findViewById(R.id.rv_mantap);
        mLayoutManager1 = new LinearLayoutManager(getApplicationContext(), LinearLayout.HORIZONTAL, false);
        mRecyclerView1.setLayoutManager(mLayoutManager1);
        mAdapter1 = new KabarTerkaitAdapter(dummyBase, DetailBeritaActivity.this, mRecyclerView1);
        mRecyclerView1.setAdapter(mAdapter1);
        makeSlider();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecyclerView1.setVisibility(View.VISIBLE);
            }
        }, 1000);
    }

    private void makeSlider() {
        mHomeSlider = (SliderLayout) findViewById(R.id.home_slider);

        HashMap<String, String> url_maps = new HashMap<String, String>();
        url_maps.put("Photo1", photoUrl);
        url_maps.put("Photo1", photoUrl);
        url_maps.put("Photo1", photoUrl);
        url_maps.put("Photo1", photoUrl);

        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getApplicationContext());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            //Snackbar.make(, slider.getBundle().get("extra") + "", Snackbar.LENGTH_SHORT).show();
                        }
                    });

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mHomeSlider.addSlider(textSliderView);
        }
        mHomeSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mHomeSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mHomeSlider.setCustomAnimation(new DescriptionAnimation());
        mHomeSlider.setDuration(4000);
        mHomeSlider.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
//            finish();
            mRecyclerView1.setVisibility(View.GONE);
            supportFinishAfterTransition();

        } else if (item.getItemId() == R.id.share_via) {
            String message = "Text I want to share.";
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(Intent.createChooser(share, "Share Melalui Aplikasi"));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.share_menu, menu);

        // return true so that the menu pop up is opened
        return true;
    }
}
