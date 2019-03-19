package app.com.baoviet.datasource;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import app.com.baoviet.constant.Constant;
import app.com.baoviet.constant.Keys;
import app.com.baoviet.entity.DataLocal;
import app.com.baoviet.entity.Invoice;
import app.com.baoviet.entity.UserDTO;
import app.com.baoviet.interfaceclass.AsyncResponse;
import app.com.baoviet.sqlite.DatabaseHelper;
import app.com.baoviet.utility.ConnectionInternet;
import app.com.baoviet.utility.StringUtil;

public class DataSourceConnection extends AsyncTask<Void, Void, String> {
    private String urlApi;
    private Activity context;
    private ProgressDialog progressDialog;
    private String method;
    private String inputParams;
    private List<Invoice> listObject;
    private StringBuffer response;
    private String responseText;
    private HttpURLConnection conn;
    public AsyncResponse delegate;
    private DatabaseHelper databaseHelper;
    private String username;
    private boolean isConnect;
    private String passwordHash;
    private boolean isNotSave;
    private boolean isNotShowLoading;

    public DataSourceConnection(Activity context, String urlApi, String inputParams, String method, String username, String passwordHash) {
        this.inputParams = inputParams;
        this.context = context;
        this.urlApi = urlApi;
        this.method = method;
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public DataSourceConnection(boolean isNotShowLoading, Activity context, String urlApi, String inputParams, String method, String username, String passwordHash) {
        this.inputParams = inputParams;
        this.context = context;
        this.urlApi = urlApi;
        this.method = method;
        this.username = username;
        this.passwordHash = passwordHash;
        this.isNotShowLoading = isNotShowLoading;
    }

    public DataSourceConnection(Activity context, String urlApi, String inputParams, String method) {
        this.inputParams = inputParams;
        this.context = context;
        this.urlApi = urlApi;
        this.method = method;
    }

    public DataSourceConnection(boolean isNotShowLoading, Activity context, String urlApi, String inputParams, String method) {
        this.inputParams = inputParams;
        this.context = context;
        this.urlApi = urlApi;
        this.method = method;
        this.isNotShowLoading = isNotShowLoading;
    }

    public DataSourceConnection(Activity context, String urlApi, String inputParams, String method, boolean isConnect) {
        this.inputParams = inputParams;
        this.context = context;
        this.urlApi = urlApi;
        this.method = method;
        this.isConnect = isConnect;
    }

    public DataSourceConnection(Activity context, String urlApi, String inputParams, String method, boolean isConnect, boolean isNotSave) {
        this.inputParams = inputParams;
        this.context = context;
        this.urlApi = urlApi;
        this.method = method;
        this.isConnect = isConnect;
        this.isNotSave = isNotSave;
    }

    public DataSourceConnection(boolean isNotShowLoading, Activity context, String urlApi, String inputParams, String method, boolean isConnect, boolean isNotSave) {
        this.inputParams = inputParams;
        this.context = context;
        this.urlApi = urlApi;
        this.method = method;
        this.isConnect = isConnect;
        this.isNotSave = isNotSave;
        this.isNotShowLoading = isNotShowLoading;
    }

    public DataSourceConnection(Activity context, String urlApi, String method) {
        this.context = context;
        this.urlApi = urlApi;
        this.method = method;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        databaseHelper = DatabaseHelper.getInstance(this.context);

        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(Constant.LOADING_PROCESS);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);
        if (!isNotShowLoading) {
            progressDialog.show();
        }
    }

    @Override
    protected String doInBackground(Void... voids) {
        if (Constant.METHOD_POST.equals(this.method)) {
            return creatingURLConnectionByPost();
        } else if (Constant.METHOD_GET.equals(this.method)) {
            return creatingURLConnectionByGet();
        } else {
            return Constant.EMPTY;
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String responseStr) {
        super.onPostExecute(responseStr);
        progressDialog.dismiss();
        DataLocal dataLocal = new DataLocal();
        if (!StringUtil.isNullOrEmpty(responseStr)) {
            try {
                JSONObject jsonObject = new JSONObject(responseStr);
                int responseStatus = jsonObject.getInt(Keys.KEY_RESPONSE_STATUS);
                if (Constant.RESPONSE_CODE_200 == responseStatus) {
                    if (Constant.URL_LOGIN.equals(this.urlApi)) {
                        String password = Constant.EMPTY;
                        JSONObject objResult = jsonObject.getJSONObject(Keys.KEY_OBJECT);
                        Gson gson = new GsonBuilder().create();
                        UserDTO userResult = gson.fromJson(objResult.toString(), UserDTO.class);
//                        Arrays.sort(userResult.getRoleMenus());
//                        userResult.setRoleMenus(userResult.getRoleMenus());
                        Constant.USER_CODE = userResult.getUserCode();
                        if (StringUtil.isNullOrEmpty(this.passwordHash)) {
                            password = SaveSharedPreference.getPassword(context);
                        } else {
                            password = this.passwordHash;
                        }
                        Constant.PASSWORD_LOCAL = StringUtil.getMD5(password);
                    }
                    if (ConnectionInternet.checkInternetConnection(this.context) && !this.isNotSave) {
                        dataLocal.setUsername(Constant.USER_CODE.toLowerCase());
                        dataLocal.setPassword(Constant.PASSWORD_LOCAL);
                        dataLocal.setApi(this.urlApi);
                        if (this.inputParams == null) {
                            this.inputParams = Constant.EMPTY;
                        }
                        dataLocal.setParams(this.inputParams);
                        dataLocal.setResponse(responseStr);
                        databaseHelper.insertDataLocal(dataLocal);
                    }
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage());
                e.getStackTrace();
            }
        }
        delegate.processFinish(responseStr, this.urlApi);
    }

    public String creatingURLConnectionByGet() {
        if (ConnectionInternet.checkInternetConnection(this.context)) {
            String urlStr = Constant.URL_CONNECTION + this.urlApi;
            try {
                URL url = new URL(urlStr); //
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(Constant.TIMEOUT_READ);
                conn.setConnectTimeout(Constant.TIMEOUT_CONNECT);
                conn.setRequestMethod(this.method);
                conn.connect();
                int responseCode = conn.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader streamReader = new InputStreamReader(conn.getInputStream());
                    //Create a new buffered reader and String Builder
                    BufferedReader reader = new BufferedReader(streamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    //Check if the line we are reading is not null
                    String inputLine;
                    while ((inputLine = reader.readLine()) != null) {
                        stringBuilder.append(inputLine);
                    }
                    //Close our InputStream and Buffered reader
                    reader.close();
                    streamReader.close();
                    //Set our result equal to our stringBuilder
                    this.responseText = stringBuilder.toString();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage());
                this.responseText = Constant.ERROR_SERVER;
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
//            if (this.urlApi == Constant.URL_GET_TREE_MENU_PUBLIC) {
//                this.responseText = Constant.URL_GET_MENU_PUBLIC;
//            }
        } else {
            if (this.isConnect) {
                if (this.isNotSave) {
                    this.responseText = Constant.ERROR_NETWORK;
                } else {
                    if (databaseHelper.checkEmptyData()) {
                        if (this.inputParams == null) {
                            this.inputParams = Constant.EMPTY;
                        }
                        DataLocal dataLocal = new DataLocal();
                        dataLocal.setApi(this.urlApi);
                        dataLocal.setParams(this.inputParams);

                        if (Constant.URL_LOGIN.equals(this.urlApi)) {
                            String username = Constant.EMPTY;
                            String password = Constant.EMPTY;
                            if (StringUtil.isNullOrEmpty(this.username)) {
                                username = SaveSharedPreference.getUserName(context);
                            } else {
                                username = this.username;
                            }
                            if (StringUtil.isNullOrEmpty(this.passwordHash)) {
                                password = SaveSharedPreference.getPassword(context);
                            } else {
                                password = this.passwordHash;
                            }
                            dataLocal.setUsername(username.toLowerCase());
                            dataLocal.setPassword(StringUtil.getMD5(password));
                        } else {
                            dataLocal.setUsername(Constant.USER_CODE.toLowerCase());
                            dataLocal.setPassword(Constant.PASSWORD_LOCAL);
                        }
                        this.responseText = databaseHelper.getResponseLocal(dataLocal);
                    } else {
                        this.responseText = Constant.ERROR_NETWORK;
                    }
                }
            } else {
                if (databaseHelper.checkEmptyData()) {
                    if (this.inputParams == null) {
                        this.inputParams = Constant.EMPTY;
                    }
                    DataLocal dataLocal = new DataLocal();
                    dataLocal.setApi(this.urlApi);
                    dataLocal.setParams(this.inputParams);

                    if (Constant.URL_LOGIN.equals(this.urlApi)) {
                        String username = Constant.EMPTY;
                        String password = Constant.EMPTY;
                        if (StringUtil.isNullOrEmpty(this.username)) {
                            username = SaveSharedPreference.getUserName(context);
                        } else {
                            username = this.username;
                        }
                        if (StringUtil.isNullOrEmpty(this.passwordHash)) {
                            password = SaveSharedPreference.getPassword(context);
                        } else {
                            password = this.passwordHash;
                        }
                        dataLocal.setUsername(username.toLowerCase());
                        dataLocal.setPassword(StringUtil.getMD5(password));
                    } else {
                        dataLocal.setUsername(Constant.USER_CODE.toLowerCase());
                        dataLocal.setPassword(Constant.PASSWORD_LOCAL);
                    }
                    this.responseText = databaseHelper.getResponseLocal(dataLocal);
                } else {
                    this.responseText = Constant.ERROR_NETWORK;
                }
            }
        }
        return this.responseText;
    }

    public String creatingURLConnectionByPost() {
        String params = Constant.EMPTY;
        if (ConnectionInternet.checkInternetConnection(context)) {
            if (!StringUtil.isNullOrEmpty(inputParams)) {
                params = this.inputParams;
            } else {
                params = Constant.EMPTY;
            }
            String urlStr = Constant.URL_CONNECTION + this.urlApi;
            try {
                URL url = new URL(urlStr); //
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(Constant.TIMEOUT_READ);
                conn.setConnectTimeout(Constant.TIMEOUT_CONNECT);
                conn.setRequestMethod(this.method);
                if (Constant.URL_LOGIN.equals(this.urlApi)) {
                    conn.setRequestProperty(Keys.KEY_APPCODE, Constant.VALUE_APPCODE);
                } else {
                    conn.setRequestProperty(Keys.KEY_TOKEN, Constant.TOKEN);
                }
                conn.setRequestProperty(Keys.KEY_CONTENT_TYPE, Constant.VALUE_CONTENT_TYPE);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.connect();

                if (!StringUtil.isNullOrEmpty(params)) {
                    OutputStream os = conn.getOutputStream();
                    os.write(params.getBytes());
                    os.flush();
                    os.close();
                }

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));
                    String output;
                    response = new StringBuffer();

                    while ((output = in.readLine()) != null) {
                        response.append(output);
                    }

                    in.close();
                    this.responseText = response.toString();
                    return this.responseText;
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage());
                this.responseText = Constant.ERROR_SERVER;
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
//            if (this.urlApi == Constant.URL_LOGIN) {
//                this.responseText = Constant.URL_LOGIN_TEMP;
//            } else if (this.urlApi == Constant.URL_GET_TREE_MENU) {
//                this.responseText = Constant.URL_TREE_MENU;
//            } else if (this.urlApi == Constant.URL_GET_SWITCH_USER_MODE) {
//                this.responseText = Constant.URL_ACCOUNT_NUMBER;
//            } else if (this.urlApi == Constant.URL_GET_GENERAL_INFOR) {
//                this.responseText = Constant.URL_GENERAL_INFOR;
//            } else if (this.urlApi == Constant.URL_GET_CUSTOMER_INFOR) {
//                this.responseText = Constant.URL_CUSTOMER_INFOR;
//            } else if (this.urlApi == Constant.URL_GET_PREMIUM_TRANS_INFOR) {
//                this.responseText = Constant.URL_PREMIUM_TRANS_INFOR;
//            } else if (this.urlApi == Constant.URL_GET_PREMIUM_TRANS) {
//                this.responseText = Constant.URL_PREMIUM_TRANS;
//            } else if (this.urlApi == Constant.URL_GET_FEES_CHARGES_TRANS) {
//                this.responseText = Constant.URL_FEE_AND_COST;
//            } else if (this.urlApi == Constant.URL_GET_BENEFIT_RESOLVE_PAID) {
//                this.responseText = Constant.URL_BENEFIT;
//            } else if (this.urlApi == Constant.URL_GET_BENEFIT_RESOLVE) {
//                this.responseText = Constant.URL_BENEFIT_PRO;
//            } else if (this.urlApi == Constant.URL_GET_BILL) {
//                this.responseText = Constant.URL_BILL;
//            } else if (this.urlApi == Constant.URL_GET_ANNUAL_REPORT) {
//                this.responseText = Constant.URL_ANNUAL_REPORT;
//            } else if (this.urlApi == Constant.URL_GET_PAV) {
//                this.responseText = Constant.URL_PAV;
//            } else if (this.urlApi == Constant.URL_GET_LOAN) {
//                this.responseText = Constant.URL_LOAN;
//            } else if (this.urlApi == Constant.URL_SEARCH_INVOICE) {
//                this.responseText = Constant.RESULT_SEARCH_INVOICE;
//            } else if (this.urlApi == Constant.URL_PAYMENT_NAPAS) {
//                this.responseText = Constant.URL_PAY_NAPAS;
//            } else if (this.urlApi == Constant.URL_PAYMENT_BAOVIET_BANK) {
//                this.responseText = Constant.URL_PAY_BVBANK;
//            } else if (this.urlApi == Constant.URL_PREMINUM_RESULT) {
//                this.responseText = Constant.URL_PREMIUM_;
//            } else if (this.urlApi == Constant.URL_GET_BACK_RETURN_URL) {
//                this.responseText = Constant.URL_BACK_URL;
//            } else if (this.urlApi == Constant.URL_UPDATE_USER_ALIAS) {
//                this.responseText = Constant.URL_USER_ALIAS;
//            } else if (this.urlApi == Constant.URL_UPDATE_PASSWORD) {
//                this.responseText = Constant.URL_UPDATE_PASS;
//            } else if (this.urlApi == Constant.URL_GET_ACCOUNT_INFOR) {
//                this.responseText = Constant.URL_ACCOUNT_INFOR;
//            } else if (this.urlApi == Constant.URL_GET_USER_INFOR) {
//                this.responseText = Constant.URL_USER_INFOR;
//            } else if (this.urlApi == Constant.URL_POST_GROUP_INFOR) {
//                this.responseText = Constant.URL_INFOR_GROUP;
//            } else if (this.urlApi == Constant.URL_POST_DOWNLOAD_REPORT_ANNUAL) {
//                this.responseText = Constant.URL_DOWNLOAD;
//            } else if (this.urlApi == Constant.URL_SEARCH_INVOICE) {
//                this.responseText = Constant.RESULT_SEARCH_INVOICE;
//            }
        } else {
            if (this.isConnect) {
                if (this.isNotSave) {
                    this.responseText = Constant.ERROR_NETWORK;
                } else {
                    if (databaseHelper.checkEmptyData()) {
                        if (this.inputParams == null) {
                            this.inputParams = Constant.EMPTY;
                        }
                        DataLocal dataLocal = new DataLocal();
                        dataLocal.setApi(this.urlApi);
                        dataLocal.setParams(this.inputParams);

                        if (Constant.URL_LOGIN.equals(this.urlApi)) {
                            String username = Constant.EMPTY;
                            String password = Constant.EMPTY;
                            if (StringUtil.isNullOrEmpty(this.username)) {
                                username = SaveSharedPreference.getUserName(context);
                            } else {
                                username = this.username;
                            }
                            if (StringUtil.isNullOrEmpty(this.passwordHash)) {
                                password = SaveSharedPreference.getPassword(context);
                            } else {
                                password = this.passwordHash;
                            }
                            dataLocal.setUsername(username.toLowerCase());
                            dataLocal.setPassword(StringUtil.getMD5(password));
                        } else {
                            dataLocal.setUsername(Constant.USER_CODE.toLowerCase());
                            dataLocal.setPassword(Constant.PASSWORD_LOCAL);
                        }
                        this.responseText = databaseHelper.getResponseLocal(dataLocal);
                    } else {
                        this.responseText = Constant.ERROR_NETWORK;
                    }
                }

            } else {
                if (databaseHelper.checkEmptyData()) {
                    if (this.inputParams == null) {
                        this.inputParams = Constant.EMPTY;
                    }
                    DataLocal dataLocal = new DataLocal();
                    dataLocal.setApi(this.urlApi);
                    dataLocal.setParams(this.inputParams);

                    if (Constant.URL_LOGIN.equals(this.urlApi)) {
                        String username = Constant.EMPTY;
                        String password = Constant.EMPTY;
                        if (StringUtil.isNullOrEmpty(this.username)) {
                            username = SaveSharedPreference.getUserName(context);
                        } else {
                            username = this.username;
                        }
                        if (StringUtil.isNullOrEmpty(this.passwordHash)) {
                            password = SaveSharedPreference.getPassword(context);
                        } else {
                            password = this.passwordHash;
                        }
                        dataLocal.setUsername(username.toLowerCase());
                        dataLocal.setPassword(StringUtil.getMD5(password));
                    } else {
                        dataLocal.setUsername(Constant.USER_CODE.toLowerCase());
                        dataLocal.setPassword(Constant.PASSWORD_LOCAL);
                    }
                    this.responseText = databaseHelper.getResponseLocal(dataLocal);
                } else {
                    this.responseText = Constant.ERROR_NETWORK;
                }
            }
        }
        return this.responseText;
    }

}
