package app.com.baoviet.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import app.com.baoviet.R;
import app.com.baoviet.adapter.CustomExpandableListFeeAdapter;
import app.com.baoviet.constant.Constant;
import app.com.baoviet.constant.Keys;
import app.com.baoviet.datasource.DataSourceConnection;
import app.com.baoviet.entity.DataLocal;
import app.com.baoviet.entity.FeesChargesTransDTO;
import app.com.baoviet.entity.ResponseStream;
import app.com.baoviet.interfaceclass.AsyncResponse;
import app.com.baoviet.interfaceclass.DialogEventListener;
import app.com.baoviet.sqlite.DatabaseHelper;
import app.com.baoviet.utility.AlertDialogCustom;
import app.com.baoviet.utility.CalculateDimension;
import app.com.baoviet.utility.ConnectionInternet;
import app.com.baoviet.utility.DateTimeDialog;
import app.com.baoviet.utility.StringUtil;

public class TabContractCostFragment extends Fragment implements View.OnClickListener, DialogEventListener, AsyncResponse {

    private EditText edtContractTabCostFromDate;
    private EditText edtContractTabCostToDate;
    private TextView tvContractTabCostErrorEmptyFromDate;
    private TextView tvContractTabCostErrorEmptyToDate;
    private TextView tvContractCostErrorDate;
    private Button btnContractTabCostSearch;
    private ExpandableListView exListFees;
    private View rlFeesPagination;
    private TextView tvPaginationTotal;
    private TextView tvPaginationBackToFirst;
    private TextView tvPaginationPrevious;
    private TextView tvPaginationNext;
    private TextView tvPaginationGoToLast;

    private TextView tvPaginationNumberOne;
    private TextView tvPaginationNumberTwo;
    private TextView tvPaginationNumberThree;
    private TextView tvPaginationNumberFour;
    private TextView tvPaginationNumberFive;

    private String txtPaginationNumberOne = Constant.STR_INT_1;
    private String txtPaginationNumberTwo = Constant.STR_INT_2;
    private String txtPaginationNumberThree = Constant.STR_INT_3;
    private String txtPaginationNumberFour = Constant.STR_INT_4;
    private String txtPaginationNumberFive = Constant.STR_INT_5;

    private int firstResultPosition;
    private int currentPage = Constant.INT_1;
    private int totalPage;

    private Calendar fromDate;
    private Calendar toDate;
    private CustomExpandableListFeeAdapter mExpandableListFeesAdapter;
    private List<FeesChargesTransDTO> listFees = new ArrayList<FeesChargesTransDTO>();

    private String accountNumber;
    private String fromDateData = Constant.EMPTY;
    private String toDateData = Constant.EMPTY;
    private String typeAccount;
    AlertDialogCustom alert;
    String message = Constant.EMPTY;
    String typeDialog = Constant.EMPTY;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_contract_cost, container, false);
        initInterface(view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            accountNumber = bundle.getString(Keys.KEY_ACCOUNT_NUMBER);
            typeAccount=bundle.getString(Keys.KEY_CONTENT_TYPE);
        }
        getData();
        return view;
    }

    public void getData() {
        //get current date
        Date dateCurrent = new Date();
        Date dateLastYear = StringUtil.getSubstractOneYearFromDate(dateCurrent);

        if (ConnectionInternet.checkInternetConnection(getContext())) {
            toDateData = StringUtil.formatDateDDMMYYYYHHmmsssFromDate(dateCurrent);
            fromDateData = StringUtil.formatDateDDMMYYYYHHmmsssFromDate(dateLastYear);
            String toDate = StringUtil.formatDateDDMMYYFromDate(dateCurrent, Constant.DDMMYY);
            String fromDate = StringUtil.getSubstractOneYear(toDate);
            edtContractTabCostFromDate.setText(fromDate);
            edtContractTabCostToDate.setText(toDate);
        } else {
            DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
            DataLocal dataParams = new DataLocal();
            dataParams.setUsername(Constant.USER_CODE.toLowerCase());
            dataParams.setPassword(Constant.PASSWORD_LOCAL);
            dataParams.setApi(Constant.URL_GET_FEES_CHARGES_TRANS);
            DataLocal dataLocal = databaseHelper.getParamsResponseLocal(dataParams);
            String params = dataLocal.getParams();
            String response = dataLocal.getResponse();
            if (!StringUtil.isNullOrEmpty(params) && !StringUtil.isNullOrEmpty(response)) {
                try {
                    JSONObject jsonResult = new JSONObject(params);
                    JSONObject data = jsonResult.getJSONObject("obj");
                    if (data != null) {
                        String fromdate = data.getString("fromDate");
                        String todate = data.getString("toDate");
                        String[] fromDateArr = fromdate.split("T");
                        String[] toDateArr = todate.split("T");
                        String fromDateVal = StringUtil.convertYYYYMMDDToDDMMYY(fromDateArr[0]);
                        String toDateVal = StringUtil.convertYYYYMMDDToDDMMYY(toDateArr[0]);
                        edtContractTabCostFromDate.setText(fromDateVal);
                        edtContractTabCostToDate.setText(toDateVal);
                        toDateData = todate;
                        fromDateData = fromdate;
                    }
                    JSONObject jsonObject = new JSONObject(response);
                    int totalRecord = jsonObject.getInt(Keys.KEY_TOTAL_RECORDS);
                    JSONArray arrResult = jsonObject.getJSONArray(Keys.KEY_OBJECT);
                    Gson gson = new GsonBuilder().create();
                    Type listType = new TypeToken<List<FeesChargesTransDTO>>() {
                    }.getType();
                    List<FeesChargesTransDTO> myModelList = gson.fromJson(arrResult.toString(), listType);
                    if (myModelList == null || Constant.INT_0 == myModelList.size()) {
                        exListFees.setVisibility(View.GONE);
                        rlFeesPagination.setVisibility(View.GONE);
                    } else {
                        exListFees.setVisibility(View.VISIBLE);
                        listFees = myModelList;
                        if (Constant.NUMBER_RECORDS_PER_PAGE >= totalRecord) {
                            rlFeesPagination.setVisibility(View.GONE);
                        } else {
                            rlFeesPagination.setVisibility(View.VISIBLE);
                            showPagination(totalRecord);
                        }
                        tvPaginationTotal.setText(totalRecord + " kết quả " + "( " + (firstResultPosition + 1) + " ~ " + (firstResultPosition + myModelList.size()) + ")");
                        //show data
                        addDrawerItems();
                    }
                } catch (Exception e) {
                    Log.e("ERROR", e.getMessage());
                    e.printStackTrace();
                }
            } else {
                toDateData = StringUtil.formatDateDDMMYYYYHHmmsssFromDate(dateCurrent);
                fromDateData = StringUtil.formatDateDDMMYYYYHHmmsssFromDate(dateLastYear);
                String toDate = StringUtil.formatDateDDMMYYFromDate(dateCurrent, Constant.DDMMYY);
                String fromDate = StringUtil.getSubstractOneYear(toDate);
                edtContractTabCostFromDate.setText(fromDate);
                edtContractTabCostToDate.setText(toDate);
            }
        }


    }

    private void addDrawerItems() {
        mExpandableListFeesAdapter = new CustomExpandableListFeeAdapter(getContext(), listFees);
        exListFees.setAdapter(mExpandableListFeesAdapter);
        //set height default of list view
        CalculateDimension.setHeightDefaultOfExpandableListView(exListFees);
        exListFees.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        exListFees.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });
        exListFees.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {

                CalculateDimension.setListViewHeight(expandableListView, groupPosition);
                return false;
            }
        });

        exListFees.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                return false;
            }
        });
    }

    public void initInterface(View view) {
        alert = new AlertDialogCustom(getContext());
        alert.dialogEventListener = this;
        edtContractTabCostFromDate = (EditText) view.findViewById(R.id.edtContractTabCostFromDate);
        edtContractTabCostToDate = (EditText) view.findViewById(R.id.edtContractTabCostToDate);
        tvContractTabCostErrorEmptyFromDate = (TextView) view.findViewById(R.id.tvContractTabCostErrorEmptyFromDate);
        tvContractTabCostErrorEmptyToDate = (TextView) view.findViewById(R.id.tvContractTabCostErrorEmptyToDate);
        tvContractCostErrorDate = (TextView) view.findViewById(R.id.tvContractCostErrorDate);
        btnContractTabCostSearch = (Button) view.findViewById(R.id.btnContractTabCostSearch);
        exListFees = (ExpandableListView) view.findViewById(R.id.exListFees);
        rlFeesPagination = (View) view.findViewById(R.id.rlFeesPagination);
        tvPaginationTotal = (TextView) view.findViewById(R.id.tvPaginationTotal);
        tvPaginationNumberOne = (TextView) view.findViewById(R.id.tvPaginationNumberOne);
        tvPaginationNumberTwo = (TextView) view.findViewById(R.id.tvPaginationNumberTwo);
        tvPaginationNumberThree = (TextView) view.findViewById(R.id.tvPaginationNumberThree);
        tvPaginationNumberFour = (TextView) view.findViewById(R.id.tvPaginationNumberFour);
        tvPaginationNumberFive = (TextView) view.findViewById(R.id.tvPaginationNumberFive);
        tvPaginationNumberOne = (TextView) rlFeesPagination.findViewById(R.id.tvPaginationNumberOne);
        tvPaginationNumberTwo = (TextView) rlFeesPagination.findViewById(R.id.tvPaginationNumberTwo);
        tvPaginationNumberThree = (TextView) rlFeesPagination.findViewById(R.id.tvPaginationNumberThree);
        tvPaginationNumberFour = (TextView) rlFeesPagination.findViewById(R.id.tvPaginationNumberFour);
        tvPaginationNumberFive = (TextView) rlFeesPagination.findViewById(R.id.tvPaginationNumberFive);

        tvPaginationBackToFirst = (TextView) rlFeesPagination.findViewById(R.id.tvPaginationBackToFirst);
        tvPaginationPrevious = (TextView) rlFeesPagination.findViewById(R.id.tvPaginationPrevious);
        tvPaginationNext = (TextView) rlFeesPagination.findViewById(R.id.tvPaginationNext);
        tvPaginationGoToLast = (TextView) rlFeesPagination.findViewById(R.id.tvPaginationGoToLast);

        edtContractTabCostFromDate.setOnClickListener(this);
        edtContractTabCostToDate.setOnClickListener(this);
        btnContractTabCostSearch.setOnClickListener(this);

        tvPaginationBackToFirst.setOnClickListener(this);
        tvPaginationPrevious.setOnClickListener(this);
        tvPaginationNext.setOnClickListener(this);
        tvPaginationGoToLast.setOnClickListener(this);

        tvPaginationNumberOne.setOnClickListener(this);
        tvPaginationNumberTwo.setOnClickListener(this);
        tvPaginationNumberThree.setOnClickListener(this);
        tvPaginationNumberFour.setOnClickListener(this);
        tvPaginationNumberFive.setOnClickListener(this);
    }

    public void setBackgroundPagination(int numberPage) {
        switch (numberPage) {
            case Constant.INT_1:
                tvPaginationNumberOne.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rounded_pagination_clicked));
                tvPaginationNumberTwo.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rounded_pagination));
                tvPaginationNumberThree.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rounded_pagination));
                tvPaginationNumberFour.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rounded_pagination));
                tvPaginationNumberFive.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rounded_pagination));
                tvPaginationNumberOne.setTextColor(getResources().getColor(R.color.white));
                tvPaginationNumberTwo.setTextColor(getResources().getColor(R.color.black));
                tvPaginationNumberThree.setTextColor(getResources().getColor(R.color.black));
                tvPaginationNumberFour.setTextColor(getResources().getColor(R.color.black));
                tvPaginationNumberFive.setTextColor(getResources().getColor(R.color.black));
                break;
            case Constant.INT_2:
                tvPaginationNumberOne.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rounded_pagination));
                tvPaginationNumberTwo.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rounded_pagination_clicked));
                tvPaginationNumberThree.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rounded_pagination));
                tvPaginationNumberFour.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rounded_pagination));
                tvPaginationNumberFive.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rounded_pagination));
                tvPaginationNumberOne.setTextColor(getResources().getColor(R.color.black));
                tvPaginationNumberTwo.setTextColor(getResources().getColor(R.color.white));
                tvPaginationNumberThree.setTextColor(getResources().getColor(R.color.black));
                tvPaginationNumberFour.setTextColor(getResources().getColor(R.color.black));
                tvPaginationNumberFive.setTextColor(getResources().getColor(R.color.black));
                break;
            case Constant.INT_3:
                tvPaginationNumberOne.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rounded_pagination));
                tvPaginationNumberTwo.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rounded_pagination));
                tvPaginationNumberThree.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rounded_pagination_clicked));
                tvPaginationNumberFour.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rounded_pagination));
                tvPaginationNumberFive.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rounded_pagination));
                tvPaginationNumberOne.setTextColor(getResources().getColor(R.color.black));
                tvPaginationNumberTwo.setTextColor(getResources().getColor(R.color.black));
                tvPaginationNumberThree.setTextColor(getResources().getColor(R.color.white));
                tvPaginationNumberFour.setTextColor(getResources().getColor(R.color.black));
                tvPaginationNumberFive.setTextColor(getResources().getColor(R.color.black));
                break;
            case Constant.INT_4:
                tvPaginationNumberOne.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rounded_pagination));
                tvPaginationNumberTwo.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rounded_pagination));
                tvPaginationNumberThree.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rounded_pagination));
                tvPaginationNumberFour.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rounded_pagination_clicked));
                tvPaginationNumberFive.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rounded_pagination));
                tvPaginationNumberOne.setTextColor(getResources().getColor(R.color.black));
                tvPaginationNumberTwo.setTextColor(getResources().getColor(R.color.black));
                tvPaginationNumberThree.setTextColor(getResources().getColor(R.color.black));
                tvPaginationNumberFour.setTextColor(getResources().getColor(R.color.white));
                tvPaginationNumberFive.setTextColor(getResources().getColor(R.color.black));
                break;
            case Constant.INT_5:
                tvPaginationNumberOne.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rounded_pagination));
                tvPaginationNumberTwo.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rounded_pagination));
                tvPaginationNumberThree.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rounded_pagination));
                tvPaginationNumberFour.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rounded_pagination));
                tvPaginationNumberFive.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rounded_pagination_clicked));
                tvPaginationNumberOne.setTextColor(getResources().getColor(R.color.black));
                tvPaginationNumberTwo.setTextColor(getResources().getColor(R.color.black));
                tvPaginationNumberThree.setTextColor(getResources().getColor(R.color.black));
                tvPaginationNumberFour.setTextColor(getResources().getColor(R.color.black));
                tvPaginationNumberFive.setTextColor(getResources().getColor(R.color.white));
                break;
        }
    }

    public void loadDataByPage() {
        try {
            DataSourceConnection connection;
            JSONObject postDataParams = new JSONObject();
            JSONObject postObjectParams = new JSONObject();
            JSONObject postPagingParams = new JSONObject();
            postPagingParams.put(Keys.KEY_FIRST_RESULT_POSITION, firstResultPosition);
            postPagingParams.put(Keys.KEY_NUM_OF_RECORDS, Constant.NUMBER_RECORDS_PER_PAGE);

            postObjectParams.put(Keys.KEY_ACCOUNT_NUMBER, accountNumber);
            postObjectParams.put(Keys.KEY_FROM_DATE, fromDateData);
            postObjectParams.put(Keys.KEY_TO_DATE, toDateData);

            postDataParams.put(Keys.KEY_OBJECT, postObjectParams);
            postDataParams.put(Keys.KEY_PAGING_DTO, postPagingParams);
            String inputParams = postDataParams.toString().replace("\\", "").replace("\"[", "[").replace("]\"", "]");
            Activity activity = getActivity();
            if (isAdded() && activity != null) {
                connection = new DataSourceConnection(activity, Constant.URL_GET_FEES_CHARGES_TRANS, inputParams, Constant.METHOD_POST, true);
                connection.delegate = this;
                connection.execute();
            }

        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            message = getResources().getString(R.string.message_error_system);
            typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
            alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.edtContractTabCostFromDate:
                // Get Current Date
                DateTimeDialog.getDate(getContext(), edtContractTabCostFromDate);
                break;
            case R.id.edtContractTabCostToDate:
                // Get Current Date
                DateTimeDialog.getDate(getContext(), edtContractTabCostToDate);
                break;
            case R.id.btnContractTabCostSearch:
                boolean isNotEmptyFromDate = true;
                boolean isNotEmptyToDate = true;
                boolean isNotValidateDate = true;
                firstResultPosition = Constant.INT_0;
                String fromDateTemp = edtContractTabCostFromDate.getText().toString();
                String toDateTemp = edtContractTabCostToDate.getText().toString();
                Date fromDate = StringUtil.convertStringDateToCalendar(fromDateTemp, Constant.DDMMYY);
                Date toDate = StringUtil.convertStringDateToCalendar(toDateTemp, Constant.DDMMYY);
                toDateData = StringUtil.formatDateDDMMYYYYHHmmsssFromDate(toDate);
                fromDateData = StringUtil.formatDateDDMMYYYYHHmmsssFromDate(fromDate);

                if (StringUtil.isNullOrEmpty(fromDateTemp)) {
                    isNotEmptyFromDate = false;
                    tvContractTabCostErrorEmptyFromDate.setVisibility(View.VISIBLE);
                } else {
                    isNotEmptyFromDate = true;
                    tvContractTabCostErrorEmptyFromDate.setVisibility(View.GONE);
                }
                if (StringUtil.isNullOrEmpty(toDateTemp)) {
                    isNotEmptyToDate = false;
                    tvContractTabCostErrorEmptyToDate.setVisibility(View.VISIBLE);
                } else {
                    isNotEmptyToDate = true;
                    tvContractTabCostErrorEmptyToDate.setVisibility(View.GONE);
                }

                if (Constant.INT_0 <= fromDate.compareTo(toDate)) {
                    exListFees.setVisibility(View.GONE);
                    isNotValidateDate = false;
                    tvContractCostErrorDate.setVisibility(View.VISIBLE);
                } else {
                    isNotValidateDate = true;
                    tvContractCostErrorDate.setVisibility(View.GONE);
                }
                if (isNotEmptyFromDate && isNotEmptyToDate && isNotValidateDate) {
                    loadDataByPage();
                }

                break;
            case R.id.tvPaginationBackToFirst:
                if (currentPage > Constant.INT_1) {
                    setBackgroundPagination(Constant.INT_1);
                    tvPaginationNumberFive.setText(String.valueOf(Constant.INT_5));
                    tvPaginationNumberFour.setText(String.valueOf(Constant.INT_4));
                    tvPaginationNumberThree.setText(String.valueOf(Constant.INT_3));
                    tvPaginationNumberTwo.setText(String.valueOf(Constant.INT_2));
                    tvPaginationNumberOne.setText(String.valueOf(Constant.INT_1));
                    firstResultPosition = Constant.INT_0;
                    currentPage = Constant.INT_1;
                    loadDataByPage();
                }
                break;
            case R.id.tvPaginationPrevious:
                if (currentPage > Constant.INT_1) {
                    if (currentPage > Integer.parseInt(String.valueOf(tvPaginationNumberOne.getText()))) {
                        currentPage -= Constant.INT_1;
                        if (currentPage == Integer.parseInt(String.valueOf(tvPaginationNumberFive.getText()))) {
                            setBackgroundPagination(Constant.INT_5);
                        } else if (currentPage == Integer.parseInt(String.valueOf(tvPaginationNumberFour.getText()))) {
                            setBackgroundPagination(Constant.INT_4);
                        } else if (currentPage == Integer.parseInt(String.valueOf(tvPaginationNumberThree.getText()))) {
                            setBackgroundPagination(Constant.INT_3);
                        } else if (currentPage == Integer.parseInt(String.valueOf(tvPaginationNumberTwo.getText()))) {
                            setBackgroundPagination(Constant.INT_2);
                        } else if (currentPage == Integer.parseInt(String.valueOf(tvPaginationNumberOne.getText()))) {
                            setBackgroundPagination(Constant.INT_1);
                        }
                    } else if (currentPage == Integer.parseInt(String.valueOf(tvPaginationNumberOne.getText()))) {
                        tvPaginationNumberFive.setText(String.valueOf(currentPage + Constant.INT_3));
                        tvPaginationNumberFour.setText(String.valueOf(currentPage + Constant.INT_2));
                        tvPaginationNumberThree.setText(String.valueOf(currentPage + Constant.INT_1));
                        tvPaginationNumberTwo.setText(String.valueOf(currentPage));
                        tvPaginationNumberOne.setText(String.valueOf(currentPage - Constant.INT_1));
                        currentPage -= Constant.INT_1;
                    }
                    firstResultPosition = Constant.NUMBER_RECORDS_PER_PAGE * (currentPage - Constant.INT_1);
                    loadDataByPage();
                }
                break;
            case R.id.tvPaginationNumberOne:
                if (tvPaginationNumberOne.getText().equals(Constant.STR_INT_1)) {
                    currentPage = Constant.INT_1;
                    firstResultPosition = Constant.INT_0;
                } else {
                    currentPage = Integer.parseInt(String.valueOf(tvPaginationNumberOne.getText()));
                }
                setBackgroundPagination(Constant.INT_1);
                firstResultPosition = Constant.NUMBER_RECORDS_PER_PAGE * (currentPage - Constant.INT_1);
                loadDataByPage();
                break;
            case R.id.tvPaginationNumberTwo:

                if (tvPaginationNumberTwo.getText().equals(Constant.STR_INT_2)) {
                    currentPage = Constant.INT_2;
                } else {
                    currentPage = Integer.parseInt(String.valueOf(tvPaginationNumberTwo.getText()));
                }
                setBackgroundPagination(Constant.INT_2);
                firstResultPosition = Constant.NUMBER_RECORDS_PER_PAGE * (currentPage - Constant.INT_1);
                loadDataByPage();
                break;
            case R.id.tvPaginationNumberThree:

                if (tvPaginationNumberThree.getText().equals(Constant.STR_INT_3)) {
                    currentPage = Constant.INT_3;
                } else {
                    currentPage = Integer.parseInt(String.valueOf(tvPaginationNumberThree.getText()));
                }
                setBackgroundPagination(Constant.INT_3);
                firstResultPosition = Constant.NUMBER_RECORDS_PER_PAGE * (currentPage - Constant.INT_1);
                loadDataByPage();
                break;
            case R.id.tvPaginationNumberFour:
                if (tvPaginationNumberFour.getText().equals(Constant.STR_INT_4)) {
                    currentPage = Constant.INT_4;
                } else {
                    currentPage = Integer.parseInt(String.valueOf(tvPaginationNumberFour.getText()));
                }
                setBackgroundPagination(Constant.INT_4);
                firstResultPosition = Constant.NUMBER_RECORDS_PER_PAGE * (currentPage - Constant.INT_1);
                loadDataByPage();
                break;
            case R.id.tvPaginationNumberFive:
                if (tvPaginationNumberFive.getText().equals(Constant.STR_INT_5)) {
                    currentPage = Constant.INT_5;
                } else {
                    currentPage = Integer.parseInt(String.valueOf(tvPaginationNumberFive.getText()));
                }
                setBackgroundPagination(Constant.INT_5);
                firstResultPosition = Constant.NUMBER_RECORDS_PER_PAGE * (currentPage - Constant.INT_1);
                loadDataByPage();
                break;
            case R.id.tvPaginationNext:
                if (currentPage < totalPage) {
                    if (currentPage < Integer.parseInt(String.valueOf(tvPaginationNumberFive.getText()))) {
                        currentPage += Constant.INT_1;
                        if (currentPage == Integer.parseInt(String.valueOf(tvPaginationNumberFive.getText()))) {
                            setBackgroundPagination(Constant.INT_5);
                        } else if (currentPage == Integer.parseInt(String.valueOf(tvPaginationNumberFour.getText()))) {
                            setBackgroundPagination(Constant.INT_4);
                        } else if (currentPage == Integer.parseInt(String.valueOf(tvPaginationNumberThree.getText()))) {
                            setBackgroundPagination(Constant.INT_3);
                        } else if (currentPage == Integer.parseInt(String.valueOf(tvPaginationNumberTwo.getText()))) {
                            setBackgroundPagination(Constant.INT_2);
                        } else if (currentPage == Integer.parseInt(String.valueOf(tvPaginationNumberOne.getText()))) {
                            setBackgroundPagination(Constant.INT_1);
                        }
                    } else if (currentPage == Integer.parseInt(String.valueOf(tvPaginationNumberFive.getText()))) {
                        tvPaginationNumberFive.setText(String.valueOf(currentPage + Constant.INT_1));
                        tvPaginationNumberFour.setText(String.valueOf(currentPage));
                        tvPaginationNumberThree.setText(String.valueOf(currentPage - Constant.INT_1));
                        tvPaginationNumberTwo.setText(String.valueOf(currentPage - Constant.INT_2));
                        tvPaginationNumberOne.setText(String.valueOf(currentPage - Constant.INT_3));
                        currentPage += Constant.INT_1;
                    }
                    firstResultPosition = Constant.NUMBER_RECORDS_PER_PAGE * (currentPage - Constant.INT_1);
                    loadDataByPage();
                }
                break;
            case R.id.tvPaginationGoToLast:
                if (currentPage < totalPage) {
                    if (totalPage >= Constant.INT_5) {
                        setBackgroundPagination(Constant.INT_5);
                        tvPaginationNumberFive.setText(String.valueOf(totalPage));
                        tvPaginationNumberFour.setText(String.valueOf(totalPage - Constant.INT_1));
                        tvPaginationNumberThree.setText(String.valueOf(totalPage - Constant.INT_2));
                        tvPaginationNumberTwo.setText(String.valueOf(totalPage - Constant.INT_3));
                        tvPaginationNumberOne.setText(String.valueOf(totalPage - Constant.INT_4));
                    } else {
                        setBackgroundPagination(totalPage);
                    }
                    currentPage = totalPage;
                    firstResultPosition = Constant.NUMBER_RECORDS_PER_PAGE * (currentPage - Constant.INT_1);
                    loadDataByPage();
                    break;
                }
        }

    }

    public void showPagination(int totalRecord) {
        totalPage = totalRecord / Constant.NUMBER_RECORDS_PER_PAGE;
        int tempNumber = totalRecord % Constant.NUMBER_RECORDS_PER_PAGE;
        if (Constant.INT_0 != tempNumber) {
            totalPage += Constant.INT_1;
        }
        if (Constant.INT_2 == totalPage) {
            tvPaginationNumberOne.setVisibility(View.VISIBLE);
            tvPaginationNumberTwo.setVisibility(View.VISIBLE);
            tvPaginationNumberThree.setVisibility(View.GONE);
            tvPaginationNumberFour.setVisibility(View.GONE);
            tvPaginationNumberFive.setVisibility(View.GONE);
        } else if (Constant.INT_3 == totalPage) {
            tvPaginationNumberOne.setVisibility(View.VISIBLE);
            tvPaginationNumberTwo.setVisibility(View.VISIBLE);
            tvPaginationNumberThree.setVisibility(View.VISIBLE);
            tvPaginationNumberFour.setVisibility(View.GONE);
            tvPaginationNumberFive.setVisibility(View.GONE);
        } else if (Constant.INT_4 == totalPage) {
            tvPaginationNumberOne.setVisibility(View.VISIBLE);
            tvPaginationNumberTwo.setVisibility(View.VISIBLE);
            tvPaginationNumberThree.setVisibility(View.VISIBLE);
            tvPaginationNumberFour.setVisibility(View.VISIBLE);
            tvPaginationNumberFive.setVisibility(View.GONE);
        } else if (Constant.INT_5 <= totalPage) {
            tvPaginationNumberOne.setVisibility(View.VISIBLE);
            tvPaginationNumberTwo.setVisibility(View.VISIBLE);
            tvPaginationNumberThree.setVisibility(View.VISIBLE);
            tvPaginationNumberFour.setVisibility(View.VISIBLE);
            tvPaginationNumberFive.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void processFinish(String result, String api) {
        try {
            if (!StringUtil.isNullOrEmpty(result)) {
                if (Constant.ERROR_SERVER.equals(result)) {
                    message = getResources().getString(R.string.message_error_server);
                    typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
                    alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                } else if (Constant.ERROR_NETWORK.equals(result)) {
                    exListFees.setVisibility(View.GONE);
                    rlFeesPagination.setVisibility(View.GONE);
                    message = getResources().getString(R.string.message_error_network);
                    typeDialog = Constant.TYPE_ALERT_DIALOG_INFOR;
                    alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                } else {
                    JSONObject jsonObject = new JSONObject(result);
                    int responseStatus = jsonObject.getInt(Keys.KEY_RESPONSE_STATUS);
                    String responseMessage = jsonObject.getString(Keys.KEY_RESPONSE_MESSAGE);
                    if (Constant.RESPONSE_CODE_200 == responseStatus) {
                        int totalRecord = jsonObject.getInt(Keys.KEY_TOTAL_RECORDS);
                        JSONArray arrResult = jsonObject.getJSONArray(Keys.KEY_OBJECT);
                        Gson gson = new GsonBuilder().create();
                        Type listType = new TypeToken<List<FeesChargesTransDTO>>() {
                        }.getType();
                        List<FeesChargesTransDTO> myModelList = gson.fromJson(arrResult.toString(), listType);
                        if (myModelList == null || Constant.INT_0 == myModelList.size()) {
                            exListFees.setVisibility(View.GONE);
                            rlFeesPagination.setVisibility(View.GONE);
                            message = getResources().getString(R.string.message_cost_and_fee_no_data);
                            typeDialog = Constant.TYPE_ALERT_DIALOG_INFOR;
                            alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                        } else {
                            exListFees.setVisibility(View.VISIBLE);
                            listFees = myModelList;
                            if (Constant.NUMBER_RECORDS_PER_PAGE >= totalRecord) {
                                rlFeesPagination.setVisibility(View.GONE);
                            } else {
                                rlFeesPagination.setVisibility(View.VISIBLE);
                                showPagination(totalRecord);
                            }
                            tvPaginationTotal.setText(totalRecord + " kết quả " + "( " + (firstResultPosition + 1) + " ~ " + (firstResultPosition + myModelList.size()) + ")");
                            //show data
                            addDrawerItems();
                        }

                    } else if (Constant.RESPONSE_CODE_412 == responseStatus) {
                        exListFees.setVisibility(View.GONE);
                        rlFeesPagination.setVisibility(View.GONE);
                        message = responseMessage;
                        typeDialog = Constant.TYPE_ALERT_DIALOG_ERROR;
                        alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                    } else {
                        exListFees.setVisibility(View.GONE);
                        rlFeesPagination.setVisibility(View.GONE);
                        message = getResources().getString(R.string.message_cost_and_fee_no_data);
                        typeDialog = Constant.TYPE_ALERT_DIALOG_INFOR;
                        alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
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
    }
}