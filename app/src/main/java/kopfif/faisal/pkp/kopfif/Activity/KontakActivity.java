package kopfif.faisal.pkp.kopfif.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import kopfif.faisal.pkp.kopfif.R;
import kopfif.faisal.pkp.kopfif.View.SquareImageView;

public class KontakActivity extends AppCompatActivity {
    private TextView mName;
    private TextView mContact;
    private SquareImageView mImage;

    private String name;
    private String contact;
    private String photoUrl;
    private static final int CALL_REQUEST_CODE = 4;
    private View rootView;
    private ImageView mMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontak);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            name = b.getString("name");
            contact = b.getString("text1");
            photoUrl = b.getString("photoUrl");
        }


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);

        mName = (TextView) findViewById(R.id.title);
        mContact = (TextView) findViewById(R.id.contact);
        mImage = (SquareImageView) findViewById(R.id.image);
        mMessage = (ImageView) findViewById(R.id.message);
//        mName.setText(name);
        mContact.setText(contact);
        Picasso.with(getApplicationContext())
                .load(photoUrl)
                .into(mImage);

        CardView mCall = (CardView) findViewById(R.id.call);
        mCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, CALL_REQUEST_CODE);
                } else {
                    call();
                }
            }
        });

        mMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSmsIntent();
            }
        });

        rootView = findViewById(R.id.rootView);
    }

    private void call() {
        Intent intent = new Intent(Intent.ACTION_CALL);

        intent.setData(Uri.parse("tel:" + contact));
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(intent);
    }

    public void startSmsIntent() {
        Intent smsIntent = new Intent( Intent.ACTION_SENDTO, Uri.parse("smsto:"+contact) );
        try{
            startActivity(smsIntent);
        }
        catch( ActivityNotFoundException e ){

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            supportFinishAfterTransition();
            //finish();
        } else if (item.getItemId() == R.id.share_via) {
            String message = "Text I want to share.";
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(Intent.createChooser(share, "Share Melalui Aplikasi"));
        }
        return super.onOptionsItemSelected(item);
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CALL_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    call();
                } else {
                    Snackbar.make(rootView, R.string.call_permission_reject, Snackbar.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
