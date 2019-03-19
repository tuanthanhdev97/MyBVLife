package app.com.baoviet.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import app.com.baoviet.R;
import app.com.baoviet.constant.Constant;
import app.com.baoviet.constant.Keys;
import app.com.baoviet.datasource.DataSourceConnection;
import app.com.baoviet.entity.ClientRegisterDTO;
import app.com.baoviet.entity.ClientRegisterNewDTO;
import app.com.baoviet.entity.ClientRegisterResponseDTO;
import app.com.baoviet.entity.ResponseStream;
import app.com.baoviet.implement.GenericTextWatcher;
import app.com.baoviet.interfaceclass.AsyncResponse;
import app.com.baoviet.interfaceclass.DialogEventListener;
import app.com.baoviet.utility.AlertDialogCustom;
import app.com.baoviet.utility.StringUtil;
import app.com.baoviet.utility.Validate;

public class RegisterFragment extends Fragment implements AsyncResponse, View.OnClickListener, DialogEventListener {
    private EditText edtRegisterBuyer;
    private EditText edtRegisterIdentifyNumber;
    private TextView tvNote;
    private TextView tvRegisterBuyerError;
    private TextView tvRegisterIdentifyNumberError;
    private Button btnSendInfor;

    private ClientRegisterNewDTO clientRegisterNewDTO;

    AlertDialogCustom alert;
    String message = Constant.EMPTY;
    String typeDialog = Constant.EMPTY;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        // initial view
        initInterface(view);
        // event onchange text in edittext
        onChangeValueEditText();
        // set text html
        setTextNote();
        // data for spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_layout, Constant.VALUE_RELT_CODE);
        adapter.setDropDownViewResource(R.layout.spinner_layout_popup);
        // event click button send infor

        return view;
    }

    public void initInterface(View view) {
        alert = new AlertDialogCustom(getActivity());
        alert.dialogEventListener = this;
        tvNote = (TextView) view.findViewById(R.id.tvNote);
        edtRegisterBuyer = (EditText) view.findViewById(R.id.edtRegisterBuyer);
        edtRegisterIdentifyNumber = (EditText) view.findViewById(R.id.edtRegisterIdentifyNumber);
        tvRegisterBuyerError = (TextView) view.findViewById(R.id.tvRegisterBuyerError);
        tvRegisterIdentifyNumberError = (TextView) view.findViewById(R.id.tvRegisterIdentifyNumberError);
        btnSendInfor = (Button) view.findViewById(R.id.btnSendInfor);
        btnSendInfor.setOnClickListener(this);
    }

    public void setTextNote() {
        SpannableString link = StringUtil.makeLinkSpan(" tại đây ", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.CHAT_LINK));
                startActivity(browserIntent);
            }
        });

        // Set the TextView's text
        tvNote.setText(Html.fromHtml(getString(R.string.txt_register_note)));

        // Append the link we created above using a function defined below.
        tvNote.append(link);

        // Append a period (this will not be a link).
        tvNote.append(Html.fromHtml(getString(R.string.txt_register_note2)));

        // This line makes the link clickable!
        StringUtil.makeLinksFocusable(tvNote);
    }

//    private SpannableString makeLinkSpan(CharSequence text, View.OnClickListener listener) {
//        SpannableString link = new SpannableString(text);
//        link.setSpan(new ClickableString(listener), 0, text.length(),
//                SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
//        return link;
//    }
//
//    private void makeLinksFocusable(TextView tv) {
//        MovementMethod m = tv.getMovementMethod();
//        if ((m == null) || !(m instanceof LinkMovementMethod)) {
//            if (tv.getLinksClickable()) {
//                tv.setMovementMethod(LinkMovementMethod.getInstance());
//            }
//        }
//    }
//
//    private static class ClickableString extends ClickableSpan {
//        private View.OnClickListener mListener;
//
//        public ClickableString(View.OnClickListener listener) {
//            mListener = listener;
//        }
//
//        @Override
//        public void onClick(View v) {
//            mListener.onClick(v);
//        }
//    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnSendInfor:
                // Buyer
                boolean isEmptyRegisterBuyer = Validate.validateForm(getActivity(), edtRegisterBuyer, tvRegisterBuyerError, Constant.VALIDATE_PATTERN_NULL_OR_EMPTY, getResources().getString(R.string.txt_register_error_buyer));
                // Identify Number
                boolean isEmptyIdentify = Validate.validateForm(getActivity(), edtRegisterIdentifyNumber, tvRegisterIdentifyNumberError, Constant.VALIDATE_PATTERN_NULL_OR_EMPTY, getResources().getString(R.string.txt_register_error_identify_code));

                if (isEmptyRegisterBuyer && isEmptyIdentify) {
                    try {
                        ClientRegisterDTO account = new ClientRegisterDTO();
                        account.setClientIdNumber(edtRegisterIdentifyNumber.getText().toString());
                        account.setClientName(edtRegisterBuyer.getText().toString());
                        Gson gson = new GsonBuilder().create();
                        String inputParams = gson.toJson(account);
                        DataSourceConnection connection = new DataSourceConnection(getActivity(), Constant.URL_VALIDATE_REGISTER_USER, inputParams, Constant.METHOD_POST, true, true
                        );
                        connection.delegate = this;
                        connection.execute();

                    } catch (Exception e) {
                        Log.e("ERROR", e.getMessage());
                        message = getResources().getString(R.string.message_error_system);
                        typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
                        alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                    }
                }
                break;

            default:
                break;
        }

    }

    // validate Empty value in edittext
    public void onChangeValueEditText() {
        edtRegisterBuyer
                .addTextChangedListener(new GenericTextWatcher(getActivity(), edtRegisterBuyer, tvRegisterBuyerError, Constant.VALIDATE_PATTERN_NULL_OR_EMPTY, getResources().getString(R.string.txt_register_error_buyer)));
        edtRegisterIdentifyNumber.addTextChangedListener(
                new GenericTextWatcher(getActivity(), edtRegisterIdentifyNumber, tvRegisterIdentifyNumberError, Constant.VALIDATE_PATTERN_NULL_OR_EMPTY, getResources().getString(R.string.txt_register_error_identify_code)));
    }

    @Override
    public void processFinish(String result, String urlApi) {
        try {
            if (!StringUtil.isNullOrEmpty(result)) {
                if (Constant.ERROR_SERVER.equals(result)) {
                    message = getResources().getString(R.string.message_error_server);
                    typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
                    alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                } else {
                    JSONObject jsonObject = new JSONObject(result);
                    int responseStatus = jsonObject.getInt(Keys.KEY_RESPONSE_STATUS);
                    String responseMessage = jsonObject.getString(Keys.KEY_RESPONSE_MESSAGE);
                    if (Constant.RESPONSE_CODE_200 == responseStatus) {
                        if (Constant.URL_VALIDATE_REGISTER_USER.equals(urlApi)) {
                            JSONObject objResult;
                            Gson gson;
                            ClientRegisterResponseDTO userResult;
                            objResult = jsonObject.getJSONObject(Keys.KEY_OBJECT);
                            if (objResult == null) {
                                message = getResources().getString(R.string.message_error_null_data);
                                typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
                                alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                            } else {
                                gson = new GsonBuilder().create();
                                userResult = gson.fromJson(objResult.toString(), ClientRegisterResponseDTO.class);

                                //set value for register new dto
                                clientRegisterNewDTO = new ClientRegisterNewDTO();
                                clientRegisterNewDTO.setAccountNumber(StringUtil.setTextValue(userResult.getAccountNumber()));
                                clientRegisterNewDTO.setClientIdNumber(StringUtil.setTextValue(userResult.getClientIdNumber()));
                                clientRegisterNewDTO.setClientName(StringUtil.setTextValue(userResult.getClientName()));
                                clientRegisterNewDTO.setEmail(StringUtil.setTextValue(userResult.getEmail()));
                                clientRegisterNewDTO.setPhoneNumber(StringUtil.setTextValue(userResult.getPhoneNumber()));
                                clientRegisterNewDTO.setUserCode(StringUtil.setTextValue(userResult.getUserCode()));

                                message = StringUtil.setTextValue(userResult.getMsgSuccess());
                                typeDialog = Constant.TYPE_ALERT_DIALOG_CONFIRM;
                                alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_VALIDATE_REGISTER, true);
                            }
                        } else if (Constant.URL_REGISTER_USER.equals(urlApi)) {
                            JSONObject objResult;
                            Gson gson;
                            ClientRegisterResponseDTO userResult;
                            objResult = jsonObject.getJSONObject(Keys.KEY_OBJECT);
                            if (objResult == null) {
                                message = getResources().getString(R.string.message_error_null_data);
                                typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
                                alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                            } else {
                                gson = new GsonBuilder().create();
                                userResult = gson.fromJson(objResult.toString(), ClientRegisterResponseDTO.class);
                                message = userResult.getMsgSuccess();
                                typeDialog = Constant.TYPE_ALERT_DIALOG_INFOR;
                                alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_CLEAR_TEXT_REGISTER, true);
                            }
                        }
                    } else {
                        message = responseMessage;
                        if (message.contains("<a")) {
                            typeDialog = Constant.TYPE_ALERT_DIALOG_LINK;
                            alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_LINK, true);
                        } else {
                            typeDialog = Constant.TYPE_ALERT_DIALOG_INFOR;
                            alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                        }
                    }
                }
            } else {
                message = getResources().getString(R.string.message_error_system);
                typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
                alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            message = getResources().getString(R.string.message_error_system);
            typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
            alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
        }
    }

    @Override
    public void processFinishStream(ResponseStream inputStream) {

    }

    @Override
    public void OnClickedButtonDialog(boolean isClickOk, String actionDialog, String typeDialog) {
        if (isClickOk) {
            switch (actionDialog) {
                case Constant.ACTION_DIALOG_VALIDATE_REGISTER:
                    try {
                        Gson gson = new GsonBuilder().create();
                        String inputParams = gson.toJson(clientRegisterNewDTO);
                        DataSourceConnection connection = new DataSourceConnection(getActivity(), Constant.URL_REGISTER_USER, inputParams, Constant.METHOD_POST, true, true
                        );
                        connection.delegate = this;
                        connection.execute();

                    } catch (Exception e) {
                        Log.e("ERROR", e.getMessage());
                        message = getResources().getString(R.string.message_error_system);
                        typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
                        alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                    }
                    break;
                case Constant.ACTION_DIALOG_CLEAR_TEXT_REGISTER:
                    edtRegisterBuyer.setText(Constant.EMPTY);
                    edtRegisterIdentifyNumber.setText(Constant.EMPTY);
                    tvRegisterBuyerError.setVisibility(View.GONE);
                    tvRegisterIdentifyNumberError.setVisibility(View.GONE);
                    break;
            }
        }
    }
}