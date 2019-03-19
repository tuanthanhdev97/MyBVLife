package app.com.baoviet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import app.com.baoviet.R;
import app.com.baoviet.constant.Constant;
import app.com.baoviet.entity.Invoice;
import app.com.baoviet.fragment.PaymentFragment;
import app.com.baoviet.utility.StringUtil;

public class CustomExpandableListPaymentInforDetailAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<Invoice> mExpandableListPreminum;
    private SortedSet<Integer> listPositionChecked = new TreeSet<Integer>();
    private LayoutInflater mLayoutInflater;
    private List<Invoice> mListInvoiceChoose = new ArrayList<Invoice>();
    private TextView tvPaymentDetailTotal;
    private BigDecimal invoiceAmountNet = new BigDecimal(Constant.INT_0);

    public CustomExpandableListPaymentInforDetailAdapter(Context context, List<Invoice> mExpandableListPreminum, TextView tvPaymentDetailTotal) {
        this.mExpandableListPreminum = mExpandableListPreminum;
        this.mContext = context;
        this.tvPaymentDetailTotal = tvPaymentDetailTotal;
        this.mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        Invoice transPreminum = null;
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
        final Invoice expandedListText = (Invoice) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_payment_infor_detail_item, null);
        }
        TextView tvPaymentInforDetailItemFromDate = (TextView) convertView.findViewById(R.id.tvPaymentInforDetailItemFromDate);
        TextView tvPaymentInforDetailItemToDate = (TextView) convertView.findViewById(R.id.tvPaymentInforDetailItemToDate);
        TextView tvPaymentInforDetailItemBillNumber = (TextView) convertView.findViewById(R.id.tvPaymentInforDetailItemBillNumber);
        TextView tvPaymentInforDetailItemFeeDiscount = (TextView) convertView.findViewById(R.id.tvPaymentInforDetailItemFeeDiscount);
        TextView tvPaymentInforDetailItemFeeNet = (TextView) convertView.findViewById(R.id.tvPaymentInforDetailItemFeeNet);

        tvPaymentInforDetailItemFromDate.setText(StringUtil.formatDateDDMMYYFromLong(expandedListText.getDfromDate()));
        tvPaymentInforDetailItemToDate.setText(StringUtil.formatDateDDMMYYFromLong(expandedListText.getDtoDate()));
        tvPaymentInforDetailItemBillNumber.setText(StringUtil.convertToCurrency(expandedListText.getBbiInvoiceAmount()));
        tvPaymentInforDetailItemFeeDiscount.setText(StringUtil.convertToCurrency(expandedListText.getBbiDiscountAmount()));
        tvPaymentInforDetailItemFeeNet.setText(StringUtil.convertToCurrency(expandedListText.getBbiInvoiceAmountNet()));
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
    public View getGroupView(final int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        final Invoice transPreminum = (Invoice) getGroup(listPosition);
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_payment_infor_detail_group, null);
        }
        CheckBox cbPaymentDetailItemHeader = (CheckBox) convertView.findViewById(R.id.cbPaymentDetailItemHeader);
        TextView tvPaymentDetailItemHeadertvFeePeriod = (TextView) convertView.findViewById(R.id.tvPaymentDetailItemHeadertvFeePeriod);
        TextView tvPaymentDetailItemHeadertvFeeVnd = (TextView) convertView.findViewById(R.id.tvPaymentDetailItemHeadertvFeeVnd);
        ImageView imgArrowPaymentInforDetail = (ImageView) convertView.findViewById(R.id.imgArrowPaymentInforDetail);
        cbPaymentDetailItemHeader.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                BigDecimal tempAmount = transPreminum.getBbiInvoiceAmountNet();

//                List<Integer> listNumberSelect = new ArrayList<Integer>();
                if (isChecked) {
                    listPositionChecked.add(listPosition);
                    transPreminum.setCheckbox(true);
                    mListInvoiceChoose.add(transPreminum);
                    invoiceAmountNet = invoiceAmountNet.add(tempAmount);
                } else {
                    listPositionChecked.remove(listPosition);
                    transPreminum.setCheckbox(false);
                    mListInvoiceChoose.remove(transPreminum);
                    invoiceAmountNet = invoiceAmountNet.subtract(tempAmount);
                }
                PaymentFragment.listPositionChecked = listPositionChecked;
                PaymentFragment.mListInvoiceChoose = mListInvoiceChoose;
                tvPaymentDetailTotal.setText(StringUtil.convertToCurrency(invoiceAmountNet));
            }
        });

        String fromDate = StringUtil.formatDateDDMMYYFromLong(transPreminum.getDfromDate());
        String toDate = StringUtil.formatDateDDMMYYFromLong(transPreminum.getDtoDate());
        tvPaymentDetailItemHeadertvFeePeriod.setText(StringUtil.setTextValue(fromDate + Constant.SYMBOL_HYPHEN + toDate));
        tvPaymentDetailItemHeadertvFeeVnd.setText(StringUtil.convertToCurrency(transPreminum.getBbiInvoiceAmountNet()));
        int countChild = getChildrenCount(listPosition);
        if (Constant.INT_0 < countChild) {
            imgArrowPaymentInforDetail.setVisibility(View.VISIBLE);
            if (isExpanded) {
                imgArrowPaymentInforDetail.setImageResource(R.drawable.ic_arrow_up);
            } else {
                imgArrowPaymentInforDetail.setImageResource(R.drawable.ic_arrow_down);
            }
        } else {
            imgArrowPaymentInforDetail.setVisibility(View.GONE);
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
