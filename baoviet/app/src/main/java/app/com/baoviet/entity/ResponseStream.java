package app.com.baoviet.entity;

import java.io.InputStream;

/**
 * Created by Administrator on 7/4/2018.
 */

public class ResponseStream {
    private String responseText;
    private InputStream responseStream;

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    public InputStream getResponseStream() {
        return responseStream;
    }

    public void setResponseStream(InputStream responseStream) {
        this.responseStream = responseStream;
    }
}
