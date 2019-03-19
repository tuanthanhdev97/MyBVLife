package app.com.baoviet.fragment;

import app.com.baoviet.R;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ExceptionFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exception, container, false);
        initInterface(view);

        return view;
    }

    public void initInterface(View view) {
    }

}