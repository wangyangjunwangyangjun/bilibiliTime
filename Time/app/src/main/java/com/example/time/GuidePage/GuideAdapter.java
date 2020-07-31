package com.example.time.GuidePage;
import android.view.View;
import android.view.ViewGroup;
import androidx.viewpager.widget.PagerAdapter;
import java.util.List;

public class GuideAdapter extends PagerAdapter {
    public List<View> viewList;
    public GuideAdapter(List<View> viewList) {
        this.viewList = viewList;
    }
    @Override
    public int getCount() {
        return viewList.size();
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }
}
