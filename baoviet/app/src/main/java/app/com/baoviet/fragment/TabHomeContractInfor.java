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
import app.com.baoviet.adapter.ContractInforTabAdapter;
import app.com.baoviet.constant.Constant;
import app.com.baoviet.entity.HomeMenuItem;
import app.com.baoviet.entity.UserDTO;
import app.com.baoviet.utility.StringUtil;

public class TabHomeContractInfor extends Fragment implements ViewPager.OnPageChangeListener {
    private ViewPager viewPagerContractInfor;
    private LinearLayout lnLayoutSlideDotContractInfor;
    private List<HomeMenuItem> listHomeMenu;
    private Context context;
    private UserDTO user;

    private ContractInforTabAdapter childContractInforAdapter;
    private int maxItemPerPage;
    private int pageNumber;
    private int dotCount;
    private ImageView[] dots;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_home_contract_infor, container, false);
        initInterface(view);
        return view;
    }

    public void initInterface(View view) {
        viewPagerContractInfor = (ViewPager) view.findViewById(R.id.viewPagerContractInfor);
        lnLayoutSlideDotContractInfor = (LinearLayout) view.findViewById(R.id.lnLayoutSlideDotContractInfor);
        addListHomeMenu();
    }

    public int getMaxItemPerPage() {
        return maxItemPerPage;
    }

    public void setMaxItemPerPage(int maxItemPerPage) {
        this.maxItemPerPage = maxItemPerPage;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public void addListHomeMenu() {
        listHomeMenu = new ArrayList<HomeMenuItem>();
        //general infor
        HomeMenuItem homeMenuGeneralInfor = new HomeMenuItem();
        homeMenuGeneralInfor.setLabelItem(getString(R.string.title_home_tab_item_general_infor));
        homeMenuGeneralInfor.setImageItem(R.mipmap.ic_menu_general_infor);
        homeMenuGeneralInfor.setKeyTransfer(Constant.TRANSFER_CONTRACT_TO_GENERAL_INFOR);
        //customer infor
        HomeMenuItem homeMenuCustomerInfor = new HomeMenuItem();
        homeMenuCustomerInfor.setLabelItem(getString(R.string.title_home_tab_item_customer_infor));
        homeMenuCustomerInfor.setImageItem(R.mipmap.ic_menu_customer_infor);
        homeMenuCustomerInfor.setKeyTransfer(Constant.TRANSFER_CONTRACT_TO_CUSTOMER_INFOR);
        //value account
        HomeMenuItem homeMenuValueAccount = new HomeMenuItem();
        homeMenuValueAccount.setLabelItem(getString(R.string.title_home_tab_item_value_account));
        homeMenuValueAccount.setImageItem(R.mipmap.ic_menu_value_account);
        homeMenuValueAccount.setKeyTransfer(Constant.TRANSFER_CONTRACT_TO_VALUE_ACCOUNT);
        //payment process
        HomeMenuItem homeMenuPaymentProcess = new HomeMenuItem();
        homeMenuPaymentProcess.setLabelItem(getString(R.string.title_home_tab_item_payment_process));
        homeMenuPaymentProcess.setImageItem(R.mipmap.ic_menu_payment_process);
        homeMenuPaymentProcess.setKeyTransfer(Constant.TRANSFER_CONTRACT_TO_PAYMENT_PROCESS);
        //fee and cost
        HomeMenuItem homeMenuFee = new HomeMenuItem();
        homeMenuFee.setLabelItem(getString(R.string.title_home_tab_item_fee_and_cost));
        homeMenuFee.setImageItem(R.mipmap.ic_menu_fee_and_cost);
        homeMenuFee.setKeyTransfer(Constant.TRANSFER_CONTRACT_TO_FEE_AND_COST);
        //benefit
        HomeMenuItem homeMenuBenefit = new HomeMenuItem();
        homeMenuBenefit.setLabelItem(getString(R.string.title_home_tab_item_benefit));
        homeMenuBenefit.setImageItem(R.mipmap.ic_menu_benefit);
        homeMenuBenefit.setKeyTransfer(Constant.TRANSFER_CONTRACT_TO_BENEFIT);
        //bill
        HomeMenuItem homeMenuEbill = new HomeMenuItem();
        homeMenuEbill.setLabelItem(getString(R.string.title_home_tab_item_ebill));
        homeMenuEbill.setImageItem(R.mipmap.ic_menu_bill);
        homeMenuEbill.setKeyTransfer(Constant.TRANSFER_CONTRACT_TO_EBILL);
        //report
        HomeMenuItem homeMenuReport = new HomeMenuItem();
        homeMenuReport.setLabelItem(getString(R.string.title_home_tab_item_annual_report));
        homeMenuReport.setImageItem(R.mipmap.ic_menu_report);
        homeMenuReport.setKeyTransfer(Constant.TRANSFER_CONTRACT_TO_ANNUAL_REPORT);


        listHomeMenu.add(homeMenuGeneralInfor);
        listHomeMenu.add(homeMenuCustomerInfor);
//        listHomeMenu.add(homeMenuValueAccount);
        listHomeMenu.add(homeMenuPaymentProcess);
        if (StringUtil.isNullOrEmpty(user.getAccountOtherNumber())) {
//            listHomeMenu.add(homeMenuFee);
        }
        listHomeMenu.add(homeMenuBenefit);
        listHomeMenu.add(homeMenuEbill);
        listHomeMenu.add(homeMenuReport);

        checkHeightGridView();
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

            lnLayoutSlideDotContractInfor.addView(dots[i], params);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dot));

    }

    public void checkHeightGridView() {
        int size = listHomeMenu.size();
        if (size <= getMaxItemPerPage()) {
            pageNumber = Constant.INT_1;
            lnLayoutSlideDotContractInfor.setVisibility(View.GONE);
        } else {
            lnLayoutSlideDotContractInfor.setVisibility(View.VISIBLE);
            if (Constant.INT_0 == size % getMaxItemPerPage()) {
                pageNumber = size / getMaxItemPerPage();
            } else {
                pageNumber = size / getMaxItemPerPage() + Constant.INT_1;
            }
            showDot(pageNumber);
        }
        if (isAdded()) {
            childContractInforAdapter = new ContractInforTabAdapter
                    (getChildFragmentManager(), getUser(), getContext(), pageNumber, getMaxItemPerPage(), listHomeMenu);
        }
        viewPagerContractInfor.setAdapter(childContractInforAdapter);
        viewPagerContractInfor.setOffscreenPageLimit(pageNumber);
        viewPagerContractInfor.addOnPageChangeListener(this);
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
        viewPagerContractInfor.setCurrentItem(position);
        for (int i = 0; i < dotCount; i++) {
            dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.nonactive_dot));
        }
        dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dot));

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}