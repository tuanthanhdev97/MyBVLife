package app.com.baoviet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import app.com.baoviet.R;
import app.com.baoviet.constant.Constant;
import app.com.baoviet.entity.BeneficiaryDTO;
import app.com.baoviet.utility.StringUtil;

public class CustomExpandableListBeneficiaryiAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<BeneficiaryDTO> mExpandableListPreminum;
    private LayoutInflater mLayoutInflater;

    public CustomExpandableListBeneficiaryiAdapter(Context context, List<BeneficiaryDTO> mExpandableListPreminum) {
        this.mExpandableListPreminum = mExpandableListPreminum;
        this.mContext = context;
        this.mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        BeneficiaryDTO transPreminum = null;
        if (mExpandableListPreminum != null) {
            transPreminum = mExpandableListPreminum.get(listPosition);
            return transPreminum;
        }
        return null;
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final BeneficiaryDTO expandedListText = (BeneficiaryDTO) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_beneficiary_tab_item, null);
        }
        TextView tvContactTabBeneficiaryItemTypeDevice = (TextView) convertView.findViewById(R.id.tvContactTabBeneficiaryItemTypeDevice);

        TextView tvContractTabBeneficiaryRate = (TextView) convertView.findViewById(R.id.tvContractTabBeneficiaryRate);
        TextView tvContractTabBeneficiaryRelate = (TextView) convertView.findViewById(R.id.tvContractTabBeneficiaryRelate);
        TextView tvContractTabBeneficiaryAddress = (TextView) convertView.findViewById(R.id.tvContractTabBeneficiaryAddress);
        TextView tvContractTabBeneficiaryEmail = (TextView) convertView.findViewById(R.id.tvContractTabBeneficiaryEmail);
        TextView tvContractTabBeneficiaryMobilePhone = (TextView) convertView.findViewById(R.id.tvContractTabBeneficiaryMobilePhone);
        TextView tvContractTabBeneficiaryCompanyPhone = (TextView) convertView.findViewById(R.id.tvContractTabBeneficiaryCompanyPhone);
        TextView tvContractTabBeneficiaryHomePhone = (TextView) convertView.findViewById(R.id.tvContractTabBeneficiaryHomePhone);
        TextView tvContractTabBeneficiaryTypeId = (TextView) convertView.findViewById(R.id.tvContractTabBeneficiaryTypeId);
        TextView tvContractTabBeneficiaryNumberId = (TextView) convertView.findViewById(R.id.tvContractTabBeneficiaryNumberId);
        TextView tvContractTabBeneficiaryDateId = (TextView) convertView.findViewById(R.id.tvContractTabBeneficiaryDateId);
        TextView tvContractTabBeneficiaryAddressId = (TextView) convertView.findViewById(R.id.tvContractTabBeneficiaryAddressId);
        TextView tvContractTabBeneficiaryTaxCode = (TextView) convertView.findViewById(R.id.tvContractTabBeneficiaryTaxCode);
        TextView tvContractTabBeneficiaryProduct = (TextView) convertView.findViewById(R.id.tvContractTabBeneficiaryProduct);

        String typeDevice = tvContactTabBeneficiaryItemTypeDevice.getText().toString();
        tvContractTabBeneficiaryRate.setText(StringUtil.setTextValue(expandedListText.getClientRate().toString()) + "%");
        tvContractTabBeneficiaryRelate.setText(StringUtil.setTextValue(expandedListText.getClientRltnshpDesc().toString()));
        tvContractTabBeneficiaryAddress.setText(StringUtil.setTextValue(expandedListText.getClntAddrs().getAddrFullAddress()));
        tvContractTabBeneficiaryEmail.setText(StringUtil.setTextValue(expandedListText.getClientEmail()));
        tvContractTabBeneficiaryMobilePhone.setText(StringUtil.setTextValue(expandedListText.getClientMobilePhone()));
        tvContractTabBeneficiaryCompanyPhone.setText(StringUtil.setTextValue(expandedListText.getClientBusinessPhone()));
        tvContractTabBeneficiaryHomePhone.setText(StringUtil.setTextValue(expandedListText.getClientHomePhone()));
        tvContractTabBeneficiaryTypeId.setText(StringUtil.getClientIdentityDoctypeFromCode(expandedListText.getClientIdentityDoctype()));
        tvContractTabBeneficiaryNumberId.setText(StringUtil.setTextValue(expandedListText.getClientIdnumber()));
        tvContractTabBeneficiaryDateId.setText(StringUtil.formatDateDDMMYYFromDate(expandedListText.getClientDateOfIssue(), Constant.DDMMYY));
        tvContractTabBeneficiaryAddressId.setText(StringUtil.setTextValue(expandedListText.getClientIssuingAuthority()));
        tvContractTabBeneficiaryTaxCode.setText(StringUtil.setTextValue(expandedListText.getClientTaxCode()));
        tvContractTabBeneficiaryProduct.setText(StringUtil.setTextValue(expandedListText.getClientRepresentativeName()));

        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        if (mExpandableListPreminum != null) {
            return Constant.INT_1;
        }
        return Constant.INT_0;
    }

    @Override
    public Object getGroup(int listPosition) {
        return mExpandableListPreminum.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return mExpandableListPreminum.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        BeneficiaryDTO transPreminum = (BeneficiaryDTO) getGroup(listPosition);
        String clientTypeCode = transPreminum.getClientTypeCode();
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_beneficiary_tab_group, null);
        }
        TextView tvContactTabBeneficiaryTypeDevice = (TextView) convertView.findViewById(R.id.tvContactTabBeneficiaryTypeDevice);
        TextView tvBeneficiaryTabHeaderFullName = (TextView) convertView.findViewById(R.id.tvBeneficiaryTabHeaderFullName);
        TextView tvBeneficiaryTabHeaderRepresentName = (TextView) convertView.findViewById(R.id.tvBeneficiaryTabHeaderRepresentName);
        LinearLayout lnBeneficiaryTabHeaderRespresentName = (LinearLayout) convertView.findViewById(R.id.lnBeneficiaryTabHeaderRespresentName);
        LinearLayout lnBeneficiaryTabHeaderBirthDay = (LinearLayout) convertView.findViewById(R.id.lnBeneficiaryTabHeaderBirthDay);
        LinearLayout lnBeneficiaryTabHeaderGender = (LinearLayout) convertView.findViewById(R.id.lnBeneficiaryTabHeaderGender);
        TextView tvBeneficiaryTabHeaderBirthDay = (TextView) convertView.findViewById(R.id.tvBeneficiaryTabHeaderBirthDay);
        TextView tvBeneficiaryTabHeaderGender = (TextView) convertView.findViewById(R.id.tvBeneficiaryTabHeaderGender);
        ImageView imgArrowBeneficiary = (ImageView) convertView.findViewById(R.id.imgArrowBeneficiary);
        String typeDevice = tvContactTabBeneficiaryTypeDevice.getText().toString();

        tvBeneficiaryTabHeaderFullName.setText(StringUtil.setTextValue(transPreminum.getClientName()));
        if (Constant.CLIENT_TYPE_CODE_COMP.equals(clientTypeCode)) {
            lnBeneficiaryTabHeaderRespresentName.setVisibility(View.VISIBLE);
            lnBeneficiaryTabHeaderBirthDay.setVisibility(View.GONE);
            lnBeneficiaryTabHeaderGender.setVisibility(View.GONE);
            tvBeneficiaryTabHeaderRepresentName.setText(StringUtil.setTextValue(transPreminum.getClientRepresentativeName()));
        } else {
            lnBeneficiaryTabHeaderRespresentName.setVisibility(View.GONE);
            lnBeneficiaryTabHeaderBirthDay.setVisibility(View.VISIBLE);
            lnBeneficiaryTabHeaderGender.setVisibility(View.VISIBLE);
            tvBeneficiaryTabHeaderBirthDay.setText(StringUtil.formatDateDDMMYYFromDate(transPreminum.getClientDateOfBirth(), Constant.DDMMYY));
            tvBeneficiaryTabHeaderGender.setText(StringUtil.setTextValue(transPreminum.getClientSexDescription()));
        }

        int countChild = getChildrenCount(listPosition);
        if (Constant.INT_0 < countChild) {
            imgArrowBeneficiary.setVisibility(View.VISIBLE);
            if (isExpanded) {
                imgArrowBeneficiary.setImageResource(R.drawable.ic_arrow_up);
            } else {
                imgArrowBeneficiary.setImageResource(R.drawable.ic_arrow_down);
            }
        } else {
            imgArrowBeneficiary.setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}
