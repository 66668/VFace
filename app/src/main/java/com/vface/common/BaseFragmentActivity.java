package com.vface.common;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.vface.inject.ActivityInjectHelper;
import com.vface.inject.InjectHelper;
import com.vface.utils.PageUtil;

public abstract class BaseFragmentActivity extends FragmentActivity {

	public static final int MESSAGE_TOAST = 1001;

	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		initView();
	}

	public void setContentView(View view, LayoutParams params) {
		super.setContentView(view, params);
		initView();
	}

	public void setContentView(View view) {
		super.setContentView(view);
		initView();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/** 
	 * 
	 * @param newClass
	 */
	public void startActivity(Class<?> newClass) {
		Intent intent = new Intent(this, newClass);
		startActivity(intent); 
	}

	public void startActivity(Class<?> newClass, Bundle extras) {
		Intent intent = new Intent(this, newClass);
		intent.putExtras(extras);
		startActivity(intent); 
	}

	/**
	 */
	@SuppressLint("HandlerLeak")
	protected Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			BaseFragmentActivity.this.handleMessage(msg);
		}
	};

	/**
	 * 
	 * @param msg
	 */
	protected void handleMessage(Message msg) {
		switch (msg.what) {
		case MESSAGE_TOAST:
			PageUtil.DisplayToast(msg.obj.toString());
			break;
		default:
			break;
		}
	}

	/**
	 * ����handler��Ϣ
	 * 
	 * @param msg
	 */
	protected void sendMessage(Message msg) {
		handler.sendMessage(msg);
	}

	/**
	 * 
	 * @param msg
	 */
	protected void sendMessage(int what) {
		handler.sendEmptyMessage(what);
	}

	/**
	 * 
	 * @param msg
	 */
	public void sendToastMessage(int resId) {
		Message msg = new Message();
		msg.what = MESSAGE_TOAST;
		msg.obj = getString(resId);
		handler.sendMessage(msg);
	}

	/**
	 * 
	 * @param msg
	 */
	public void sendToastMessage(String result) {
		Message msg = new Message();
		msg.what = MESSAGE_TOAST;
		msg.obj = result;
		handler.sendMessage(msg);
	}

	/**
	 */
	void initView() {
		InjectHelper helper = new ActivityInjectHelper(this);
		helper.initView();
	}

	@Override
	protected void onResume() {
		super.onResume(); 
	}

	@Override
	protected void onPause() {
		super.onPause(); 
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	public enum Method {
		Click, LongClick, ItemClick, itemLongClick
	}
}
