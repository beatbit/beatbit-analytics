package com.beatbit.analytics;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

/**
 * Created by Daniel on 8/2/2015.
 */
public class MonitorService extends Service {
    private static final String TAG = "";
    private Socket socket;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            doWork();
        } catch(Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void doWork() throws Exception {
        socket = IO.socket("https://beatbit-server-test-danielchristopher1.c9.io");

        socket.on(Constants.ARREST, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                String patientID = (String) args[0];

                Intent intent = new Intent();
                intent.setAction(Constants.ARREST);
                intent.putExtra(Constants.PATIENT_ID, patientID);

                // Notify listeners
                sendBroadcast(intent);
            }
        });

        socket.connect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }
}
