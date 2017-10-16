package com.vface;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.vface.common.BaseActivity;
import com.vface.common.MyHttpException;
import com.vface.dialog.Loading;
import com.vface.helper.UserHelper;
import com.vface.inject.ViewInject;

public class ChangePasswordActivity extends BaseActivity {

	@ViewInject(id = R.id.imgBack, click = "imgBackClick")
	View imgBack;

	@ViewInject(id = R.id.btnSave, click = "btnSaveClick")
	View btnSave;

	@ViewInject(id = R.id.txtName)
	TextView txtName;

	@ViewInject(id = R.id.txtNewPwd)
	EditText txtNewPwd;

	@ViewInject(id = R.id.txtNewPwd1)
	EditText txtNewPwd1;

	@ViewInject(id = R.id.txtOldPwd)
	EditText txtOldPwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_pwd);
		txtName.setText(UserHelper.getCurrentUser().getFullname());
	}

	public void btnSaveClick(View v) {
		if (TextUtils.isEmpty(txtOldPwd.getText())
				|| TextUtils.isEmpty(txtNewPwd.getText())
				|| TextUtils.isEmpty(txtNewPwd1.getText())) {
			sendToastMessage("密码不能为空！");
			return;
		}
		if (!txtNewPwd.getText().toString()
				.equals(txtNewPwd1.getText().toString())) {
			sendToastMessage("两次输入的新密码不一致！");
			return;
		}
		Loading.run(this, new Runnable() {
			@Override
			public void run() {
				try {
					String msg = UserHelper
							.changePassword(ChangePasswordActivity.this,
									txtOldPwd.getText().toString(), txtNewPwd
											.getText().toString());
					ChangePasswordActivity.this.sendToastMessage(msg);
					ChangePasswordActivity.this.finish();
				} catch (MyHttpException e) {
					e.printStackTrace();
					ChangePasswordActivity.this.sendToastMessage(e.getMessage());
				}
			}
		});
		// this.finish();
	}

	public void imgBackClick(View v) {
		this.finish();
	}

}
