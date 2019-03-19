package app.com.baoviet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import app.com.baoviet.R;
import app.com.baoviet.constant.Constant;
import app.com.baoviet.entity.ClaimPaymentDTO;
import app.com.baoviet.utility.StringUtil;

public class CustomExpandableListBenefitAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<ClaimPaymentDTO> mExpandableListPreminum;
    private LayoutInflater mLayoutInflater;
    private String typeDevice;

    public CustomExpandableListBenefitAdapter(Context context, List<ClaimPaymentDTO> mExpandableListPreminum) {
        this.mExpandableListPreminum = mExpandableListPreminum;
        this.mContext = context;
        this.mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        ClaimPaymentDTO transPreminum = null;
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
        final ClaimPaymentDTO expandedListText = (ClaimPaymentDTO) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_benefit_tab_item, null);
        }
        TextView tvContractTabBenefitPaymentNumber;
        TextView tvContractTabBenefitDateProcess;
        TextView tvContractTabBenefitAmountMoney;

        TextView tvContactTabBenefitTypeDevice = (TextView) convertView.findViewById(R.id.tvContactTabBenefitTypeDevice);
        typeDevice = tvContactTabBenefitTypeDevice.getText().toString();
        if (typeDevice.equals(Constant.TYPE_DEVICE_PHONE)) {
            tvContractTabBenefitPaymentNumber = (TextView) convertView.findViewById(R.id.tvContractTabBenefitPaymentNumber);
            tvContractTabBenefitPaymentNumber.setText(StringUtil.setTextValue(expandedListText.getCltrId().toString()));
            tvContractTabBenefitDateProcess = (TextView) convertView.findViewById(R.id.tvContractTabBenefitDateProcess);
            tvContractTabBenefitDateProcess.setText(StringUtil.formatDateDDMMYYFromDate(expandedListText.getCltrPaymentDate(), Constant.DDMMYY));
            tvContractTabBenefitAmountMoney = (TextView) convertView.findViewById(R.id.tvContractTabBenefitAmountMoney);
            tvContractTabBenefitAmountMoney.setText(StringUtil.convertToCurrency(expandedListText.getCltrAmount()));
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
        ClaimPaymentDTO transPreminum = (ClaimPaymentDTO) getGroup(listPosition);
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_benefit_tab_group, null);
        }
        TextView tvBenefitTabHeaderDeviceType = (TextView) convertView.findViewById(R.id.tvBenefitTabHeaderDeviceType);
        TextView tvBenefitTabHeaderNumberPayment = (TextView) convertView.findViewById(R.id.tvBenefitTabHeaderNumberPayment);
        tvBenefitTabHeaderNumberPayment.setText(StringUtil.setTextValue(transPreminum.getCltrId().toString()));
        ImageView imgArrowBenefit = null;
        TextView tvBenefitTabHeaderDateProcess;
        TextView tvBenefitTabHeaderAmountMoney;
        typeDevice = tvBenefitTabHeaderDeviceType.getText().toString();
        if (typeDevice.equals(Constant.TYPE_DEVICE_TABLET)) {
            tvBenefitTabHeaderDateProcess = (TextView) convertView.findViewById(R.id.tvBenefitTabHeaderDateProcess);
            tvBenefitTabHeaderAmountMoney = (TextView) convertView.findViewById(R.id.tvBenefitTabHeaderAmountMoney);
            tvBenefitTabHeaderDateProcess.setText(StringUtil.formatDateDDMMYYFromDate(transPreminum.getCltrPaymentDate(), Constant.DDMMYY));
            tvBenefitTabHeaderAmountMoney.setText(StringUtil.convertToCurrency(transPreminum.getCltrAmount()));
        } else if (typeDevice.equals(Constant.TYPE_DEVICE_PHONE)) {
            imgArrowBenefit = (ImageView) convertView.findViewById(R.id.imgArrowBenefit);
            int countChild = getChildrenCount(listPosition);
            if (Constant.INT_0 < countChild) {
                imgArrowBenefit.setVisibility(View.VISIBLE);
                if (isExpanded) {
                    imgArrowBenefit.setImageResource(R.drawable.ic_arrow_up);
                } else {
                    imgArrowBenefit.setImageResource(R.drawable.ic_arrow_down);
                }
            } else {
                imgArrowBenefit.setVisibility(View.GONE);
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
