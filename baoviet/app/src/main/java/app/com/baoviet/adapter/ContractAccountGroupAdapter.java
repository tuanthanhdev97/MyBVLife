package app.com.baoviet.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import app.com.baoviet.constant.Constant;
import app.com.baoviet.fragment.TabContractBenefitFragment;
import app.com.baoviet.fragment.TabContractBillFragment;
import app.com.baoviet.fragment.TabContractCostFragment;
import app.com.baoviet.fragment.TabContractCustomerInforFragment;
import app.com.baoviet.fragment.TabContractGeneralInforFragment;
import app.com.baoviet.fragment.TabContractNoticeFragment;
import app.com.baoviet.fragment.TabContractPaymentProcessFragment;
import app.com.baoviet.fragment.TabContractValueAccountFragment;

public class ContractAccountGroupAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;
    private String contractNumber;
    private Context context;

    public ContractAccountGroupAdapter(FragmentManager fm, int NumOfTabs, String contractNumber, Context context) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.contractNumber = contractNumber;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case Constant.INT_0:
                TabContractGeneralInforFragment tabGeneralInfor = new TabContractGeneralInforFragment();
//                tabGeneralInfor.setAccountNumber(this.contractNumber);
                return tabGeneralInfor;
            case Constant.INT_1:
                TabContractCustomerInforFragment tabCustomerInfor = new TabContractCustomerInforFragment();
//                tabCustomerInfor.setAccountNumber(this.contractNumber);
//                tabCustomerInfor.setContext(this.context);
                return tabCustomerInfor;
            case Constant.INT_2:
                TabContractValueAccountFragment tabValueAccount = new TabContractValueAccountFragment();
//                tabValueAccount.setAccountNumber(this.contractNumber);
//                tabValueAccount.setContext(this.context);
                return tabValueAccount;
            case Constant.INT_3:
                TabContractPaymentProcessFragment tabPaymentProcess = new TabContractPaymentProcessFragment();
//                tabPaymentProcess.setAccountNumber(this.contractNumber);
//                tabPaymentProcess.setContext(this.context);
                return tabPaymentProcess;
            case Constant.INT_4:
                TabContractCostFragment tabCost = new TabContractCostFragment();
//                tabCost.setAccountNumber(this.contractNumber);
//                tabCost.setContext(this.context);
                return tabCost;
            case Constant.INT_5:
                TabContractBenefitFragment tabBenefit = new TabContractBenefitFragment();
//                tabBenefit.setAccountNumber(this.contractNumber);
//                tabBenefit.setContext(this.context);
                return tabBenefit;
            case Constant.INT_6:
                TabContractBillFragment tabBill = new TabContractBillFragment();
//                tabBill.setAccountNumber(this.contractNumber);
//                tabBill.setContext(this.context);
                return tabBill;
            case Constant.INT_7:
                TabContractNoticeFragment tabNotice = new TabContractNoticeFragment();
//                tabNotice.setAccountNumber(this.contractNumber);
//                tabNotice.setContext(this.context);
                return tabNotice;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
