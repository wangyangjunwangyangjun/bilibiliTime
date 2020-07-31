package com.example.time.GuidePage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.time.LoginActivity;
import com.example.time.R;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {
    private ViewPager viewPager = null;
    private int[] resources = {R.layout.viewpage_image1, R.layout.viewpage_image2, R.layout.viewpage_image3};
    private List<View> listViews = new ArrayList<View>();
    private LayoutInflater inflater = null;
    private Button start;
    private Button skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
    }
    private void initView() {
        inflater = LayoutInflater.from(this);
        viewPager = (ViewPager) findViewById(R.id.guide_viewpager);
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
        start = (Button) findViewById(R.id.start);
        skip = findViewById(R.id.skip);
        start.setVisibility(View.INVISIBLE);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        for(int i=0;i<resources.length;i++){
            View view = inflater.inflate(resources[i], null);
            listViews.add(view);
        }
        viewPager.setAdapter(new MyPageAdapter());
    }
    private class MyPageAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return listViews.size();
        }
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager)container).addView(listViews.get(position));
            return listViews.get(position);
        }
        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager)container).removeView(listViews.get(position));
        }
    }
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
        @Override
        public void onPageSelected(int arg0) {
            if (arg0 == resources.length -1){
                start.setVisibility(View.VISIBLE);
            }else {
                start.setVisibility(View.INVISIBLE);
            }
        }
    }
}