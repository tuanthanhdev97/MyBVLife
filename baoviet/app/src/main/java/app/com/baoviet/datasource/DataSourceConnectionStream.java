package app.com.baoviet.datasource;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.provider.DocumentFile;
import android.util.Log;

import com.github.barteksc.pdfviewer.PDFView;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import app.com.baoviet.constant.Constant;
import app.com.baoviet.constant.Keys;
import app.com.baoviet.entity.ResponseStream;
import app.com.baoviet.interfaceclass.AsyncResponse;
import app.com.baoviet.utility.StringUtil;

public class DataSourceConnectionStream extends AsyncTask<Void, Void, ResponseStream> {
    private String urlApi;
    private Context context;
    private JSONObject jsonParams;
    private ProgressDialog progressDialog;
    private String method;
    private HttpURLConnection conn;
    public AsyncResponse delegate;
    private boolean isConnect;
    private String typeAction;
    private String fileName;
    private PDFView pdfView;
    private byte[] bytes;
    private DocumentFile pickedDir;

    public DataSourceConnectionStream(Activity context, String urlApi, JSONObject jsonParams, String method, boolean isConnect, String fileName, String typeAction, DocumentFile pickedDir) {
        this.jsonParams = jsonParams;
        this.context = context;
        this.urlApi = urlApi;
        this.method = method;
        this.isConnect = isConnect;
        this.fileName = fileName;
        this.typeAction = typeAction;
        this.pickedDir = pickedDir;
    }

    public DataSourceConnectionStream(Activity context, String urlApi, JSONObject jsonParams, String method, boolean isConnect, String fileName, String typeAction) {
        this.jsonParams = jsonParams;
        this.context = context;
        this.urlApi = urlApi;
        this.method = method;
        this.isConnect = isConnect;
        this.fileName = fileName;
        this.typeAction = typeAction;
    }

    public DataSourceConnectionStream(Activity context, String urlApi, JSONObject jsonParams, String method, boolean isConnect, String typeAction, PDFView pdfView) {
        this.jsonParams = jsonParams;
        this.context = context;
        this.urlApi = urlApi;
        this.method = method;
        this.isConnect = isConnect;
        this.typeAction = typeAction;
        this.pdfView = pdfView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(Constant.LOADING_PROCESS);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);
        progressDialog.show();
    }

    @Override
    protected ResponseStream doInBackground(Void... voids) {
        return creatingURLConnectionByPost();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    private boolean checkInternetConnection() {
        ConnectivityManager
                cm = (ConnectivityManager) this.context.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    protected void onPostExecute(ResponseStream responseStream) {
        super.onPostExecute(responseStream);
        progressDialog.dismiss();
        if (Constant.PDF_TYPE_VIEW.equals(this.typeAction) &&
                !Constant.STREAM_RESPONSE_TEXT_NETWORK_ERROR.equals(responseStream.getResponseText())) {
            pdfView.fromBytes(this.bytes).load();
        }
        delegate.processFinishStream(responseStream);
    }

    public ResponseStream creatingURLConnectionByPost() {
        ResponseStream responseStream = new ResponseStream();
        InputStream is = null;
        if (checkInternetConnection()) {
            String params;
            if (jsonParams != null) {
                params = this.jsonParams.toString().replace("\\", "").replace("\"[", "[").replace("]\"", "]");
            } else {
                params = Constant.EMPTY;
            }
            String urlStr = Constant.URL_CONNECTION + this.urlApi;
            try {
                URL url = new URL(urlStr);
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
                    is = new BufferedInputStream(conn.getInputStream());
                    responseStream.setResponseStream(is);

                    if (Constant.PDF_TYPE_DOWNLOAD.equals(this.typeAction) || Constant.XML_TYPE_DOWNLOAD.equals(this.typeAction)) {
                        OutputStream outStream = null;
                        try {
                            if (this.pickedDir == null) {
                                File file = new File(this.fileName);
                                if (!file.exists()) {
                                    file.createNewFile();
                                }
                                outStream = new FileOutputStream(file, false);
                            } else {
                                DocumentFile documentFile = pickedDir.findFile(this.fileName);
                                if (documentFile == null || !documentFile.exists()) {
                                    documentFile = pickedDir.createFile("//MIME type", this.fileName);
                                }
                                outStream = this.context.getContentResolver().openOutputStream(documentFile.getUri());
                            }
                            byte[] buffer = new byte[8 * 1024];
                            int bytesRead;
                            while ((bytesRead = is.read(buffer)) != -1) {
                                outStream.write(buffer, 0, bytesRead);
                            }
                            responseStream.setResponseText(Constant.STREAM_RESPONSE_TEXT_SUCCESS);

                        } catch (FileNotFoundException e) {
                            responseStream.setResponseText(Constant.STREAM_RESPONSE_TEXT_FILE_NOT_FOUND);
                        } catch (IOException e) {
                            responseStream.setResponseText(Constant.STREAM_RESPONSE_TEXT_FILE_NOT_FOUND);
                        } catch (Exception ex) {
                            responseStream.setResponseText(Constant.STREAM_RESPONSE_TEXT_UNSUCCESS);
                            ex.printStackTrace();
                        } finally {
                            try {
                                if (outStream != null) {
                                    outStream.close();
                                }
                                if (is != null) {
                                    is.close();
                                }
                            } catch (Exception e) {
                                Log.e("ERROR", e.getMessage());
                                responseStream.setResponseText(Constant.STREAM_RESPONSE_TEXT_UNSUCCESS);
                                e.printStackTrace();
                            }
                        }
                    } else if (Constant.PDF_TYPE_VIEW.equals(this.typeAction)) {
                        ByteArrayOutputStream os = null;
                        try {
                            os = new ByteArrayOutputStream();

                            byte[] buffer = new byte[1024];
                            int len;

                            while ((len = is.read(buffer)) != -1) {
                                // write bytes from the buffer into output stream
                                os.write(buffer, 0, len);
                            }
                            this.bytes = os.toByteArray();
                            responseStream.setResponseText(Constant.STREAM_RESPONSE_LOADED_VIEW);
                        } catch (Exception e) {
                            responseStream.setResponseText(Constant.STREAM_RESPONSE_TEXT_UNSUCCESS);
                        } finally {
                            if (os != null) {
                                os.close();
                            }
                            if (is != null) {
                                is.close();
                            }
                        }
                    }
                } else {
                    responseStream.setResponseText(Constant.STREAM_RESPONSE_TEXT_UNSUCCESS_404);
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage());
                responseStream.setResponseText(Constant.STREAM_RESPONSE_TEXT_UNSUCCESS);
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
        } else {
            if (this.isConnect) {
                responseStream.setResponseText(Constant.STREAM_RESPONSE_TEXT_NETWORK_ERROR);
            }
        }

        return responseStream;
    }

}
