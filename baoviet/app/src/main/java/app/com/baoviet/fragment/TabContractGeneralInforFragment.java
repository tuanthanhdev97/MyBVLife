package app.com.baoviet.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.List;

import app.com.baoviet.R;
import app.com.baoviet.adapter.GeneralInforBenefitAdapter;
import app.com.baoviet.constant.Constant;
import app.com.baoviet.constant.Keys;
import app.com.baoviet.datasource.DataSourceConnection;
import app.com.baoviet.entity.AccountInfoDTO;
import app.com.baoviet.entity.BenifitTypeDTO;
import app.com.baoviet.entity.ResponseStream;
import app.com.baoviet.interfaceclass.AsyncResponse;
import app.com.baoviet.interfaceclass.DialogEventListener;
import app.com.baoviet.utility.AlertDialogCustom;
import app.com.baoviet.utility.CalculateDimension;
import app.com.baoviet.utility.StringUtil;

public class TabContractGeneralInforFragment extends Fragment implements AsyncResponse, DialogEventListener, View.OnTouchListener {

    private EditText edtContractTabGeneralProduct;
    private EditText edtContractTabGeneralStatus;
    private EditText edtContractTabGeneralReleaseDate;
    private EditText edtContractTabGeneralEffectiveDate;
    private EditText edtContractTabGeneralAmountMoney;
    private EditText edtContractTabGeneralRate;
    private EditText edtContractTabGeneralPaymentDeadline;
    private EditText edtContractTabGeneralMaintainDeadline;
    private EditText edtContractTabGeneralRetirementBenefit;
    private EditText edtContractTabGeneralPeriodically;
    private EditText edtContractTabGeneralSpecialOption;
    private EditText edtContractTabGeneralDateRetirementBenefits;
    private EditText edtContractTabGeneralStandardRetirementAge;
    private EditText edtContractTabGeneralBurialAllowance;
    private EditText edtContractTabGeneralDeathBenefitInsurance;
    private EditText edtContractTabGeneralPayPeriodically;
    private EditText edtContractTabGeneralPremiumCommitment;
    private EditText edtContractTabGeneralPremiumRegular;
    private EditText edtContractTabGeneralMembershipPremiumRate;
    private EditText edtContractTabGeneralNnextPaymentDueDate;
    private EditText edtContractTabGeneralInsurancePremiums;
    private EditText edtContractTabGeneralRateInsuranceBuyer;
    private EditText edtContractTabGeneralDeathBenefitSecond;
    private EditText edtContractTabGeneralPermanentDisabilityBenefitSecond;
    private EditText edtContractTabGeneralFatalDiseaseBenefitSecond;
    private EditText edtContractTabGeneralDeathBenefitThird;
    private EditText edtContractTabGeneralSeriousIllnessBenefitThird;
    private EditText edtContractTabGeneralFatalDiseaseBenefitThird;
    private EditText edtContractTabGeneralFinancialSupportBenefitThird;

    private LinearLayout lnContractTabGeneralBenefitFirst;
    private LinearLayout lnContractTabGeneralBenefitSecond;
    private LinearLayout lnContractTabGeneralBenefitThird;
    private LinearLayout lnContractTabGeneralBenefitFourth;
    private LinearLayout lnContractTabGeneralPremiumCommitment;
    private LinearLayout lnContractTabGeneralPremiumRegular;
    private LinearLayout lnContractTabGeneralMembershipPremiumRate;
    private LinearLayout lnContractTabGeneralNnextPaymentDueDate;
    private LinearLayout lnContractTabGeneralInsurancePremiums;
    private LinearLayout lnContractTabGeneralRateInsuranceBuyer;
    private LinearLayout lnContractGeneral;
    private LinearLayout lnContractTabGeneralFatalDiseaseBenefitSecond;
    private ListView lvGeneralInforBenefit;

    private TextView tvContractGeneral;
    private String accountNumber;
    private String typeAccount;
    private String productTypeCode;

    AlertDialogCustom alert;
    String message = Constant.EMPTY;
    String typeDialog = Constant.EMPTY;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_contract_general_infor, container, false);
        initInterface(view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            accountNumber = bundle.getString(Keys.KEY_ACCOUNT_NUMBER);
            typeAccount = bundle.getString(Keys.KEY_TYPE_ACCOUNT);
            productTypeCode = bundle.getString(Keys.KEY_PRODUCT_TYPE_CODE);
        }
        getData();
        return view;
    }

    public void initInterface(View view) {
        alert = new AlertDialogCustom(getContext());
        alert.dialogEventListener = this;
        edtContractTabGeneralProduct = (EditText) view.findViewById(R.id.edtContractTabGeneralProduct);
        edtContractTabGeneralStatus = (EditText) view.findViewById(R.id.edtContractTabGeneralStatus);
        edtContractTabGeneralReleaseDate = (EditText) view.findViewById(R.id.edtContractTabGeneralReleaseDate);
        edtContractTabGeneralEffectiveDate = (EditText) view.findViewById(R.id.edtContractTabGeneralEffectiveDate);
        edtContractTabGeneralAmountMoney = (EditText) view.findViewById(R.id.edtContractTabGeneralAmountMoney);
        edtContractTabGeneralRate = (EditText) view.findViewById(R.id.edtContractTabGeneralRate);
        edtContractTabGeneralPaymentDeadline = (EditText) view.findViewById(R.id.edtContractTabGeneralPaymentDeadline);
        edtContractTabGeneralMaintainDeadline = (EditText) view.findViewById(R.id.edtContractTabGeneralMaintainDeadline);
        edtContractTabGeneralRetirementBenefit = (EditText) view.findViewById(R.id.edtContractTabGeneralRetirementBenefit);
        edtContractTabGeneralPeriodically = (EditText) view.findViewById(R.id.edtContractTabGeneralPeriodically);
        edtContractTabGeneralSpecialOption = (EditText) view.findViewById(R.id.edtContractTabGeneralSpecialOption);
        edtContractTabGeneralDateRetirementBenefits = (EditText) view.findViewById(R.id.edtContractTabGeneralDateRetirementBenefits);
        edtContractTabGeneralStandardRetirementAge = (EditText) view.findViewById(R.id.edtContractTabGeneralStandardRetirementAge);
        edtContractTabGeneralBurialAllowance = (EditText) view.findViewById(R.id.edtContractTabGeneralBurialAllowance);
        edtContractTabGeneralDeathBenefitInsurance = (EditText) view.findViewById(R.id.edtContractTabGeneralDeathBenefitInsurance);
        edtContractTabGeneralPayPeriodically = (EditText) view.findViewById(R.id.edtContractTabGeneralPayPeriodically);
        edtContractTabGeneralPremiumCommitment = (EditText) view.findViewById(R.id.edtContractTabGeneralPremiumCommitment);
        edtContractTabGeneralPremiumRegular = (EditText) view.findViewById(R.id.edtContractTabGeneralPremiumRegular);
        edtContractTabGeneralMembershipPremiumRate = (EditText) view.findViewById(R.id.edtContractTabGeneralMembershipPremiumRate);
        edtContractTabGeneralNnextPaymentDueDate = (EditText) view.findViewById(R.id.edtContractTabGeneralNnextPaymentDueDate);
        edtContractTabGeneralInsurancePremiums = (EditText) view.findViewById(R.id.edtContractTabGeneralInsurancePremiums);
        edtContractTabGeneralRateInsuranceBuyer = (EditText) view.findViewById(R.id.edtContractTabGeneralRateInsuranceBuyer);
        lnContractGeneral = (LinearLayout) view.findViewById(R.id.lnContractGeneral);
        tvContractGeneral = (TextView) view.findViewById(R.id.tvContractGeneral);
        edtContractTabGeneralDeathBenefitSecond = (EditText) view.findViewById(R.id.edtContractTabGeneralDeathBenefitSecond);
        edtContractTabGeneralPermanentDisabilityBenefitSecond = (EditText) view.findViewById(R.id.edtContractTabGeneralPermanentDisabilityBenefitSecond);
        edtContractTabGeneralFatalDiseaseBenefitSecond = (EditText) view.findViewById(R.id.edtContractTabGeneralFatalDiseaseBenefitSecond);
        edtContractTabGeneralDeathBenefitThird = (EditText) view.findViewById(R.id.edtContractTabGeneralDeathBenefitThird);
        edtContractTabGeneralSeriousIllnessBenefitThird = (EditText) view.findViewById(R.id.edtContractTabGeneralSeriousIllnessBenefitThird);
        edtContractTabGeneralFatalDiseaseBenefitThird = (EditText) view.findViewById(R.id.edtContractTabGeneralFatalDiseaseBenefitThird);
        edtContractTabGeneralFinancialSupportBenefitThird = (EditText) view.findViewById(R.id.edtContractTabGeneralFinancialSupportBenefitThird);
        lnContractTabGeneralBenefitFirst = (LinearLayout) view.findViewById(R.id.lnContractTabGeneralBenefitFirst);
        lnContractTabGeneralBenefitSecond = (LinearLayout) view.findViewById(R.id.lnContractTabGeneralBenefitSecond);
        lnContractTabGeneralBenefitThird = (LinearLayout) view.findViewById(R.id.lnContractTabGeneralBenefitThird);
        lnContractTabGeneralBenefitFourth = (LinearLayout) view.findViewById(R.id.lnContractTabGeneralBenefitFourth);
        lnContractTabGeneralPremiumCommitment = (LinearLayout) view.findViewById(R.id.lnContractTabGeneralPremiumCommitment);
        lnContractTabGeneralPremiumRegular = (LinearLayout) view.findViewById(R.id.lnContractTabGeneralPremiumRegular);
        lnContractTabGeneralMembershipPremiumRate = (LinearLayout) view.findViewById(R.id.lnContractTabGeneralMembershipPremiumRate);
        lnContractTabGeneralNnextPaymentDueDate = (LinearLayout) view.findViewById(R.id.lnContractTabGeneralNnextPaymentDueDate);
        lnContractTabGeneralInsurancePremiums = (LinearLayout) view.findViewById(R.id.lnContractTabGeneralInsurancePremiums);
        lnContractTabGeneralRateInsuranceBuyer = (LinearLayout) view.findViewById(R.id.lnContractTabGeneralRateInsuranceBuyer);
        lnContractTabGeneralFatalDiseaseBenefitSecond = (LinearLayout) view.findViewById(R.id.lnContractTabGeneralFatalDiseaseBenefitSecond);
        lvGeneralInforBenefit = (ListView) view.findViewById(R.id.lvGeneralInforBenefit);
        edtContractTabGeneralFatalDiseaseBenefitSecond.setOnTouchListener(this);
    }

    public void getData() {
        try {
            if (accountNumber != null) {
                DataSourceConnection connection;
                Activity activity = getActivity();
                if (isAdded() && activity != null) {
                    connection = new DataSourceConnection(activity, Constant.URL_GET_GENERAL_INFOR, accountNumber, Constant.METHOD_POST);
                    connection.delegate = this;
                    connection.execute();
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

    public void showData(AccountInfoDTO accountInfor) {
        String mortalityTypeCode = accountInfor.getAccMortalityTypeCode();
        String productCode = accountInfor.getAccProductCode();
        String freqCode = accountInfor.getAccFreqCode();
        List<BenifitTypeDTO> listBenefit = accountInfor.getBenefitTypeList();
        boolean isNullOrEmptyListBenefit;
        if (listBenefit == null) {
            isNullOrEmptyListBenefit = true;
        } else {
            if (Constant.INT_0 == listBenefit.size()) {
                isNullOrEmptyListBenefit = true;
            } else {
                isNullOrEmptyListBenefit = false;
            }
        }

        if (accountInfor != null) {
            lnContractGeneral.setVisibility(View.VISIBLE);
            tvContractGeneral.setVisibility(View.GONE);
            edtContractTabGeneralProduct.setText(StringUtil.setTextValue(accountInfor.getAccProductName()));
            edtContractTabGeneralStatus.setText(StringUtil.setTextValue(accountInfor.getAccStatusDescription()));
            edtContractTabGeneralReleaseDate.setText(StringUtil.formatDateDDMMYYFromDate(accountInfor.getAccProcessedDate(), Constant.DDMMYY));
            edtContractTabGeneralEffectiveDate.setText(StringUtil.formatDateDDMMYYFromDate(accountInfor.getAccEffectiveDate(), Constant.DDMMYY));
            edtContractTabGeneralAmountMoney.setText(StringUtil.convertToCurrency(accountInfor.getBenefitDTO().getStbhtv()));
            if (accountInfor.getAccRetyShortName() != null) {
                edtContractTabGeneralRate.setText(StringUtil.setTextValue(accountInfor.getAccRetyShortName()) + Constant.SYMBOL_PERCENT);
            } else {
                edtContractTabGeneralRate.setText(Constant.RATE_ZERO_PERCENT);
            }
            edtContractTabGeneralPaymentDeadline.setText(StringUtil.setTextValue(accountInfor.getAccPremTermDesc()));
            if (!accountInfor.getAccStatusCode().equals(Constant.ACCOUNT_STATUS_CODE_CPUP)) {
                edtContractTabGeneralMaintainDeadline.setText(StringUtil.setTextValue(accountInfor.getAccRiskTermDesc()));
            } else {
                edtContractTabGeneralMaintainDeadline.setText(Constant.INT_0);
            }
            //first benefit
            if (productTypeCode.equals(Constant.PRODUCT_TYPE_CODE_PENG) ||
                    productTypeCode.equals(Constant.PRODUCT_TYPE_CODE_PENI)) {
                lnContractTabGeneralBenefitFirst.setVisibility(View.VISIBLE);
                lnContractTabGeneralBenefitSecond.setVisibility(View.GONE);
                lnContractTabGeneralBenefitThird.setVisibility(View.GONE);
                lnContractTabGeneralBenefitFourth.setVisibility(View.GONE);

                //section first benefit
                edtContractTabGeneralRetirementBenefit.setText(StringUtil.setTextValue(accountInfor.getAccRetireBenefits()));
                edtContractTabGeneralPeriodically.setText(StringUtil.setTextValue(accountInfor.getAccRetiredAgeDesc()));
                edtContractTabGeneralSpecialOption.setText(StringUtil.setTextValue(accountInfor.getAccFirstPav()));
                edtContractTabGeneralDateRetirementBenefits.setText(StringUtil.formatDateDDMMYYFromDate(accountInfor.getAccRiskCeaseDate(), Constant.DDMMYY));
                edtContractTabGeneralStandardRetirementAge.setText(StringUtil.setTextValue(accountInfor.getAccRetiredAge()));
                edtContractTabGeneralBurialAllowance.setText(StringUtil.convertToCurrency(accountInfor.getBenefitDTO().getQltcmt()));
                if (mortalityTypeCode.equals(Constant.MORTALITY_TYPE_CODE_EMBD)) {
                    edtContractTabGeneralDeathBenefitInsurance.setText(Constant.MORTALITY_TYPE_BASIC);
                } else if (mortalityTypeCode.equals(Constant.MORTALITY_TYPE_CODE_NOEM)) {
                    edtContractTabGeneralDeathBenefitInsurance.setText(Constant.MORTALITY_TYPE_ADVANCE);
                }
            } // second benefit
            else if (!productTypeCode.equals(Constant.PRODUCT_TYPE_CODE_PENG)
                    && !productTypeCode.equals(Constant.PRODUCT_TYPE_CODE_PENI)
                    && !checkProductCode(productCode) && Constant.TYPE_ACCOUNT_TALISMAN.equals(typeAccount)) {
                lnContractTabGeneralBenefitFirst.setVisibility(View.GONE);
                lnContractTabGeneralBenefitSecond.setVisibility(View.VISIBLE);
                lnContractTabGeneralBenefitThird.setVisibility(View.GONE);
                lnContractTabGeneralBenefitFourth.setVisibility(View.GONE);

                //section second benefit
                if (mortalityTypeCode.equals(Constant.MORTALITY_TYPE_CODE_EMBD)) {
                    edtContractTabGeneralDeathBenefitSecond.setText(Constant.MORTALITY_TYPE_BASIC);
                } else if (mortalityTypeCode.equals(Constant.MORTALITY_TYPE_CODE_NOEM)) {
                    edtContractTabGeneralDeathBenefitSecond.setText(Constant.MORTALITY_TYPE_ADVANCE);
                }
                if (!Constant.PRODUCT_TYPE_CODE_TRLF.equals(productCode)) {
                    lnContractTabGeneralFatalDiseaseBenefitSecond.setVisibility(View.VISIBLE);
                } else {
                    lnContractTabGeneralFatalDiseaseBenefitSecond.setVisibility(View.GONE);
                }
            } //third benefit
            else if (productTypeCode.equals(Constant.PRODUCT_TYPE_CODE_UNIV) && checkProductCode(productCode)) {
                lnContractTabGeneralBenefitFirst.setVisibility(View.GONE);
                lnContractTabGeneralBenefitSecond.setVisibility(View.GONE);
                lnContractTabGeneralBenefitThird.setVisibility(View.VISIBLE);
                lnContractTabGeneralBenefitFourth.setVisibility(View.GONE);
                if (mortalityTypeCode.equals(Constant.MORTALITY_TYPE_CODE_EMBD)) {
                    edtContractTabGeneralDeathBenefitThird.setText(Constant.MORTALITY_TYPE_BASIC);
                } else if (mortalityTypeCode.equals(Constant.MORTALITY_TYPE_CODE_NOEM)) {
                    edtContractTabGeneralDeathBenefitThird.setText(Constant.MORTALITY_TYPE_ADVANCE);
                }
                edtContractTabGeneralSeriousIllnessBenefitThird.setText(StringUtil.convertToCurrency(accountInfor.getBenefitDTO().getQlbnt()));
                edtContractTabGeneralFinancialSupportBenefitThird.setText(StringUtil.convertToCurrency(accountInfor.getBenefitDTO().getQlhttc()));
            } else if (Constant.TYPE_ACCOUNT_BVLIFE.equals(typeAccount) && productTypeCode.equals(Constant.PRODUCT_TYPE_CODE_TRLF)
                    && !isNullOrEmptyListBenefit) {
                lnContractTabGeneralBenefitFirst.setVisibility(View.GONE);
                lnContractTabGeneralBenefitSecond.setVisibility(View.GONE);
                lnContractTabGeneralBenefitThird.setVisibility(View.GONE);
                lnContractTabGeneralBenefitFourth.setVisibility(View.VISIBLE);
                GeneralInforBenefitAdapter benefitAdapter = new GeneralInforBenefitAdapter(getActivity(), R.layout.list_generalinfor_benefit, listBenefit);
                lvGeneralInforBenefit.setAdapter(benefitAdapter);
                CalculateDimension.setHeightDefaultOfListView(lvGeneralInforBenefit);
            }
            edtContractTabGeneralPayPeriodically.setText(StringUtil.setTextValue(accountInfor.getAccFreqDesc()));
            if (!freqCode.equals(Constant.FREQ_TYPE_CODE_SING) && Constant.TYPE_ACCOUNT_TALISMAN.equals(typeAccount)) {
                lnContractTabGeneralPremiumCommitment.setVisibility(View.VISIBLE);
                lnContractTabGeneralInsurancePremiums.setVisibility(View.VISIBLE);
                edtContractTabGeneralInsurancePremiums.setText(StringUtil.convertToCurrency(accountInfor.getAccInvoicedAmount()));
                edtContractTabGeneralPremiumCommitment.setText(StringUtil.convertToCurrency(accountInfor.getAccRegularPremium()));
            }
            if (!freqCode.equals(Constant.FREQ_TYPE_CODE_SING) && Constant.TYPE_ACCOUNT_BVLIFE.equals(typeAccount)) {
                lnContractTabGeneralPremiumRegular.setVisibility(View.VISIBLE);
                edtContractTabGeneralPremiumRegular.setText(StringUtil.convertToCurrency(accountInfor.getAccRegularPremium()));
            }
            if (productTypeCode.equals(Constant.PRODUCT_TYPE_CODE_PENG)) {
                lnContractTabGeneralRateInsuranceBuyer.setVisibility(View.VISIBLE);
                lnContractTabGeneralMembershipPremiumRate.setVisibility(View.VISIBLE);
                edtContractTabGeneralRateInsuranceBuyer.setText(StringUtil.roundRatio(accountInfor.getRatioGroupPrem(), Constant.INT_2));
                edtContractTabGeneralMembershipPremiumRate.setText(StringUtil.roundRatio(accountInfor.getRatioReguPrem(), Constant.INT_2));
            }
            if (!freqCode.equals(Constant.FREQ_TYPE_CODE_SING)) {
                lnContractTabGeneralNnextPaymentDueDate.setVisibility(View.VISIBLE);
                edtContractTabGeneralNnextPaymentDueDate.setText(StringUtil.formatDateDDMMYYFromDate(accountInfor.getAccNextBillingDue(), Constant.DDMMYY));
            }

        } else {
            lnContractGeneral.setVisibility(View.GONE);
            tvContractGeneral.setVisibility(View.VISIBLE);
        }
    }

    public boolean checkProductCode(String productCode) {
        if (!StringUtil.isNullOrEmpty(productCode)) {
            return productCode.contains(Constant.PRODUCT_CODE_AUVL04);
        } else {
            return false;
        }
    }

    @Override
    public void processFinish(String result, String urlApi) {
        try {
            if (!StringUtil.isNullOrEmpty(result)) {
                if (Constant.ERROR_SERVER.equals(result)) {
                    message = getResources().getString(R.string.message_error_server);
                    typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
                    alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                }
                JSONObject jsonObject = new JSONObject(result);
                int responseStatus = jsonObject.getInt(Keys.KEY_RESPONSE_STATUS);
                String responseMessage = jsonObject.getString(Keys.KEY_RESPONSE_MESSAGE);
                if (Constant.RESPONSE_CODE_200 == responseStatus) {
                    JSONObject objResult = jsonObject.getJSONObject(Keys.KEY_OBJECT);
                    Gson gson = new GsonBuilder().create();
                    AccountInfoDTO accountInfor = gson.fromJson(objResult.toString(), AccountInfoDTO.class);
//                    ContractAccountInvidualFragment parentFrag = (ContractAccountInvidualFragment) this.getParentFragment();
//                    parentFrag.setTextLastTimeUpdate(accountInfor.getShortDesc());
                    //show data
                    showData(accountInfor);
                } else if (Constant.RESPONSE_CODE_412 == responseStatus) {
                    message = responseMessage;
                    typeDialog = Constant.TYPE_ALERT_DIALOG_ERROR;
                    alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                } else {
                    message = getResources().getString(R.string.message_general_infor_no_data);
                    typeDialog = Constant.TYPE_ALERT_DIALOG_INFOR;
                    alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
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
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()) {
            case R.id.edtContractTabGeneralFatalDiseaseBenefitSecond:
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View popupView = layoutInflater.inflate(R.layout.popup_text, null);
                    final PopupWindow popupWindow = new PopupWindow(popupView, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        TextView tvContentPopup = (TextView) popupView.findViewById(R.id.tvContentPopup);

                        tvContentPopup.setText(edtContractTabGeneralFatalDiseaseBenefitSecond.getText().toString());
                        popupWindow.showAsDropDown(view, 0, 0, Gravity.BOTTOM);
                        popupWindow.setOutsideTouchable(true);
                        popupWindow.setFocusable(true);
                        popupWindow.update();
                    }
                }
                break;
        }
        return false;
    }
}