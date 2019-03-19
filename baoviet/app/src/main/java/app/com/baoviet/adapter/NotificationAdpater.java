package app.com.baoviet.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import app.com.baoviet.R;
import app.com.baoviet.constant.Constant;
import app.com.baoviet.entity.NotificationDTO;
import app.com.baoviet.utility.StringUtil;

/**
 * Created by Administrator on 6/21/2018.
 */

public class NotificationAdpater extends ArrayAdapter<NotificationDTO> {
    private Context context;
    private int resource;
    private List<NotificationDTO> arrNotificationDTO;

    public NotificationAdpater(Context context, int resource, List<NotificationDTO> arrNotificationDTO) {
        super(context, resource, arrNotificationDTO);
        this.context = context;
        this.resource = resource;
        this.arrNotificationDTO = arrNotificationDTO;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_notification_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvTimeNotification = (TextView) convertView.findViewById(R.id.tvNotificationTime);
            viewHolder.tvContentNotification = (TextView) convertView.findViewById(R.id.tvNotificationContent);
            viewHolder.tvTitleNotification = (TextView) convertView.findViewById(R.id.tvNotificationTitle);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        NotificationDTO notificationDTO = arrNotificationDTO.get(position);
        if (Constant.NOTIFY_TYPE_LINK.equals(notificationDTO.getNotifyType())) {
            String contentNof = notificationDTO.getNotifyTypeDescription();
            SpannableString contentSpannable = new SpannableString(contentNof);
            contentSpannable.setSpan(new UnderlineSpan(), 0, contentNof.length(), 0);
            contentSpannable.setSpan(new StyleSpan(Typeface.ITALIC), 0, StringUtil.setTextValue(contentNof).length(), 0);
            viewHolder.tvContentNotification.setTextColor(context.getResources().getColor(R.color.blue));
            viewHolder.tvContentNotification.setText(contentSpannable);
        } else {
            viewHolder.tvContentNotification.setTextColor(context.getResources().getColor(R.color.color_hint));
            viewHolder.tvContentNotification.setText(StringUtil.setTextValue(notificationDTO.getNotifyContent()));

        }
        viewHolder.tvTitleNotification.setText(StringUtil.setTextValue(notificationDTO.getNotifyTitle()));
        viewHolder.tvTimeNotification.setText(StringUtil.formatDateDDMMYYFromLong(notificationDTO.getUpdatedDate()));
        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    public class ViewHolder {
        TextView tvTimeNotification;
        TextView tvContentNotification;
        TextView tvTitleNotification;
    }
}
