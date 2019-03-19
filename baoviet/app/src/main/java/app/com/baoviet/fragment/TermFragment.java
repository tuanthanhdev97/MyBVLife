package app.com.baoviet.fragment;

import app.com.baoviet.R;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TermFragment extends Fragment {
	private TextView tvContent;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_terms, container, false);
		initInterface(view);
		tvContent.setText(Html.fromHtml(getString(R.string.content_term)));
		return view;
	}

	public void initInterface(View view) {
		tvContent = (TextView) view.findViewById(R.id.tvContentTerm);
	}

}