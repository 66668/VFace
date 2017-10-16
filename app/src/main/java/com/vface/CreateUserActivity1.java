package com.vface;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.vface.bizmodel.MemberModel;
import com.vface.common.BaseActivity;
import com.vface.common.MyHttpException;
import com.vface.dialog.Loading;
import com.vface.helper.MemberHelper;
import com.vface.helper.UserHelper;
import com.vface.inject.ViewInject;

public class CreateUserActivity1 extends BaseActivity{

    private static final int GET_DATA_END = 9;
    @ViewInject(id = R.id.imgBack, click = "imgBackClick")
	View imgBack;

    @ViewInject(id = R.id.phone_num_edit)
    EditText phoneEdit;

    @ViewInject(id = R.id.submit_btn,click = "submit")
    Button submitBtn;

    private String phoneNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_user1);


	}


	public void imgBackClick(View v) {
		this.finish();
	}

    public void submit(View v){

        Loading.run(this,new Runnable() {
            @Override
            public void run() {

                phoneNum = phoneEdit.getText().toString();
                if(TextUtils.isEmpty(phoneNum.trim())){
                    sendToastMessage("请输入新会员手机号码");
                    return;
                }
                
                if(!isMobileNO(phoneNum)){
                    sendToastMessage("请输入正确的手机号码");
                    return;
                }
                
                final String storeId = UserHelper.getCurrentUser().getStoreId();

                try {
                    MemberModel memberModel = MemberHelper.checkMemberRegister(CreateUserActivity1.this,storeId,phoneNum);

                    sendMessage(GET_DATA_END,memberModel);

                } catch (MyHttpException e) {
                    e.printStackTrace();
                    sendToastMessage(e.getMessage());
                }
            }
        });

    }
    
	/**
	 * 验证手机格式
	 */
	private static boolean isMobileNO(String mobiles) {
		/*
		移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		联通：130、131、132、152、155、156、185、186
		电信：133、153、180、189、（1349卫通）
		总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		*/
		String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (TextUtils.isEmpty(mobiles)) return false;
		else return mobiles.matches(telRegex);
    }

    @Override
    protected void handleMessage(Message msg) {

        switch (msg.what){
            case GET_DATA_END:
                MemberModel memberModel = (MemberModel) msg.obj;
                Intent intent = new Intent(CreateUserActivity1.this,CreateUserActivity.class);
                if(memberModel != null){
                    intent.putExtra("memberModel",memberModel);
                }
                intent.putExtra("phoneNum",phoneNum);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }

        super.handleMessage(msg);
    }
}
