package app.com.baoviet.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import app.com.baoviet.R;
import app.com.baoviet.adapter.CustomExpandableListAnnualNoticeAdapter;
import app.com.baoviet.constant.Constant;
import app.com.baoviet.constant.Keys;
import app.com.baoviet.datasource.DataSourceConnection;
import app.com.baoviet.entity.AnnualReport;
import app.com.baoviet.entity.ResponseStream;
import app.com.baoviet.interfaceclass.AsyncResponse;
import app.com.baoviet.interfaceclass.DialogEventListener;
import app.com.baoviet.utility.AlertDialogCustom;
import app.com.baoviet.utility.CalculateDimension;
import app.com.baoviet.utility.StringUtil;

public class TabContractNoticeFragment extends Fragment implements View.OnClickListener, AsyncResponse, DialogEventListener {

    private TextView tvContractTabNoticeMessageResponse;
    private TextView tvContractTabNoticeNoData;
    private String accountNumber;

    private ExpandableListView exListNotice;
    AlertDialogCustom alert;
    String message = Constant.EMPTY;
    String typeDialog = Constant.EMPTY;

    //view for pagination
    private View rlNoticePagination;
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

    private int firstResultPosition;
    private int currentPage = Constant.INT_1;
    private int totalPage;

    private String typeAccount;

    private List<AnnualReport> listPreminumTrans = new ArrayList<AnnualReport>();
    private CustomExpandableListAnnualNoticeAdapter mExpandableListNoticeAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_contract_notice, container, false);
        initInterface(view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            accountNumber = bundle.getString(Keys.KEY_ACCOUNT_NUMBER);
            typeAccount = bundle.getString(Keys.KEY_TYPE_ACCOUNT);
        }
        loadDataByPage();
        return view;
    }

    public void initInterface(View view) {
        alert = new AlertDialogCustom(getContext());
        alert.dialogEventListener = this;
        tvContractTabNoticeMessageResponse = (TextView) view.findViewById(R.id.tvContractTabNoticeMessageResponse);
        tvContractTabNoticeNoData = (TextView) view.findViewById(R.id.tvContractTabNoticeNoData);
        exListNotice = (ExpandableListView) view.findViewById(R.id.exListNotice);
        rlNoticePagination = (View) view.findViewById(R.id.rlNoticePagination);
        tvPaginationNumberOne = (TextView) rlNoticePagination.findViewById(R.id.tvPaginationNumberOne);
        tvPaginationNumberTwo = (TextView) rlNoticePagination.findViewById(R.id.tvPaginationNumberTwo);
        tvPaginationNumberThree = (TextView) rlNoticePagination.findViewById(R.id.tvPaginationNumberThree);
        tvPaginationNumberFour = (TextView) rlNoticePagination.findViewById(R.id.tvPaginationNumberFour);
        tvPaginationNumberFive = (TextView) rlNoticePagination.findViewById(R.id.tvPaginationNumberFive);

        tvPaginationBackToFirst = (TextView) rlNoticePagination.findViewById(R.id.tvPaginationBackToFirst);
        tvPaginationPrevious = (TextView) rlNoticePagination.findViewById(R.id.tvPaginationPrevious);
        tvPaginationNext = (TextView) rlNoticePagination.findViewById(R.id.tvPaginationNext);
        tvPaginationGoToLast = (TextView) rlNoticePagination.findViewById(R.id.tvPaginationGoToLast);
        tvPaginationTotal = (TextView) view.findViewById(R.id.tvPaginationTotal);

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

    private void addDrawerItems() {
        mExpandableListNoticeAdapter = new CustomExpandableListAnnualNoticeAdapter(getActivity(), listPreminumTrans, TabContractNoticeFragment.this);
        exListNotice.setAdapter(mExpandableListNoticeAdapter);
        //set height default of list view
        CalculateDimension.setHeightDefaultOfExpandableListView(exListNotice);
        exListNotice.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        exListNotice.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });
        exListNotice.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {

                CalculateDimension.setListViewHeight(expandableListView, groupPosition);
                return false;
            }
        });

        exListNotice.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                return false;
            }
        });
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

            postDataParams.put(Keys.KEY_OBJECT, postObjectParams);
            postDataParams.put(Keys.KEY_PAGING_DTO, postPagingParams);
            String inputParams = postDataParams.toString().replace("\\", "").replace("\"[", "[").replace("]\"", "]");
            Activity activity = getActivity();
            if (isAdded() && activity != null) {
                connection = new DataSourceConnection(activity, Constant.URL_GET_ANNUAL_REPORT, inputParams, Constant.METHOD_POST);
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
    public void onClick(View view) {

        switch (view.getId()) {

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
                    int totalRecord = jsonObject.getInt(Keys.KEY_TOTAL_RECORDS);
                    tvContractTabNoticeMessageResponse.setVisibility(View.GONE);
                    if (urlApi.equals(Constant.URL_GET_ANNUAL_REPORT)) {
                        exListNotice.setVisibility(View.VISIBLE);
                        JSONArray arrResult = jsonObject.getJSONArray(Keys.KEY_OBJECT);
                        Gson gson = new GsonBuilder().create();
                        Type listType = new TypeToken<List<AnnualReport>>() {
                        }.getType();
                        List<AnnualReport> myModelList = gson.fromJson(arrResult.toString(), listType);
//                        List<AnnualReport> myModelList = gson.fromJson("[{\"contractAgentCode\":\"D100189155\",\"contractAgentName\":\"Hoàng Mai Anh\",\"contractBuyer\":\"Tập Đoàn Bảo Việt\",\"contractCompany\":\"Hà Nội\",\"contractCreateDate\":\"2018-03-23\",\"contractDeadline\":\"2016-01-29\",\"contractId\":\"568733697\",\"contractPolicyholder\":\"Thái Huy Chương\",\"contractYear\":2017,\"contractFilePath\":\"\\\\2017\\\\TAL\\\\PERS\\\\01000\\\\01063\\\\2\\\\01062_01063_00000_100189155_568733697.pdf\",\"contractUpdateDate\":\"2018-03-23\"}]", listType);
                        if (myModelList == null || Constant.INT_0 == myModelList.size()) {
                            tvContractTabNoticeMessageResponse.setVisibility(View.GONE);
                            exListNotice.setVisibility(View.GONE);
                            tvContractTabNoticeNoData.setVisibility(View.VISIBLE);
                        } else {
                            tvContractTabNoticeMessageResponse.setVisibility(View.GONE);
                            exListNotice.setVisibility(View.VISIBLE);
                            tvContractTabNoticeNoData.setVisibility(View.GONE);
                            listPreminumTrans = myModelList;
                            if (Constant.NUMBER_RECORDS_PER_PAGE >= totalRecord) {
                                rlNoticePagination.setVisibility(View.GONE);
                            } else {
                                rlNoticePagination.setVisibility(View.VISIBLE);
                                showPagination(totalRecord);
                            }
                            tvPaginationTotal.setText(totalRecord + " kết quả " + "( " + (firstResultPosition + 1) + " ~ " + (firstResultPosition + myModelList.size()) + ")");
                            //show data
                            addDrawerItems();
                        }
                    }
                } else if (Constant.RESPONSE_CODE_412 == responseStatus) {
                    tvContractTabNoticeMessageResponse.setVisibility(View.VISIBLE);
                    exListNotice.setVisibility(View.GONE);
                    rlNoticePagination.setVisibility(View.GONE);
                    tvContractTabNoticeMessageResponse.setText(responseMessage);
                } else {
                    tvContractTabNoticeMessageResponse.setVisibility(View.GONE);
                    exListNotice.setVisibility(View.GONE);
                    tvContractTabNoticeNoData.setVisibility(View.VISIBLE);
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