package app.com.baoviet.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.com.baoviet.ContractActivity;
import app.com.baoviet.FooterActivity;
import app.com.baoviet.MainActivity;
import app.com.baoviet.R;
import app.com.baoviet.ViewPDFActivity;
import app.com.baoviet.constant.Constant;
import app.com.baoviet.utility.GrantPermission;

public class FooterFragment extends Fragment implements View.OnClickListener {
    private TextView tvMainPolicy;
    private TextView tvMainTerm;
    private TextView tvMainContact;
    private TextView tvLoginChat;
    private TextView tvLoginHotline;
    private TextView tvFooterTypeDevice;
    private LinearLayout lnMainPolicy;
    private LinearLayout lnMainContact;
    private LinearLayout lnMainTerm;
    private LinearLayout lnMainChat;
    private LinearLayout lnMainHotline;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_footer, container, false);
        initInterface(view);
        return view;
    }

    public void initInterface(View view) {
        tvFooterTypeDevice = (TextView) view.findViewById(R.id.tvFooterTypeDevice);
        if (Constant.TYPE_DEVICE_PHONE.equals(tvFooterTypeDevice.getText())) {
//            tvMainPolicy = (TextView) view.findViewById(R.id.tvMainPolicy);
//            tvMainTerm = (TextView) view.findViewById(R.id.tvMainTerm);
//            tvMainContact = (TextView) view.findViewById(R.id.tvMainContact);
//            tvLoginChat = (TextView) view.findViewById(R.id.tvLoginChat);
//            tvLoginHotline = (TextView) view.findViewById(R.id.tvLoginHotline);
        } else if (Constant.TYPE_DEVICE_TABLET.equals(tvFooterTypeDevice.getText())) {
            lnMainPolicy = (LinearLayout) view.findViewById(R.id.lnMainPolicy);
            lnMainContact = (LinearLayout) view.findViewById(R.id.lnMainContact);
            lnMainTerm = (LinearLayout) view.findViewById(R.id.lnMainTerm);
            lnMainChat = (LinearLayout) view.findViewById(R.id.lnMainChat);
            lnMainHotline = (LinearLayout) view.findViewById(R.id.lnMainHotline);
            lnMainPolicy.setOnClickListener(this);
            lnMainContact.setOnClickListener(this);
            lnMainTerm.setOnClickListener(this);
            lnMainChat.setOnClickListener(this);
            lnMainHotline.setOnClickListener(this);

        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lnMainPolicy:
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).navigationFragment(Constant.TRANSFER_LOGIN_TO_SECURITY);
                } else if (getActivity() instanceof ContractActivity) {
                    ((ContractActivity) getActivity()).navigationFragmentFromContract(Constant.TRANSFER_LOGIN_TO_SECURITY);
                } else if (getActivity() instanceof FooterActivity) {
                    ((FooterActivity) getActivity()).navigationFragmentFromFooter(Constant.TRANSFER_LOGIN_TO_SECURITY);
                } else if (getActivity() instanceof ViewPDFActivity) {
                    ((ViewPDFActivity) getActivity()).navigationFragmentFromFooter(Constant.TRANSFER_LOGIN_TO_SECURITY);
                }
                break;
            case R.id.lnMainContact:
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).navigationFragment(Constant.TRANSFER_LOGIN_TO_CONTACT);
                } else if (getActivity() instanceof ContractActivity) {
                    ((ContractActivity) getActivity()).navigationFragmentFromContract(Constant.TRANSFER_LOGIN_TO_CONTACT);
                } else if (getActivity() instanceof FooterActivity) {
                    ((FooterActivity) getActivity()).navigationFragmentFromFooter(Constant.TRANSFER_LOGIN_TO_CONTACT);
                } else if (getActivity() instanceof ViewPDFActivity) {
                    ((ViewPDFActivity) getActivity()).navigationFragmentFromFooter(Constant.TRANSFER_LOGIN_TO_CONTACT);
                }
                break;
            case R.id.lnMainTerm:
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).navigationFragment(Constant.TRANSFER_LOGIN_TO_TERMS);
                } else if (getActivity() instanceof ContractActivity) {
                    ((ContractActivity) getActivity()).navigationFragmentFromContract(Constant.TRANSFER_LOGIN_TO_TERMS);
                } else if (getActivity() instanceof FooterActivity) {
                    ((FooterActivity) getActivity()).navigationFragmentFromFooter(Constant.TRANSFER_LOGIN_TO_TERMS);
                } else if (getActivity() instanceof ViewPDFActivity) {
                    ((ViewPDFActivity) getActivity()).navigationFragmentFromFooter(Constant.TRANSFER_LOGIN_TO_TERMS);
                }
                break;
            case R.id.lnMainChat:
                Intent browserIntentChat = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.CHAT_LINK));
                startActivity(browserIntentChat);
                break;
            case R.id.lnMainHotline:
                if (GrantPermission.hasPermissionCallPhone(getActivity())) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:18006966"));
                    startActivity(callIntent);
                }
                break;
        }

    }
}