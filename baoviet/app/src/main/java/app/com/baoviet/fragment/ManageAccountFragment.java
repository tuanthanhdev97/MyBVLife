package app.com.baoviet.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import app.com.baoviet.R;

public class ManageAccountFragment extends Fragment implements View.OnClickListener {
    private EditText edtManageAccountUsername;
    private EditText edtManageAccountName;
    private EditText edtManageAccountFromDate;
    private EditText edtManageAccountToDate;
    private EditText edtManageAccountContractNoGroup;
    private EditText edtManageAccountContractNoInvidual;
    private EditText edtManageAccountEmail;
    private EditText edtManageAccountPhone;
    private EditText edtManageAccountNumberIdentify;

    private LinearLayout lnManageAccountPhone;
    private LinearLayout lnManageAccountEmail;
    private LinearLayout lnManageAccountStatus;
    private LinearLayout lnManageAccountType;
    private LinearLayout lnManageAccountTypeIdentify;
    private LinearLayout lnManageAccountNumberIdentify;

    private Spinner spManageAccountStatus;
    private Spinner spManageAccountType;
    private Spinner spManageAccountTypeIdentify;

    private ImageView imgvManageAccountExpandCollapse;
    private TextView tvManageAccountCollapse;
    private Button btnManageAccountSearch;
    private Button btnCollapse;
    private boolean isExpand;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_account, container, false);
        initInterface(view);
        return view;
    }

    public void initInterface(View view) {
        edtManageAccountUsername = (EditText) view.findViewById(R.id.edtManageAccountUsername);
        edtManageAccountName = (EditText) view.findViewById(R.id.edtManageAccountName);
        edtManageAccountFromDate = (EditText) view.findViewById(R.id.edtManageAccountFromDate);
        edtManageAccountToDate = (EditText) view.findViewById(R.id.edtManageAccountToDate);
        edtManageAccountContractNoGroup = (EditText) view.findViewById(R.id.edtManageAccountContractNoGroup);
        edtManageAccountContractNoInvidual = (EditText) view.findViewById(R.id.edtManageAccountContractNoInvidual);
        edtManageAccountEmail = (EditText) view.findViewById(R.id.edtManageAccountEmail);
        edtManageAccountPhone = (EditText) view.findViewById(R.id.edtManageAccountPhone);
        edtManageAccountNumberIdentify = (EditText) view.findViewById(R.id.edtManageAccountNumberIdentify);

        lnManageAccountPhone = (LinearLayout) view.findViewById(R.id.lnManageAccountPhone);
        lnManageAccountEmail = (LinearLayout) view.findViewById(R.id.lnManageAccountEmail);
        lnManageAccountStatus = (LinearLayout) view.findViewById(R.id.lnManageAccountStatus);
        lnManageAccountType = (LinearLayout) view.findViewById(R.id.lnManageAccountType);
        lnManageAccountTypeIdentify = (LinearLayout) view.findViewById(R.id.lnManageAccountTypeIdentify);
        lnManageAccountNumberIdentify = (LinearLayout) view.findViewById(R.id.lnManageAccountNumberIdentify);


        spManageAccountStatus = (Spinner) view.findViewById(R.id.spManageAccountStatus);
        spManageAccountType = (Spinner) view.findViewById(R.id.spManageAccountType);
        spManageAccountTypeIdentify = (Spinner) view.findViewById(R.id.spManageAccountTypeIdentify);

        btnManageAccountSearch = (Button) view.findViewById(R.id.btnManageAccountSearch);
        btnCollapse = (Button) view.findViewById(R.id.btnCollapse);
        btnManageAccountSearch.setOnClickListener(this);
        btnCollapse.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCollapse:
                isExpand = !isExpand;
                if (isExpand) {
                    btnCollapse.setText(R.string.txt_manage_account_collapse);
                    btnCollapse.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bullet_toggle_minus, 0, 0, 0);
                    lnManageAccountPhone.setVisibility(View.VISIBLE);
                    lnManageAccountEmail.setVisibility(View.VISIBLE);
                    lnManageAccountStatus.setVisibility(View.VISIBLE);
                    lnManageAccountType.setVisibility(View.VISIBLE);
                    lnManageAccountTypeIdentify.setVisibility(View.VISIBLE);
                    lnManageAccountNumberIdentify.setVisibility(View.VISIBLE);
                } else {
                    btnCollapse.setText(R.string.txt_manage_account_expand);
                    btnCollapse.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bullet_toggle_plus, 0, 0, 0);
                    lnManageAccountPhone.setVisibility(View.GONE);
                    lnManageAccountEmail.setVisibility(View.GONE);
                    lnManageAccountStatus.setVisibility(View.GONE);
                    lnManageAccountType.setVisibility(View.GONE);
                    lnManageAccountTypeIdentify.setVisibility(View.GONE);
                    lnManageAccountNumberIdentify.setVisibility(View.GONE);
                }
                break;
            default:
                break;
        }
    }
}