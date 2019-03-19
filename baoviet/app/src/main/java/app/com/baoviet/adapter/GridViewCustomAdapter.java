package app.com.baoviet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import app.com.baoviet.R;
import app.com.baoviet.constant.Constant;
import app.com.baoviet.entity.HomeMenuItem;
import app.com.baoviet.utility.StringUtil;

/**
 * Created by macosmv on 7/16/18.
 */

public class GridViewCustomAdapter extends BaseAdapter {

    private List<HomeMenuItem> listHomeMenu;
    //    private LayoutInflater mLayoutInflater;
    private Context context;

    public GridViewCustomAdapter(Context context, List<HomeMenuItem> listHomeMenu) {
//        this.mLayoutInflater = LayoutInflater.from(context);
        this.listHomeMenu = listHomeMenu;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listHomeMenu == null ? Constant.INT_0 : listHomeMenu.size();
    }

    @Override
    public Object getItem(int position) {
        return listHomeMenu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewImageTextHolder viewImageTextHolder;
        if (view == null) {
            LayoutInflater mLayoutInflater = LayoutInflater.from(this.context);
            view = mLayoutInflater
                    .inflate(R.layout.item_layout_home_menu, viewGroup, false);
            viewImageTextHolder = new ViewImageTextHolder();
            viewImageTextHolder.lnHomeMenu = (LinearLayout) view.findViewById(R.id.lnHomeMenu);
            viewImageTextHolder.tvMenuLabel = (TextView) view.findViewById(
                    R.id.tvHomeMenuLabel);
            viewImageTextHolder.imgMenu = (ImageView) view.findViewById(
                    R.id.imgHomeMenu);
            view.setTag(viewImageTextHolder);
        } else {
            viewImageTextHolder = (ViewImageTextHolder) view.getTag();
        }
        HomeMenuItem homeMenu = listHomeMenu.get(position);
        String label = homeMenu.getLabelItem();
        int image = homeMenu.getImageItem();

        viewImageTextHolder.tvMenuLabel.setText(StringUtil.setTextValue(label));
        viewImageTextHolder.imgMenu.setImageResource(image);

        return view;
    }

    private class ViewImageTextHolder {
        private LinearLayout lnHomeMenu;
        private TextView tvMenuLabel;
        private ImageView imgMenu;
    }
}
