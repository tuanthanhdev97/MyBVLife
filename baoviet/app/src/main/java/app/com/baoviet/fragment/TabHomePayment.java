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

public class TabHomePayment extends Fragment implements AdapterView.OnItemClickListener {
    private GridView gridHomePayment;
    private List<HomeMenuItem> listHomeMenu;
    private GridViewCustomAdapter gridViewCustomAdapter;
    private Context context;
    private UserDTO userDTO;

    Fragment fr = null;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_home_payment, container, false);
        initInterface(view);
        return view;
    }

    public void initInterface(View view) {
        gridHomePayment = (GridView) view.findViewById(R.id.gridHomePayment);
        addListHomeMenu();

    }

    public void addListHomeMenu() {
        listHomeMenu = new ArrayList<HomeMenuItem>();
        HomeMenuItem homeMenuPayment = new HomeMenuItem();
        homeMenuPayment.setLabelItem(getString(R.string.title_home_tab_item_payment));
        homeMenuPayment.setImageItem(R.mipmap.ic_menu_payment);
        homeMenuPayment.setKeyTransfer(Constant.TRANSFER_LOGIN_TO_PAYMENT);
        listHomeMenu.add(homeMenuPayment);
        gridViewCustomAdapter = new GridViewCustomAdapter(getActivity(), listHomeMenu);
        gridHomePayment.setAdapter(gridViewCustomAdapter);
        gridHomePayment.setOnItemClickListener(this);
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
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        fm = getActivity().getSupportFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        HomeMenuItem homeMenuItem = listHomeMenu.get(position);
        String keytransfer = homeMenuItem.getKeyTransfer();
        switch (keytransfer) {
            case Constant.TRANSFER_LOGIN_TO_PAYMENT:
                ((MainActivity) getActivity()).tvActionBar.setVisibility(View.VISIBLE);
                ((MainActivity) getActivity()).tvActionBar.setText(R.string.txt_payment_header);
                Bundle bundle = new Bundle();
                if (!StringUtil.isNullOrEmpty(Constant.TOKEN)) {
                    bundle.putString(Keys.KEY_PHONE_NUMBER, StringUtil.setTextValue(getUserDTO().getMobifone()));
                    bundle.putString(Keys.KEY_FULLNAME, StringUtil.setTextValue(getUserDTO().getFullName()));
                    bundle.putStringArrayList(Keys.KEY_LIST_ACCOUNT_NUMBER, (ArrayList<String>) getUserDTO().getAccounts());
                }
                fr = new PaymentFragment();
                fr.setArguments(bundle);
                fragmentTransaction.replace(R.id.frameContent, fr, Constant.TRANSFER_LOGIN_TO_PAYMENT);
                fragmentTransaction.addToBackStack(Constant.TRANSFER_LOGIN_TO_PAYMENT);
                fragmentTransaction.commit();
                break;
        }
    }

}