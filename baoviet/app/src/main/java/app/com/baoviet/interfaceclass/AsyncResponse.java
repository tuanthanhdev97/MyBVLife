package app.com.baoviet.interfaceclass;

import java.io.InputStream;

import app.com.baoviet.entity.ResponseStream;

public interface AsyncResponse {
    void processFinish(String output, String api);

    void processFinishStream(ResponseStream inputStream);
}