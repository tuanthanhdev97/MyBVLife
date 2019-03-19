package app.com.baoviet.utility;

import java.util.TimerTask;

import app.com.baoviet.constant.Constant;

import android.app.Activity;
import android.support.v4.view.ViewPager;

public class TimeTask extends TimerTask {
	private Activity activity;
	private ViewPager viewpager;

	public TimeTask(Activity activity, ViewPager viewpager) {
		this.activity = activity;
		this.viewpager = viewpager;
	}

	@Override
	public void run() {
		activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (viewpager.getCurrentItem() == Constant.INT_0) {
					viewpager.setCurrentItem(Constant.INT_1);
				} else if (viewpager.getCurrentItem() == Constant.INT_1) {
					viewpager.setCurrentItem(Constant.INT_2);
				} else {
					viewpager.setCurrentItem(Constant.INT_0);
				}
			}
		});
	}

}
