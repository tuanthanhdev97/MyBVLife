package app.com.baoviet.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import app.com.baoviet.ContractActivity;
import app.com.baoviet.R;
import app.com.baoviet.adapter.GridViewCustomAdapter;
import app.com.baoviet.constant.Constant;
import app.com.baoviet.constant.Keys;
import app.com.baoviet.entity.HomeMenuItem;
import app.com.baoviet.entity.UserDTO;

public class TabChildContractInfor extends Fragment implements AdapterView.OnItemClickListener {
    private GridView gridChildContractInfor;
    private GridViewCustomAdapter gridViewCustomAdapter;
    private Context context;
    private UserDTO userDTO;

    private List<HomeMenuItem> listHomeMenu;
    private List<HomeMenuItem> listChild;
    private int fromItem;
    private int toItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_child_contract_infor, container, false);
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
        gridChildContractInfor = (GridView) view.findViewById(R.id.gridChildContractInfor);
        listChild = new ArrayList<>();
        for (int i = this.fromItem; i <= this.toItem; i++) {
            listChild.add(this.listHomeMenu.get(i));
        }
        gridViewCustomAdapter = new GridViewCustomAdapter(getActivity(), listChild);
        gridChildContractInfor.setAdapter(gridViewCustomAdapter);
        gridChildContractInfor.setOnItemClickListener(this);
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
        Activity activity = getActivity();
        Bundle bundle;
        Intent intentContract;
        if (isAdded() && activity != null) {
            HomeMenuItem homeMenuItem = listChild.get(position);
            String keytransfer = homeMenuItem.getKeyTransfer();
            switch (keytransfer) {
                case Constant.TRANSFER_CONTRACT_TO_GENERAL_INFOR:
                    bundle = new Bundle();
                    bundle.putSerializable(Keys.KEY_USER_LOGIN, getUserDTO());
                    bundle.putString(Keys.KEY_TRANSFER, Constant.TRANSFER_CONTRACT_TO_GENERAL_INFOR);
                    intentContract = new Intent(activity, ContractActivity.class);
                    intentContract.putExtras(bundle);
                    activity.startActivity(intentContract);
                    break;
                case Constant.TRANSFER_CONTRACT_TO_CUSTOMER_INFOR:
                    bundle = new Bundle();
                    bundle.putSerializable(Keys.KEY_USER_LOGIN, getUserDTO());
                    bundle.putString(Keys.KEY_TRANSFER, Constant.TRANSFER_CONTRACT_TO_CUSTOMER_INFOR);
                    intentContract = new Intent(activity, ContractActivity.class);
                    intentContract.putExtras(bundle);
                    activity.startActivity(intentContract);
                    break;
                case Constant.TRANSFER_CONTRACT_TO_VALUE_ACCOUNT:
                    bundle = new Bundle();
                    bundle.putSerializable(Keys.KEY_USER_LOGIN, getUserDTO());
                    bundle.putString(Keys.KEY_TRANSFER, Constant.TRANSFER_CONTRACT_TO_VALUE_ACCOUNT);
                    intentContract = new Intent(activity, ContractActivity.class);
                    intentContract.putExtras(bundle);
                    activity.startActivity(intentContract);
                    break;

                case Constant.TRANSFER_CONTRACT_TO_PAYMENT_PROCESS:
                    bundle = new Bundle();
                    bundle.putSerializable(Keys.KEY_USER_LOGIN, getUserDTO());
                    bundle.putString(Keys.KEY_TRANSFER, Constant.TRANSFER_CONTRACT_TO_PAYMENT_PROCESS);
                    intentContract = new Intent(activity, ContractActivity.class);
                    intentContract.putExtras(bundle);
                    activity.startActivity(intentContract);
                    break;

                case Constant.TRANSFER_CONTRACT_TO_FEE_AND_COST:
                    bundle = new Bundle();
                    bundle.putSerializable(Keys.KEY_USER_LOGIN, getUserDTO());
                    bundle.putString(Keys.KEY_TRANSFER, Constant.TRANSFER_CONTRACT_TO_FEE_AND_COST);
                    intentContract = new Intent(activity, ContractActivity.class);
                    intentContract.putExtras(bundle);
                    activity.startActivity(intentContract);
                    break;
                case Constant.TRANSFER_CONTRACT_TO_BENEFIT:
                    bundle = new Bundle();
                    bundle.putSerializable(Keys.KEY_USER_LOGIN, getUserDTO());
                    bundle.putString(Keys.KEY_TRANSFER, Constant.TRANSFER_CONTRACT_TO_BENEFIT);
                    intentContract = new Intent(activity, ContractActivity.class);
                    intentContract.putExtras(bundle);
                    activity.startActivity(intentContract);
                    break;
                case Constant.TRANSFER_CONTRACT_TO_EBILL:
                    bundle = new Bundle();
                    bundle.putSerializable(Keys.KEY_USER_LOGIN, getUserDTO());
                    bundle.putString(Keys.KEY_TRANSFER, Constant.TRANSFER_CONTRACT_TO_EBILL);
                    intentContract = new Intent(activity, ContractActivity.class);
                    intentContract.putExtras(bundle);
                    activity.startActivity(intentContract);
                    break;
                case Constant.TRANSFER_CONTRACT_TO_ANNUAL_REPORT:
                    bundle = new Bundle();
                    bundle.putSerializable(Keys.KEY_USER_LOGIN, getUserDTO());
                    bundle.putString(Keys.KEY_TRANSFER, Constant.TRANSFER_CONTRACT_TO_ANNUAL_REPORT);
                    intentContract = new Intent(activity, ContractActivity.class);
                    intentContract.putExtras(bundle);
                    activity.startActivity(intentContract);
                    break;
            }
        }
    }

}