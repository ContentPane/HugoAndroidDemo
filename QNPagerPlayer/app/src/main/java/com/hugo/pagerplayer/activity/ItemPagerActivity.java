package com.hugo.pagerplayer.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hugo.pagerplayer.Config;
import com.hugo.pagerplayer.R;
import com.hugo.pagerplayer.fragment.PlayerFragment;
import com.hugo.pagerplayer.view.DefaultTransformer;
import com.hugo.pagerplayer.view.VerticalViewPager;

import org.simple.eventbus.EventBus;



public class ItemPagerActivity extends AppCompatActivity {

    final static String TAG = "==ItemPagerActivity==";


    public static int mIndex;
    private VerticalViewPager viewPager;
    private FragmentStatePagerAdapter adapter;


    public static final String[] TEST_URL_ARRAY = {
            "http://hifang-video.fujuhaofang.com/video-android-20180824164044",
            "http://ofy2p10w1.bkt.clouddn.com/passive.mp4",
            "http://ohjxta96r.bkt.clouddn.com/life_game.mp4",
            "http://ohjxta96r.bkt.clouddn.com/concat_1.mp4",
            "http://ohjxta96r.bkt.clouddn.com/oceans.mp4",
            "http://ohjxta96r.bkt.clouddn.com/tmp1510308023323",
            "http://oymtq6tey.bkt.clouddn.com/test-1.MP4",
            "http://oymtq6tey.bkt.clouddn.com/short_video_184_41_20171111134121.mp4",
            "http://oymtq6tey.bkt.clouddn.com/short_video_101_49_20171203212235.mp4",
            "http://oymtq6tey.bkt.clouddn.com/short_video_184_41_20171111130411.mp4",
            "https://beespic.nnxianggu.com/FqHhXqtYWUmfB40Vu8jP6X4giWqZ.mp4"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);


        mIndex = getIntent().getIntExtra(Config.POSITION, 0);

        viewPager = new VerticalViewPager(this);
        viewPager.setId(R.id.viewPager);
        setContentView(viewPager);

        FragmentManager fm = getSupportFragmentManager();
        adapter = new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                String url;
                url = TEST_URL_ARRAY[position % TEST_URL_ARRAY.length].trim();
                return PlayerFragment.newInstance(url, position);
            }

            @Override
            public int getCount() {
                return 20;
            }
        };


        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(false, new DefaultTransformer());
        viewPager.setCurrentItem(mIndex);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mIndex = position;
                Log.e(TAG, "position==" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e(TAG, "state==" + state);
                if (state == 1) {
                    EventBus.getDefault().post(0, Config.EVENT_PAUSE);
                } else if (state == 0) {
                    EventBus.getDefault().post(mIndex, Config.EVENT_START);

                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
