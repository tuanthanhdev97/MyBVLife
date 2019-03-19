package app.com.baoviet.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import app.com.baoviet.MainActivity;
import app.com.baoviet.R;
import app.com.baoviet.adapter.GridViewCustomAdapter;
import app.com.baoviet.constant.Constant;
import app.com.baoviet.constant.Keys;
import app.com.baoviet.entity.HomeMenuItem;
import app.com.baoviet.entity.UserDTO;
import app.com.baoviet.utility.StringUtil;

public class TabHomeInforAccount extends Fragment implements AdapterView.OnItemClickListener {
    private GridView gridHomeInforAccount;
    private List<HomeMenuItem> listHomeMenu;
    private GridViewCustomAdapter gridViewCustomAdapter;
    private Context context;
    private UserDTO userDTO;
    Fragment fr = null;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_home_infor_account, container, false);
        initInterface(view);
        return view;
    }


    public void initInterface(View view) {
        gridHomeInforAccount = (GridView) view.findViewById(R.id.gridHomeInforAccount);
        gridHomeInforAccount.setOnItemClickListener(this);
        addListHomeMenu();
    }

    public void addListHomeMenu() {
        listHomeMenu = new ArrayList<HomeMenuItem>();
        HomeMenuItem homeMenuInforAccount = new HomeMenuItem();
        homeMenuInforAccount.setLabelItem(getString(R.string.title_home_tab_item_infor_account));
        homeMenuInforAccount.setImageItem(R.mipmap.ic_menu_infor_account);
        homeMenuInforAccount.setKeyTransfer(Constant.NAV_INFOR_ACCOUNT);

        HomeMenuItem homeMenuUpdateAccount = new HomeMenuItem();
        homeMenuUpdateAccount.setLabelItem(getString(R.string.title_home_tab_item_update_infor));
        homeMenuUpdateAccount.setImageItem(R.mipmap.ic_menu_update_account);
        homeMenuUpdateAccount.setKeyTransfer(Constant.NAV_UPDATE_ACCOUNT);

        listHomeMenu.add(homeMenuInforAccount);
        listHomeMenu.add(homeMenuUpdateAccount);
        gridViewCustomAdapter = new GridViewCustomAdapter(getActivity(), listHomeMenu);
        gridHomeInforAccount.setAdapter(gridViewCustomAdapter);
    }

    public GridView getGridHomeInforAccount() {
        return gridHomeInforAccount;
    }

    public void setGridHomeInforAccount(GridView gridHomeInforAccount) {
        this.gridHomeInforAccount = gridHomeInforAccount;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    @Override
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        fm = getActivity().getSupportFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        HomeMenuItem homeMenuItem = listHomeMenu.get(position);
        String keytransfer = homeMenuItem.getKeyTransfer();
        switch (keytransfer) {
            case Constant.NAV_INFOR_ACCOUNT:
                ((MainActivity) getActivity()).tvActionBar.setVisibility(View.VISIBLE);
                ((MainActivity) getActivity()).tvActionBar.setText(R.string.txt_infor_account_header);
                Bundle bundleInforAcc = new Bundle();
                if (!StringUtil.isNullOrEmpty(Constant.TOKEN)) {
                    bundleInforAcc.putString(Keys.KEY_USER_CODE, getUserDTO().getUserCode());
                }
                fr = new InforAccountFragment();
                fr.setArguments(bundleInforAcc);
                fragmentTransaction.replace(R.id.frameContent, fr, Constant.NAV_INFOR_ACCOUNT);
                fragmentTransaction.addToBackStack(Constant.NAV_INFOR_ACCOUNT);
                fragmentTransaction.commit();
                break;
            case Constant.NAV_UPDATE_ACCOUNT:
                ((MainActivity) getActivity()).tvActionBar.setVisibility(View.VISIBLE);
                ((MainActivity) getActivity()).tvActionBar.setText(R.string.txt_update_account_header);
                Bundle bundleUpdateAccount = new Bundle();
                if (!StringUtil.isNullOrEmpty(Constant.TOKEN)) {
                    bundleUpdateAccount.putString(Keys.KEY_USER_TYPE_CODE, getUserDTO().getUserTypeCode());
                    bundleUpdateAccount.putString(Keys.KEY_USERNAME, getUserDTO().getUserCode());
                }
                fr = new UpdateAccountFragment();
                fr.setArguments(bundleUpdateAccount);
                fragmentTransaction.replace(R.id.frameContent, fr, Constant.NAV_UPDATE_ACCOUNT);
                fragmentTransaction.addToBackStack(Constant.NAV_UPDATE_ACCOUNT);
                fragmentTransaction.commit();
                break;
        }
    }
}