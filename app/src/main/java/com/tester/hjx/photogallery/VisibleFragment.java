package com.tester.hjx.photogallery;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

public class VisibleFragment extends Fragment {
    private static final String TAG = "VisibleFragment";
    private BroadcastReceiver mOnShowNotification = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            Toast.makeText(getActivity(),"Got a broacast: "+intent.getAction(),Toast.LENGTH_LONG).show();
            Log.i(TAG, "canceling notificaton");
            setResultCode(Activity.RESULT_CANCELED);
        }
    };

    @Override
    public void onStart(){
        super.onStart();
        IntentFilter filter = new IntentFilter(PollService.ACTION_SHOW_NOTIFICATION);
        getActivity().registerReceiver(mOnShowNotification, filter,PollService.PERM_PRIVATE,null);
    }

    @Override
    public void onStop(){
        super.onStop();
        getActivity().unregisterReceiver(mOnShowNotification);
    }
}
