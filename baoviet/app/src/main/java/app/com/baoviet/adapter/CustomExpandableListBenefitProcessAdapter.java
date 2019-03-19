package app.com.baoviet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import app.com.baoviet.R;
import app.com.baoviet.constant.Constant;
import app.com.baoviet.entity.ClaimInfoDTO;
import app.com.baoviet.utility.StringUtil;

public class CustomExpandableListBenefitProcessAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<ClaimInfoDTO> mExpandableListPreminum;
    private LayoutInflater mLayoutInflater;
    private String typeDevice;

    public CustomExpandableListBenefitProcessAdapter(Context context, List<ClaimInfoDTO> mExpandableListPreminum) {
        this.mExpandableListPreminum = mExpandableListPreminum;
        this.mContext = context;
        this.mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        ClaimInfoDTO transPreminum = null;
        if (mExpandableListPreminum != null && typeDevice.equals(Constant.TYPE_DEVICE_PHONE)) {
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
        final ClaimInfoDTO expandedListText = (ClaimInfoDTO) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_benefit_tab_processing_item, null);
        }
        TextView tvContractTabBenefitProAssigner;
        TextView tvContractTabBenefitProBirthDay;
        TextView tvContractTabBenefitProDateRisk;
        TextView tvContractTabBenefitProRiskType;
        TextView tvContractTabBenefitProductCode;
        RelativeLayout rlContractTabBenefitProductCode;

        TextView tvContactTabBenefitProItemTypeDevice = (TextView) convertView.findViewById(R.id.tvContactTabBenefitProItemTypeDevice);
        typeDevice = tvContactTabBenefitProItemTypeDevice.getText().toString();
        if (typeDevice.equals(Constant.TYPE_DEVICE_PHONE)) {
            tvContractTabBenefitProAssigner = (TextView) convertView.findViewById(R.id.tvContractTabBenefitProAssigner);
            tvContractTabBenefitProBirthDay = (TextView) convertView.findViewById(R.id.tvContractTabBenefitProBirthDay);
            tvContractTabBenefitProDateRisk = (TextView) convertView.findViewById(R.id.tvContractTabBenefitProDateRisk);
            tvContractTabBenefitProRiskType = (TextView) convertView.findViewById(R.id.tvContractTabBenefitProRiskType);
            tvContractTabBenefitProductCode = (TextView) convertView.findViewById(R.id.tvContractTabBenefitProductCode);
            rlContractTabBenefitProductCode = (RelativeLayout) convertView.findViewById(R.id.rlContractTabBenefitProductCode);

            if (!StringUtil.isNullOrEmpty(expandedListText.getClaimProductCode())) {
                rlContractTabBenefitProductCode.setVisibility(View.VISIBLE);
                tvContractTabBenefitProductCode.setText(StringUtil.setTextValue(expandedListText.getClaimProductCode()));
            } else {
                rlContractTabBenefitProductCode.setVisibility(View.GONE);
            }

            tvContractTabBenefitProAssigner.setText(StringUtil.setTextValue(expandedListText.getClaimClientName()));
            tvContractTabBenefitProBirthDay.setText(StringUtil.formatDateDDMMYYFromDate(expandedListText.getClaimClientDateOfBirth(), Constant.DDMMYY));
            tvContractTabBenefitProDateRisk.setText(StringUtil.formatDateDDMMYYFromDate(expandedListText.getClaimEventDate(), Constant.DDMMYY));
            tvContractTabBenefitProRiskType.setText(StringUtil.setTextValue(expandedListText.getClaimRiskEventDesc()));

        } else if (typeDevice.equals(Constant.TYPE_DEVICE_TABLET)) {

        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        if (mExpandableListPreminum != null && typeDevice.equals(Constant.TYPE_DEVICE_PHONE)) {
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
        ClaimInfoDTO transPreminum = (ClaimInfoDTO) getGroup(listPosition);
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_benefit_tab_processing_group, null);
        }
        TextView tvContactTabBenefitProTypeDevice = (TextView) convertView.findViewById(R.id.tvContactTabBenefitProTypeDevice);

        TextView tvBenefitTabProFullName = (TextView) convertView.findViewById(R.id.tvBenefitTabProFullName);
        TextView tvBenefitTabProBirthDay = (TextView) convertView.findViewById(R.id.tvBenefitTabProBirthDay);
        tvBenefitTabProFullName.setText(StringUtil.setTextValue(transPreminum.getClaimClientName()));
        tvBenefitTabProBirthDay.setText(StringUtil.formatDateDDMMYYFromDate(transPreminum.getClaimClientDateOfBirth(), Constant.DDMMYY));

        ImageView imgArrowBenefitPro;
        TextView tvBenefitTabProDateRisk;
        TextView tvBenefitTabProTypeRisk;
        TextView tvBenefitTabProductCode;
        LinearLayout lnBenefitTabProductCode;
        typeDevice = tvContactTabBenefitProTypeDevice.getText().toString();
        if (typeDevice.equals(Constant.TYPE_DEVICE_TABLET)) {
            tvBenefitTabProDateRisk = (TextView) convertView.findViewById(R.id.tvBenefitTabProDateRisk);
            tvBenefitTabProTypeRisk = (TextView) convertView.findViewById(R.id.tvBenefitTabProTypeRisk);
            lnBenefitTabProductCode = (LinearLayout) convertView.findViewById(R.id.lnBenefitTabProductCode);
            tvBenefitTabProductCode = (TextView) convertView.findViewById(R.id.tvBenefitTabProductCode);
            if (!StringUtil.isNullOrEmpty(transPreminum.getClaimProductCode())) {
                lnBenefitTabProductCode.setVisibility(View.VISIBLE);
                tvBenefitTabProductCode.setText(StringUtil.setTextValue((transPreminum.getClaimProductCode())));
            } else {
                lnBenefitTabProductCode.setVisibility(View.GONE);
            }
            tvBenefitTabProDateRisk.setText(StringUtil.formatDateDDMMYYFromDate(transPreminum.getClaimEventDate(), Constant.DDMMYY));
            tvBenefitTabProTypeRisk.setText(StringUtil.setTextValue(transPreminum.getClaimRiskEventDesc()));
        } else if (typeDevice.equals(Constant.TYPE_DEVICE_PHONE)) {
            imgArrowBenefitPro = (ImageView) convertView.findViewById(R.id.imgArrowBenefitPro);
            int countChild = getChildrenCount(listPosition);
            if (Constant.INT_0 < countChild && typeDevice.equals(Constant.TYPE_DEVICE_PHONE)) {
                imgArrowBenefitPro.setVisibility(View.VISIBLE);
                if (isExpanded) {
                    imgArrowBenefitPro.setImageResource(R.drawable.ic_arrow_up);
                } else {
                    imgArrowBenefitPro.setImageResource(R.drawable.ic_arrow_down);
                }
            } else {
                imgArrowBenefitPro.setVisibility(View.GONE);
            }
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
