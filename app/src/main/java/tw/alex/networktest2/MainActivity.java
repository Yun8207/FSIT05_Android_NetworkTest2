package tw.alex.networktest2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private ConnectivityManager cmgr;
    private MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cmgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        myReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(myReceiver, filter);


    }

    @Override
    public void finish() {
        unregisterReceiver(myReceiver);
        super.finish();
    }

    public void test1(View view) {
        NetworkInfo info = cmgr.getActiveNetworkInfo();
        boolean isConnected = info != null && info.isConnected();
        Log.v("alex", "network : " + isConnected);

        NetworkInfo wifiinfo = cmgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        Log.v("alex","wifi : " + wifiinfo.isConnected());
    }

    private boolean isNetworkActive(){
        NetworkInfo info = cmgr.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (!isNetworkActive()){
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Net Info")
                        .setMessage("Internet Unstable")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setCancelable(false)
                        .show();
            }
        }
    }
}
