package app.com.baoviet.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import app.com.baoviet.R;
import app.com.baoviet.adapter.ContactTabAdapter;
import app.com.baoviet.constant.Constant;
import app.com.baoviet.entity.HomeMenuItem;

public class TabHomeContact extends Fragment implements ViewPager.OnPageChangeListener {

    private ViewPager viewPagerContact;
    private LinearLayout lnLayoutSlideDotContact;
    private List<HomeMenuItem> listHomeMenu;
    private Context context;

    private ContactTabAdapter childContractInforAdapter;
    private int maxItemPerPage;
    private int pageNumber;
    private int dotCount;
    private ImageView[] dots;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_home_contact, container, false);
        initInterface(view);
        return view;
    }


    public void initInterface(View view) {
        viewPagerContact = (ViewPager) view.findViewById(R.id.viewPagerContact);
        lnLayoutSlideDotContact = (LinearLayout) view.findViewById(R.id.lnLayoutSlideDotContact);
        addListHomeMenu();
    }

    public int getMaxItemPerPage() {
        return maxItemPerPage;
    }

    public void setMaxItemPerPage(int maxItemPerPage) {
        this.maxItemPerPage = maxItemPerPage;
    }

    public void showDot(int pageNumber) {
        // add dot to slideshow image
        // initial dot
        dotCount = pageNumber;
        dots = new ImageView[dotCount];
        for (int i = 0; i < dotCount; i++) {
            dots[i] = new ImageView(getActivity().getApplicationContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.nonactive_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(Constant.DOT_MARGIN_LEFT, Constant.DOT_MARGIN_TOP, Constant.DOT_MARGIN_RIGHT,
                    Constant.DOT_MARGIN_BOTTM);

            lnLayoutSlideDotContact.addView(dots[i], params);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dot));

    }

    public void addListHomeMenu() {
        listHomeMenu = new ArrayList<HomeMenuItem>();
        //security
        HomeMenuItem homeMenuSecurity = new HomeMenuItem();
        homeMenuSecurity.setLabelItem(getString(R.string.title_home_tab_item_security));
        homeMenuSecurity.setImageItem(R.mipmap.ic_menu_security);
        homeMenuSecurity.setKeyTransfer(Constant.TRANSFER_LOGIN_TO_SECURITY);

        //contact
        HomeMenuItem homeMenuContact = new HomeMenuItem();
        homeMenuContact.setLabelItem(getString(R.string.title_home_tab_item_contact));
        homeMenuContact.setImageItem(R.mipmap.ic_menu_contact);
        homeMenuContact.setKeyTransfer(Constant.TRANSFER_LOGIN_TO_CONTACT);

        //policy
        HomeMenuItem homeMenuPolicy = new HomeMenuItem();
        homeMenuPolicy.setLabelItem(getString(R.string.title_home_tab_item_terms));
        homeMenuPolicy.setImageItem(R.mipmap.ic_menu_terms);
        homeMenuPolicy.setKeyTransfer(Constant.TRANSFER_LOGIN_TO_TERMS);

        //chat
        HomeMenuItem homeMenuChat = new HomeMenuItem();
        homeMenuChat.setLabelItem(getString(R.string.title_home_tab_item_chat_online));
        homeMenuChat.setImageItem(R.mipmap.ic_menu_chat);
        homeMenuChat.setKeyTransfer(Constant.TRANSFER_TO_CHAT);

        //hotline
        HomeMenuItem homeMenuHotline = new HomeMenuItem();
        homeMenuHotline.setLabelItem(getString(R.string.title_home_tab_item_hotline));
        homeMenuHotline.setImageItem(R.mipmap.ic_menu_hotline);
        homeMenuHotline.setKeyTransfer(Constant.TRANSFER_TO_HOTLINE);

        listHomeMenu.add(homeMenuSecurity);
        listHomeMenu.add(homeMenuContact);
        listHomeMenu.add(homeMenuPolicy);
        listHomeMenu.add(homeMenuChat);
        listHomeMenu.add(homeMenuHotline);

        checkHeightGridView();
    }

    public void checkHeightGridView() {
        int size = listHomeMenu.size();
        if (size <= getMaxItemPerPage()) {
            pageNumber = Constant.INT_1;
            lnLayoutSlideDotContact.setVisibility(View.GONE);
        } else {
            lnLayoutSlideDotContact.setVisibility(View.VISIBLE);
            if (Constant.INT_0 == size % getMaxItemPerPage()) {
                pageNumber = size / getMaxItemPerPage();
            } else {
                pageNumber = size / getMaxItemPerPage() + Constant.INT_1;
            }
            showDot(pageNumber);
        }
        childContractInforAdapter = new ContactTabAdapter(getChildFragmentManager(), getActivity(), pageNumber, getMaxItemPerPage(), listHomeMenu);
        viewPagerContact.setAdapter(childContractInforAdapter);
        viewPagerContact.setOffscreenPageLimit(pageNumber);
        viewPagerContact.addOnPageChangeListener(this);
    }

    @Override
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        viewPagerContact.setCurrentItem(position);
        for (int i = 0; i < dotCount; i++) {
            dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.nonactive_dot));
        }
        dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dot));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
