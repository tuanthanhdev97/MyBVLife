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

public class ServiceFragment extends Fragment implements View.OnClickListener, DialogEventListener {

    private TextView tvServicePaymentChannel;
    private TextView tvServiceChangeInforContract;
    private TextView tvServiceBenefit;
    private TextView tvServiceAcknowledge;
    private TextView tvServiceTakeCare;

    AlertDialogCustom alert;
    String message = Constant.EMPTY;
    String typeDialog = Constant.EMPTY;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service, container, false);
        // initial view
        initInterface(view);
        return view;
    }

    public void initInterface(View view) {
        alert = new AlertDialogCustom(getActivity());
        alert.dialogEventListener = this;

        tvServicePaymentChannel = (TextView) view.findViewById(R.id.tvServicePaymentChannel);
        tvServiceChangeInforContract = (TextView) view.findViewById(R.id.tvServiceChangeInforContract);
        tvServiceBenefit = (TextView) view.findViewById(R.id.tvServiceBenefit);
        tvServiceAcknowledge = (TextView) view.findViewById(R.id.tvServiceAcknowledge);
        tvServiceTakeCare = (TextView) view.findViewById(R.id.tvServiceTakeCare);

        String strPayment = getActivity().getResources().getString(R.string.txt_service_payment);
        String strChangeInfor = getActivity().getResources().getString(R.string.txt_service_change_infor_contract);
        String strBenefit = getActivity().getResources().getString(R.string.txt_service_benefit);
        String strAcknowledge = getActivity().getResources().getString(R.string.txt_service_customer);
        String strTakeCare = getActivity().getResources().getString(R.string.txt_service_take_care);

        SpannableString investStr = new SpannableString(strPayment);
        investStr.setSpan(new UnderlineSpan(), 0, strPayment.length(), 0);
        tvServicePaymentChannel.setText(investStr);

        SpannableString saveStr = new SpannableString(strChangeInfor);
        saveStr.setSpan(new UnderlineSpan(), 0, strChangeInfor.length(), 0);
        tvServiceChangeInforContract.setText(saveStr);

        SpannableString protectedStr = new SpannableString(strBenefit);
        protectedStr.setSpan(new UnderlineSpan(), 0, strBenefit.length(), 0);
        tvServiceBenefit.setText(protectedStr);

        SpannableString retiredStr = new SpannableString(strAcknowledge);
        retiredStr.setSpan(new UnderlineSpan(), 0, strAcknowledge.length(), 0);
        tvServiceAcknowledge.setText(retiredStr);

        SpannableString businessStr = new SpannableString(strTakeCare);
        businessStr.setSpan(new UnderlineSpan(), 0, strTakeCare.length(), 0);
        tvServiceTakeCare.setText(businessStr);

        tvServicePaymentChannel.setOnClickListener(this);
        tvServiceChangeInforContract.setOnClickListener(this);
        tvServiceBenefit.setOnClickListener(this);
        tvServiceAcknowledge.setOnClickListener(this);
        tvServiceTakeCare.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
//
            case R.id.tvServicePaymentChannel:
                Intent intentInvest = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.CHANNEL_PAYMENT_LINK));
                startActivity(intentInvest);
                break;
            case R.id.tvServiceChangeInforContract:
                Intent intentSave = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.CHANGE_INFOR_CONTRACT_LINK));
                startActivity(intentSave);
                break;
            case R.id.tvServiceBenefit:
                Intent intentProtected = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.BENEFIT_LINK));
                startActivity(intentProtected);
                break;
            case R.id.tvServiceAcknowledge:
                Intent intentRetire = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.ACKNOWLEDGE_LINK));
                startActivity(intentRetire);
                break;
            case R.id.tvServiceTakeCare:
                Intent intentBusiness = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.TAKE_CARE_LINK));
                startActivity(intentBusiness);
                break;

            default:
                break;
        }

    }

    @Override
    public void OnClickedButtonDialog(boolean isClickOk, String actionDialog, String typeDialog) {

    }
}