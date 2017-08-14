package kopfif.faisal.pkp.kopfif.Services;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import kopfif.faisal.pkp.kopfif.Function.SessionManagerUtil;


/**
 * Created by Faisal on 12/7/2016.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        sendRegistrationToServer(refreshedToken);

    }

    private void sendRegistrationToServer(String token){
        SessionManagerUtil util = new SessionManagerUtil(this);
        util.saveFcmToken(token);
    }
}
