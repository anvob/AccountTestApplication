package com.anvob.accounttestapplication.AccountAuthenticator;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by anvob on 06.10.2017.
 */

public class BeBossAuthenticatorService extends Service {
    @Override
    public IBinder onBind(Intent intent) {

        BeBossAuthenticator authenticator = new BeBossAuthenticator(this);
        return authenticator.getIBinder();
    }
}
