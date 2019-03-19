package app.com.baoviet.utility;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.EditText;

import app.com.baoviet.constant.Constant;

public class DateTimeDialog {
    
    public static void getDate(Context context, final EditText edittext) {
        String saveDate = edittext.getText().toString();
        String[] arrDate = saveDate.split(Constant.SYMBOL_SLASH);
        int mDay = Constant.INT_0;
        int mMonth = Constant.INT_0;
        int mYear = Constant.INT_0;
        try {
            mDay = Integer.parseInt(arrDate[0]);
            mMonth = Integer.parseInt(arrDate[1]);
            mYear = Integer.parseInt(arrDate[2]);
        } catch (Exception e) {

        }
        final String[] valueDate = {Constant.EMPTY};

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        valueDate[0] = StringUtil.formatDateDDMMYYYY(dayOfMonth, monthOfYear + 1, year, Constant.DDMMYY);
                        edittext.setText(valueDate[0]);
                    }
                }
                , mYear, mMonth - 1, mDay);
        datePickerDialog.show();
    }
}
