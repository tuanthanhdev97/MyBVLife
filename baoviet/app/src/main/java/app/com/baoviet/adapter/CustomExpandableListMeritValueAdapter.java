package app.com.baoviet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import app.com.baoviet.R;
import app.com.baoviet.constant.Constant;
import app.com.baoviet.entity.LoanDTO;
import app.com.baoviet.utility.StringUtil;

import java.util.List;

public class CustomExpandableListMeritValueAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<LoanDTO> mExpandableListPreminum;
    private LayoutInflater mLayoutInflater;

    public CustomExpandableListMeritValueAdapter(Context context, List<LoanDTO> mExpandableListPreminum) {
        this.mExpandableListPreminum = mExpandableListPreminum;
        this.mContext = context;
        this.mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        LoanDTO transPreminum = null;
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
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
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
        LoanDTO transPreminum = (LoanDTO) getGroup(listPosition);
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_merit_value_tab_group, null);
        }
        TextView tvContactTabMeritValueTypeDevice = (TextView) convertView.findViewById(R.id.tvContactTabMeritValueTypeDevice);
        TextView tvMeritValueTabHeaderDate = (TextView) convertView.findViewById(R.id.tvMeritValueTabHeaderDate);
        TextView tvMeritValueTabHeaderTotal = (TextView) convertView.findViewById(R.id.tvMeritValueTabHeaderTotal);
        String typeDevice = tvContactTabMeritValueTypeDevice.getText().toString();

        tvMeritValueTabHeaderDate.setText(StringUtil.formatDateDDMMYYFromLong(transPreminum.getElvDate()));
        tvMeritValueTabHeaderTotal.setText(StringUtil.convertToCurrency(transPreminum.getElvGrossAmount()));
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
