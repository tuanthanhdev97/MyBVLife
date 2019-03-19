package app.com.baoviet.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import app.com.baoviet.constant.Constant;
import app.com.baoviet.entity.UserDTO;
import app.com.baoviet.fragment.TabHomeContact;
import app.com.baoviet.fragment.TabHomeContractInfor;
import app.com.baoviet.fragment.TabHomeInforAccount;
import app.com.baoviet.fragment.TabHomePayment;
import app.com.baoviet.fragment.TabHomeProduct;
import app.com.baoviet.fragment.TabHomeTradeOnline;

public class HomeMenuAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;
    private Context context;
    private UserDTO user;
    private int maxItemPerPage;

    public HomeMenuAdapter(FragmentManager fm, int NumOfTabs, UserDTO user, Context context, int maxItemPerPage) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.context = context;
        this.user = user;
        this.maxItemPerPage = maxItemPerPage;
    }

    @Override
    public Fragment getItem(int position) {
        if (user.isAccount()) {
            switch (position) {
                case Constant.INT_0:
                    TabHomePayment tabHomePayment = new TabHomePayment();
                    tabHomePayment.setContext(this.context);
                    tabHomePayment.setUserDTO(user);
                    return tabHomePayment;
                case Constant.INT_1:
                    TabHomeContractInfor tabHomeContractInfor = new TabHomeContractInfor();
                    tabHomeContractInfor.setContext(this.context);
                    tabHomeContractInfor.setUser(user);
                    tabHomeContractInfor.setMaxItemPerPage(this.maxItemPerPage);
                    return tabHomeContractInfor;
                case Constant.INT_2:
                    TabHomeInforAccount tabHomeInforAccount = new TabHomeInforAccount();
                    tabHomeInforAccount.setContext(this.context);
                    tabHomeInforAccount.setUserDTO(user);
                    return tabHomeInforAccount;
                case Constant.INT_3:
                    TabHomeTradeOnline tabHomeTradeOnline = new TabHomeTradeOnline();
                    tabHomeTradeOnline.setContext(this.context);
                    return tabHomeTradeOnline;
                case Constant.INT_4:
                    TabHomeProduct tabHomeProduct = new TabHomeProduct();
                    tabHomeProduct.setContext(this.context);
                    return tabHomeProduct;
                case Constant.INT_5:
                    TabHomeContact tabHomeContact = new TabHomeContact();
                    tabHomeContact.setContext(this.context);
                    tabHomeContact.setMaxItemPerPage(this.maxItemPerPage);
                    return tabHomeContact;
                default:
                    return null;
            }
        } else {
            switch (position) {
                case Constant.INT_0:
                    TabHomePayment tabHomePayment = new TabHomePayment();
                    tabHomePayment.setContext(this.context);
                    tabHomePayment.setUserDTO(user);
                    return tabHomePayment;
                case Constant.INT_1:
                    TabHomeInforAccount tabHomeInforAccount = new TabHomeInforAccount();
                    tabHomeInforAccount.setContext(this.context);
                    tabHomeInforAccount.setUserDTO(user);
                    return tabHomeInforAccount;
                case Constant.INT_2:
                    TabHomeTradeOnline tabHomeTradeOnline = new TabHomeTradeOnline();
                    tabHomeTradeOnline.setContext(this.context);
                    return tabHomeTradeOnline;
                case Constant.INT_3:
                    TabHomeProduct tabHomeProduct = new TabHomeProduct();
                    tabHomeProduct.setContext(this.context);
                    return tabHomeProduct;
                case Constant.INT_4:
                    TabHomeContact tabHomeContact = new TabHomeContact();
                    tabHomeContact.setContext(this.context);
                    tabHomeContact.setMaxItemPerPage(this.maxItemPerPage);
                    return tabHomeContact;
                default:
                    return null;
            }
        }

    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
