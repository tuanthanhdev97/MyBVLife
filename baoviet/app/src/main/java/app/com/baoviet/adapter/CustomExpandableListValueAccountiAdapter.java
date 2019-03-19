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
import app.com.baoviet.entity.PavDTO;
import app.com.baoviet.utility.StringUtil;

public class CustomExpandableListValueAccountiAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<PavDTO> mExpandableListPreminum;
    private LayoutInflater mLayoutInflater;
    private String productTypeCode;

    public CustomExpandableListValueAccountiAdapter(Context context, List<PavDTO> mExpandableListPreminum, String productTypeCode) {
        this.mExpandableListPreminum = mExpandableListPreminum;
        this.mContext = context;
        this.productTypeCode = productTypeCode;
        this.mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        PavDTO transPreminum = null;
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
        final PavDTO expandedListText = (PavDTO) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_value_account_tab_item, null);
        }
        TextView tvContactTabValueAccountItemTypeDevice = (TextView) convertView.findViewById(R.id.tvContactTabValueAccountItemTypeDevice);

        TextView tvContractTabAccountValueInsuredPerson = (TextView) convertView.findViewById(R.id.tvContractTabAccountValueInsuredPerson);
        TextView tvContractTabAccountValueInsuredPersonRate = (TextView) convertView.findViewById(R.id.tvContractTabAccountValueInsuredPersonRate);
        TextView tvContractTabAccountValueBuyerValue = (TextView) convertView.findViewById(R.id.tvContractTabAccountValueBuyerValue);
        TextView tvContractTabAccountValueBuyerRate = (TextView) convertView.findViewById(R.id.tvContractTabAccountValueBuyerRate);
        TextView tvContractTabAccountValueSummary = (TextView) convertView.findViewById(R.id.tvContractTabAccountValueSummary);

        String typeDevice = tvContactTabValueAccountItemTypeDevice.getText().toString();

        tvContractTabAccountValueInsuredPerson.setText(StringUtil.convertToCurrency(expandedListText.getPavValueM()));
        tvContractTabAccountValueInsuredPersonRate.setText(StringUtil.setTextValue(expandedListText.getPavRatioM() + Constant.EMPTY));
        tvContractTabAccountValueBuyerValue.setText(StringUtil.convertToCurrency(expandedListText.getPavValueS()));
        tvContractTabAccountValueBuyerRate.setText(StringUtil.setTextValue(expandedListText.getPavRatioS() + Constant.EMPTY));
        tvContractTabAccountValueSummary.setText(StringUtil.convertToCurrency(expandedListText.getPavTotal()));
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
        PavDTO transPreminum = (PavDTO) getGroup(listPosition);
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_value_account_tab_group, null);
        }
        TextView tvContactTabValueAccountTypeDevice = (TextView) convertView.findViewById(R.id.tvContactTabValueAccountTypeDevice);
        LinearLayout lnAccountValueTabHeaderTotal = (LinearLayout) convertView.findViewById(R.id.lnAccountValueTabHeaderTotal);
        LinearLayout lnAccountValueTabHeaderVnd = (LinearLayout) convertView.findViewById(R.id.lnAccountValueTabHeaderVnd);
        TextView tvAccountValueTabHeaderDate = (TextView) convertView.findViewById(R.id.tvAccountValueTabHeaderDate);
        TextView tvAccountValueTabHeaderTotal = (TextView) convertView.findViewById(R.id.tvAccountValueTabHeaderTotal);
        TextView tvAccountValueTabHeaderVnd = (TextView) convertView.findViewById(R.id.tvAccountValueTabHeaderVnd);
        ImageView imgArrowValueAccount = (ImageView) convertView.findViewById(R.id.imgArrowValueAccount);
        String typeDevice = tvContactTabValueAccountTypeDevice.getText().toString();

        tvAccountValueTabHeaderDate.setText(StringUtil.formatDateDDMMYYFromLong(transPreminum.getPavDate()));
        if (this.productTypeCode.equals(Constant.PRODUCT_TYPE_CODE_PENG)) {
            lnAccountValueTabHeaderTotal.setVisibility(View.VISIBLE);
            lnAccountValueTabHeaderVnd.setVisibility(View.GONE);
            tvAccountValueTabHeaderTotal.setText(StringUtil.convertToCurrency(transPreminum.getPavTotal()));
        } else if (!this.productTypeCode.equals(Constant.PRODUCT_TYPE_CODE_PENG)) {
            lnAccountValueTabHeaderTotal.setVisibility(View.GONE);
            lnAccountValueTabHeaderVnd.setVisibility(View.VISIBLE);
            if (!this.productTypeCode.equals(Constant.PRODUCT_TYPE_CODE_PENI)) {
                tvAccountValueTabHeaderVnd.setText(StringUtil.convertToCurrency(transPreminum.getPavValueO()));
            } else if (this.productTypeCode.equals(Constant.PRODUCT_TYPE_CODE_PENI)) {
                tvAccountValueTabHeaderVnd.setText(StringUtil.convertToCurrency(transPreminum.getPavValueM()));
            }
        }

        int countChild = getChildrenCount(listPosition);
        if (Constant.INT_0 < countChild) {
            imgArrowValueAccount.setVisibility(View.VISIBLE);
            if (isExpanded) {
                imgArrowValueAccount.setImageResource(R.drawable.ic_arrow_up);
            } else {
                imgArrowValueAccount.setImageResource(R.drawable.ic_arrow_down);
            }
        } else {
            imgArrowValueAccount.setVisibility(View.GONE);
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
