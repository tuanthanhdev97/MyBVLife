package app.com.baoviet.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import app.com.baoviet.entity.HomeMenuItem;
import app.com.baoviet.fragment.TabChildContact;

public class ContactTabAdapter extends FragmentStatePagerAdapter {
    private int pageNumber;
    private int maxItemPerPage;
    private Context context;
    private List<HomeMenuItem> listHomeMenu;

    public ContactTabAdapter(FragmentManager fm, Context context, int pageNumber, int maxItemPerPage, List<HomeMenuItem> listHomeMenu) {
        super(fm);
        this.pageNumber = pageNumber;
        this.context = context;
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
        TabChildContact tabChildContact = new TabChildContact();
        tabChildContact.setContext(this.context);
        tabChildContact.setListHomeMenu(this.listHomeMenu);
        tabChildContact.setFromItem(fromPosition);
        tabChildContact.setToItem(toPosition);
        return tabChildContact;
    }

    @Override
    public int getCount() {
        return this.pageNumber;
    }
}
