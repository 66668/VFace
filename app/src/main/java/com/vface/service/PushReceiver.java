package com.vface.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.igexin.sdk.PushConsts;
import com.vface.common.MyApplication;
import com.vface.helper.MemberHelper;
import com.vface.helper.UserHelper;
import com.vface.utils.ConfigUtil;

public class PushReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(final Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		Log.d("GetuiSdkDemo", "onReceive() action=" + bundle.getInt("action"));
		switch (bundle.getInt(PushConsts.CMD_ACTION)) {
		case PushConsts.GET_MSG_DATA: 
			byte[] payload = bundle.getByteArray("payload");
			if (payload != null) {
				String data = new String(payload);
				Log.d("GetuiSdkDemo", "Got Payload:" + data);
				try{
					
					MyApplication.getInstance().ClickMember=MemberHelper.getMemberFromJson(context,data);
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
			break;
		case PushConsts.GET_CLIENTID: 
			final String cid = bundle.getString("clientid");
			Log.d("GetuiSdkDemo", "Got ClientID:" + cid);
			ConfigUtil configUtil = new ConfigUtil(context);
			configUtil.setPushId(cid);
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						UserHelper.setPushKey(context, cid);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}).start(); 
			break;
		default:
			break;
		}
	}
}