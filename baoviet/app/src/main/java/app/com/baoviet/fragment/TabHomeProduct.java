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
import app.com.baoviet.entity.HomeMenuItem;

public class TabHomeProduct extends Fragment implements AdapterView.OnItemClickListener {
    private GridView gridHomeProduct;
    private List<HomeMenuItem> listHomeMenu;
    private GridViewCustomAdapter gridViewCustomAdapter;
    private Context context;
    Fragment fr = null;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_home_product, container, false);
        initInterface(view);
        return view;
    }


    public void initInterface(View view) {
        gridHomeProduct = (GridView) view.findViewById(R.id.gridHomeProduct);
        gridHomeProduct.setOnItemClickListener(this);
        addListHomeMenu();
    }

    public void addListHomeMenu() {
        listHomeMenu = new ArrayList<HomeMenuItem>();

        // product
        HomeMenuItem homeMenuProduct = new HomeMenuItem();
        homeMenuProduct.setLabelItem(getString(R.string.title_home_tab_item_product));
        homeMenuProduct.setImageItem(R.mipmap.ic_menu_product);
        homeMenuProduct.setKeyTransfer(Constant.TRANSFER_TO_PRODUCT);
        // service
        HomeMenuItem homeMenuService = new HomeMenuItem();
        homeMenuService.setLabelItem(getString(R.string.title_home_tab_item_service));
        homeMenuService.setImageItem(R.mipmap.ic_menu_service);
        homeMenuService.setKeyTransfer(Constant.TRANSFER_TO_SERVICE);

        listHomeMenu.add(homeMenuProduct);
        listHomeMenu.add(homeMenuService);

        gridViewCustomAdapter = new GridViewCustomAdapter(getActivity(), listHomeMenu);
        gridHomeProduct.setAdapter(gridViewCustomAdapter);
    }

    @Override
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        fm = getActivity().getSupportFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        HomeMenuItem homeMenuItem = listHomeMenu.get(position);
        String keytransfer = homeMenuItem.getKeyTransfer();
        switch (keytransfer) {
            case Constant.TRANSFER_TO_PRODUCT:
                ((MainActivity) getActivity()).tvActionBar.setVisibility(View.VISIBLE);
                ((MainActivity) getActivity()).tvActionBar.setText(R.string.title_home_tab_item_product_header);
                fr = new ProductFragment();
                fragmentTransaction.replace(R.id.frameContent, fr, Constant.TRANSFER_TO_PRODUCT);
                fragmentTransaction.addToBackStack(Constant.TRANSFER_TO_PRODUCT);
                fragmentTransaction.commit();
                break;
            case Constant.TRANSFER_TO_SERVICE:
                ((MainActivity) getActivity()).tvActionBar.setVisibility(View.VISIBLE);
                ((MainActivity) getActivity()).tvActionBar.setText(R.string.title_home_tab_item_service_header);
                fr = new ServiceFragment();
                fragmentTransaction.replace(R.id.frameContent, fr, Constant.TRANSFER_TO_SERVICE);
                fragmentTransaction.addToBackStack(Constant.TRANSFER_TO_SERVICE);
                fragmentTransaction.commit();
                break;

//            case Constant.TRANSFER_TO_INVEST:
//                Intent intentInvest = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.INVEST_LINK));
//                startActivity(intentInvest);
//                break;
//            case Constant.TRANSFER_TO_SAVE:
//                Intent intentSave = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.SAVE_MONEY_LINK));
//                startActivity(intentSave);
//                break;
//            case Constant.TRANSFER_TO_PROTECTED:
//                Intent intentProtected = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.PROTECTED_LINK));
//                startActivity(intentProtected);
//                break;
//            case Constant.TRANSFER_TO_BUSINESS:
//                Intent intentBusiness = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.BUSINESS_LINK));
//                startActivity(intentBusiness);
//                break;
//            case Constant.TRANSFER_TO_RETIRE:
//                Intent intentRetire = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.RETIRE_LINK));
//                startActivity(intentRetire);
//                break;
//            case Constant.TRANSFER_TO_BANK:
//                Intent intentBank = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.BANK_LINK));
//                startActivity(intentBank);
//            break;
        }
    }
}