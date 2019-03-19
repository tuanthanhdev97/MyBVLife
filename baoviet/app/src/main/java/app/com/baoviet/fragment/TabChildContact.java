package app.com.baoviet.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import app.com.baoviet.entity.HomeMenuItem;
import app.com.baoviet.entity.UserDTO;
import app.com.baoviet.utility.GrantPermission;

public class TabChildContact extends Fragment implements AdapterView.OnItemClickListener {
    private GridView gridChildContact;
    private GridViewCustomAdapter gridViewCustomAdapter;
    private Context context;
    private UserDTO userDTO;

    private List<HomeMenuItem> listHomeMenu;
    private List<HomeMenuItem> listChild;
    private int fromItem;
    private int toItem;

    Fragment fr = null;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_child_contact, container, false);
        initInterface(view);
        return view;
    }

    public List<HomeMenuItem> getListHomeMenu() {
        return listHomeMenu;
    }

    public void setListHomeMenu(List<HomeMenuItem> listHomeMenu) {
        this.listHomeMenu = listHomeMenu;
    }

    public int getFromItem() {
        return fromItem;
    }

    public void setFromItem(int fromItem) {
        this.fromItem = fromItem;
    }

    public int getToItem() {
        return toItem;
    }

    public void setToItem(int toItem) {
        this.toItem = toItem;
    }

    public void initInterface(View view) {
        gridChildContact = (GridView) view.findViewById(R.id.gridChildContact);
        listChild = new ArrayList<>();
        for (int i = this.fromItem; i <= this.toItem; i++) {
            listChild.add(this.listHomeMenu.get(i));
        }
        gridViewCustomAdapter = new GridViewCustomAdapter(getActivity(), listChild);
        gridChildContact.setAdapter(gridViewCustomAdapter);
        gridChildContact.setOnItemClickListener(this);
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
        HomeMenuItem homeMenuItem = listChild.get(position);
        String keytransfer = homeMenuItem.getKeyTransfer();
        switch (keytransfer) {
            case Constant.TRANSFER_LOGIN_TO_SECURITY:
                ((MainActivity) getActivity()).tvActionBar.setVisibility(View.VISIBLE);
                ((MainActivity) getActivity()).tvActionBar.setText(R.string.txt_Login_Security);
                fr = new SecurityFragment();
                fragmentTransaction.replace(R.id.frameContent, fr, Constant.TRANSFER_LOGIN_TO_SECURITY);
                fragmentTransaction.addToBackStack(Constant.TRANSFER_LOGIN_TO_SECURITY);
                fragmentTransaction.commit();
                break;
            case Constant.TRANSFER_LOGIN_TO_TERMS:
                ((MainActivity) getActivity()).tvActionBar.setVisibility(View.VISIBLE);
                ((MainActivity) getActivity()).tvActionBar.setText(R.string.txt_Login_Terms);
                fr = new TermFragment();
                fragmentTransaction.replace(R.id.frameContent, fr, Constant.TRANSFER_LOGIN_TO_TERMS);
                fragmentTransaction.addToBackStack(Constant.TRANSFER_LOGIN_TO_TERMS);
                fragmentTransaction.commit();
                break;
            case Constant.TRANSFER_LOGIN_TO_CONTACT:
                ((MainActivity) getActivity()).tvActionBar.setVisibility(View.VISIBLE);
                ((MainActivity) getActivity()).tvActionBar.setText(R.string.txt_Login_Contact);
                fr = new ContactFragment();
                fragmentTransaction.replace(R.id.frameContent, fr, Constant.TRANSFER_LOGIN_TO_CONTACT);
                fragmentTransaction.addToBackStack(Constant.TRANSFER_LOGIN_TO_CONTACT);
                fragmentTransaction.commit();
                break;
            case Constant.TRANSFER_TO_CHAT:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.CHAT_LINK));
                startActivity(browserIntent);
                break;
            case Constant.TRANSFER_TO_HOTLINE:
                if (GrantPermission.hasPermissionCallPhone(context)) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:18006966"));
                    startActivity(callIntent);
                }
                break;
        }

    }

}