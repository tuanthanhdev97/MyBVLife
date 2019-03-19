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
import app.com.baoviet.entity.FeesChargesTransDTO;
import app.com.baoviet.utility.StringUtil;

public class CustomExpandableListFeeAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<FeesChargesTransDTO> mExpandableListPreminum;
    private LayoutInflater mLayoutInflater;

    public CustomExpandableListFeeAdapter(Context context, List<FeesChargesTransDTO> mExpandableListPreminum) {
        this.mExpandableListPreminum = mExpandableListPreminum;
        this.mContext = context;
        this.mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        FeesChargesTransDTO transPreminum = null;
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
        final FeesChargesTransDTO expandedListText = (FeesChargesTransDTO) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_fee_tab_item, null);
        }
        TextView tvContractTabFeeManageContract = (TextView) convertView.findViewById(R.id.tvContractTabFeeManageContract);
        TextView tvContractTabFeeRisk = (TextView) convertView.findViewById(R.id.tvContractTabFeeRisk);
        TextView tvContractTabFeeOther = (TextView) convertView.findViewById(R.id.tvContractTabFeeOther);
        TextView tvContractTabFeeTotal = (TextView) convertView.findViewById(R.id.tvContractTabFeeTotal);
        TextView tvContactTabFeeTypeDevice = (TextView) convertView.findViewById(R.id.tvContactTabFeeTypeDevice);
        TextView tvContractTabFeeNumberPayment;
        TextView tvContractTabFeeProcessEffectiveDate;

        String typeDevice = tvContactTabFeeTypeDevice.getText().toString();
        if (typeDevice.equals(Constant.TYPE_DEVICE_PHONE)) {
            tvContractTabFeeNumberPayment = (TextView) convertView.findViewById(R.id.tvContractTabFeeNumberPayment);
            tvContractTabFeeNumberPayment.setText(StringUtil.setTextValue(expandedListText.getActrId().toString()));
            tvContractTabFeeProcessEffectiveDate = (TextView) convertView.findViewById(R.id.tvContractTabFeeProcessEffectiveDate);
            tvContractTabFeeProcessEffectiveDate.setText(StringUtil.formatDateDDMMYYFromDate(expandedListText.getActrEffectiveDate(), Constant.DDMMYY));
        } else if (typeDevice.equals(Constant.TYPE_DEVICE_TABLET)) {

        }

        tvContractTabFeeManageContract.setText(StringUtil.convertToCurrency(expandedListText.getActrAmountFees()));
        tvContractTabFeeRisk.setText(StringUtil.convertToCurrency(expandedListText.getActrAmountCharges()));
        tvContractTabFeeOther.setText(StringUtil.convertToCurrency(expandedListText.getActrAmountOthers()));
        tvContractTabFeeTotal.setText(StringUtil.convertToCurrency(expandedListText.getActrAmountTotal()));

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
        FeesChargesTransDTO transPreminum = (FeesChargesTransDTO) getGroup(listPosition);
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_fee_tab_group, null);
        }
        TextView tvContactTabFeeTypeDevice = (TextView) convertView.findViewById(R.id.tvContactTabFeeTypeDevice);
        TextView tvFeeTabHeaderNumberPayment = (TextView) convertView.findViewById(R.id.tvFeeTabHeaderNumberPayment);
        ImageView imgArrowFee = (ImageView) convertView.findViewById(R.id.imgArrowFee);
        TextView tvFeeTabHeaderEffectiveDate;
        TextView tvFeeTabHeaderTotal;
        String typeDevice = tvContactTabFeeTypeDevice.getText().toString();
        if (typeDevice.equals(Constant.TYPE_DEVICE_TABLET)) {
            tvFeeTabHeaderEffectiveDate = (TextView) convertView.findViewById(R.id.tvFeeTabHeaderEffectiveDate);
            tvFeeTabHeaderTotal = (TextView) convertView.findViewById(R.id.tvFeeTabHeaderTotal);
            tvFeeTabHeaderEffectiveDate.setText(StringUtil.formatDateDDMMYYFromDate(transPreminum.getActrEffectiveDate(), Constant.DDMMYY));
            tvFeeTabHeaderTotal.setText(StringUtil.convertToCurrency(transPreminum.getActrAmountTotal()));
        } else if (typeDevice.equals(Constant.TYPE_DEVICE_PHONE)) {

        }
        int countChild = getChildrenCount(listPosition);
        if (Constant.INT_0 < countChild) {
            imgArrowFee.setVisibility(View.VISIBLE);
            if (isExpanded) {
                imgArrowFee.setImageResource(R.drawable.ic_arrow_up);
            } else {
                imgArrowFee.setImageResource(R.drawable.ic_arrow_down);
            }
        } else {
            imgArrowFee.setVisibility(View.GONE);
        }
        tvFeeTabHeaderNumberPayment.setText(StringUtil.setTextValue(transPreminum.getActrId().toString()));
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
