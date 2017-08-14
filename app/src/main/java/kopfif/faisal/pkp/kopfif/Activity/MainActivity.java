package kopfif.faisal.pkp.kopfif.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mikepenz.ionicons_typeface_library.Ionicons;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;
import java.util.List;

import kopfif.faisal.pkp.kopfif.Fragment.AgendaFragment;
import kopfif.faisal.pkp.kopfif.Fragment.BerandaFragment;
import kopfif.faisal.pkp.kopfif.Fragment.CategoryFragment;
import kopfif.faisal.pkp.kopfif.Fragment.ChatingFragment;
import kopfif.faisal.pkp.kopfif.Fragment.KabarFragment;
import kopfif.faisal.pkp.kopfif.Fragment.KontakFragment;
import kopfif.faisal.pkp.kopfif.Fragment.MemberFragment;
import kopfif.faisal.pkp.kopfif.Fragment.PAnggotaFragment;
import kopfif.faisal.pkp.kopfif.Fragment.PopulerFragment;
import kopfif.faisal.pkp.kopfif.Fragment.SMChanellingFragment;
import kopfif.faisal.pkp.kopfif.Fragment.SettingFragment;
import kopfif.faisal.pkp.kopfif.Fragment.TerbaruFragment;
import kopfif.faisal.pkp.kopfif.Function.FragNavControllers;
import kopfif.faisal.pkp.kopfif.Function.SessionManagerUtil;
import kopfif.faisal.pkp.kopfif.R;

public class MainActivity extends AppCompatActivity implements
        BerandaFragment.OnFragmentInteractionListener,
        AgendaFragment.OnFragmentInteractionListener,
        ChatingFragment.OnFragmentInteractionListener,
        KabarFragment.OnFragmentInteractionListener,
        KontakFragment.OnFragmentInteractionListener,
        PAnggotaFragment.OnFragmentInteractionListener,
        SettingFragment.OnFragmentInteractionListener,
        SMChanellingFragment.OnFragmentInteractionListener,
        MemberFragment.OnFragmentInteractionListener,
        PopulerFragment.OnFragmentInteractionListener,
        TerbaruFragment.OnFragmentInteractionListener,
        CategoryFragment.OnFragmentInteractionListener {

    private Drawer navigationDrawer;
    private FragNavControllers fragNavController;

    private final int TAB_BERANDA = FragNavControllers.TAB1;
    private final int TAB_AGENDA = FragNavControllers.TAB2;
    private final int TAB_KONTAK = FragNavControllers.TAB3;
    private final int TAB_KABAR = FragNavControllers.TAB4;
    private final int TAB_PANGGOTA = FragNavControllers.TAB5;
    private final int TAB_CHATING = FragNavControllers.TAB6;
    private final int TAB_SMCHANELLING = FragNavControllers.TAB7;
    private final int TAB_SETTING = FragNavControllers.TAB8;
    private final int TAB_MEMBER = FragNavControllers.TAB9;
    private SessionManagerUtil util;
    private AppBarLayout appBarLayout;
    private Boolean doubleBackToExitPressedOnce = false;
//    private ArrayList<DrawerItemModel> drawerBase = new ArrayList<DrawerItemModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        util = new SessionManagerUtil(getApplicationContext());
        appBarLayout = (AppBarLayout) findViewById(R.id.my_appbar_container);

        List<Fragment> fragments = new ArrayList<>(4);

        fragments.add(BerandaFragment.newInstance("", ""));
        fragments.add(AgendaFragment.newInstance("", ""));
        fragments.add(KontakFragment.newInstance("", ""));
        fragments.add(KabarFragment.newInstance("", ""));
        fragments.add(PAnggotaFragment.newInstance("", ""));
        fragments.add(ChatingFragment.newInstance("", ""));
        fragments.add(SMChanellingFragment.newInstance("", ""));
        fragments.add(SettingFragment.newInstance("", ""));
        fragments.add(MemberFragment.newInstance("", ""));

        //link fragments to container
        fragNavController = new FragNavControllers(getSupportFragmentManager(),
                R.id.container, fragments);

        makeDrawer(savedInstanceState);

    }

    private boolean checkLogin() {
        String isLogin = util.sessionUserGet("isLogin");
        if (isLogin.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    private void makeDrawer(Bundle savedInstanceState) {

        //Toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Beranda");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);

        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.material_background)
                .addProfiles(
                        new ProfileDrawerItem()
//                                .withName(util.sessionUserGet("name"))
                                .withEmail(util.sessionUserGet("email"))
                                .withIcon(getResources().getDrawable(R.drawable.avatar))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        //if you want to update the items at a later time it is recommended to keep it in a variable
        navigationDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggleAnimated(true)
                .withTranslucentStatusBar(false)
                .withFullscreen(true)
                .withSavedInstance(savedInstanceState)
                .withAccountHeader(headerResult)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem == null) return false;
                        long id = drawerItem.getIdentifier();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            appBarLayout.setElevation(6);
                            getSupportActionBar().setElevation(6);
                        }
                        switch (Long.toString(id)) {
                            case "0":
                                fragNavController.switchTab(TAB_BERANDA);
                                toolbar.setTitle("Beranda");
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    appBarLayout.setElevation(0);
                                    getSupportActionBar().setElevation(0);
                                }
                                break;
                            case "1":
                                fragNavController.switchTab(TAB_AGENDA);
                                toolbar.setTitle("Agenda");
                                break;
                            case "2":
                                fragNavController.switchTab(TAB_KONTAK);
                                toolbar.setTitle("Kontak");
                                break;
                            case "3":
                                fragNavController.switchTab(TAB_KABAR);
                                toolbar.setTitle("Kabar 2000");
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    appBarLayout.setElevation(0);
                                    getSupportActionBar().setElevation(0);
                                }
                                break;
                            case "4":
                                fragNavController.switchTab(TAB_PANGGOTA);
                                toolbar.setTitle("Pengajuan Anggota");
                                break;
                            case "5":
                                fragNavController.switchTab(TAB_CHATING);
                                toolbar.setTitle("Chating");
                                break;
                            case "6":
                                fragNavController.switchTab(TAB_MEMBER);
                                toolbar.setTitle("Member");
                                break;
                            case "7":
                                fragNavController.switchTab(TAB_SMCHANELLING);
                                toolbar.setTitle("Social Media Chaneling");
                                break;
                            case "8":
//                                 fragNavController.switchTab(TAB_SETTING);
//                                toolbar.setTitle("Setting");
                                startActivity(new Intent(getApplicationContext(), SetelanActivity.class));
                                break;
                            case "9":
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                break;
                            case "10":
                                util.sessionUserSave("isLogin", "false");
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                break;
                        }
                        navigationDrawer.closeDrawer();
                        return true;
                    }
                })

                .build();

        seedDrawer();


    }

    private void seedDrawer() {
        PrimaryDrawerItem beranda = new PrimaryDrawerItem()
                .withIdentifier(0)
                .withName("Beranda")
                .withIcon(Ionicons.Icon.ion_android_home);
//        PrimaryDrawerItem agenda = new PrimaryDrawerItem()
//                .withIdentifier(1)
//                .withName("Agenda")
//                .withIcon(Ionicons.Icon.ion_android_calendar);
        PrimaryDrawerItem kontak = new PrimaryDrawerItem()
                .withIdentifier(2)
                .withName("Kontak")
                .withIcon(Ionicons.Icon.ion_android_contacts);
        PrimaryDrawerItem kabar2000 = new PrimaryDrawerItem()
                .withIdentifier(3)
                .withName("Kabar 2000")
                .withIcon(Ionicons.Icon.ion_ios_paper);
        PrimaryDrawerItem pAnggota = new PrimaryDrawerItem()
                .withIdentifier(4)
                .withName("Pengajuan Anggota")
                .withIcon(Ionicons.Icon.ion_android_contact);
        PrimaryDrawerItem chating = new PrimaryDrawerItem()
                .withIdentifier(5)
                .withName("Chating")
                .withIcon(Ionicons.Icon.ion_chatboxes);
        PrimaryDrawerItem member = new PrimaryDrawerItem()
                .withIdentifier(6)
                .withName("Member Only")
                .withIcon(Ionicons.Icon.ion_social_usd);
//        SecondaryDrawerItem sMC = new SecondaryDrawerItem()
//                .withIdentifier(7)
//                .withName("Social Media Chaneling")
//                .withIcon(Ionicons.Icon.ion_android_share);
        SecondaryDrawerItem setting = new SecondaryDrawerItem()
                .withIdentifier(8)
                .withName("Setting")
                .withIcon(Ionicons.Icon.ion_android_settings);
        SecondaryDrawerItem login = new SecondaryDrawerItem()
                .withIdentifier(9)
                .withName("Login")
                .withIcon(Ionicons.Icon.ion_log_in);
        SecondaryDrawerItem logout = new SecondaryDrawerItem()
                .withIdentifier(10)
                .withName("Logout")
                .withIcon(Ionicons.Icon.ion_log_out);

        navigationDrawer.removeAllItems();
        if (checkLogin()) {
            navigationDrawer.addItems(beranda, kontak, kabar2000, pAnggota, chating, member, new DividerDrawerItem(), setting, logout);
        } else {
            navigationDrawer.addItems(beranda, kontak, kabar2000, pAnggota, new DividerDrawerItem(), setting, login);
        }
        navigationDrawer.setSelection(0);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onKabarFragmentEnter() {
        Log.d("", "");
    }

    @Override
    public void onKabarFragmentExit() {
        Log.d("", "");
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            util.sessionUserSave("isLogin", "false");
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
//            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tekan lagi untuk keluar", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
