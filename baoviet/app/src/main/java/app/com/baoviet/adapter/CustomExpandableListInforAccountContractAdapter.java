package app.com.baoviet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

import app.com.baoviet.R;
import app.com.baoviet.constant.Constant;
import app.com.baoviet.entity.UsersAcctRltnshpDTO;
import app.com.baoviet.utility.StringUtil;

public class CustomExpandableListInforAccountContractAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<UsersAcctRltnshpDTO> mExpandableListPreminum;
    private LayoutInflater mLayoutInflater;

    public CustomExpandableListInforAccountContractAdapter(Context context, List<UsersAcctRltnshpDTO> mExpandableListPreminum) {
        this.mExpandableListPreminum = mExpandableListPreminum;
        this.mContext = context;
        this.mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        UsersAcctRltnshpDTO transPreminum = null;
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
        UsersAcctRltnshpDTO transPreminum = (UsersAcctRltnshpDTO) getGroup(listPosition);
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_infor_account_contract, null);
        }
        TextView tvInforAccountContract = (TextView) convertView.findViewById(R.id.tvInforAccountContract);
        TextView tvInforAccountRole = (TextView) convertView.findViewById(R.id.tvInforAccountRole);
        TextView tvInforAccountTypeDevice = (TextView) convertView.findViewById(R.id.tvInforAccountTypeDevice);
        String typeDevice = tvInforAccountTypeDevice.getText().toString();

        tvInforAccountContract.setText(StringUtil.setTextValue(transPreminum.getAccountNumber()));
        tvInforAccountRole.setText(StringUtil.setTextValue(StringUtil.getReltCode(transPreminum.getReltCode())));
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
