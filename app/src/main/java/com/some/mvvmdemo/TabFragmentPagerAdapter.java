package com.some.mvvmdemo;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import com.some.common.util.SampleApplicationContext;
import com.some.mvvmdemo.base.BaseFragment;


import java.util.List;


/**
 * Author：lejialin
 * Date: 2015-06-05
 */
public class TabFragmentPagerAdapter extends FragmentPagerAdapter {

    private static final int TYPE_RES = 0;
    private static final int TYPE_STRING = 1;

    private  int[] titles;
    private  String[] titleStrs;

    protected List<BaseFragment> fragments;

    private int type ;

    public TabFragmentPagerAdapter(FragmentManager fm, List<BaseFragment> fragmentList) {
        super(fm);
        fragments = fragmentList;
    }

    public TabFragmentPagerAdapter(FragmentManager fm, List<BaseFragment> fragmentList, int[] titles) {
        super(fm);
        fragments = fragmentList;
        this.titles = titles;
        type = TYPE_RES;
    }

    public TabFragmentPagerAdapter(FragmentManager fm, List<BaseFragment> fragmentList, String[] titles) {
        super(fm);
        fragments = fragmentList;
        this.titleStrs = titles;
        type = TYPE_STRING;
    }

    public void setFragments(List<BaseFragment> fragmentList){
        fragments = fragmentList;
        notifyDataSetChanged();
    }

    public void setTitleStrs(String[] titles){
        this.titleStrs = titles;
    }

    @Override
    public Fragment getItem(int arg0) {
        if( fragments == null || fragments.size() < arg0){
            return  null;
        }else {
            return fragments.get(arg0);
        }
    }

    @Override
    public int getCount() {
        if (fragments != null){
            return fragments.size();

        }else {
            return  0;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(type == TYPE_RES){
            if (fragments != null && titles != null
                    && titles.length > position){
                int resId = titles[position];
                return resId != 0 ?
                        SampleApplicationContext.application.getText(resId) : "";
            }
        }else if(type == TYPE_STRING){
            if (fragments != null && titleStrs != null
                    && titleStrs.length > position){
                return titleStrs[position];
            }
        }

        return "";
    }

    public void onPageSelected(int position) {
    }
}


