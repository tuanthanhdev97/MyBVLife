package app.com.baoviet.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.com.baoviet.R;
import app.com.baoviet.constant.Constant;
import app.com.baoviet.interfaceclass.DialogEventListener;
import app.com.baoviet.utility.AlertDialogCustom;

public class ProductFragment extends Fragment implements View.OnClickListener, DialogEventListener {

    private TextView tvProductInvest;
    private TextView tvProductSave;
    private TextView tvProductProtected;
    private TextView tvProductRetired;
    private TextView tvProductBusiness;
    private TextView tvProductBank;

    AlertDialogCustom alert;
    String message = Constant.EMPTY;
    String typeDialog = Constant.EMPTY;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        // initial view
        initInterface(view);
        return view;
    }

    public void initInterface(View view) {
        alert = new AlertDialogCustom(getActivity());
        alert.dialogEventListener = this;

        tvProductInvest = (TextView) view.findViewById(R.id.tvProductInvest);
        tvProductSave = (TextView) view.findViewById(R.id.tvProductSave);
        tvProductProtected = (TextView) view.findViewById(R.id.tvProductProtected);
        tvProductRetired = (TextView) view.findViewById(R.id.tvProductRetired);
        tvProductBusiness = (TextView) view.findViewById(R.id.tvProductBusiness);
        tvProductBank = (TextView) view.findViewById(R.id.tvProductBank);

        String strInvest = getActivity().getResources().getString(R.string.txt_product_invest);
        String strSave = getActivity().getResources().getString(R.string.txt_product_save);
        String strProtected = getActivity().getResources().getString(R.string.txt_product_protected);
        String strRetired = getActivity().getResources().getString(R.string.txt_product_retired);
        String strBusiness = getActivity().getResources().getString(R.string.txt_product_business);
        String strBank = getActivity().getResources().getString(R.string.txt_product_bank);

        SpannableString investStr = new SpannableString(strInvest);
        investStr.setSpan(new UnderlineSpan(), 0, strInvest.length(), 0);
        tvProductInvest.setText(investStr);

        SpannableString saveStr = new SpannableString(strSave);
        saveStr.setSpan(new UnderlineSpan(), 0, strSave.length(), 0);
        tvProductSave.setText(saveStr);

        SpannableString protectedStr = new SpannableString(strProtected);
        protectedStr.setSpan(new UnderlineSpan(), 0, strProtected.length(), 0);
        tvProductProtected.setText(protectedStr);

        SpannableString retiredStr = new SpannableString(strRetired);
        retiredStr.setSpan(new UnderlineSpan(), 0, strRetired.length(), 0);
        tvProductRetired.setText(retiredStr);

        SpannableString businessStr = new SpannableString(strBusiness);
        businessStr.setSpan(new UnderlineSpan(), 0, strBusiness.length(), 0);
        tvProductBusiness.setText(businessStr);

        SpannableString bankStr = new SpannableString(strBank);
        bankStr.setSpan(new UnderlineSpan(), 0, strBank.length(), 0);
        tvProductBank.setText(bankStr);


        tvProductInvest.setOnClickListener(this);
        tvProductSave.setOnClickListener(this);
        tvProductProtected.setOnClickListener(this);
        tvProductRetired.setOnClickListener(this);
        tvProductBusiness.setOnClickListener(this);
        tvProductBank.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
//
            case R.id.tvProductInvest:
                Intent intentInvest = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.INVEST_LINK));
                startActivity(intentInvest);
                break;
            case R.id.tvProductSave:
                Intent intentSave = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.SAVE_MONEY_LINK));
                startActivity(intentSave);
                break;
            case R.id.tvProductProtected:
                Intent intentProtected = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.PROTECTED_LINK));
                startActivity(intentProtected);
                break;
            case R.id.tvProductRetired:
                Intent intentRetire = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.RETIRE_LINK));
                startActivity(intentRetire);
                break;
            case R.id.tvProductBusiness:
                Intent intentBusiness = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.BUSINESS_LINK));
                startActivity(intentBusiness);
                break;
            case R.id.tvProductBank:
                Intent intentBank = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.BANK_LINK));
                startActivity(intentBank);
                break;

            default:
                break;
        }

    }

    @Override
    public void OnClickedButtonDialog(boolean isClickOk, String actionDialog, String typeDialog) {

    }
}