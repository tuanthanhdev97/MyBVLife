package app.com.baoviet.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.com.baoviet.R;

public class GuideFragment extends Fragment {
	private TextView tvContent;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_guide, container, false);
		initInterface(view);
		return view;
	}

	public void initInterface(View view) {
	}

}