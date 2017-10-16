package com.vface;

import android.os.Bundle;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.igexin.sdk.PushManager;
import com.vface.common.BaseActivity;
import com.vface.common.MyApplication;
import com.vface.common.MyHttpException;
import com.vface.dialog.Loading;
import com.vface.helper.UserHelper;
import com.vface.inject.ViewInject;
import com.vface.utils.ConfigUtil;
import com.vface.utils.PageUtil;

public class LoginActivity extends BaseActivity {
	//登录按钮
	@ViewInject(id = R.id.btnLogin, click = "btnLoginClick")
	public Button btnLogin;
	//商家账号
    @ViewInject(id = R.id.txtShopAccount)
    public EditText txtShopAccount;
    //用户名
	@ViewInject(id = R.id.txtAccount)
	public EditText txtAccount;
	//密码
	@ViewInject(id = R.id.txtPassword)
	public EditText txtPassword;
	//协议
    @ViewInject(id = R.id.provision_text)
    public TextView txtProvision;
    //同意协议
    @ViewInject(id = R.id.ifAgreeProvisionCheckBox)
    public CheckBox ifAgreeProvisionCheckBox;

	private final int LOGIN_SUCESS = 2001; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		//中断保存（只有登录成功才可以获得数据，保存set方法在UserHelper在线登录方法内）
		ConfigUtil configUtil = new ConfigUtil(this);
        txtShopAccount.setText(configUtil.getShopAccount());
		txtAccount.setText(configUtil.getAccount());
        if(txtShopAccount.getText().toString().length()>0){

            if (txtAccount.getText().toString().length()>0) {
                txtPassword.setFocusable(true);
                txtPassword.requestFocus();
                txtPassword.setFocusableInTouchMode(true);
            }else{
                txtAccount.setFocusable(true);
                txtAccount.requestFocus();
                txtAccount.setFocusableInTouchMode(true);
            }
        }
        //调用下边方法：checkBox的监听
        initProvisionTextView();
	}
	//onCreate方法中调用
    private void initProvisionTextView() {
    	//checkBox的监听
        ifAgreeProvisionCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                btnLogin.setEnabled(isChecked);//设置是变灰
            }
        });

        String provisionText = "我已阅读并同意<font color=\"#ffffff\"><a href = 'http://protocols.pss100.com/store.html'>此协议</a></font>";
        txtProvision.setText(Html.fromHtml(provisionText));//html设置协议内容
        txtProvision.setMovementMethod(LinkMovementMethod.getInstance());//实现文本的滚动
    }
    //登录监听
	public void btnLoginClick(View v) {
		if (!checkInput()) {
			return;
		}
		Loading.run(this, new Runnable() { 
			@Override
			public void run() {
				try {
					UserHelper.loginOnline(LoginActivity.this,
                            txtShopAccount.getText().toString(),
                            txtAccount.getText().toString(),
                            txtPassword.getText().toString());
					MyApplication.getInstance().setIsLogin(true);
					sendMessage(LOGIN_SUCESS);
				} catch (MyHttpException e) { 
					e.printStackTrace();
					if(TextUtils.isEmpty(e.getMessage())){
						sendToastMessage("用户名或密码错误!");
					}else {						
						sendToastMessage(e.getMessage());
					}
				}
			}
		});
		
	} 

	@Override
	protected void handleMessage(Message msg) { 
		super.handleMessage(msg);
		switch (msg.what) {
		case LOGIN_SUCESS: 
			this.finish();
			PushManager.getInstance().turnOnPush(this);
			startActivity(MainActivity.class);
			break; 
		default:
			break;
		}
	}

	private boolean checkInput() {
        if (TextUtils.isEmpty(txtShopAccount.getText())) {
            PageUtil.DisplayToast(R.string.please_input_shop_account);
            return false;
        }
		if (TextUtils.isEmpty(txtAccount.getText())) {
			PageUtil.DisplayToast(R.string.please_input_account);
			return false;
		}
		if (TextUtils.isEmpty(txtPassword.getText())) {
			PageUtil.DisplayToast(R.string.please_input_password);
			return false;
		}
		return true;
	}
}
