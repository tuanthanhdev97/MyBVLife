package app.com.baoviet.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;

import app.com.baoviet.R;
import app.com.baoviet.entity.BenifitTypeDTO;
import app.com.baoviet.utility.StringUtil;

/**
 * Created by Administrator on 6/21/2018.
 */

public class GeneralInforBenefitAdapter extends ArrayAdapter<BenifitTypeDTO> {
    private Context context;
    private List<BenifitTypeDTO> listBenefitDTO;

    public GeneralInforBenefitAdapter(Context context, int resource, List<BenifitTypeDTO> listBenefitDTO) {
        super(context, resource, listBenefitDTO);
        this.context = context;
        this.listBenefitDTO = listBenefitDTO;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        final View viewEdittext;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_generalinfor_benefit, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvGeneralInforBenefitTitle = (TextView) convertView.findViewById(R.id.tvGeneralInforBenefitTitle);
            viewHolder.edtGeneralInforBenefitContent = (EditText) convertView.findViewById(R.id.edtGeneralInforBenefitContent);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewEdittext = convertView;
        viewHolder.edtGeneralInforBenefitContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (view.getId()) {
                    case R.id.edtGeneralInforBenefitContent:
                        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View popupView = layoutInflater.inflate(R.layout.popup_text, null);
                        final PopupWindow popupWindow = new PopupWindow(popupView, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            TextView tvContentPopup = (TextView) popupView.findViewById(R.id.tvContentPopup);

                            tvContentPopup.setText(viewHolder.edtGeneralInforBenefitContent.getText().toString());
                            popupWindow.showAsDropDown(view, 0, 0, Gravity.BOTTOM);
                            popupWindow.setOutsideTouchable(true);
                            popupWindow.setFocusable(true);
                            popupWindow.update();
                        }
                        break;
                }
                return false;
            }
        });
        BenifitTypeDTO BenifitTypeDTO = listBenefitDTO.get(position);
        viewHolder.tvGeneralInforBenefitTitle.setText(StringUtil.setTextValue(BenifitTypeDTO.getBenefitCategoryDescription()));
        viewHolder.edtGeneralInforBenefitContent.setText(StringUtil.setTextValue(BenifitTypeDTO.getBenefitComments()));
        return convertView;
    }

    public class ViewHolder {
        TextView tvGeneralInforBenefitTitle;
        EditText edtGeneralInforBenefitContent;
    }
}
