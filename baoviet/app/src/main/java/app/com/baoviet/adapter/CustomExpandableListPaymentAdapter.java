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
import app.com.baoviet.entity.PremiumTransDTO;
import app.com.baoviet.utility.StringUtil;

public class CustomExpandableListPaymentAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<PremiumTransDTO> mExpandableListPreminum;
    private LayoutInflater mLayoutInflater;
    private String typeAccount;
    private String productTypeCode;

    public CustomExpandableListPaymentAdapter(Context context, List<PremiumTransDTO> mExpandableListPreminum, String typeAccount, String productTypeCode) {
        this.mExpandableListPreminum = mExpandableListPreminum;
        this.mContext = context;
        this.typeAccount = typeAccount;
        this.productTypeCode = productTypeCode;
        this.mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        PremiumTransDTO transPreminum = null;
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
        final PremiumTransDTO expandedListText = (PremiumTransDTO) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_payment_tab_item, null);
        }
        TextView tvContractTabPaymentProcessDayTrading = (TextView) convertView.findViewById(R.id.tvContractTabPaymentProcessDayTrading);
        TextView tvContractTabPaymentProcessTotal = (TextView) convertView.findViewById(R.id.tvContractTabPaymentProcessTotal);
        TextView tvContractTabPaymentProcessTotalLabel = (TextView) convertView.findViewById(R.id.tvContractTabPaymentProcessTotalLabel);
        TextView tvContactTabPaymentTypeDevice = (TextView) convertView.findViewById(R.id.tvContactTabPaymentTypeDevice);
        TextView tvContractTabPaymentProcessEffectiveDate;
        LinearLayout lnContractTabPaymentProcessPeriodicPremiumsTa = (LinearLayout) convertView.findViewById(R.id.lnContractTabPaymentProcessPeriodicPremiumsTa);
        LinearLayout lnContractTabPaymentProcessPeriodicPremiumsBv = (LinearLayout) convertView.findViewById(R.id.lnContractTabPaymentProcessPeriodicPremiumsBv);

        //Talisman
        TextView tvContractTabPaymentProcessPerBuyer;
        TextView tvContractTabPaymentProcessMember;
        TextView tvContractTabPaymentProcessAddBuyer;
        TextView tvContractTabPaymentProcessMemberSecond;

        //Bvlife
        TextView tvContractTabPaymentProcessPeriodicPremiumsBv;
        TextView tvContractTabPaymentProcessAdditionalPremiumBv;

        String typeDevice = tvContactTabPaymentTypeDevice.getText().toString();
        if (Constant.PRODUCT_TYPE_CODE_PENG.equals(this.productTypeCode)) {
            lnContractTabPaymentProcessPeriodicPremiumsTa.setVisibility(View.VISIBLE);
            lnContractTabPaymentProcessPeriodicPremiumsBv.setVisibility(View.GONE);
            tvContractTabPaymentProcessPerBuyer = (TextView) convertView.findViewById(R.id.tvContractTabPaymentProcessPerBuyer);
            tvContractTabPaymentProcessMember = (TextView) convertView.findViewById(R.id.tvContractTabPaymentProcessMember);
            tvContractTabPaymentProcessAddBuyer = (TextView) convertView.findViewById(R.id.tvContractTabPaymentProcessAddBuyer);
            tvContractTabPaymentProcessMemberSecond = (TextView) convertView.findViewById(R.id.tvContractTabPaymentProcessMemberSecond);

            tvContractTabPaymentProcessPerBuyer.setText(StringUtil.convertToCurrency(expandedListText.getActrAmountReguSponsor()));
            tvContractTabPaymentProcessMember.setText(StringUtil.convertToCurrency(expandedListText.getActrAmountReguMember()));
            tvContractTabPaymentProcessAddBuyer.setText(StringUtil.convertToCurrency(expandedListText.getActrAmountLsumSponsor()));
            tvContractTabPaymentProcessMemberSecond.setText(StringUtil.convertToCurrency(expandedListText.getActrAmountLsumMember()));
        }

        if (Constant.TYPE_ACCOUNT_TALISMAN.equals(typeAccount) && (Constant.PRODUCT_TYPE_CODE_UNIV.equals(this.productTypeCode)
                || Constant.PRODUCT_TYPE_CODE_TRLF.equals(this.productTypeCode))) {
            lnContractTabPaymentProcessPeriodicPremiumsTa.setVisibility(View.GONE);
            lnContractTabPaymentProcessPeriodicPremiumsBv.setVisibility(View.VISIBLE);
            tvContractTabPaymentProcessPeriodicPremiumsBv = (TextView) convertView.findViewById(R.id.tvContractTabPaymentProcessPeriodicPremiumsBv);
            tvContractTabPaymentProcessAdditionalPremiumBv = (TextView) convertView.findViewById(R.id.tvContractTabPaymentProcessAdditionalPremiumBv);
            tvContractTabPaymentProcessPeriodicPremiumsBv.setText(StringUtil.convertToCurrency(expandedListText.getActrAmountReguPolicyholder()));
            tvContractTabPaymentProcessAdditionalPremiumBv.setText(StringUtil.convertToCurrency(expandedListText.getActrAmountLsumPolicyholder()));
        }

        if (Constant.PRODUCT_TYPE_CODE_PENI.equals(this.productTypeCode)) {
            lnContractTabPaymentProcessPeriodicPremiumsTa.setVisibility(View.GONE);
            lnContractTabPaymentProcessPeriodicPremiumsBv.setVisibility(View.VISIBLE);
            tvContractTabPaymentProcessPeriodicPremiumsBv = (TextView) convertView.findViewById(R.id.tvContractTabPaymentProcessPeriodicPremiumsBv);
            tvContractTabPaymentProcessAdditionalPremiumBv = (TextView) convertView.findViewById(R.id.tvContractTabPaymentProcessAdditionalPremiumBv);
            tvContractTabPaymentProcessPeriodicPremiumsBv.setText(StringUtil.convertToCurrency(expandedListText.getActrAmountReguMember()));
            tvContractTabPaymentProcessAdditionalPremiumBv.setText(StringUtil.convertToCurrency(expandedListText.getActrAmountLsumMember()));
        }

        tvContractTabPaymentProcessDayTrading.setText(StringUtil.formatDateDDMMYYFromDate(expandedListText.getActrProcessedDate(), Constant.DDMMYY));

        if (Constant.TYPE_ACCOUNT_BVLIFE.equals(typeAccount)) {
            tvContractTabPaymentProcessTotalLabel.setText(mContext.getString(R.string.txt_contract_tab_payment_process_item_list_total_bvlife));
            tvContractTabPaymentProcessTotal.setText(StringUtil.convertToCurrency(expandedListText.getActrAmountReguPolicyholder()));
        } else if (Constant.TYPE_ACCOUNT_TALISMAN.equals(typeAccount)) {
            tvContractTabPaymentProcessTotalLabel.setText(mContext.getString(R.string.txt_contract_tab_payment_process_item_list_total));
            tvContractTabPaymentProcessTotal.setText(StringUtil.convertToCurrency(expandedListText.getActrAmountTotal()));
        }
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
        PremiumTransDTO transPreminum = (PremiumTransDTO) getGroup(listPosition);
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_payment_tab_group, null);
        }
        TextView tvContactTabPaymentTypeDevice = (TextView) convertView.findViewById(R.id.tvContactTabPaymentTypeDevice);
        TextView tvPaymentTabHeaderNumberPayment = (TextView) convertView.findViewById(R.id.tvPaymentTabHeaderNumberPayment);
        ImageView imgArrowPayment = (ImageView) convertView.findViewById(R.id.imgArrowPayment);
        TextView tvPaymentTabHeaderEffectiveDate;
        TextView tvPaymentTabHeaderAmountMoney;
        String typeDevice = tvContactTabPaymentTypeDevice.getText().toString();
        tvPaymentTabHeaderEffectiveDate = (TextView) convertView.findViewById(R.id.tvPaymentTabHeaderEffectiveDate);
        tvPaymentTabHeaderAmountMoney = (TextView) convertView.findViewById(R.id.tvPaymentTabHeaderAmountMoney);
        tvPaymentTabHeaderEffectiveDate.setText(StringUtil.formatDateDDMMYYFromDate(transPreminum.getActrEffectiveDate(), Constant.DDMMYY));
        tvPaymentTabHeaderAmountMoney.setText(StringUtil.convertToCurrency(transPreminum.getActrAmountTotal()));

        int countChild = getChildrenCount(listPosition);
        if (Constant.INT_0 < countChild) {
            imgArrowPayment.setVisibility(View.VISIBLE);
            if (isExpanded) {
                imgArrowPayment.setImageResource(R.drawable.ic_arrow_up);
            } else {
                imgArrowPayment.setImageResource(R.drawable.ic_arrow_down);
            }
        } else {
            imgArrowPayment.setVisibility(View.GONE);
        }
        tvPaymentTabHeaderNumberPayment.setText(StringUtil.setTextValue(transPreminum.getActrId()));
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
