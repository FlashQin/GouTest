package net.goutalk.fowit.Adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import net.goutalk.fowit.R;

import java.util.List;


public class ViewPagerAdapter extends FragmentPagerAdapter {
    private String tabTitles[] = new String[]{(R.string.body_detail) + "", R.string.product_case + "", R.string.evaluation + ""};
    private List<Fragment> list;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {

        return list.get(position);
    }


    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
