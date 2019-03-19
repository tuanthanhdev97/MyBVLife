package app.com.baoviet.utility;

import app.com.baoviet.R;
import app.com.baoviet.constant.Constant;

import android.app.Activity;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Color;
import android.graphics.drawable.LayerDrawable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

public class Validate {

    public static boolean validateMappingValue(Activity activity, EditText firstEdt, EditText secondEdt, TextView tvError, String typeValidate, String message) {
        String firstValue = firstEdt.getText().toString();
        String secondValue = secondEdt.getText().toString();
        LayerDrawable myGrad = (LayerDrawable) secondEdt.getBackground();
        int strokePixel = Convert.convertDpToPixel(activity, Constant.WIDTH_OF_STROKE);
        boolean isValidate = false;
        switch (typeValidate) {
            case Constant.VALIDATE_PATTERN_COMPARE_PASSWORD:
                if (!StringUtil.isNullOrEmpty(firstValue) && !StringUtil.isNullOrEmpty(secondValue)) {
                    if (firstValue.equals(secondValue)) {
                        tvError.setVisibility(View.VISIBLE);
                        tvError.setText(message);
                        isValidate = false;
                    } else {
                        tvError.setVisibility(View.GONE);
                        isValidate = true;
                    }
                }
                break;
            case Constant.VALIDATE_PATTERN_CONFIRM_NEW_PASSWORD:
                if (!StringUtil.isNullOrEmpty(firstValue) && !StringUtil.isNullOrEmpty(secondValue)) {
                    if (!firstValue.equals(secondValue)) {
                        tvError.setVisibility(View.VISIBLE);
                        tvError.setText(message);
                        isValidate = false;
                    } else {
                        tvError.setVisibility(View.GONE);
                        isValidate = true;
                    }
                }
                break;
        }
        return isValidate;

    }

    public static boolean validateForm(Activity activity, EditText edtInput, TextView tvError, String typeValidate, String error) {
        int strokePixel = Convert.convertDpToPixel(activity, Constant.WIDTH_OF_STROKE);
        boolean isValidate = false;
        LayerDrawable myGrad = (LayerDrawable) edtInput.getBackground();
        String valueEdittext = edtInput.getText().toString();
        switch (typeValidate) {
            case Constant.VALIDATE_PATTERN_NULL_OR_EMPTY:
                if (StringUtil.isNullOrEmpty(valueEdittext)) {
                    tvError.setVisibility(View.VISIBLE);
                    tvError.setText(error);
                    isValidate = false;
                } else {
                    tvError.setVisibility(View.GONE);
                    isValidate = true;
                }
                break;
            case Constant.VALIDATE_PATTERN_PHONE:
                if (!StringUtil.isNullOrEmpty(valueEdittext)) {
                    Pattern pattern = Pattern.compile(Constant.REGEX_PATTERN_MOBILE_PHONE);
                    isValidate = pattern.matcher(valueEdittext).matches();
                    if (!isValidate) {
                        tvError.setText(error);
                        tvError.setVisibility(View.VISIBLE);
                    } else {
                        tvError.setVisibility(View.GONE);
                    }
                }
                break;
            case Constant.VALIDATE_PATTERN_PASSWORD:
                if (!StringUtil.isNullOrEmpty(valueEdittext)) {
                    Pattern patternPassword = Pattern.compile(Constant.REGEX_PATTERN_PASSWORD);
                    isValidate = patternPassword.matcher(valueEdittext).matches();
                    if (!isValidate) {
                        tvError.setText(error);
                        tvError.setVisibility(View.VISIBLE);
                    } else {
                        tvError.setVisibility(View.GONE);
                    }
                }
                break;
            case Constant.VALIDATE_PATTERN_EMAIL:
                if (!StringUtil.isNullOrEmpty(valueEdittext)) {
                    Pattern pattern = Pattern.compile(Constant.REGEX_PATTERN_EMAIL);
                    isValidate = pattern.matcher(valueEdittext).matches();
                    if (!isValidate) {
                        tvError.setText(error);
                        tvError.setVisibility(View.VISIBLE);
                    } else {
                        tvError.setVisibility(View.GONE);
                    }
                }
                break;
            default:
                isValidate = false;
                break;
        }
        return isValidate;
    }
}
