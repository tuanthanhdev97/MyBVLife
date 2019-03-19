package app.com.baoviet.service;

import com.google.firebase.iid.FirebaseInstanceIdService;


public class FirebaseIDService extends FirebaseInstanceIdService {
    String token;

    @Override
    public void onTokenRefresh() {
//        this.token = FirebaseInstanceId.getInstance().getToken();
    }

    public String getTokenNotification() {
        this.onTokenRefresh();
        return this.token;
    }
}
