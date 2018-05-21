package netpay.com.riskmanagersdk.test;

import android.app.Application;

import netpay.com.riskmanager.RiskManager;

/**
 * Created by AcheDev on 5/21/18.
 */

public class SubApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RiskManager.build()
                .userName("netpay_devs@netpay.com.mx")
                .password("adm0n2")
                .storeIdAcq("470137")
                .addEnvironmnet(RiskManager.Environment.SANDBOX);
    }
}
