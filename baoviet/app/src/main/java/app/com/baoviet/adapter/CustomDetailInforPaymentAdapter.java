package app.com.baoviet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

public class CustomDetailInforPaymentAdapter extends BaseAdapter {

    private List<Invoice> listInvoid = new ArrayList<Invoice>();
    private LayoutInflater inflater;
    private Context context;
    private SortedSet<Integer> listPositionChecked = new TreeSet<Integer>();
    private PaymentHolder paymentHolder;
    private TextView tvPaymentDetailTotal;
    private List<Invoice> mListInvoiceChoose = new ArrayList<Invoice>();
    private BigDecimal invoiceAmountNet = new BigDecimal(Constant.INT_0);

    public CustomDetailInforPaymentAdapter(Context context, List<Invoice> listInvoid, TextView tvPaymentDetailTotal) {
        this.listInvoid = listInvoid;
        this.context = context;
        this.tvPaymentDetailTotal = tvPaymentDetailTotal;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listInvoid.size();
    }

    @Override
    public Object getItem(int position) {
        return listInvoid.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class PaymentHolder {
        TextView tvPaymentDetailItemstt;
        TextView tvPaymentDetailItemFromDate;
        TextView tvPaymentDetailItemToDate;
        TextView tvPaymentDetailItemBill;
        TextView tvPaymentDetailItemDiscount;
        TextView tvPaymentDetailItemTotal;
        CheckBox cbPaymentDetailItem;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.list_item_detail_infor_payment, viewGroup, false);
            paymentHolder = new PaymentHolder();
            paymentHolder.tvPaymentDetailItemstt = (TextView) view.findViewById(R.id.tvPaymentDetailItemstt);
            paymentHolder.tvPaymentDetailItemFromDate = (TextView) view.findViewById(R.id.tvPaymentDetailItemFromDate);
            paymentHolder.tvPaymentDetailItemToDate = (TextView) view.findViewById(R.id.tvPaymentDetailItemToDate);
            paymentHolder.tvPaymentDetailItemBill = (TextView) view.findViewById(R.id.tvPaymentDetailItemBill);
            paymentHolder.tvPaymentDetailItemDiscount = (TextView) view.findViewById(R.id.tvPaymentDetailItemDiscount);
            paymentHolder.tvPaymentDetailItemTotal = (TextView) view.findViewById(R.id.tvPaymentDetailItemTotal);
            paymentHolder.cbPaymentDetailItem = (CheckBox) view.findViewById(R.id.cbPaymentDetailItem);
            view.setTag(paymentHolder);
        } else {
            paymentHolder = (PaymentHolder) view.getTag();
        }
        final Invoice invoice = (Invoice) getItem(position);
        paymentHolder.tvPaymentDetailItemstt.setText((position + 1) + Constant.EMPTY);
        paymentHolder.tvPaymentDetailItemFromDate.setText(StringUtil.formatDateDDMMYYFromLong(invoice.getDfromDate()));
        paymentHolder.tvPaymentDetailItemToDate.setText(StringUtil.formatDateDDMMYYFromLong(invoice.getDtoDate()));
        paymentHolder.tvPaymentDetailItemBill.setText(StringUtil.convertToCurrency(invoice.getBbiInvoiceAmount()));
        paymentHolder.tvPaymentDetailItemDiscount.setText(StringUtil.convertToCurrency(invoice.getBbiDiscountAmount()));
        paymentHolder.tvPaymentDetailItemTotal.setText(StringUtil.convertToCurrency(invoice.getBbiInvoiceAmountNet()));
        paymentHolder.cbPaymentDetailItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                BigDecimal tempAmount = invoice.getBbiInvoiceAmountNet();
                if (isChecked) {
                    listPositionChecked.add(position);
                    invoice.setCheckbox(true);
                    mListInvoiceChoose.add(invoice);
                    invoiceAmountNet = invoiceAmountNet.add(tempAmount);
                } else {
                    listPositionChecked.remove(position);
                    invoice.setCheckbox(false);
                    mListInvoiceChoose.remove(invoice);
                    invoiceAmountNet = invoiceAmountNet.subtract(tempAmount);
                }
                PaymentFragment.listPositionChecked = listPositionChecked;
                PaymentFragment.mListInvoiceChoose = mListInvoiceChoose;
                tvPaymentDetailTotal.setText(StringUtil.convertToCurrency(invoiceAmountNet));
            }
        });

        return view;
    }
}
