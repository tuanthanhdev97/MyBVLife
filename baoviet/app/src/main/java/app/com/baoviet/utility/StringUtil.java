package app.com.baoviet.utility;


import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import app.com.baoviet.constant.Constant;

public class StringUtil {
    public static boolean isNullOrEmpty(String value) {
        if (value == null || value.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public static Calendar convertDateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static Date convertStringDateToCalendar(String value, String formatDate) {
        Date date = null;
        Calendar cal = Calendar.getInstance();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
            cal.setTime(sdf.parse(value));
            date = cal.getTime();
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());

        }
        return date;
    }

    public static String getSubstractOneYear(String fromDate) {
        String date = Constant.EMPTY;
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat(Constant.DDMMYY);
            cal.setTime(sdf.parse(fromDate));
            // to get previous year add -1
            cal.add(Calendar.YEAR, Constant.INT_NEGATIVE_1);
            Date lastYear = cal.getTime();
            date = formatDateDDMMYYFromDate(lastYear, Constant.DDMMYY);
            return date;
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            return date;
        }
    }

    public static String convertYYYYMMDDToDDMMYY(String fromDate) {
        String dateResult = Constant.EMPTY;
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat(Constant.YYYYMMDD);
            cal.setTime(sdf.parse(fromDate));

            Date date = cal.getTime();
            dateResult = formatDateDDMMYYFromDate(date, Constant.DDMMYY);
            return dateResult;
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            return dateResult;
        }
    }

    public static Date getSubstractOneYearFromDate(Date fromDate) {
        Date lastYear = null;
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(fromDate);
            // to get previous year add -1
            cal.add(Calendar.YEAR, Constant.INT_NEGATIVE_1);
            lastYear = cal.getTime();

        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());

        }
        return lastYear;
    }

    public static String formatDateDDMMYYFromDate(Date date, String formatDate) {
        if (date == null) {
            return Constant.EMPTY;
        } else {
            Calendar calendar = convertDateToCalendar(date);
            int dayofmonth = calendar.get(Calendar.DATE);
            int month = calendar.get(Calendar.MONTH) + Constant.INT_1;
            int year = calendar.get(Calendar.YEAR);
            int hour = calendar.get(Calendar.HOUR);
            return formatDateDDMMYYYY(dayofmonth, month, year, formatDate);
        }

    }

    public static String formatDateDDMMYYFromLong(Long longdate) {
        if (longdate == null) {
            return Constant.EMPTY;
        } else {
            Date date = new Date(longdate);
            Calendar calendar = convertDateToCalendar(date);
            int dayofmonth = calendar.get(Calendar.DATE);
            int month = calendar.get(Calendar.MONTH) + Constant.INT_1;
            int year = calendar.get(Calendar.YEAR);
            int hour = calendar.get(Calendar.HOUR);
            return formatDateDDMMYYYY(dayofmonth, month, year, Constant.DDMMYY);
        }

    }

    public static String formatDateDDMMYYYYHHmmsssFromDate(Date date) {
        if (date == null) {
            return Constant.EMPTY;
        } else {
            Calendar calendar = convertDateToCalendar(date);
            int dayofmonth = calendar.get(Calendar.DATE);
            int month = calendar.get(Calendar.MONTH) + Constant.INT_1;
            int year = calendar.get(Calendar.YEAR);
            int hour = calendar.get(Calendar.HOUR);
            int minute = calendar.get(Calendar.MINUTE);
            int second = calendar.get(Calendar.SECOND);
            int minisecond = calendar.get(Calendar.MILLISECOND);
            return formatDateDDMMYYYYHHmmsss(dayofmonth, month, year, hour, minute, second, minisecond, Constant.DDMMYYYYTHHmmssSSSZ);
        }

    }

    public static String formatDateDDMMYYYYHHmmsssFromCalendar(Calendar calendar) {
        if (calendar == null) {
            return Constant.EMPTY;
        } else {
            int dayofmonth = calendar.get(Calendar.DATE);
            int month = calendar.get(Calendar.MONTH) + Constant.INT_1;
            int year = calendar.get(Calendar.YEAR);
            int hour = calendar.get(Calendar.HOUR);
            int minute = calendar.get(Calendar.MINUTE);
            int second = calendar.get(Calendar.SECOND);
            int minisecond = calendar.get(Calendar.MILLISECOND);
            return formatDateDDMMYYYYHHmmsss(dayofmonth, month, year, hour, minute, second, minisecond, Constant.DDMMYYYYTHHmmssSSSZ);
        }

    }

    //add 0 to datetime if between 0 to 9
    public static String formatDateTime(int value) {
        String resultValue = Constant.EMPTY;
        if (value >= 0 && value < 10) {
            resultValue = Constant.STR_INT_0 + value;
        } else {
            resultValue = Constant.EMPTY + value;
        }
        return resultValue;
    }

    public static String formatMiniSecond(int value) {
        String resultValue = Constant.EMPTY;
        if (value >= 0 && value < 10) {
            resultValue = Constant.STR_INT_0 + Constant.STR_INT_0 + value;
        } else if (value > 10 && value < 100) {
            resultValue = Constant.STR_INT_0 + value;
        } else if (value > 100) {
            resultValue = Constant.EMPTY + value;
        }
        return resultValue;
    }

    public static String formatDateDDMMYYYYHHmmsss(int date, int month, int year, int hour, int minute, int second, int minisecond, String format) {
        String mDate = Constant.EMPTY;
        String mMonth = Constant.EMPTY;
        String mYear = String.valueOf(year);
        String mHour = Constant.EMPTY;
        String mMinute = Constant.EMPTY;
        String mSecond = Constant.EMPTY;
        String mMiniSecond = Constant.EMPTY;
        String valueReturn = Constant.EMPTY;
        //date
        mDate = formatDateTime(date);
        mMonth = formatDateTime(month);
        mHour = formatDateTime(hour);
        mMinute = formatDateTime(minute);
        mSecond = formatDateTime(second);
        mMiniSecond = formatMiniSecond(minisecond);

        switch (format) {
            case Constant.DDMMYYYYTHHmmssSSSZ:
                valueReturn = mYear + Constant.SYMBOL_HYPHEN + mMonth + Constant.SYMBOL_HYPHEN + mDate +
                        "T" + mHour + Constant.SYMBOL_COLON + mMinute + Constant.SYMBOL_COLON + mSecond +
                        Constant.SYMBOL_DOT + mMiniSecond + "Z";
                break;
            default:
                break;
        }
        return valueReturn;
    }

    public static String formatDateDDMMYYYY(int date, int month, int year, String format) {
        String mDate = Constant.EMPTY;
        String mMonth = Constant.EMPTY;
        String mYear = String.valueOf(year);
        String valueReturn = Constant.EMPTY;
        mDate = formatDateTime(date);
        mMonth = formatDateTime(month);
        switch (format) {
            case Constant.DDMMYY:
                valueReturn = mDate + Constant.SYMBOL_SLASH + mMonth + Constant.SYMBOL_SLASH + mYear;
                break;
            case Constant.YYYYMMDD:
                valueReturn = mYear + Constant.SYMBOL_HYPHEN + mMonth + Constant.SYMBOL_HYPHEN + mDate;
                break;
            default:
                break;
        }
        return valueReturn;
    }

    public static String roundRatio(BigDecimal number, int index) {
        if (number == null) {
            return Constant.EMPTY;
        }
        double value = Math.pow(Constant.INT_10, index);
        double result = Math.round(number.doubleValue() * value) / value;
        return String.valueOf(result);
    }

    public static String convertToCurrency(BigDecimal input) {
        if (input == null) {
            return Constant.EMPTY;
        }
        double currency = input.doubleValue();
        if (currency == 0.0) {
            return "0";
        }
        if (currency < Constant.INT_1000) {
            return String.valueOf(currency);
        } else {
            NumberFormat formatter = new DecimalFormat("###,###");
            String resp = formatter.format(currency);
            return resp;
        }
    }

    public static String convertToCurrency(String input) {
        if (input == null) {
            return Constant.EMPTY;
        }
        double currency = Double.parseDouble(input);
        if (currency < Constant.INT_1000) {
            return String.valueOf(currency);
        } else {
            NumberFormat formatter = new DecimalFormat("###,###");
            String resp = formatter.format(currency);
            return resp;
        }
    }

    public static String getUserTypeFromCode(String value) {
        String resultValue = Constant.EMPTY;
        switch (value) {
            case Constant.USER_TYPE_CODE_COMP:
                resultValue = Constant.USER_TYPE_CODE_VALUE_COMP;
                break;
            case Constant.USER_TYPE_CODE_STAFF:
                resultValue = Constant.USER_TYPE_CODE_VALUE_STAFF;
                break;
            case Constant.USER_TYPE_CODE_PERS:
                resultValue = Constant.USER_TYPE_CODE_VALUE_PERS;
                break;
            default:
                resultValue = Constant.EMPTY;
                break;
        }
        return resultValue;
    }

    // get client identity doc type
    public static String getClientIdentityDoctypeFromCode(String value) {
        String returnValue = Constant.EMPTY;
        switch (value) {
            case Constant.CLIENT_DOC_TYPE_VNID:
                returnValue = Constant.CLIENT_DOC_TYPE_VALUE_VNID;
                break;
            case Constant.CLIENT_DOC_TYPE_GTTT:
                returnValue = Constant.CLIENT_DOC_TYPE_VALUE_GTTT;
                break;
            case Constant.CLIENT_DOC_TYPE_GPKD:
                returnValue = Constant.CLIENT_DOC_TYPE_VALUE_GPKD;
                break;
            case Constant.CLIENT_DOC_TYPE_PSPT:
                returnValue = Constant.CLIENT_DOC_TYPE_VALUE_PSPT;
                break;
            case Constant.CLIENT_DOC_TYPE_BCER:
                returnValue = Constant.CLIENT_DOC_TYPE_VALUE_BCER;
                break;
            case Constant.CLIENT_DOC_TYPE_THCC:
                returnValue = Constant.CLIENT_DOC_TYPE_VALUE_THCC;
                break;
            case Constant.CLIENT_DOC_TYPE_KHAC:
                returnValue = Constant.CLIENT_DOC_TYPE_VALUE_KHAC;
                break;
            default:
                returnValue = Constant.EMPTY;
                break;
        }
        return returnValue;
    }

    public static String getReltCode(String value) {
        String returnValue = Constant.EMPTY;
        switch (value) {
            case Constant.RELT_CODE_OWNR:
                returnValue = Constant.RELT_CODE_OWNR_VALUE;
                break;
            case Constant.RELT_CODE_SPON:
                returnValue = Constant.RELT_CODE_SPON_VALUE;
                break;
            case Constant.RELT_CODE_LIFE:
                returnValue = Constant.RELT_CODE_LIFE_VALUE;
                break;
            case Constant.RELT_CODE_MEMB:
                returnValue = Constant.RELT_CODE_MEMB_VALUE;
                break;
            case Constant.RELT_CODE_BENE:
                returnValue = Constant.RELT_CODE_BENE_VALUE;
                break;
            default:
                returnValue = Constant.EMPTY;
        }
        return returnValue;
    }

    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String setTextValue(Object obj) {
        String result = Constant.EMPTY;
        if (obj == null) {
            return result;
        }
        return obj.toString();
    }

    public static String getNameFilePDF(String path) {
        String result = Constant.EMPTY;
        String[] array = path.split(Constant.SYMBOL_SLASH);
        if (Constant.INT_0 < array.length) {
            result = array[(array.length - 1)];
        }
        return result;
    }

    public static SpannableString makeLinkSpan(CharSequence text, View.OnClickListener listener) {
        SpannableString link = new SpannableString(text);
        link.setSpan(new ClickableString(listener), 0, text.length(),
                SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        return link;
    }

    public static void makeLinksFocusable(TextView tv) {
        MovementMethod m = tv.getMovementMethod();
        if ((m == null) || !(m instanceof LinkMovementMethod)) {
            if (tv.getLinksClickable()) {
                tv.setMovementMethod(LinkMovementMethod.getInstance());
            }
        }
    }

    public static class ClickableString extends ClickableSpan {
        private View.OnClickListener mListener;

        public ClickableString(View.OnClickListener listener) {
            mListener = listener;
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(v);
        }
    }

}
