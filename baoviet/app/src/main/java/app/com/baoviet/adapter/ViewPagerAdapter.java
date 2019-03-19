package app.com.baoviet.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import app.com.baoviet.R;

public class ViewPagerAdapter extends PagerAdapter {
    private ArrayList<Integer> images;
    private LayoutInflater inflater;
    private Context context;

    public ViewPagerAdapter(Context context, ArrayList<Integer> images) {
        this.context = context;
        this.images = images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = null;
        try {
            myImageLayout = inflater.inflate(R.layout.image_layout, view, false);
            ImageView myImage = (ImageView) myImageLayout
                    .findViewById(R.id.imgSlideShow);
            myImage.setImageResource(images.get(position));
            view.addView(myImageLayout, 0);

        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            System.out.println(e.getMessage());
        }
        return myImageLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
