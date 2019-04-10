package com.angadi.tripmanagementa.Custum;


import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


import java.util.Hashtable;

/**
 * Created by snyxius on 3/2/17.
 */

public class Notification extends FirebaseInstanceIdService {
    private static final String TAG = "FirebaseIDService";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String device_id = FirebaseInstanceId.getInstance().getToken();
//        Log.d(TAG, "device_id: " + device_id);
        Hashtable<String,String> data = new Hashtable<>();
//        data.put("gender",CallList.readFromPreferences(CallList.getAppContext(),"data",""));
//       data.put("App_Name","The CallList");




        // TODO: Implement this method to send any registration to your app's servers.
//        sendRegistrationToServer(refreshedToken);
    }
}
