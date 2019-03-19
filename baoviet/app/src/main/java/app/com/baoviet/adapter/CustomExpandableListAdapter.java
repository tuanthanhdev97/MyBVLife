package app.com.baoviet.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import app.com.baoviet.R;
import app.com.baoviet.constant.Constant;
import app.com.baoviet.entity.MenuNavigation;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<MenuNavigation> listMenuNavigation;

    public CustomExpandableListAdapter(Context context, List<MenuNavigation> listMenuNavigation) {
        this.mContext = context;
        this.listMenuNavigation = listMenuNavigation;
        this.mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        String titleParent = Constant.EMPTY;
        if (checkListNullOrEmpty(listMenuNavigation)) {
            MenuNavigation menuParent = listMenuNavigation.get(listPosition);
            if (checkListNullOrEmpty(menuParent.getListMenuChild())) {
                return menuParent.getListMenuChild().get(expandedListPosition);
            }
        }
        return null;
    }

    private boolean checkListNullOrEmpty(List<MenuNavigation> listMenuNavigation) {
        if (listMenuNavigation == null) {
            return false;
        } else {
            if (Constant.INT_0 == listMenuNavigation.size()) {
                return false;
            } else {
                return true;
            }
        }
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolderGroup viewHolderGroup;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_nav_item, parent, false);
            viewHolderGroup = new ViewHolderGroup();
            viewHolderGroup.imgViewHolder = (ImageView) convertView.findViewById(R.id.imgNavViewChild);
            viewHolderGroup.tvViewHolder = (TextView) convertView
                    .findViewById(R.id.expandedListItem);
            convertView.setTag(viewHolderGroup);
        } else {
            viewHolderGroup = (ViewHolderGroup) convertView.getTag();
        }
        MenuNavigation menuChild = (MenuNavigation) getChild(listPosition, expandedListPosition);
        String expandedListText = menuChild.getTitle();

        viewHolderGroup.tvViewHolder.setText(expandedListText);
        viewHolderGroup.imgViewHolder.setImageResource(menuChild.getImageLogo());
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        if (checkListNullOrEmpty(listMenuNavigation)) {
            MenuNavigation menuParent = listMenuNavigation.get(listPosition);
            if (checkListNullOrEmpty(menuParent.getListMenuChild())) {
                return menuParent.getListMenuChild().size();
            }
        }
        return Constant.INT_0;
    }

    @Override
    public Object getGroup(int listPosition) {
        return listMenuNavigation.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        if (checkListNullOrEmpty(listMenuNavigation)) {
            return listMenuNavigation.size();
        }
        return Constant.INT_0;
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        ViewHolderGroup viewHolderGroup;
        MenuNavigation menuParent = (MenuNavigation) getGroup(listPosition);
        String listTitle = menuParent.getTitle();
        List<MenuNavigation> menuChild = menuParent.getListMenuChild();
        int tabIconColor;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_nav_group, parent, false);
            viewHolderGroup = new ViewHolderGroup();
            viewHolderGroup.rlViewHolder = (RelativeLayout) convertView.findViewById(R.id.lnNavMenuGroup);
            viewHolderGroup.imgViewHolder = (ImageView) convertView.findViewById(R.id.imgNavViewParent);
            viewHolderGroup.tvViewHolder = (TextView) convertView
                    .findViewById(R.id.listTitle);
            convertView.setTag(viewHolderGroup);
        } else {
            viewHolderGroup = (ViewHolderGroup) convertView.getTag();
        }
        if (checkListNullOrEmpty(menuChild)) {
            if (isExpanded) {
                viewHolderGroup.rlViewHolder.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.gradient_menu));
                viewHolderGroup.tvViewHolder.setTextColor(mContext.getResources().getColor(R.color.white));
                tabIconColor = ContextCompat.getColor(mContext, R.color.white);
            } else {
                viewHolderGroup.rlViewHolder.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
                viewHolderGroup.tvViewHolder.setTextColor(mContext.getResources().getColor(R.color.black));
                tabIconColor = ContextCompat.getColor(mContext, R.color.blue);
            }
        } else {
            viewHolderGroup.rlViewHolder.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
            viewHolderGroup.tvViewHolder.setTextColor(mContext.getResources().getColor(R.color.black));
            tabIconColor = ContextCompat.getColor(mContext, R.color.blue);
        }

        viewHolderGroup.imgViewHolder.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
        viewHolderGroup.tvViewHolder.setText(listTitle);
        viewHolderGroup.imgViewHolder.setImageResource(menuParent.getImageLogo());
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

    private class ViewHolderGroup {
        RelativeLayout rlViewHolder;
        ImageView imgViewHolder;
        TextView tvViewHolder;
    }
}
