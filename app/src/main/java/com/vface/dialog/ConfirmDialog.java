package com.vface.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.vface.R;
import com.vface.utils.Utils;

public class ConfirmDialog extends Dialog implements View.OnClickListener{

	private Context context;
	private ConfirmDialogCallBack callBack;
	
	public ConfirmDialog(Context context,String title,String content,ConfirmDialogCallBack callBack) {
		super(context, R.style.LoadingDialog);
		this.context = context;
		this.callBack = callBack;
		init(title,content);
	}
	
	public ConfirmDialog(Context context,String title,String content,String confirmText,String cancelText,ConfirmDialogCallBack callBack) {
		super(context, R.style.LoadingDialog);
		this.context = context;
		this.callBack = callBack;
		init(title,content,confirmText,cancelText);
	}
	
	public interface ConfirmDialogCallBack{
		
		public void confirm();
		public void cancel();
		
	}

	private void init(String title,String content) {
		String confirmText = context.getResources().getString(R.string.confirm);
		String cancelText = context.getResources().getString(R.string.cancel);
		init(title,content,confirmText,cancelText);
	}
	
	private void init(String title,String content,String confirmText,String cancelText) {
		
		View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_confirm, null);
		
		TextView titleView = (TextView) dialogView.findViewById(R.id.dialog_title);
		TextView contentView = (TextView) dialogView.findViewById(R.id.dialog_content);
		TextView confirmTextView = (TextView) dialogView.findViewById(R.id.confirm_btn);
		TextView cancelTextView = (TextView) dialogView.findViewById(R.id.cancel_btn);
		
		
		if(TextUtils.isEmpty(title)){
			titleView.setVisibility(View.GONE);
		}else{
			titleView.setText(title);
		}
		contentView.setText(content);
		confirmTextView.setText(confirmText);
		cancelTextView.setText(cancelText);
		
		confirmTextView.setOnClickListener(this);
		cancelTextView.setOnClickListener(this);
		
		setContentView(dialogView);
		
	}
	
	
	
	@Override
	public void show() {
		super.show();
		WindowManager.LayoutParams lp = this.getWindow().getAttributes();
		int screenWidth = (int)(Utils.getScreenWidth((Activity)context));
		lp.width = screenWidth - screenWidth * 60 / 640;
		lp.gravity = Gravity.CENTER;
		this.getWindow().setAttributes(lp);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.confirm_btn:
			dismiss();
			callBack.confirm();
			break;
		case R.id.cancel_btn:
			dismiss();
			callBack.cancel();
			break;
		default:
			break;
		}
	}
	
}
