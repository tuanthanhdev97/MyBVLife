package app.com.baoviet.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import app.com.baoviet.entity.HomeMenuItem;
import app.com.baoviet.entity.UserDTO;
import app.com.baoviet.fragment.TabChildContractInfor;

public class ContractInforTabAdapter extends FragmentStatePagerAdapter {
    private int pageNumber;
    private int maxItemPerPage;
    private Context context;
    private UserDTO user;
    private List<HomeMenuItem> listHomeMenu;

    public ContractInforTabAdapter(FragmentManager fm, UserDTO user, Context context, int pageNumber, int maxItemPerPage, List<HomeMenuItem> listHomeMenu) {
        super(fm);
        this.pageNumber = pageNumber;
        this.context = context;
        this.user = user;
        this.listHomeMenu = listHomeMenu;
        this.maxItemPerPage = maxItemPerPage;
    }

    @Override
    public Fragment getItem(int position) {
        int fromPosition;
        int toPosition;
        fromPosition = maxItemPerPage * position;
        if (position == (pageNumber - 1)) {
            toPosition = this.listHomeMenu.size() - 1;
        } else {
            toPosition = fromPosition + maxItemPerPage - 1;
        }
        TabChildContractInfor tabChildContractInfor = new TabChildContractInfor();
        tabChildContractInfor.setContext(this.context);
        tabChildContractInfor.setUserDTO(user);
        tabChildContractInfor.setListHomeMenu(this.listHomeMenu);
        tabChildContractInfor.setFromItem(fromPosition);
        tabChildContractInfor.setToItem(toPosition);
        return tabChildContractInfor;
    }

    @Override
    public int getCount() {
        return this.pageNumber;
    }
}
