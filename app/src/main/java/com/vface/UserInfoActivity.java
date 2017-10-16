package com.vface;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.vface.bizmodel.MemberModel;
import com.vface.bizmodel.VisitRecordModel;
import com.vface.common.BaseActivity;
import com.vface.common.ImageLoadingConfig;
import com.vface.common.MyApplication;
import com.vface.common.MyHttpException;
import com.vface.dialog.Loading;
import com.vface.helper.MemberHelper;
import com.vface.inject.ViewInject;

public class UserInfoActivity extends BaseActivity {

	@ViewInject(id = R.id.imgBack, click = "imgBackClick")
	View imgBack;

	@ViewInject(id = R.id.imgHeader)
	ImageView imgHeader;

	@ViewInject(id = R.id.txtUserName)
	TextView txtUserName;

	@ViewInject(id = R.id.txtLastTime)
	TextView txtTime;

    @ViewInject(id = R.id.txtLastTimeLayout)
    LinearLayout txtLastTimeLayout;

	@ViewInject(id = R.id.txtPhoneNum)
	TextView txtPhone;

	@ViewInject(id = R.id.txtId)
	TextView txtId;

	@ViewInject(id = R.id.txtBirthday)
	TextView txtBirthday;

	@ViewInject(id = R.id.txtCity)
	TextView txtCity;

	@ViewInject(id = R.id.txtAddress)
	TextView txtAddress;

	@ViewInject(id = R.id.txtEmail)
	TextView txtEmail;
	
	@ViewInject(id = R.id.txtNickname)
	TextView txtNickname;
	
	@ViewInject(id = R.id.txtSex)
	TextView txtSex;
	 
	@ViewInject(id = R.id.txtIsStealth)
	TextView txtIsStealth;
	
	@ViewInject(id = R.id.txtIsSpecialFocus)
	TextView txtIsSpecialFocus;
	
	@ViewInject(id = R.id.txtRemark)
	TextView txtRemark;

    @ViewInject(id = R.id.guide_btn,click = "goGuideShop")
    TextView guideBtn;

    @ViewInject(id = R.id.consume_history_btn,click = "goConsumeHistory")
    TextView consumeHistoryBtn;
    
    @ViewInject(id = R.id.edit_user_btn,click = "goEditUser")
    TextView editUserBtn;


    @ViewInject(id = R.id.txtGradeName)
    TextView txtGradeName;


    @ViewInject(id = R.id.txtMoney)
    TextView txtMoney;

    @ViewInject(id = R.id.txtPoints)
    TextView txtPoints;

    @ViewInject(id = R.id.txtCardNumber)
    TextView txtMemberCardNumber;

	private ImageLoader imgLoader;
	private DisplayImageOptions imgOption;

	String storeMemberId;
	String visitTime="";
    boolean fromPush = false;
	MemberModel memberModel;
    VisitRecordModel visitRecordModel;
	
	final int INIT_MEMBER=2001;
	public static final int REQUEST_CODE_FOR_EDIT_USER = 2003;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info); 
		imgLoader = ImageLoader.getInstance();
		imgLoader.init(ImageLoaderConfiguration.createDefault(this));
		imgOption = ImageLoadingConfig.generateDisplayImageOptions(R.drawable.photo);

        Bundle bundle = getIntent().getExtras();
        //推送 或者 管理会员
        if(bundle != null){
        	storeMemberId = bundle.getString("memberId");
            visitTime = bundle.getString("visitTime");
            fromPush = bundle.containsKey("from");
        }
        //会员轨迹
        visitRecordModel = (VisitRecordModel) getIntent().getSerializableExtra("visitRecordModel");

        if(visitRecordModel != null){
            visitTime = visitRecordModel.getCreateTime();
        }

		if(visitTime != null){
            txtTime.setText(visitTime);
        }else{
            txtLastTimeLayout.setVisibility(View.GONE);
        }
		
		if(fromPush){
			memberModel = MyApplication.getInstance().ClickMember;
			if(TextUtils.isEmpty(memberModel.getStoreMemberId())){
				sendMessage(INIT_MEMBER, memberModel);
			}else{
				getMemberModelFromNet(memberModel.getStoreMemberId());
			}
			MyApplication.getInstance().ClickMember = null;
		}else if(storeMemberId != null){
			getMemberModelFromNet(storeMemberId);
		}else if(visitRecordModel != null){
            initMemeberView(visitRecordModel);
        }
	}
	
	private void getMemberModelFromNet(final String memberId){
		Loading.run(this, new Runnable() {
			
			@Override
			public void run() {
				try {
					memberModel = MemberHelper.getMember(UserInfoActivity.this,
							memberId);
					sendMessage(INIT_MEMBER, memberModel);
				} catch (MyHttpException e) { 
					e.printStackTrace();
					sendToastMessage(e.getMessage());
				}
			}
		});
	}
	
	
	@Override
	protected void handleMessage(Message msg) { 
		super.handleMessage(msg);
		switch (msg.what) {
		case INIT_MEMBER:
			initMemeberView((MemberModel) msg.obj);
			break;
		default:
			break;
		}
	}



	void initMemeberView(final MemberModel memberModel){
		try {
            boolean ifVfaceMember = false;
            if(TextUtils.isEmpty(memberModel.getStoreMemberId())){//storeMemberId为空，则为vface会员
                ifVfaceMember = true;
            }

			imgLoader.displayImage(memberModel.getPicPath(), imgHeader,imgOption);
            if(ifVfaceMember){
                txtUserName.setText(memberModel.getVFaceFullName()+"(唯脸会员)");
                txtNickname.setText(memberModel.getVFaceNickName());
                txtSex.setText(memberModel.getVFaceSex() ==0 ?"女士":"先生");
                txtBirthday.setText(memberModel.getVFaceBirthday());
                txtPhone.setText("手机号："+getHidePhoneNum(memberModel.getPhoneNumber()));
                guideBtn.setVisibility(View.GONE);//TODO
                consumeHistoryBtn.setVisibility(View.GONE);
            }else{
                txtUserName.setText(memberModel.getFullName());
                txtNickname.setText(memberModel.getNickName());
                txtBirthday.setText(memberModel.getBirthday());
                txtPhone.setAutoLinkMask(Linkify.PHONE_NUMBERS);
                txtPhone.setText("手机号："+memberModel.getPhoneNumber());
                txtSex.setText(memberModel.getSex() ==0 ?"女士":"先生");
                txtMemberCardNumber.setText("卡号："+memberModel.getMemberCardNumber());
                editUserBtn.setVisibility(View.VISIBLE);
                editUserBtn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						//编辑会员信息
				    	Intent intent = new Intent();
				    	intent.setClass(UserInfoActivity.this, EditUserActivity.class);
				    	intent.putExtra("memberModel", memberModel);
				    	startActivityForResult(intent,REQUEST_CODE_FOR_EDIT_USER);
					}
				});
            }
//			txtTime.setText(txtTime.getText() + memberModel.getStrCreateTime());

            txtGradeName.setText("会员等级："+memberModel.getGradeName());
            txtMoney.setText("余额："+memberModel.getTotalMoney()+"元");
            txtPoints.setText("积分："+memberModel.getTotalPoints());
            
			txtId.setText(memberModel.getIdNumber());
			txtCity.setText(memberModel.getCityName()); 
			txtAddress.setText(memberModel.getAddress());
			txtEmail.setText(memberModel.getEmail()); 

			txtIsSpecialFocus.setText(memberModel.isIsSpecialFocus()?  "是" :"否");
			txtIsStealth.setText(memberModel.isIsStealth()?  "是" :"否");
			txtRemark.setText(memberModel.getRemark());
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

    void initMemeberView(VisitRecordModel recordModel){//唯脸会员
        try {
            imgLoader.displayImage(recordModel.getPicPath(), imgHeader,imgOption);
            txtUserName.setText(recordModel.getVFaceFullName()+"(唯脸会员)");
            txtNickname.setText(recordModel.getVFaceNickName());
            txtSex.setText(recordModel.getVFaceSex());
            txtBirthday.setText(recordModel.getVFaceBirthday());
            txtPhone.setText("手机号："+getHidePhoneNum(recordModel.getPhoneNumber()));
            guideBtn.setVisibility(View.GONE);//TODO
            consumeHistoryBtn.setVisibility(View.GONE);
            txtCity.setText(recordModel.getCityName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getHidePhoneNum(String phone){
        return phone.substring(0,3) + "****" + phone.substring(7, phone.length());
    }

	public void imgBackClick(View v) {
		this.finish();
	}

    public void goGuideShop(View v){
        Intent intent = new Intent();
        intent.setClass(this,GuideShopingActivity.class);
        if(memberModel != null)
            intent.putExtra("memberModel",memberModel);
        if(visitRecordModel !=null)
            intent.putExtra("visitRecordModel",visitRecordModel);
        startActivity(intent);
    }

    public void goConsumeHistory(View v){
        Intent intent = new Intent();
        intent.setClass(this,UserConsumeHistory.class);
        intent.putExtra("vfaceMemberCode",memberModel.getVFACEMemberCode());
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(requestCode == REQUEST_CODE_FOR_EDIT_USER){
    		if(resultCode == EditUserActivity.RESULT_CODE_FOR_EDIT_USER_SUCCESS){
    			getMemberModelFromNet(memberModel.getStoreMemberId());
    			setResult(resultCode);
    		}
    	}
    	super.onActivityResult(requestCode, resultCode, data);
    }

}
