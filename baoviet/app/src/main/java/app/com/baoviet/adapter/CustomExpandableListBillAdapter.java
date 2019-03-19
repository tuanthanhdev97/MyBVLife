package app.com.baoviet.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import app.com.baoviet.ContractActivity;
import app.com.baoviet.R;
import app.com.baoviet.ViewPDFActivity;
import app.com.baoviet.constant.Constant;
import app.com.baoviet.constant.Keys;
import app.com.baoviet.entity.BillDTO;
import app.com.baoviet.utility.GrantPermission;
import app.com.baoviet.utility.StringUtil;

public class CustomExpandableListBillAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<BillDTO> mExpandableListPreminum;
    private LayoutInflater mLayoutInflater;

    public CustomExpandableListBillAdapter(Context context, List<BillDTO> mExpandableListPreminum) {
        this.mExpandableListPreminum = mExpandableListPreminum;
        this.mContext = context;
        this.mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        BillDTO transPreminum = null;
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
        final BillDTO expandedListText = (BillDTO) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_bill_tab_item, null);
        }
        TextView tvContractTabBillBuyer = (TextView) convertView.findViewById(R.id.tvContractTabBillBuyer);
        TextView tvContractTabBillTaxCode = (TextView) convertView.findViewById(R.id.tvContractTabBillTaxCode);
        TextView tvContractTabBillAddress = (TextView) convertView.findViewById(R.id.tvContractTabBillAddress);
        TextView tvContractTabBillContractNumber = (TextView) convertView.findViewById(R.id.tvContractTabBillContractNumber);
        TextView tvContractTabBillCouselors = (TextView) convertView.findViewById(R.id.tvContractTabBillCouselors);
        TextView tvContractTabBillFromDate = (TextView) convertView.findViewById(R.id.tvContractTabBillFromDate);
        TextView tvContractTabBillToDate = (TextView) convertView.findViewById(R.id.tvContractTabBillToDate);
        TextView tvContactTabBillItemTypeDevice = (TextView) convertView.findViewById(R.id.tvContactTabBillItemTypeDevice);

        String typeDevice = tvContactTabBillItemTypeDevice.getText().toString();
        tvContractTabBillBuyer.setText(StringUtil.setTextValue(expandedListText.getBillPolicyholder()));
        tvContractTabBillTaxCode.setText(StringUtil.setTextValue(expandedListText.getBillTax()));
        tvContractTabBillAddress.setText(StringUtil.setTextValue(expandedListText.getBillAddress()));
        tvContractTabBillContractNumber.setText(StringUtil.setTextValue(expandedListText.getBillAccountNumber()));
        tvContractTabBillFromDate.setText(StringUtil.formatDateDDMMYYFromDate(expandedListText.getBillFromChargePeriod(), Constant.DDMMYY));
        tvContractTabBillToDate.setText(StringUtil.formatDateDDMMYYFromDate(expandedListText.getBillToChargePeriod(), Constant.DDMMYY));
        tvContractTabBillCouselors.setText(StringUtil.setTextValue(expandedListText.getBillOutletName()));

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
        final BillDTO transPreminum = (BillDTO) getGroup(listPosition);
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_bill_tab_group, null);
        }
        TextView tvBillTabHeaderNumber = (TextView) convertView.findViewById(R.id.tvBillTabHeaderNumber);
        TextView tvBillTabHeaderEffectiveDate = (TextView) convertView.findViewById(R.id.tvBillTabHeaderEffectiveDate);
        TextView tvBillTabHeaderAmountMoney = (TextView) convertView.findViewById(R.id.tvBillTabHeaderAmountMoney);
        TextView tvContactTabBillTypeDevice = (TextView) convertView.findViewById(R.id.tvContactTabBillTypeDevice);
        LinearLayout lnBillTabHeaderShow = (LinearLayout) convertView.findViewById(R.id.lnBillTabHeaderShow);
        LinearLayout lnBillTabHeaderDownload = (LinearLayout) convertView.findViewById(R.id.lnBillTabHeaderDownload);
        LinearLayout lnBillTabHeaderDownloadXML = (LinearLayout) convertView.findViewById(R.id.lnBillTabHeaderDownloadXML);
        ImageView imgArrowBill = (ImageView) convertView.findViewById(R.id.imgArrowBill);
        String typeDevice = tvContactTabBillTypeDevice.getText().toString();

        lnBillTabHeaderShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ViewPDFActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Keys.KEY_TRANSFER, Constant.TRANSFER_TO_VIEW_PDF);
                bundle.putSerializable(Keys.KEY_BILL_DTO, transPreminum);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

//        lnBillTabHeaderDownload.setVisibility(View.GONE);
        lnBillTabHeaderDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GrantPermission.hasPermissionWriteExternalStorage(mContext)) {
                    ((ContractActivity) mContext).setDataToDownloadPDF(null, transPreminum, Constant.PDF_TYPE_DOWNLOAD);
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
                    ((Activity) mContext).startActivityForResult(intent, Constant.REQUEST_CODE_CHOOSE_FOLDER);
                }
            }
        });
        lnBillTabHeaderDownloadXML.setVisibility(View.GONE);
//        lnBillTabHeaderDownloadXML.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (GrantPermission.hasPermissionWriteExternalStorage(mContext)) {
//                    ((ContractActivity) mContext).setDataToDownloadPDF(null, transPreminum, Constant.XML_TYPE_DOWNLOAD);
//                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
//                    ((Activity) mContext).startActivityForResult(intent, Constant.REQUEST_CODE_CHOOSE_FOLDER);
//                }
//            }
//        });

        tvBillTabHeaderNumber.setText(StringUtil.setTextValue(transPreminum.getBillBillNumber()));
        tvBillTabHeaderEffectiveDate.setText(StringUtil.setTextValue(StringUtil.formatDateDDMMYYFromLong(transPreminum.getBillCreatedDate())));
        tvBillTabHeaderAmountMoney.setText(StringUtil.convertToCurrency(transPreminum.getBillInvoiceAmount()));

        int countChild = getChildrenCount(listPosition);
        if (Constant.INT_0 < countChild) {
            imgArrowBill.setVisibility(View.VISIBLE);
            if (isExpanded) {
                imgArrowBill.setImageResource(R.drawable.ic_arrow_up);
            } else {
                imgArrowBill.setImageResource(R.drawable.ic_arrow_down);
            }
        } else {
            imgArrowBill.setVisibility(View.GONE);
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
