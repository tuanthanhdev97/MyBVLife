package app.com.baoviet.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import app.com.baoviet.MainActivity;
import app.com.baoviet.R;
import app.com.baoviet.adapter.HomeMenuAdapter;
import app.com.baoviet.constant.Constant;
import app.com.baoviet.constant.Keys;
import app.com.baoviet.datasource.SaveSharedPreference;
import app.com.baoviet.entity.UserDTO;
import app.com.baoviet.interfaceclass.DialogEventListener;
import app.com.baoviet.utility.AlertDialogCustom;
import app.com.baoviet.utility.Convert;
import app.com.baoviet.utility.StringUtil;

public class HomeFragment extends Fragment implements TabLayout.OnTabSelectedListener, View.OnClickListener, ViewTreeObserver.OnPreDrawListener, DialogEventListener {
    private TabLayout tabLayoutHome;
    private ViewPager viewPagerHome;
    private HomeMenuAdapter adapterHomeMenu;
    private int tabIconColor;
    private TextView tvHomeTitleTab;
    private TextView tvHomeNumberNotify;
    private TextView tvHomeTypeDevice;
    private ImageView imgHomeLogoMenu;
    private ImageView imgHomeLogoBell;
    private UserDTO user;
    private String titleTab;
    private String titleSaved;
    AlertDialogCustom alert;
    String message = Constant.EMPTY;
    String typeDialog = Constant.EMPTY;

    int maxItemPerPage;
    int tabNumber;
    int tabNumberSaved;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initInterface(view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            user = (UserDTO) bundle.getSerializable(Keys.KEY_USER_LOGIN);
            setUser(user);
        }

        //Check height of item to show tab
        ViewTreeObserver greenObserver = viewPagerHome.getViewTreeObserver();
        greenObserver.addOnPreDrawListener(this);

        return view;
    }

    public void initInterface(View view) {
        alert = new AlertDialogCustom(getContext());
        alert.dialogEventListener = this;
        tabLayoutHome = (TabLayout) view.findViewById(R.id.tabLayoutHome);
        viewPagerHome = (ViewPager) view.findViewById(R.id.viewPagerHome);
        tvHomeTitleTab = (TextView) view.findViewById(R.id.tvHomeTitleTab);
        tvHomeNumberNotify = (TextView) view.findViewById(R.id.tvHomeNumberNotify);
        tvHomeTypeDevice = (TextView) view.findViewById(R.id.tvHomeTypeDevice);
        imgHomeLogoMenu = (ImageView) view.findViewById(R.id.imgHomeLogoMenu);
        imgHomeLogoBell = (ImageView) view.findViewById(R.id.imgHomeLogoBell);

        ((MainActivity) getActivity()).hiddenHeader();
        imgHomeLogoBell.setOnClickListener(this);
        imgHomeLogoMenu.setOnClickListener(this);
    }

    @Override
    public boolean onPreDraw() {
        viewPagerHome.getViewTreeObserver().removeOnPreDrawListener(this);
        int column = Constant.INT_0;
        int heightItem = Constant.INT_0;
        int heightEmpty = Convert.pxToDp(getContext(), viewPagerHome.getHeight());
        int cellSpacing = (int) (getContext().getResources().getDimension(R.dimen.dimen_cell_spacing_vertical) / (getResources().getDisplayMetrics().density));
        String typeDevice = tvHomeTypeDevice.getText().toString();
        if (Constant.TYPE_DEVICE_PHONE.equals(typeDevice)) {
            column = getContext().getResources().getInteger(R.integer.column_gridview_phone);
            heightItem = (int) (getContext().getResources().getDimension(R.dimen.dimen_height_of_item_menu) / (getResources().getDisplayMetrics().density));
        } else if (Constant.TYPE_DEVICE_TABLET.equals(typeDevice)) {
            column = getContext().getResources().getInteger(R.integer.column_gridview_tablet);
            heightItem = (int) (getContext().getResources().getDimension(R.dimen.dimen_height_of_item_menu_tablet) / (getResources().getDisplayMetrics().density));
        }
        int heightItemWithSpacing = heightItem + cellSpacing;
        int rate = heightEmpty / heightItemWithSpacing;
        int rowPerPage;
        maxItemPerPage = Constant.INT_0;
        if (Constant.INT_0 == rate) {
            message = getResources().getString(R.string.message_error_screen_resolution);
            typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
            alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
        } else {
            rowPerPage = rate;
            maxItemPerPage = rowPerPage * column;
        }
        addTabLayout();
        return true;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public void addTabLayout() {
        View view1 = getLayoutInflater().inflate(R.layout.customimagetablayout, null);
        View view2 = getLayoutInflater().inflate(R.layout.customimagetablayout, null);
        View view3 = getLayoutInflater().inflate(R.layout.customimagetablayout, null);
        View view4 = getLayoutInflater().inflate(R.layout.customimagetablayout, null);
        View view5 = getLayoutInflater().inflate(R.layout.customimagetablayout, null);
        View view6 = getLayoutInflater().inflate(R.layout.customimagetablayout, null);
        if (Constant.INT_0 == tabLayoutHome.getTabCount()) {
            view1.findViewById(R.id.img_icon).setBackgroundResource(R.mipmap.ic_menu_tab_payment);
            tabLayoutHome.addTab(tabLayoutHome.newTab().setCustomView(view1));

            view2.findViewById(R.id.img_icon).setBackgroundResource(R.mipmap.ic_menu_tab_contract);
            if (user.isAccount()) {
                tabLayoutHome.addTab(tabLayoutHome.newTab().setCustomView(view2));
            }
            view3.findViewById(R.id.img_icon).setBackgroundResource(R.mipmap.ic_menu_tab_infor_account);
            tabLayoutHome.addTab(tabLayoutHome.newTab().setCustomView(view3));

            view4.findViewById(R.id.img_icon).setBackgroundResource(R.mipmap.ic_menu_tab_trade_online);
            tabLayoutHome.addTab(tabLayoutHome.newTab().setCustomView(view4));

            view5.findViewById(R.id.img_icon).setBackgroundResource(R.mipmap.ic_menu_tab_product);
            tabLayoutHome.addTab(tabLayoutHome.newTab().setCustomView(view5));
            view6.findViewById(R.id.img_icon).setBackgroundResource(R.mipmap.ic_menu_tab_contact);
            if (Constant.TYPE_DEVICE_PHONE.equals(tvHomeTypeDevice.getText())) {
                tabLayoutHome.addTab(tabLayoutHome.newTab().setCustomView(view6));
            }
        }
        if (StringUtil.isNullOrEmpty(titleTab)) {
            tabNumberSaved = Constant.INT_0;
            titleSaved = (getString(R.string.title_home_tab_item_payment));
        }
        tvHomeTitleTab.setText(titleSaved);
        tabLayoutHome.getTabAt(tabNumberSaved).select();
        if (user.isAccount()) {
            switch (tabNumberSaved) {
                case Constant.INT_0:
                    view1.findViewById(R.id.img_icon).setBackgroundResource(R.mipmap.ic_menu_tab_payment_large);
                    break;
                case Constant.INT_1:
                    view2.findViewById(R.id.img_icon).setBackgroundResource(R.mipmap.ic_menu_tab_contract_large);
                    break;
                case Constant.INT_2:
                    view3.findViewById(R.id.img_icon).setBackgroundResource(R.mipmap.ic_menu_tab_infor_account_large);
                    break;
                case Constant.INT_3:
                    view4.findViewById(R.id.img_icon).setBackgroundResource(R.mipmap.ic_menu_tab_trade_online_large);
                    break;
                case Constant.INT_4:
                    view5.findViewById(R.id.img_icon).setBackgroundResource(R.mipmap.ic_menu_tab_product_large);
                    break;
                case Constant.INT_5:
                    view6.findViewById(R.id.img_icon).setBackgroundResource(R.mipmap.ic_menu_tab_contact_large);
                    break;
            }
        } else {
            switch (tabNumberSaved) {
                case Constant.INT_0:
                    view1.findViewById(R.id.img_icon).setBackgroundResource(R.mipmap.ic_menu_tab_payment_large);
                    break;
                case Constant.INT_1:
                    view3.findViewById(R.id.img_icon).setBackgroundResource(R.mipmap.ic_menu_tab_infor_account_large);
                    break;
                case Constant.INT_2:
                    view4.findViewById(R.id.img_icon).setBackgroundResource(R.mipmap.ic_menu_tab_trade_online_large);
                    break;
                case Constant.INT_3:
                    view5.findViewById(R.id.img_icon).setBackgroundResource(R.mipmap.ic_menu_tab_product_large);
                    break;
                case Constant.INT_4:
                    view6.findViewById(R.id.img_icon).setBackgroundResource(R.mipmap.ic_menu_tab_contact_large);
                    break;
            }
        }
        addTabToAdapter();
    }

    public void addTabToAdapter() {
        try {
            int numOfTab = tabLayoutHome.getTabCount();
            if (isAdded()) {
                adapterHomeMenu = new HomeMenuAdapter
                        (getChildFragmentManager(), numOfTab, getUser(), getContext(), maxItemPerPage);
            }
            if (user.isAccount()) {
                viewPagerHome.setOffscreenPageLimit(6);
            } else {
                viewPagerHome.setOffscreenPageLimit(5);
            }
            viewPagerHome.setAdapter(adapterHomeMenu);
            viewPagerHome.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutHome));
            tabLayoutHome.addOnTabSelectedListener(this);
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            SaveSharedPreference.setUser(getContext(), Constant.EMPTY, Constant.EMPTY);
            message = getResources().getString(R.string.message_error_system);
            typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
            alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPagerHome.setCurrentItem(tab.getPosition());
        tabIconColor = ContextCompat.getColor(this.getContext(), R.color.white);
//        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
        titleTab = Constant.EMPTY;
        if (user.isAccount()) {
            switch (tab.getPosition()) {
                case Constant.INT_0:
                    View view = tab.getCustomView();
                    view.findViewById(R.id.img_icon).setBackgroundResource(R.mipmap.ic_menu_tab_payment_large);
                    titleTab = getString(R.string.title_home_tab_payment);
                    tabNumber = Constant.INT_0;
                    break;
                case Constant.INT_1:
                    View view2 = tab.getCustomView();
                    view2.findViewById(R.id.img_icon).setBackgroundResource(R.mipmap.ic_menu_tab_contract_large);
                    titleTab = getString(R.string.title_home_tab_contract);
                    tabNumber = Constant.INT_1;
                    break;
                case Constant.INT_2:
                    View view3 = tab.getCustomView();
                    view3.findViewById(R.id.img_icon).setBackgroundResource(R.mipmap.ic_menu_tab_infor_account_large);
                    titleTab = getString(R.string.title_home_tab_infor_account);
                    tabNumber = Constant.INT_2;
                    break;
                case Constant.INT_3:
                    View view4 = tab.getCustomView();
                    view4.findViewById(R.id.img_icon).setBackgroundResource(R.mipmap.ic_menu_tab_trade_online_large);
                    titleTab = getString(R.string.title_home_tab_trade_online);
                    tabNumber = Constant.INT_3;
                    break;
                case Constant.INT_4:
                    View view5 = tab.getCustomView();
                    view5.findViewById(R.id.img_icon).setBackgroundResource(R.mipmap.ic_menu_tab_product_large);
                    titleTab = getString(R.string.title_home_tab_product);
                    tabNumber = Constant.INT_4;
                    break;
                case Constant.INT_5:
                    View view6 = tab.getCustomView();
                    view6.findViewById(R.id.img_icon).setBackgroundResource(R.mipmap.ic_menu_tab_contact_large);
                    titleTab = getString(R.string.title_home_tab_contact);
                    tabNumber = Constant.INT_5;
                    break;

            }
        } else {
            switch (tab.getPosition()) {
                case Constant.INT_0:
                    View view = tab.getCustomView();
                    view.findViewById(R.id.img_icon).setBackgroundResource(R.mipmap.ic_menu_tab_payment_large);
                    titleTab = getString(R.string.title_home_tab_payment);
                    tabNumber = Constant.INT_0;
                    break;
                case Constant.INT_1:
                    View view3 = tab.getCustomView();
                    view3.findViewById(R.id.img_icon).setBackgroundResource(R.mipmap.ic_menu_tab_infor_account_large);
                    titleTab = getString(R.string.title_home_tab_infor_account);
                    tabNumber = Constant.INT_1;
                    break;
                case Constant.INT_2:
                    View view4 = tab.getCustomView();
                    view4.findViewById(R.id.img_icon).setBackgroundResource(R.mipmap.ic_menu_tab_trade_online_large);
                    titleTab = getString(R.string.title_home_tab_trade_online);
                    tabNumber = Constant.INT_2;
                    break;
                case Constant.INT_3:
                    View view5 = tab.getCustomView();
                    view5.findViewById(R.id.img_icon).setBackgroundResource(R.mipmap.ic_menu_tab_product_large);
                    titleTab = getString(R.string.title_home_tab_product);
                    tabNumber = Constant.INT_3;
                    break;
                case Constant.INT_4:
                    View view6 = tab.getCustomView();
                    view6.findViewById(R.id.img_icon).setBackgroundResource(R.mipmap.ic_menu_tab_contact_large);
                    titleTab = getString(R.string.title_home_tab_contact);
                    tabNumber = Constant.INT_4;
                    break;

            }
        }
        tabNumberSaved = tabNumber;
        tvHomeTitleTab.setText(titleTab);

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        if (user.isAccount()) {
            switch (tab.getPosition()) {
                case Constant.INT_0:
                    View view = tab.getCustomView();
                    view.findViewById(R.id.img_icon).setBackgroundResource(R.mipmap.ic_menu_tab_payment);
                    break;
                case Constant.INT_1:
                    View view2 = tab.getCustomView();
                    view2.findViewById(R.id.img_icon).setBackgroundResource(R.mipmap.ic_menu_tab_contract);
                    break;
                case Constant.INT_2:
                    View view3 = tab.getCustomView();
                    view3.findViewById(R.id.img_icon).setBackgroundResource(R.mipmap.ic_menu_tab_infor_account);
                    break;
                case Constant.INT_3:
                    View view4 = tab.getCustomView();
                    view4.findViewById(R.id.img_icon).setBackgroundResource(R.mipmap.ic_menu_tab_trade_online);
                    break;
                case Constant.INT_4:
                    View view5 = tab.getCustomView();
                    view5.findViewById(R.id.img_icon).setBackgroundResource(R.mipmap.ic_menu_tab_product);
                    break;
                case Constant.INT_5:
                    View view6 = tab.getCustomView();
                    view6.findViewById(R.id.img_icon).setBackgroundResource(R.mipmap.ic_menu_tab_contact);
                    break;
            }
        } else {
            switch (tab.getPosition()) {
                case Constant.INT_0:
                    View view1 = tab.getCustomView();
                    view1.findViewById(R.id.img_icon).setBackgroundResource(R.mipmap.ic_menu_tab_payment);
                    break;
                case Constant.INT_1:
                    View view2 = tab.getCustomView();
                    view2.findViewById(R.id.img_icon).setBackgroundResource(R.mipmap.ic_menu_tab_infor_account);
                    break;
                case Constant.INT_2:
                    View view3 = tab.getCustomView();
                    view3.findViewById(R.id.img_icon).setBackgroundResource(R.mipmap.ic_menu_tab_trade_online);
                    break;
                case Constant.INT_3:
                    View view4 = tab.getCustomView();
                    view4.findViewById(R.id.img_icon).setBackgroundResource(R.mipmap.ic_menu_tab_product);
                    break;
                case Constant.INT_4:
                    View view5 = tab.getCustomView();
                    view5.findViewById(R.id.img_icon).setBackgroundResource(R.mipmap.ic_menu_tab_contact);
                    break;
            }
        }

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgHomeLogoMenu:
                ((MainActivity) getActivity()).openCloseMenu();
                break;
            case R.id.imgHomeLogoBell:
                //TempNoti-start
//                ((MainActivity) getActivity()).navigationFragment(Constant.TRANSFER_TO_NOTIFICATION_PAGE);
                //TempNoti-end
                break;
        }
    }

    @Override
    public void OnClickedButtonDialog(boolean isClickOk, String actionDialog, String
            typeDialog) {

    }

    @Override
    public void onPause() {
        super.onPause();
        titleSaved = titleTab;
        tabNumberSaved = tabNumber;
    }
}