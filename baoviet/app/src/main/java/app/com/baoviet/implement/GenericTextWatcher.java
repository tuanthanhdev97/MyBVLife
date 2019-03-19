package app.com.baoviet.implement;

import app.com.baoviet.R;
import app.com.baoviet.constant.Constant;
import app.com.baoviet.utility.Convert;
import app.com.baoviet.utility.StringUtil;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.LayerDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

public class GenericTextWatcher implements TextWatcher {
    private EditText edittext;
    private TextView text;
    private Activity activity;
    private String typeValidate;
    private String error;
    private Button button;
    private EditText firstEditext;
    private EditText secondEditext;

    public GenericTextWatcher(Activity activity, EditText edittext, TextView text, String typeValidate, String error) {
        this.edittext = edittext;
        this.text = text;
        this.activity = activity;
        this.error = error;
        this.typeValidate = typeValidate;
    }

    public GenericTextWatcher(Activity activity, EditText edittext, Button button, String typeValidate) {
        this.edittext = edittext;
        this.button = button;
        this.activity = activity;
        this.typeValidate = typeValidate;
    }

    public GenericTextWatcher(Activity activity, EditText firstEditext, EditText secondEditext, TextView text, String typeValidate, String error) {
        this.activity = activity;
        this.firstEditext = firstEditext;
        this.secondEditext = secondEditext;
        this.text = text;
        this.typeValidate = typeValidate;
        this.error = error;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String valueOfEdittext = Constant.EMPTY;
        String firstValue = Constant.EMPTY;
        String secondValue = Constant.EMPTY;
        LayerDrawable myGrad;
        int widthStroke = Convert.convertDpToPixel(activity, Constant.WIDTH_OF_STROKE);
        if (edittext != null) {
            valueOfEdittext = edittext.getText().toString();
        }

        if (firstEditext != null && secondEditext != null) {
            firstValue = firstEditext.getText().toString();
            secondValue = secondEditext.getText().toString();
        }
        switch (this.typeValidate) {
            case Constant.VALIDATE_PATTERN_NULL_OR_EMPTY:
                myGrad = (LayerDrawable) edittext.getBackground();
                if (StringUtil.isNullOrEmpty(valueOfEdittext)) {
                    text.setVisibility(View.VISIBLE);
                    text.setText(StringUtil.setTextValue(error));
                } else {
                    text.setVisibility(View.GONE);
                }
                break;
            case Constant.VALIDATE_PATTERN_PHONE:
                myGrad = (LayerDrawable) edittext.getBackground();
                if (!StringUtil.isNullOrEmpty(valueOfEdittext)) {
                    Pattern pattern = Pattern.compile(Constant.REGEX_PATTERN_MOBILE_PHONE);
                    boolean isMatch = pattern.matcher(valueOfEdittext).matches();
                    if (isMatch) {
                        text.setVisibility(View.GONE);
                    } else {
                        text.setText(StringUtil.setTextValue(error));
                        text.setVisibility(View.VISIBLE);
                    }
                }

                break;
            case Constant.VALIDATE_PATTERN_NULL_EMPTY_BTN:
                if (StringUtil.isNullOrEmpty(valueOfEdittext)) {
                    this.button.setEnabled(false);
                    this.button.setAlpha(0.5f);
                } else {
                    this.button.setEnabled(true);
                    this.button.setAlpha(1f);
                }
                break;
            case Constant.VALIDATE_PATTERN_COMPARE_PASSWORD:
                myGrad = (LayerDrawable) secondEditext.getBackground();
                if (!StringUtil.isNullOrEmpty(firstValue) && !StringUtil.isNullOrEmpty(secondValue)) {
                    if (firstValue.equals(secondValue)) {
                        text.setVisibility(View.VISIBLE);
                        text.setText(StringUtil.setTextValue(error));
                    } else {
                        text.setVisibility(View.GONE);
                    }
                }
                break;
            case Constant.VALIDATE_PATTERN_CONFIRM_NEW_PASSWORD:
                myGrad = (LayerDrawable) secondEditext.getBackground();
                if (!StringUtil.isNullOrEmpty(firstValue) && !StringUtil.isNullOrEmpty(secondValue)) {
                    if (!firstValue.equals(secondValue)) {
                        text.setVisibility(View.VISIBLE);
                        text.setText(StringUtil.setTextValue(error));
                    } else {
                        text.setVisibility(View.GONE);
                    }
                }
                break;
            case Constant.VALIDATE_PATTERN_EMAIL:
                if (!StringUtil.isNullOrEmpty(valueOfEdittext)) {
                    Pattern pattern = Pattern.compile(Constant.REGEX_PATTERN_EMAIL);
                    boolean isMatch = pattern.matcher(valueOfEdittext).matches();
                    if (isMatch) {
                        text.setVisibility(View.GONE);
                    } else {
                        text.setText(StringUtil.setTextValue(error));
                        text.setVisibility(View.VISIBLE);
                    }
                }
                break;
        }


    }

}
