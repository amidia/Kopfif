package kopfif.faisal.pkp.kopfif.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import kopfif.faisal.pkp.kopfif.Fragment.CategoryFragment;
import kopfif.faisal.pkp.kopfif.Fragment.PopulerFragment;
import kopfif.faisal.pkp.kopfif.Fragment.TerbaruFragment;
import kopfif.faisal.pkp.kopfif.R;


public class PagerAdapter extends FragmentStatePagerAdapter {
    int pageCount = 3;

    private String[] tabTitle = {
            "Populer",
            "Terbaru",
            "Category",
    };


    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                PopulerFragment tab1 = new PopulerFragment();
                return tab1;
            case 1:
                PopulerFragment tab2 = new PopulerFragment();
                return tab2;
            case 2:
                PopulerFragment tab3 = new PopulerFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return pageCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }
}