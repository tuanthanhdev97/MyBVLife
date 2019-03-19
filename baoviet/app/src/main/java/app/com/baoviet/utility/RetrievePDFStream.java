package app.com.baoviet.utility;

import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import app.com.baoviet.entity.ResponseStream;

/**
 * Created by Administrator on 7/5/2018.
 */

public class RetrievePDFStream extends AsyncTask<ResponseStream, Void, ResponseStream> {
    @Override
    protected ResponseStream doInBackground(ResponseStream... responseStreams) {
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            File root = android.os.Environment.getExternalStorageDirectory();
            File dir = new File(root.getAbsolutePath() + "/abc");
            dir.mkdirs();
//                        File file = new File(dir, StringUtil.getNameFilePDF(filePath));
            File file = new File(dir, "test13.pdf");

//                        InputStream is = inputStream.getResponseStream();
            is = responseStreams[0].getResponseStream();
            fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = is.read(buffer)) != -1) {
                fos.write(buffer, 0, b);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage());
                e.printStackTrace();
            }
        }
        return responseStreams[0];
    }

    @Override
    protected void onPostExecute(ResponseStream responseStream) {
        super.onPostExecute(responseStream);

    }
}
