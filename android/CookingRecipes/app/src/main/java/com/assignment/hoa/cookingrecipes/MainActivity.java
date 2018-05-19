package com.assignment.hoa.cookingrecipes;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FragmentOne.OnFragmentInteractionListener
,FragmentTwo.OnFragmentInteractionListener,FragmentThree.OnFragmentInteractionListener{
    RecyclerView rc_lowcarb;
//    RcAdapter adapter;
    TabLayout tablayout;
    ViewPager viewpager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        rc_lowcarb = (RecyclerView) findViewById(R.id.rc_lowcarb);
        tablayout = (TabLayout) findViewById(R.id.tablayout);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
//        new getXml().execute("http://www.food.com/rssapi.do?page_type=26&slug=chicken");


        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentOne(),"Easy Dinner Recipes");
        adapter.addFragment(new FragmentTwo(),"Healthy recipes");
        adapter.addFragment(new FragmentThree(),"Seasonal recipes");
        viewpager.setAdapter(adapter);
        // limit number of current page
        viewpager.setOffscreenPageLimit(3);
        tablayout.setupWithViewPager(viewpager);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> mFragments = new ArrayList<>();
        List<String> names = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
        public void addFragment(Fragment frag,String title){
            mFragments.add(frag);
            names.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return names.get(position);
        }
    }
}
