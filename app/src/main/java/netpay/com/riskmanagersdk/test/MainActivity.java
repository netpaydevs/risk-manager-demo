package netpay.com.riskmanagersdk.test;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import netpay.com.riskmanager.RiskManager;
import netpay.com.riskmanager.core.beans.riskmanager.Bill;
import netpay.com.riskmanager.core.beans.riskmanager.Item;
import netpay.com.riskmanager.core.beans.riskmanager.MerchantDefinedData;
import netpay.com.riskmanager.core.beans.riskmanager.PurchaseTotals;
import netpay.com.riskmanager.core.beans.riskmanager.Ship;
import netpay.com.riskmanager.core.beans.transactions.ChargeTransactionResponse;
import netpay.com.riskmanager.core.beans.transactions.Promotion;
import netpay.com.riskmanager.core.beans.transactions.TransactionMerchant;
import netpay.com.riskmanager.listeners.NetPayServiceListener;

public class MainActivity extends AppCompatActivity implements NetPayServiceListener {

    private final String LOG_CAT = getClass().getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bill b = new Bill();
                b.setCity("Monterrey");
                b.setCountry("MX");
                b.setFirstName("Mailto");
                b.setLastName("Lmailto");
                b.setEmail("accept@netpay.com.mx");
                b.setIpAddress("192.172.21.5.43");
                b.setPhoneNumber("8119656543");
                b.setPostalCode("67209");
                b.setState("Nuevo León");
                b.setStreet1("Street1");
                b.setStreet2("Street2");

                Ship s = new Ship();
                s.setCity("Monterrey");
                s.setCountry("MX");
                s.setShippingMethod("tablerate_bestway");
                s.setFirstName("Mailto");
                s.setLastName("Lmailto");
                s.setPhoneNumber("8119656543");
                s.setPostalCode("67209");
                s.setState("León");
                s.setStreet1("Street1");
                s.setStreet2("Street2");

                PurchaseTotals p = new PurchaseTotals();
                p.setCurrency("MXN");
                p.setGrandTotalAmount(1346.50);

                List<MerchantDefinedData> merchantDefinedDatas = new ArrayList<>();

                MerchantDefinedData merchantDefinedData = new MerchantDefinedData();

                List<Item> items = new ArrayList<>();
                Item i = new Item();
                i.setId("12832");
                i.setUnitPrice(1346.50);
                i.setProductCode("Autos");
                i.setProductName("HELICOPTERO VOICE COMMAN");
                i.setProductSKU("17800884");
                i.setQuantity(1);

                items.add(i);

                RiskManager.getInstance()
                        .addNetPayServiceListener(MainActivity.this)
                        .start(MainActivity.this,String.valueOf(new Date().getTime()), Promotion.NORMAL, TransactionMerchant.TransactionType.AUTH,b, s, merchantDefinedDatas, items, p);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccessAuthListener(NetPayStatus status, Integer responseCode) {
        Log.d(LOG_CAT, "onSuccessAuthListener(): " + status + "-> " + responseCode);
    }

    @Override
    public void onFailureAuthListener(NetPayStatus status, Integer responseCode, String errorMessage) {
        Log.d(LOG_CAT, "onFailureAuthListener(): " + status + " -> " + responseCode + " -> " + errorMessage);
    }

    @Override
    public void onSuccessRiskListener(NetPayStatus status, Integer responseCode, RiskStatus riskStatus) {
        Log.d(LOG_CAT, "onSuccessRiskListener(): " + status + " -> " + responseCode + " -> " + riskStatus.name());
    }

    @Override
    public void onFailureRiskListener(NetPayStatus status, Integer responseCode, String errorMessage, RiskStatus riskStatus) {
        Log.d(LOG_CAT, "onFailureRiskListener(): " + status + " -> " + responseCode + " -> " + errorMessage + " -> " + riskStatus.name());
    }

    @Override
    public void onSuccessRisk3DSListener(NetPayStatus status, Integer responseCode, Risk3dsStatus risk3dsStatus) {
        Log.d(LOG_CAT, "onSuccessRisk3DSListener(): " + status + " -> " + responseCode + " -> " + risk3dsStatus.name());
    }

    @Override
    public void onFailureRisk3DSListener(NetPayStatus status, Integer responseCode, Risk3dsStatus risk3dsStatus) {
        Log.d(LOG_CAT, "onFailureRisk3DSListener(): " + status + " -> " + responseCode + " -> " + risk3dsStatus.name());
    }

    @Override
    public void onSuccessChargeListener(NetPayStatus status, Integer responseCode, TransactionStatus transactionStatus, ChargeTransactionResponse chargeTransactionResponse) {
        Log.d(LOG_CAT, "onSuccessChargeListener(): NetPayStatus -> " + status.name() + " responseCode -> " + responseCode + " transactionStatus -> " + transactionStatus.name());
    }

    @Override
    public void onRejectChargeListener(NetPayStatus status, Integer responseCode, TransactionStatus transactionStatus, ChargeTransactionResponse chargeTransactionResponse) {
        Log.d(LOG_CAT, "onRejectChargeListener(): NetPayStatus -> " + status.name() + " responseCode -> " + responseCode + " transactionStatus -> " + transactionStatus.name());
    }

    @Override
    public void onFailureChargeListener(NetPayStatus status, Integer responseCode, TransactionStatus transactionStatus) {
        Log.d(LOG_CAT, "onFailureChargeListener():  NetPayStatus -> " + status.name() + " responseCode -> " + responseCode + " transactionStatus -> " + transactionStatus.name());
    }
}
